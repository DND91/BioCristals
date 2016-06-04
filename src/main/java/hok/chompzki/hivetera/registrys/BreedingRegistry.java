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
		register((IInsect)ItemRegistry.voidCrawler, (IInsect)ItemRegistry.voidCrawler, new ItemStack(Items.ender_pearl, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.voidCrawler, 1));
		register((IInsect)ItemRegistry.crootClaw, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Items.ender_pearl, 1, OreDictionary.WILDCARD_VALUE), 4000, new ItemStack(ItemRegistry.voidCrawler, 1));
		
		register((IInsect)ItemRegistry.lightBringer, (IInsect)ItemRegistry.lightBringer, new ItemStack(Blocks.lit_pumpkin, 1, OreDictionary.WILDCARD_VALUE), 250, new ItemStack(ItemRegistry.lightBringer, 1));
		register((IInsect)ItemRegistry.hivebag, (IInsect)ItemRegistry.crootBeetle, new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.lightBringer, 1));
		register((IInsect)ItemRegistry.honeyWidow, (IInsect)ItemRegistry.honeyWidow, new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.honeyWidow, 1));
		register((IInsect)ItemRegistry.hivebag, (IInsect)ItemRegistry.hivebag, new ItemStack(ItemRegistry.nomadSack, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.honeyWidow, 1));
		register((IInsect)ItemRegistry.clayFoamer, (IInsect)ItemRegistry.clayFoamer, new ItemStack(Items.clay_ball, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.clayFoamer, 1));
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.kraKenBug, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.clayFoamer, 1));
		register((IInsect)ItemRegistry.sprinter, (IInsect)ItemRegistry.sprinter, new ItemStack(Items.sugar, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.sprinter, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Items.sugar, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.sprinter, 1));
		register((IInsect)ItemRegistry.darter, (IInsect)ItemRegistry.darter, new ItemStack(Blocks.log, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.darter, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(Items.feather, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.darter, 1));
		register((IInsect)ItemRegistry.dragonShell, (IInsect)ItemRegistry.dragonShell, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.dragonShell, 1));
		register((IInsect)ItemRegistry.honeyWidow, (IInsect)ItemRegistry.clayFoamer, new ItemStack(ItemRegistry.chitinHelmet, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.dragonShell, 1));
		register((IInsect)ItemRegistry.waterHunter, (IInsect)ItemRegistry.waterHunter, new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.waterHunter, 1));
		register((IInsect)ItemRegistry.clayHunter, (IInsect)ItemRegistry.clayHunter, new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.waterHunter, 1));
		register((IInsect)ItemRegistry.hillSpider, (IInsect)ItemRegistry.hillSpider, new ItemStack(Blocks.ladder, 1, OreDictionary.WILDCARD_VALUE), 200, new ItemStack(ItemRegistry.hillSpider, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Blocks.ladder, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.hillSpider, 1));
		register((IInsect)ItemRegistry.fireSprinter, (IInsect)ItemRegistry.fireSprinter, new ItemStack(Items.lava_bucket, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.fireSprinter, 1));
		register((IInsect)ItemRegistry.lightBringer, (IInsect)ItemRegistry.hivebag, new ItemStack(Items.lava_bucket, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.fireSprinter, 1));
		register((IInsect)ItemRegistry.deathsWing, (IInsect)ItemRegistry.deathsWing, new ItemStack(Items.golden_apple, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.deathsWing, 1));
		register((IInsect)ItemRegistry.clayFoamer, (IInsect)ItemRegistry.lifeShader, new ItemStack(Items.golden_apple, 1, OreDictionary.WILDCARD_VALUE), 4000, new ItemStack(ItemRegistry.deathsWing, 1));
		register((IInsect)ItemRegistry.carpaceSlug, (IInsect)ItemRegistry.carpaceSlug, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.carpaceSlug, 1));
		register((IInsect)ItemRegistry.crootBeetle, (IInsect)ItemRegistry.crootBeetle, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.carpaceSlug, 1));
		register((IInsect)ItemRegistry.carpaceSnail, (IInsect)ItemRegistry.carpaceSnail, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.carpaceSnail, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(ItemRegistry.chitinPlate, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.carpaceSnail, 1));
		register((IInsect)ItemRegistry.lifeShader, (IInsect)ItemRegistry.lifeShader, new ItemStack(Items.apple, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.lifeShader, 1));
		register((IInsect)ItemRegistry.hivebag, (IInsect)ItemRegistry.kittehBeetle, new ItemStack(Items.apple, 1, OreDictionary.WILDCARD_VALUE), 4000, new ItemStack(ItemRegistry.lifeShader, 1));
		register((IInsect)ItemRegistry.voidFarer, (IInsect)ItemRegistry.voidFarer, new ItemStack(Items.ender_eye, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.voidFarer, 1));
		register((IInsect)ItemRegistry.voidCrawler, (IInsect)ItemRegistry.voidCrawler, new ItemStack(Items.ender_eye, 1, OreDictionary.WILDCARD_VALUE), 4000, new ItemStack(ItemRegistry.voidFarer, 1));
		register((IInsect)ItemRegistry.sludgeGrim, (IInsect)ItemRegistry.sludgeGrim, new ItemStack(Items.arrow, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.sludgeGrim, 1));
		register((IInsect)ItemRegistry.voidCrawler, (IInsect)ItemRegistry.waterHunter, new ItemStack(Items.arrow, 1, OreDictionary.WILDCARD_VALUE), 4000, new ItemStack(ItemRegistry.sludgeGrim, 1));
		register((IInsect)ItemRegistry.bullWorm, (IInsect)ItemRegistry.bullWorm, new ItemStack(Blocks.cactus, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.bullWorm, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.waterHunter, new ItemStack(Blocks.cactus, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.bullWorm, 1));
		register((IInsect)ItemRegistry.ssb, (IInsect)ItemRegistry.ssb, new ItemStack(Items.iron_ingot, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.ssb, 1));
		register((IInsect)ItemRegistry.wsb, (IInsect)ItemRegistry.wsb, new ItemStack(Items.iron_ingot, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.ssb, 1));
		register((IInsect)ItemRegistry.blackLeech, (IInsect)ItemRegistry.blackLeech, new ItemStack(Items.spider_eye, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.blackLeech, 1));
		register((IInsect)ItemRegistry.hivebag, (IInsect)ItemRegistry.wsb, new ItemStack(Items.spider_eye, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.blackLeech, 1));
		register((IInsect)ItemRegistry.hungerSwarm, (IInsect)ItemRegistry.hungerSwarm, new ItemStack(Items.beef, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.hungerSwarm, 1));
		register((IInsect)ItemRegistry.blackLeech, (IInsect)ItemRegistry.wsb, new ItemStack(Items.beef, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.hungerSwarm, 1));
		register((IInsect)ItemRegistry.greenBlower, (IInsect)ItemRegistry.greenBlower, new ItemStack(Blocks.tnt, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.greenBlower, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Blocks.tnt, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.greenBlower, 1));
		register((IInsect)ItemRegistry.priestBeetle, (IInsect)ItemRegistry.priestBeetle, new ItemStack(ItemRegistry.hiveBrain, 1, OreDictionary.WILDCARD_VALUE), 8000, new ItemStack(ItemRegistry.priestBeetle, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(ItemRegistry.hiveBrain, 1, OreDictionary.WILDCARD_VALUE), 10000, new ItemStack(ItemRegistry.priestBeetle, 1));
		register((IInsect)ItemRegistry.colonialSlug, (IInsect)ItemRegistry.colonialSlug, new ItemStack(Items.wheat_seeds, 1, OreDictionary.WILDCARD_VALUE), 400, new ItemStack(ItemRegistry.colonialSlug, 1));
		register((IInsect)ItemRegistry.kittehBeetle, (IInsect)ItemRegistry.crootBeetle, new ItemStack(Items.wheat_seeds, 1, OreDictionary.WILDCARD_VALUE), 800, new ItemStack(ItemRegistry.colonialSlug, 1));
		register((IInsect)ItemRegistry.blueHorder, (IInsect)ItemRegistry.blueHorder, new ItemStack(Items.gold_ingot, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.blueHorder, 1));
		register((IInsect)ItemRegistry.crootClaw, (IInsect)ItemRegistry.crootClaw, new ItemStack(Items.gold_ingot, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.blueHorder, 1));
		register((IInsect)ItemRegistry.hungerLarva, (IInsect)ItemRegistry.hungerLarva, new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.hungerLarva, 1));
		register((IInsect)ItemRegistry.kraKenBug, (IInsect)ItemRegistry.kraKenBug, new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE), 2000, new ItemStack(ItemRegistry.hungerLarva, 1));
		register((IInsect)ItemRegistry.medusaEye, (IInsect)ItemRegistry.medusaEye, new ItemStack(Blocks.stone, 1, OreDictionary.WILDCARD_VALUE), 500, new ItemStack(ItemRegistry.medusaEye, 1));
		register((IInsect)ItemRegistry.bullWorm, (IInsect)ItemRegistry.crootClaw, new ItemStack(Blocks.stone, 1, OreDictionary.WILDCARD_VALUE), 1000, new ItemStack(ItemRegistry.medusaEye, 1));
		
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
		
		
	}
	
}





