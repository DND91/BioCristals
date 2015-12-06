package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.data.BiomeKittehData;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class BiomeRegistry {
	
	public static HashMap<BiomeDictionary.Type, BiomeKittehData> kittehsBiomes = new HashMap<BiomeDictionary.Type, BiomeKittehData>();
	
	public void init(FMLInitializationEvent event) {
		
		kittehsBiomes.put(BiomeDictionary.Type.BEACH, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.clayHunter)
				.add(1, ItemRegistry.crootClaw)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.FOREST, (new BiomeKittehData())
				.add(10, ItemRegistry.crootBeetle)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(10, ItemRegistry.kraKenBug)
				.add(1, ItemRegistry.hivebag)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.SNOWY, (new BiomeKittehData())
				.add(1, ItemRegistry.crootClaw)
				.add(10, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.OCEAN, (new BiomeKittehData())
				.add(10, ItemRegistry.clayHunter)
				.add(5, ItemRegistry.chitinPlate)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.WATER, (new BiomeKittehData())
				.add(10, ItemRegistry.clayHunter)
				.add(5, ItemRegistry.chitinPlate)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.SANDY, (new BiomeKittehData())
				.add(10, ItemRegistry.crootClaw)
				.add(5, ItemRegistry.kittehBeetle)
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.MESA, (new BiomeKittehData())
				.add(10, ItemRegistry.crootClaw)
				.add(1, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.HILLS, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(5, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.RIVER, (new BiomeKittehData())
				.add(8, ItemRegistry.clayHunter)
				.add(2, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.NETHER, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.WASTELAND, (new BiomeKittehData())
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.MOUNTAIN, (new BiomeKittehData())
				.add(1, ItemRegistry.crootClaw)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.MUSHROOM, (new BiomeKittehData())
				.add(1, ItemRegistry.kittehBeetle)
				.add(1, ItemRegistry.clayHunter)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.PLAINS, (new BiomeKittehData())
				.add(10, ItemRegistry.crootBeetle)
				.add(10, ItemRegistry.wsb)
				.add(10, (ItemStack)null));
		kittehsBiomes.put(BiomeDictionary.Type.JUNGLE, (new BiomeKittehData())
				.add(10, ItemRegistry.kittehBeetle)
				.add(5, ItemRegistry.wsb)
				.add(5, ItemRegistry.hivebag)
				.add(10, ItemRegistry.crootBeetle)
				.add(10, (ItemStack)null));
		
		kittehsBiomes.put(BiomeDictionary.Type.END, (new BiomeKittehData())
				.add(10, (ItemStack)null));
		
		kittehsBiomes.put(BiomeDictionary.Type.MAGICAL, (new BiomeKittehData())
				.add(10, (ItemStack)null));
		
	}
	
	
	
}
