package hok.chompzki.hivetera;

import hok.chompzki.hivetera.api.IDataFile;
import hok.chompzki.hivetera.registrys.GuiHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class StorageHandler {
	
	// ASSUMPTION IS THAT OVERWORLD IS NEVER UNLOADED WHILE A WORLD IS ACTIVE!
	
	private static String currentWorld = null;
	
	private static ArrayList<IDataFile> dataFiles = new ArrayList<IDataFile>();
	
	public static void register(IDataFile file){
		dataFiles.add(file);
	}
	
	@SubscribeEvent
	public void load(WorldEvent.Load event){
		if(event.world.isRemote)
			return;
		
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		if(!levelName.equals("MpServer") && !levelName.equals(currentWorld) && dimenstionName.equals("Overworld")){
			this.controllSetup(event.world);
			
			for(IDataFile dataFile : dataFiles){
				dataFile.clear();
			}
			
			//System.out.println(" --- LOAD --- ");
			//System.out.println("LEVEL NAME: " + levelName);
			//System.out.println("DIMENSION NAME: " + dimenstionName);
			//System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectory().getPath());
			
			currentWorld = levelName;
			load(event.world);
		}
		
		
	}
	
	private void load(World world){
		for(IDataFile dataFile : dataFiles){
			File file = new File(getLocalPath(world) + dataFile.getFile());
			ObjectInputStream inputStream;
			try {
				inputStream = new ObjectInputStream(new FileInputStream (file));
				dataFile.setObject((Serializable) inputStream.readObject());
		        inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent
	public void save(WorldEvent.Save event){
		if(event.world.isRemote)
			return;
		
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		
		if(!levelName.equals("MpServer") && levelName.equals(currentWorld) && dimenstionName.equals("Overworld")){
			this.controllSetup(event.world);
			//System.out.println(" --- SAVE --- ");
			//System.out.println("LEVEL NAME: " + levelName);
			//System.out.println("DIMENSION NAME: " + dimenstionName);
			//System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectory().getPath());
			save(event.world);
		}
		
	}
	
	private void save(World world){
		
		for(IDataFile dataFile : dataFiles){
			try {
				File file = new File(getLocalPath(world) + dataFile.getFile());
				
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream (file));
	            outputStream.writeObject(dataFile.getObject());
	            outputStream.close( );
	            
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent
	public void unload(WorldEvent.Unload event){
		GuiHandler.book = null;
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		this.controllSetup(event.world);
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		
		if(!levelName.equals("MpServer") && levelName.equals(currentWorld) && dimenstionName.equals("Overworld")){
			//System.out.println(" --- CLEAR --- ");
			//System.out.println("LEVEL NAME: " + levelName);
			//System.out.println("DIMENSION NAME: " + dimenstionName);
			//System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectory().getPath());
			save(event.world);
			currentWorld = null;
			for(IDataFile dataFile : dataFiles){
				dataFile.clear();
			}
		}
		
	}
	
	protected void controllSetup(World world){
		String dict = world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		
		File workdict = new File(getLocalPath(world));
		if(!workdict.exists()){
			workdict.mkdir();
		}
		
		for(IDataFile dataFile : dataFiles){
			try {
				File file = new File(getLocalPath(world) + dataFile.getFile());
				if(!file.exists()){
					file.createNewFile();
				
					Serializable data = dataFile.getEmtptyObject();
					
					ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream (file));
		            outputStream.writeObject(data);
		            outputStream.close( );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	private String getLocalPath(World world){
		String dict = world.getSaveHandler().getWorldDirectory().getPath();
		if(!MinecraftServer.getServer().isDedicatedServer()){
			return dict + File.separator + "BioCristals" + File.separator;
		}else{
			return dict + File.separator + "BioCristals" + File.separator;
		}
	}
}
