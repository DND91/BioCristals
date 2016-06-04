package hok.chompzki.hivetera.hunger;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.CommonProxy;
import hok.chompzki.hivetera.StorageHandler;
import hok.chompzki.hivetera.api.IDataFile;
import hok.chompzki.hivetera.client.gui.GuiInventoryOverlay;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.hunger.network.PlayerHungerSyncMessage;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.network.MessageHandlerInserCrafting;
import hok.chompzki.hivetera.research.data.network.MessageInsertCrafting;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenHandler;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveHandler;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncHandler;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncMessage;
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

public class PlayerHungerStorage implements IDataFile{
	
	private static PlayerHungerStorage clientStorage = null;
	private static PlayerHungerStorage serverStorage = null;
	
	public static PlayerHungerStorage instance(boolean client){
		if(clientStorage == null && client){
			clientStorage = new PlayerHungerStorage("HUNGER CLIENT", false);
		}
		if(serverStorage == null && !client){
			serverStorage = new PlayerHungerStorage("HUNGER SERVER", true);
		}
		
		return !client ? serverStorage : clientStorage;
	}
	
	protected HashMap<String, PlayerHungerNetwork> networks =  new HashMap<String, PlayerHungerNetwork>();
	protected HashMap<UUID, List<String>> lissensOn = new HashMap<UUID, List<String>>();
	protected String name = "SOTIS";
	
	protected PlayerHungerStorage(String name, boolean saveHandler){
		this.name = name;
		if(saveHandler)
			StorageHandler.register(this);
	}
	
	public PlayerHungerNetwork get(String id){
		return networks.get(id);
	}
	
	public void put(String id, PlayerHungerNetwork res){
		networks.put(id, res);
		res.setStorage(this);
	}
	
	public Collection<PlayerHungerNetwork> getAllCurrent(){
		return networks.values();
	}
	
	@Override
	public String getFile() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		return side + "playerHunger.dat";
	}

	@Override
	public Serializable getObject() {
		//System.out.println(name + " -- SAVING PLAYER DATA -- ");
		ArrayList<PlayerHungerNetworkData> list = new ArrayList<PlayerHungerNetworkData>();
		for(Entry<String, PlayerHungerNetwork> entry : networks.entrySet()){
			list.add(entry.getValue().toData());
		}
		return list;
	}
	
	@Override
	public void setObject(Serializable obj) {
		//System.out.println(name + " -- LOADING PLAYER DATA -- ");
		ArrayList<PlayerHungerNetworkData> list = (ArrayList<PlayerHungerNetworkData>)obj;
		networks.clear();
		
		for(PlayerHungerNetworkData data : list){
			PlayerHungerNetwork res = data.toForm();
			res.setStorage(this);
			res.updateHunger();
			this.networks.put(res.getName(), res);
		}
	}
	
	@Override
	public Serializable getEmtptyObject() {
		return new ArrayList<PlayerHungerNetworkData>();
	}

	@Override
	public void clear() {
		networks.clear();
		lissensOn.clear();
	}
	
	public void activate(PlayerHungerNetwork player){
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT)
			return;
		String subject = player.getName();
		for(Entry<UUID, List<String>> entry : lissensOn.entrySet()){
			UUID observer = entry.getKey();
			EntityPlayerMP obs = CommonProxy.getPlayer(observer);
			List<String> subjects = entry.getValue();
			if(subjects.contains(subject))
					HiveteraMod.network.sendTo(new PlayerHungerSyncMessage(player), obs);
		}
	}
	
	@SubscribeEvent
	public void playerLogout(PlayerLoggedOutEvent event){
		UUID id = event.player.getGameProfile().getId();
		deregisterLissnar(id);
	}
	
	@SubscribeEvent
	public void playerLogin(PlayerLoggedInEvent event){
		String id = event.player.getCommandSenderName();
		if(!this.networks.containsKey(id)){
			this.put(id, new PlayerHungerNetwork(id));
			this.get(id).setName(id);
		}
		registerLissner(event.player.getGameProfile().getId(), id);
	}
	
	public void registerLissner(UUID observer, String subject) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT)
			return;
		
		if(!lissensOn.containsKey(observer))
			lissensOn.put(observer, new ArrayList<String>());
		List<String> list = lissensOn.get(observer);
		if(list.contains(subject))
			return;
		list.add(subject);
		PlayerHungerNetwork sub = this.get(subject);
		EntityPlayerMP player = CommonProxy.getPlayer(observer);
		
		HiveteraMod.network.sendTo(new PlayerHungerSyncMessage(sub), player);
	}
	
	public void deregisterLissner(UUID observer, String subject){
		if(!lissensOn.containsKey(observer)){
			lissensOn.put(observer, new ArrayList<String>());
			return;
		}
		List<String> list = lissensOn.get(observer);
		list.remove(subject);
	}
	
	public void deregisterLissnar(UUID id){
		lissensOn.remove(id);
	}
	
	// Junction logic
	public void feed(String id, String channel, ResourcePackage pack) {
		PlayerHungerNetwork network = this.get(id);
		network.feed(channel, pack);
	}
	
	public ResourcePackage drain(String id, String channel, Double amount) {
		PlayerHungerNetwork network = this.get(id);
		return network.drain(channel, amount);
	}
	
	public boolean containsKey(String id){
		return this.networks.containsKey(id);
	}

	public boolean canFeed(String id, String channel, ResourcePackage pack) {
		PlayerHungerNetwork network = this.get(id);
		return network.canFeed(channel, pack);
	}
}
