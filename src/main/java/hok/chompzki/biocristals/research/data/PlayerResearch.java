package hok.chompzki.biocristals.research.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class PlayerResearch implements Serializable {
	
	private UUID ownerId = null;
	private String username = null;
	
	private ArrayList<String> completed = new ArrayList<String>();
	private ArrayList<String> headCompleted = new ArrayList<String>();
	private ArrayList<String> favorised = new ArrayList<String>();
	
	
	transient private PlayerStorage currentStorage = null;
	
	public PlayerResearch(UUID id) {
		ownerId = id;
		
	}
	
	public void setStorage(PlayerStorage stor){
		currentStorage = stor;
	}
	
	public boolean hasCompleted(String code) {
		return completed.contains(code);
	}
	
	public void addCompleted(String code) {
		this.completed.add(code);
		if(currentStorage != null)
			currentStorage.activate(this);
	}
	
	public boolean hasFaved(String code) {
		return favorised.contains(code);
	}
	
	public void addFaved(String code) {
		this.favorised.add(code);
		if(currentStorage != null)
			currentStorage.activate(this);
	}
	
	public void removeFaved(String code) {
		this.favorised.remove(code);
		if(currentStorage != null)
			currentStorage.activate(this);
	}
	
	public void addHeadCompleted(String code) {
		this.headCompleted.add(code);
		this.completed.add(code);
		if(currentStorage != null)
			currentStorage.activate(this);
	}
	
	public boolean isHead(String code) {
		return headCompleted.contains(code);
	}
	

	public UUID getOwnerId() {
		return ownerId;
	}
	public String getUsername(World world) {
		if(username == null)
			username = "UNKOWN";

		if(world != null && world.func_152378_a(ownerId) != null && !username.equals(world.func_152378_a(ownerId).getGameProfile().getName())){
			username = world.func_152378_a(ownerId).getGameProfile().getName();
			if(currentStorage != null)
				currentStorage.activate(this);
		}
		
		return username;
	}
	
	public List<String> getCompleted(){
		List<String> list = new ArrayList<String>();
		list.addAll(completed);
		list.addAll(headCompleted);
		return list;
	}

	public List<String> getFaved() {
		return this.favorised;
	}

	public List<String> notIn(PlayerResearch research) {
		List<String> diff = new ArrayList<String>();
		
		for(String code : this.getCompleted()){
			if(!research.hasCompleted(code)){
				diff.add(code);
			}
		}
		
		return diff;
	}
	
	
	
}
