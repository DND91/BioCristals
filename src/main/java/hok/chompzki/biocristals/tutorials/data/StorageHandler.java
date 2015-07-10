package hok.chompzki.biocristals.tutorials.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class StorageHandler {
	
	private static String currentWorld = null;
	public static HashMap<UUID, DataPlayerProgression> data = new HashMap<UUID, DataPlayerProgression>();
	
	public static DataPlayerProgression getPlayerBioData(UUID id){
		if(!data.containsKey(id)){
			data.put(id, new DataPlayerProgression(id));
		}
		return data.get(id);
	}
	
	@SubscribeEvent
	public void load(WorldEvent.Load event){
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		if(!levelName.equals("MpServer") && !levelName.equals(currentWorld)){
			this.controllSetup(event.world);
			
			System.out.println(" --- LOAD --- ");
			System.out.println("LEVEL NAME: " + levelName);
			System.out.println("DIMENSION NAME: " + dimenstionName);
			System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectoryName());
			
			currentWorld = levelName;
			load(event.world);
		}
		
		
	}
	
	private void load(World world){
		String dict = world.getSaveHandler().getWorldDirectoryName();
		File playerSave = new File("saves\\" + dict + "\\BioCristals\\playerBio.dat");
		ObjectInputStream inputStream;
		try {
			inputStream = new ObjectInputStream(new FileInputStream (playerSave));
	        data = (HashMap<UUID, DataPlayerProgression>)inputStream.readObject();
	        inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void save(WorldEvent.Save event){
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		
		if(!levelName.equals("MpServer") && levelName.equals(currentWorld) && dimenstionName.equals("Overworld")){
			this.controllSetup(event.world);
			System.out.println(" --- SAVE --- ");
			System.out.println("LEVEL NAME: " + levelName);
			System.out.println("DIMENSION NAME: " + dimenstionName);
			System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectoryName());
			save(event.world);
		}
		
	}
	
	private void save(World world){
		String dict = world.getSaveHandler().getWorldDirectoryName();
		try {
			File playerSave = new File("saves\\" + dict + "\\BioCristals\\playerBio.dat");
			
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream (playerSave));
            outputStream.writeObject(data);
            outputStream.close( );
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void unload(WorldEvent.Unload event){
		String dict = event.world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		this.controllSetup(event.world);
		String levelName = event.world.getWorldInfo().getWorldName();
		String dimenstionName = event.world.provider.getDimensionName();
		
		if(!levelName.equals("MpServer") && levelName.equals(currentWorld) && dimenstionName.equals("Overworld")){
			System.out.println(" --- CLEAR --- ");
			System.out.println("LEVEL NAME: " + levelName);
			System.out.println("DIMENSION NAME: " + dimenstionName);
			System.out.println("WORLD DIRECTORY NAME: " + event.world.getSaveHandler().getWorldDirectoryName());
			save(event.world);
			currentWorld = null;
			data.clear();
		}
		
	}
	
	protected void controllSetup(World world){
		String dict = world.getSaveHandler().getWorldDirectoryName();
		if(dict.equals("none")){
			return;
		}
		File workdict = new File("saves\\" + dict + "\\BioCristals\\");
		if(!workdict.exists()){
			workdict.mkdir();
		}
		try {
			File playerSave = new File("saves\\" + dict + "\\BioCristals\\playerBio.dat");
			if(!playerSave.exists()){
				playerSave.createNewFile();
			
				HashMap<String, DataPlayerProgression> dataProgression = new HashMap<String, DataPlayerProgression>();
				
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream (playerSave));
	            outputStream.writeObject(dataProgression);
	            outputStream.close( );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
