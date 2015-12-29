package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.StorageHandler;
import hok.chompzki.biocristals.api.IDataFile;
import hok.chompzki.biocristals.client.gui.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.network.MessageHandlerInserCrafting;
import hok.chompzki.biocristals.research.data.network.MessageInsertCrafting;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageSyncHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageSyncMessage;
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
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PlayerResearchStorage implements IDataFile{

	private static PlayerResearchStorage clientStorage = null;
	private static PlayerResearchStorage serverStorage = null;
	
	public static PlayerResearchStorage instance(boolean client){
		if(clientStorage == null && client){
			clientStorage = new PlayerResearchStorage("RESEARCH CLIENT", false);
		}
		if(serverStorage == null && !client){
			serverStorage = new PlayerResearchStorage("RESEARCH SERVER", true);
		}
		
		return client ? clientStorage : serverStorage;
	}
	
	protected HashMap<UUID, PlayerResearch> players =  new HashMap<UUID, PlayerResearch>();
	protected HashMap<UUID, List<UUID>> lissensOn = new HashMap<UUID, List<UUID>>();
	protected String name = "SOTIS";
	
	protected PlayerResearchStorage(String name, boolean saveHandler){
		this.name = name;
		if(saveHandler)
			StorageHandler.register(this);
	}
	
	public PlayerResearch get(UUID id){
		return players.get(id);
	}
	
	public void put(UUID id, PlayerResearch res){
		players.put(id, res);
		res.setStorage(this);
	}
	
	public Collection<PlayerResearch> getAllCurrent(){
		return players.values();
	}
	
	@Override
	public String getFile() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		return side + "playerResearch.dat";
	}

	@Override
	public Serializable getObject() {
		System.out.println(name + " -- SAVING PLAYER DATA -- ");
		for(Entry<UUID, PlayerResearch> entry : players.entrySet()){
			UUID id = entry.getKey();
			PlayerResearch res = entry.getValue();
			res.setStorage(this);
		}
		return players;
	}
	
	@Override
	public void setObject(Serializable obj) {
		System.out.println(name + " -- LOADING PLAYER DATA -- ");
		players = (HashMap<UUID, PlayerResearch>) obj;
		for(Entry<UUID, PlayerResearch> entry : players.entrySet()){
			UUID id = entry.getKey();
			PlayerResearch res = entry.getValue();
			res.setStorage(this);
		}
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
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT)
			return;
		UUID subject = player.getOwnerId();
		for(Entry<UUID, List<UUID>> entry : lissensOn.entrySet()){
			UUID observer = entry.getKey();
			EntityPlayerMP obs = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(observer);
			List<UUID> subjects = entry.getValue();
			for(UUID s : subjects)
				if(s.compareTo(subject) == 0)
					BioCristalsMod.network.sendTo(new PlayerStorageSyncMessage(player), obs);
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
		UUID id = event.player.getGameProfile().getId();
		System.out.println("LOG IN!!! ");
		if(!this.players.containsKey(id)){
			EntityPlayerMP player = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(id);
			player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.researchBook));
			this.put(id, new PlayerResearch(id));
			this.get(id).setUsername(event.player.getCommandSenderName());
		}
		registerLissner(id, id);
	}
	
	public void registerLissner(UUID observer, UUID subject) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT)
			return;
		
		if(!lissensOn.containsKey(observer))
			lissensOn.put(observer, new ArrayList<UUID>());
		List<UUID> list = lissensOn.get(observer);
		if(list.contains(subject))
			return;
		list.add(subject);
		PlayerResearch sub = this.get(subject);
		EntityPlayerMP player = (EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().func_152378_a(observer);
		BioCristalsMod.network.sendTo(new PlayerStorageSyncMessage(sub), player);
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
		//for(List<UUID> list : lissensOn.values())
		//	list.remove(id);
	}
	
	public void printLissensOn(UUID id){
		PlayerResearch res = this.get(id);
		System.out.println("OBSERVER: " + res.getUsername());
		List<UUID> subjects = lissensOn.get(id);
		System.out.print("SUBJECTS: ");
		for(UUID sid : subjects){
			PlayerResearch subject = this.get(sid);
			System.out.print(subject.getUsername() + ", ");
		}
		System.out.println();
	}
}
