package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.registrys.ReserchRegistry;

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
	private ArrayList<String> favorised = new ArrayList<String>();
	
	
	transient private PlayerResearchStorage currentStorage = null;
	
	public PlayerResearch(UUID id) {
		ownerId = id;
		completed.add(ReserchRegistry.tutorial);
	}
	
	public void setStorage(PlayerResearchStorage stor){
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
	

	public UUID getOwnerId() {
		return ownerId;
	}
	public String getUsername() {
		return username;
	}
	
	public List<String> getCompleted(){
		List<String> list = new ArrayList<String>();
		for(String s : completed)
			list.add(s);
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
	
	public void print(){
		System.out.println("OWNER: " + getOwnerId());
		System.out.println("USERNAME: " + getUsername());
		System.out.print("FAVED: " + getFaved().size() + "; ");
		for(String s : getFaved())
			System.out.print(s + ", ");
		System.out.println();
		System.out.print("COMPLETED: " + getCompleted().size() + "; ");
		for(String s : getCompleted())
			System.out.print(s + ", ");
		System.out.println();
		
	}

	public void setUsername(String name) {
		this.username = name;
	}
	
}
