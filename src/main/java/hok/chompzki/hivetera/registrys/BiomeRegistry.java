package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.data.BiomeKittehData;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class BiomeRegistry {
	
	public static LinkedHashMap<Integer, BiomeDictionary.Type> idToBiome = new LinkedHashMap<Integer, BiomeDictionary.Type>();
	public static LinkedHashMap<BiomeDictionary.Type, Integer> biomeToId = new LinkedHashMap<BiomeDictionary.Type, Integer>();
	
	public static void registerID(int id, BiomeDictionary.Type type, BiomeKittehData data){
		idToBiome.put(id, type);
		biomeToId.put(type, id);
		kittehsBiomes.put(type, data);
	}
	
	public static HashMap<BiomeDictionary.Type, BiomeKittehData> kittehsBiomes = new HashMap<BiomeDictionary.Type, BiomeKittehData>();
	
	public void init(FMLPreInitializationEvent event) {
		registerID(0, BiomeDictionary.Type.BEACH, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.clayHunter)
				.add(1, ItemRegistry.crootClaw)
				.add(10, (ItemStack)null));
		
		registerID(1, BiomeDictionary.Type.FOREST, (new BiomeKittehData())
				.add(10, ItemRegistry.crootBeetle)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(10, ItemRegistry.kraKenBug)
				.add(1, ItemRegistry.hivebag)
				.add(10, (ItemStack)null));
		
		registerID(2, BiomeDictionary.Type.SNOWY, (new BiomeKittehData())
				.add(1, ItemRegistry.crootClaw)
				.add(10, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		
		registerID(3, BiomeDictionary.Type.OCEAN, (new BiomeKittehData())
				.add(10, ItemRegistry.clayHunter)
				.add(5, ItemRegistry.chitinPlate)
				.add(10, (ItemStack)null));
		
		registerID(4, BiomeDictionary.Type.WATER, (new BiomeKittehData())
				.add(10, ItemRegistry.clayHunter)
				.add(5, ItemRegistry.chitinPlate)
				.add(10, (ItemStack)null));
		
		registerID(5, BiomeDictionary.Type.SANDY, (new BiomeKittehData())
				.add(10, ItemRegistry.crootClaw)
				.add(5, ItemRegistry.kittehBeetle)
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(6, BiomeDictionary.Type.MESA, (new BiomeKittehData())
				.add(10, ItemRegistry.crootClaw)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(7, BiomeDictionary.Type.HILLS, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(5, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		
		registerID(8, BiomeDictionary.Type.RIVER, (new BiomeKittehData())
				.add(8, ItemRegistry.clayHunter)
				.add(2, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(9, BiomeDictionary.Type.NETHER, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(10, BiomeDictionary.Type.WASTELAND, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(11, BiomeDictionary.Type.MOUNTAIN, (new BiomeKittehData())
				.add(1, ItemRegistry.crootClaw)
				.add(10, (ItemStack)null));
		
		registerID(12, BiomeDictionary.Type.MUSHROOM, (new BiomeKittehData())
				.add(1, ItemRegistry.kittehBeetle)
				.add(1, ItemRegistry.clayHunter)
				.add(10, (ItemStack)null));
		
		registerID(13, BiomeDictionary.Type.PLAINS, (new BiomeKittehData())
				.add(10, ItemRegistry.crootBeetle)
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		
		registerID(14, BiomeDictionary.Type.JUNGLE, (new BiomeKittehData())
				.add(10, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(5, ItemRegistry.hivebag)
				.add(10, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		
		registerID(15, BiomeDictionary.Type.END, (new BiomeKittehData())
				.add(10, (ItemStack)null));
		
		registerID(16, BiomeDictionary.Type.MAGICAL, (new BiomeKittehData())
				.add(10, (ItemStack)null));
		
	}
	
	
	
}
