package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.recipes.BreedingRecipe;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class BreedingRegistry {
	
	public static ArrayList<BreedingRecipe> list = new ArrayList<BreedingRecipe>();
	
	//Input 0,1: Insects
	//Input   2: Nesting material
	//Input   3: Food -> foodLevel * foodSaturationLevel* 2.0F increase in process
	//Output  4: Result
	
	public static void register(IInsect pappa, IInsect mamma, ItemStack nestMaterial, int foodNeed, ItemStack result){
		BreedingRecipe breed = new BreedingRecipe();
		breed.pappa = pappa;
		breed.mamma = mamma;
		breed.nestMaterial = nestMaterial;
		breed.foodNeed = foodNeed;
		breed.result = result;
		list.add(breed);
	}
	
	public static BreedingRecipe get(IInsect pappa, IInsect mamma, ItemStack material){
		for(BreedingRecipe rep : list){
			if(((pappa == rep.pappa && mamma == rep.mamma) || (mamma == rep.pappa && pappa == rep.mamma)) && OreDictionary.itemMatches(rep.nestMaterial, material, false))
				return rep;
		}
		
		return null;
	}
	
	public static BreedingRecipe get(IInsect pappa, IInsect mamma){
		for(BreedingRecipe rep : list){
			if(((pappa == rep.pappa && mamma == rep.mamma) || (mamma == rep.pappa && pappa == rep.mamma)))
				return rep;
		}
		
		return null;
	}
	
	public static BreedingRecipe get(ItemStack result){
		for(BreedingRecipe rep : list){
			if(OreDictionary.itemMatches(rep.result, result, true))
				return rep;
		}
		
		return null;
	}
	
	public void init(FMLInitializationEvent event) {
		register((IInsect)ItemRegistry.crootBeetle, (IInsect)ItemRegistry.crootBeetle, new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE), 100, new ItemStack(ItemRegistry.crootBeetle, 1));
		register((IInsect)ItemRegistry.crootClaw, (IInsect)ItemRegistry.crootClaw, new ItemStack(Blocks.cactus, 1, OreDictionary.WILDCARD_VALUE), 200, new ItemStack(ItemRegistry.crootClaw, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE), 50, new ItemStack(ItemRegistry.kraKenBug, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(Blocks.log, 1, OreDictionary.WILDCARD_VALUE), 300, new ItemStack(ItemRegistry.wsb, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(Blocks.log2, 1, OreDictionary.WILDCARD_VALUE), 300, new ItemStack(ItemRegistry.wsb, 1));
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(Items.melon, 1, OreDictionary.WILDCARD_VALUE), 100, new ItemStack(ItemRegistry.kittehBeetle, 1));
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(Items.clay_ball, 1, OreDictionary.WILDCARD_VALUE), 200, new ItemStack(ItemRegistry.clayHunter, 1));
		register((IInsect)ItemRegistry.hivebag, (IInsect)ItemRegistry.hivebag, new ItemStack(Items.reeds, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.hivebag, 1));
		register((IInsect)ItemRegistry.fruitSpider, (IInsect)ItemRegistry.fruitSpider, new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE), 50, new ItemStack(ItemRegistry.fruitSpider, 1));
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE), 100, new ItemStack(ItemRegistry.fruitSpider, 1));
		register((IInsect)ItemRegistry.voidCrawler, (IInsect)ItemRegistry.voidCrawler, new ItemStack(Items.ender_pearl, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.voidCrawler, 1));
		register((IInsect)ItemRegistry.crootClaw, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Items.ender_pearl, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.voidCrawler, 1));
		
		
		int helmetCost = 1000;
		int chestCost = 4000;
		int leggingsCost = 2000;
		int bootsCost = 1000;
		
		//ARMORS RED
		Item item = ItemRegistry.chitinHelmet;
		ItemStack armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(item), helmetCost, armor);
		item = ItemRegistry.chitinChestplate;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(item), chestCost, armor);
		item = ItemRegistry.chitinLeggings;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(item), leggingsCost, armor);
		item = ItemRegistry.chitinBoots;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(item), bootsCost, armor);
		
		//ARMORS GREEN
		item = ItemRegistry.chitinHelmet;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[2]);
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(item), helmetCost, armor);
		item = ItemRegistry.chitinChestplate;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[2]);
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(item), chestCost, armor);
		item = ItemRegistry.chitinLeggings;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[2]);
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(item), leggingsCost, armor);
		item = ItemRegistry.chitinBoots;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[2]);
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(item), bootsCost, armor);
		
		//ARMORS BLUE
		item = ItemRegistry.chitinHelmet;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[3]);
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(item), helmetCost, armor);
		item = ItemRegistry.chitinChestplate;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[3]);
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(item), chestCost, armor);
		item = ItemRegistry.chitinLeggings;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[3]);
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(item), leggingsCost, armor);
		item = ItemRegistry.chitinBoots;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[3]);
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(item), bootsCost, armor);
		
		//ARMORS YELLOW
		item = ItemRegistry.chitinHelmet;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[4]);
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(item), helmetCost, armor);
		item = ItemRegistry.chitinChestplate;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[4]);
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(item), chestCost, armor);
		item = ItemRegistry.chitinLeggings;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[4]);
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(item), leggingsCost, armor);
		item = ItemRegistry.chitinBoots;
		armor = new ItemStack(item);
		armor.getItem().onCreated(armor, null, null);
		armor.getTagCompound().setString("PATTERN", ArmorPatternRegistry.pattern_names[4]);
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(item), bootsCost, armor);
		
	}
	
}





