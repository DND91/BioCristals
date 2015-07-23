package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageSyncMessage;
import hok.chompzki.biocristals.research.events.MessageHandlerInserCrafting;
import hok.chompzki.biocristals.research.events.MessageInsertCrafting;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PlayerStorage implements IDataFile, IMessageHandler<PlayerStorageSyncMessage, IMessage> {
	//TODO: Insert instance() somewhere in the game loading... so that it dosn't happen first time in world
	protected static PlayerStorage storage = null;
	
	public static PlayerStorage instance(){
		if(storage == null){
			storage = new PlayerStorage();
		}
		return storage;
	}
	
	protected HashMap<UUID, PlayerResearch> players =  new HashMap<UUID, PlayerResearch>();
	protected HashMap<UUID, List<UUID>> lissensOn = new HashMap<UUID, List<UUID>>();
	protected SimpleNetworkWrapper network;
	
	protected PlayerStorage(){
		StorageHandler.register(this);
		network = NetworkRegistry.INSTANCE.newSimpleChannel("BioC_PS");
	    network.registerMessage(this, PlayerStorageSyncMessage.class, 0, Side.CLIENT);
	    network.registerMessage(PlayerStorageDelissenHandler.class, PlayerStorageDelissenMessage.class, 1, Side.SERVER);
	    network.registerMessage(MessageHandlerInserCrafting.class, MessageInsertCrafting.class, 2, Side.SERVER);
	    network.registerMessage(PlayerStorageFaveHandler.class, PlayerStorageFaveMessage.class, 3, Side.SERVER);
	}
	
	public PlayerResearch get(UUID id){
		if(!players.containsKey(id)){
			players.put(id, new PlayerResearch(id));
		}
		players.get(id).setStorage(this);
		return players.get(id);
	}
	
	public Collection<PlayerResearch> getAllCurrent(){
		return players.values();
	}
	
	public SimpleNetworkWrapper getNetwork(){
		return network;
	}
	
	@Override
	public String getFile() {
		return "playerResearch.dat";
	}

	@Override
	public Serializable getObject() {
		return players;
	}
	
	public void sendToServer(IMessage message){
		network.sendToServer(message);
	}
	
	@Override
	public void setObject(Serializable obj) {
		players = (HashMap<UUID, PlayerResearch>) obj;
		for(PlayerResearch res : players.values())
			res.setStorage(this);
	}

	@Override
	public Serializable getEmtptyObject() {
		return new HashMap<UUID, PlayerResearch>();
	}

	@Override
	public void clear() {
		players.clear();
		lissensOn.clear();
	}
	
	public void activate(PlayerResearch player){
		System.out.println("ACTIVATE!!!!");
		UUID subject = player.getOwnerId();
		for(Entry<UUID, List<UUID>> entry : lissensOn.entrySet()){
			UUID observer = entry.getKey();
			EntityPlayerMP obs = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(observer);
			List<UUID> subjects = entry.getValue();
			for(UUID s : subjects)
				if(s.compareTo(subject) == 0)
					network.sendTo(new PlayerStorageSyncMessage(player), obs);
		}
	}
	
	@SubscribeEvent
	public void playerLogout(PlayerLoggedOutEvent event){
		System.out.println("LOG OFF!!!");
		UUID id = event.player.getGameProfile().getId();
		deregisterLissnar(id);
	}
	
	@SubscribeEvent
	public void playerLogin(PlayerLoggedInEvent event){
		System.out.println("LOG IN!!!");
		UUID id = event.player.getGameProfile().getId();
		if(!this.players.containsKey(id)){
			EntityPlayerMP player = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(id);
			player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.researchBook));
			this.get(id);
		}
		registerLissner(id, id);
	}
	
	public void registerLissner(UUID observer, UUID subject) {
		if(!lissensOn.containsKey(observer))
			lissensOn.put(observer, new ArrayList<UUID>());
		List<UUID> list = lissensOn.get(observer);
		if(list.contains(subject))
			return;
		list.add(subject);
		PlayerResearch sub = this.get(subject);
		EntityPlayerMP player = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(observer);
		network.sendTo(new PlayerStorageSyncMessage(sub), player);
	}
	
	public void deregisterLissner(UUID observer, UUID subject){
		if(!lissensOn.containsKey(observer)){
			lissensOn.put(observer, new ArrayList<UUID>());
			return;
		}
		List<UUID> list = lissensOn.get(observer);
		list.remove(subject);
	}
	
	public void deregisterLissnar(UUID id){
		lissensOn.remove(id);
		for(List<UUID> list : lissensOn.values())
			list.remove(id);
	}
	
	@Override
	public IMessage onMessage(PlayerStorageSyncMessage message, MessageContext ctx) {
		PlayerResearch research = message.getResearch();
		if(research == null)
			return null;
		UUID id = research.getOwnerId();
		this.players.put(id, research);
		
		if(Minecraft.getMinecraft().thePlayer.getGameProfile().getId().equals(id)){
			GuiInventoryOverlay.craftingHelper.clear();
			for(String code : research.getFaved()){
				if(ReserchDataNetwork.instance().getResearch(code) == null)
					continue;
				ArticleContent content = ReserchDataNetwork.instance().getResearch(code).getContent();
				GuiInventoryOverlay.craftingHelper.add(content.getFaved());
			}
		}
		
		return null;
	}
	
	public void printLissensOn(UUID id){
		PlayerResearch res = this.get(id);
		System.out.println("OBSERVER: " + res.getUsername(null));
		List<UUID> subjects = lissensOn.get(id);
		System.out.print("SUBJECTS: ");
		for(UUID sid : subjects){
			PlayerResearch subject = this.get(sid);
			System.out.print(subject.getUsername(null) + ", ");
		}
		System.out.println();
	}
}
