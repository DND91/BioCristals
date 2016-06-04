package hok.chompzki.hivetera.registrys;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.items.ItemAttuner;
import hok.chompzki.hivetera.items.ItemBioBlob;
import hok.chompzki.hivetera.items.ItemBioReagent;
import hok.chompzki.hivetera.items.ItemBiomeSample;
import hok.chompzki.hivetera.items.ItemCatalystInjector;
import hok.chompzki.hivetera.items.ItemChitinPlate;
import hok.chompzki.hivetera.items.ItemCollector;
import hok.chompzki.hivetera.items.ItemCrootHoe;
import hok.chompzki.hivetera.items.ItemCrootIronPickaxe;
import hok.chompzki.hivetera.items.ItemCrootStick;
import hok.chompzki.hivetera.items.ItemDebuggerStick;
import hok.chompzki.hivetera.items.ItemHiveBrain;
import hok.chompzki.hivetera.items.ItemNomadSack;
import hok.chompzki.hivetera.items.ItemResearchBook;
import hok.chompzki.hivetera.items.armor.ItemArmorAttuner;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.insects.ItemBlackLeech;
import hok.chompzki.hivetera.items.armor.insects.ItemBlueHorder;
import hok.chompzki.hivetera.items.armor.insects.ItemBullWorm;
import hok.chompzki.hivetera.items.armor.insects.ItemMedusaEye;
import hok.chompzki.hivetera.items.armor.insects.ItemCarpaceSlug;
import hok.chompzki.hivetera.items.armor.insects.ItemCarpaceSnail;
import hok.chompzki.hivetera.items.armor.insects.ItemClayFoamer;
import hok.chompzki.hivetera.items.armor.insects.ItemColonialSlug;
import hok.chompzki.hivetera.items.armor.insects.ItemDarter;
import hok.chompzki.hivetera.items.armor.insects.ItemDeathsWing;
import hok.chompzki.hivetera.items.armor.insects.ItemDragonShell;
import hok.chompzki.hivetera.items.armor.insects.ItemFireSprinter;
import hok.chompzki.hivetera.items.armor.insects.ItemGreenBlower;
import hok.chompzki.hivetera.items.armor.insects.ItemHillSpider;
import hok.chompzki.hivetera.items.armor.insects.ItemHungerLarva;
import hok.chompzki.hivetera.items.armor.insects.ItemHungerSwarm;
import hok.chompzki.hivetera.items.armor.insects.ItemLifeShader;
import hok.chompzki.hivetera.items.armor.insects.ItemLightbringer;
import hok.chompzki.hivetera.items.armor.insects.ItemPriestBeetle;
import hok.chompzki.hivetera.items.armor.insects.ItemSSB;
import hok.chompzki.hivetera.items.armor.insects.ItemSludgeGrim;
import hok.chompzki.hivetera.items.armor.insects.ItemSprinter;
import hok.chompzki.hivetera.items.armor.insects.ItemVoidFarer;
import hok.chompzki.hivetera.items.armor.insects.ItemWaterHunter;
import hok.chompzki.hivetera.items.insects.ItemClayHunter;
import hok.chompzki.hivetera.items.insects.ItemCrootBeetle;
import hok.chompzki.hivetera.items.insects.ItemCrootClaw;
import hok.chompzki.hivetera.items.insects.ItemFruitSpider;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.items.insects.ItemHoneyWidow;
import hok.chompzki.hivetera.items.insects.ItemKittehBeetle;
import hok.chompzki.hivetera.items.insects.ItemKraKenBug;
import hok.chompzki.hivetera.items.insects.ItemUnlockBug;
import hok.chompzki.hivetera.items.insects.ItemVoidCrawler;
import hok.chompzki.hivetera.items.insects.ItemWSB;
import hok.chompzki.hivetera.items.token.ItemBank;
import hok.chompzki.hivetera.items.token.ItemBridge;
import hok.chompzki.hivetera.items.token.ItemEater;
import hok.chompzki.hivetera.items.token.ItemFeeder;
import hok.chompzki.hivetera.items.token.ItemFilter;
import hok.chompzki.hivetera.items.token.ItemTransformer;
import hok.chompzki.hivetera.recipes.RecipeData;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.common.util.EnumHelper;


/**
 * 
 * @author Chompzki
 *	- REMEMBER LINKS -
 * Making mode use other mods ore and so on: https://github.com/MinecraftForge/MinecraftForge/pull/1926#issuecomment-110486973
 * DON'T YOU FUCKING DARE INVENTING THE FUCKING WHEEL AGAIN!!!!
 * 
 * 
 */

public class ItemRegistry {
	
	public static ArmorMaterial chitin_armor = EnumHelper.addArmorMaterial("chitin_armor", 20, new int[] {2, 6, 5, 2}, 1);
	public static ToolMaterial crootIron = EnumHelper.addToolMaterial("crootIron", 2, 500, 7.0F, 3.0F, 5);
	
	public static Item attuner = null;
	public static Item bioReagent = null;
	public static Item collector = null;
	public static Item catalystInjector = null;
	public static Item researchBook = null;
	public static Item bioBlob = null;
	public static Item debuggingStick = null;
	public static Item hivebag = null;
	public static Item crootBeetle = null;
	public static Item crootClaw = null;
	public static Item kraKenBug = null;
	public static Item crootStick = null;
	public static Item wsb = null;
	public static ItemNomadSack nomadSack = null;
	public static Item kittehBeetle = null;
	public static Item clayHunter = null;
	public static Item chitinPlate = null;
	public static Item chitinHelmet = null;
	public static Item chitinChestplate = null;
	public static Item chitinLeggings = null;
	public static Item chitinBoots = null;
	public static Item unlockBug = null;
	public static Item crootIronPickaxe = null;
	public static Item crootWoodHoe = null;
	public static Item fruitSpider = null;
	public static Item voidCrawler = null;
	public static Item hiveBrain = null;
	public static Item lightBringer = null;
	public static Item honeyWidow = null;
	public static Item armorAttuner = null;
	public static Item clayFoamer = null;
	public static Item sprinter = null;
	public static Item darter = null;
	public static Item dragonShell = null;
	public static Item waterHunter = null;
	public static Item hillSpider = null;
	public static Item fireSprinter = null;
	public static Item deathsWing = null;
	public static Item carpaceSlug = null;
	public static Item carpaceSnail = null;
	public static Item lifeShader = null;
	public static Item voidFarer = null;
	public static Item sludgeGrim = null;
	public static Item bullWorm = null;
	public static Item ssb = null;
	public static Item blackLeech = null;
	public static Item hungerSwarm = null;
	public static Item greenBlower = null;
	public static Item priestBeetle = null;
	public static Item colonialSlug = null;
	public static Item blueHorder = null;
	public static Item hungerLarva = null;
	public static Item medusaEye = null;
	
	public static Item tokenFeeder = null;
	public static Item tokenBridge = null;
	public static Item tokenBank = null;
	public static Item tokenEater = null;
	public static Item tokenFilter = null;
	public static Item tokenTransformer = null;
	
	public static Item biomeSample = null;
	
	
	public void registerItems(){
		attuner = new ItemAttuner();
		bioReagent = new ItemBioReagent();
		collector = new ItemCollector();
		catalystInjector = new ItemCatalystInjector();
		researchBook = new ItemResearchBook();
		bioBlob = new ItemBioBlob();
		debuggingStick = new ItemDebuggerStick();
		hivebag = new ItemHivebag();
		crootBeetle = new ItemCrootBeetle();
		crootClaw = new ItemCrootClaw();
		kraKenBug = new ItemKraKenBug();
		crootStick = new ItemCrootStick();
		wsb = new ItemWSB();
		nomadSack = new ItemNomadSack();
		kittehBeetle = new ItemKittehBeetle();
		clayHunter = new ItemClayHunter();
		//clayHunter.setContainerItem(clayHunter);
		chitinPlate = new ItemChitinPlate();
		unlockBug = new ItemUnlockBug();
		crootIronPickaxe = new ItemCrootIronPickaxe();
		crootWoodHoe = new ItemCrootHoe(7*7-1);
		fruitSpider = new ItemFruitSpider();
		voidCrawler = new ItemVoidCrawler();
		lightBringer = new ItemLightbringer();
		honeyWidow = new ItemHoneyWidow();
		armorAttuner = new ItemArmorAttuner();
		clayFoamer = new ItemClayFoamer();
		sprinter = new ItemSprinter();
		darter = new ItemDarter();
		dragonShell = new ItemDragonShell();
		waterHunter = new ItemWaterHunter();
		hillSpider = new ItemHillSpider();
		fireSprinter = new ItemFireSprinter();
		deathsWing = new ItemDeathsWing();
		carpaceSlug = new ItemCarpaceSlug();
		carpaceSnail = new ItemCarpaceSnail();
		lifeShader = new ItemLifeShader();
		voidFarer = new ItemVoidFarer();
		sludgeGrim = new ItemSludgeGrim();
		bullWorm = new ItemBullWorm();
		ssb = new ItemSSB();
		blackLeech = new ItemBlackLeech();
		hungerSwarm = new ItemHungerSwarm();
		greenBlower = new ItemGreenBlower();
		priestBeetle = new ItemPriestBeetle();
		colonialSlug = new ItemColonialSlug();
		blueHorder = new ItemBlueHorder();
		hungerLarva = new ItemHungerLarva();
		medusaEye = new ItemMedusaEye();
		
		hiveBrain = new ItemHiveBrain();
		tokenFeeder = new ItemFeeder();
		tokenBridge = new ItemBridge();
		tokenBank = new ItemBank();
		tokenEater = new ItemEater();
		tokenFilter = new ItemFilter();
		tokenTransformer = new ItemTransformer();
		
		biomeSample = new ItemBiomeSample();
		
		chitin_armor.customCraftingMaterial = this.chitinPlate;
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(bioReagent, ItemBioReagent.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(collector, ItemCollector.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(catalystInjector, ItemCatalystInjector.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(researchBook, ItemResearchBook.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(bioBlob, ItemBioBlob.NAME, HiveteraMod.MODID);
		
		GameRegistry.registerItem(debuggingStick, ItemDebuggerStick.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(hivebag, ItemHivebag.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(crootBeetle, ItemCrootBeetle.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(crootClaw, ItemCrootClaw.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(kraKenBug, ItemKraKenBug.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(crootStick, ItemCrootStick.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(wsb, ItemWSB.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(nomadSack, ItemNomadSack.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(kittehBeetle, ItemKittehBeetle.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(clayHunter, ItemClayHunter.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(chitinPlate, ItemChitinPlate.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(unlockBug, ItemUnlockBug.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(crootIronPickaxe, ItemCrootIronPickaxe.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(crootWoodHoe, ItemCrootHoe.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(fruitSpider, ItemFruitSpider.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(voidCrawler, ItemVoidCrawler.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(lightBringer, ItemLightbringer.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(honeyWidow, ItemHoneyWidow.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(armorAttuner, ItemArmorAttuner.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(clayFoamer, ItemClayFoamer.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(sprinter, ItemSprinter.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(darter, ItemDarter.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(dragonShell, ItemDragonShell.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(waterHunter, ItemWaterHunter.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(hillSpider, ItemHillSpider.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(fireSprinter, ItemFireSprinter.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(deathsWing, ItemDeathsWing.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(carpaceSlug, ItemCarpaceSlug.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(carpaceSnail, ItemCarpaceSnail.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(lifeShader, ItemLifeShader.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(voidFarer, ItemVoidFarer.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(sludgeGrim, ItemSludgeGrim.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(bullWorm, ItemBullWorm.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(ssb, ItemSSB.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(blackLeech, ItemBlackLeech.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(hungerSwarm, ItemHungerSwarm.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(greenBlower, ItemGreenBlower.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(priestBeetle, ItemPriestBeetle.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(colonialSlug, ItemColonialSlug.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(blueHorder, ItemBlueHorder.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(hungerLarva, ItemHungerLarva.NAME, HiveteraMod.MODID);
		GameRegistry.registerItem(medusaEye, ItemMedusaEye.NAME, HiveteraMod.MODID);
		
		GameRegistry.registerItem(chitinHelmet = new ItemBioModArmor("chitinHelmet", chitin_armor, "chitin_layer", 0), "chitinHelmet"); //0 for helmet
		GameRegistry.registerItem(chitinChestplate = new ItemBioModArmor("chitinChestplate", chitin_armor, "chitin_layer", 1), "chitinChestplate"); // 1 for chestplate
		GameRegistry.registerItem(chitinLeggings = new ItemBioModArmor("chitinLeggings", chitin_armor, "chitin_layer", 2), "chitinLeggings"); // 2 for leggings
		GameRegistry.registerItem(chitinBoots = new ItemBioModArmor("chitinBoots", chitin_armor, "chitin_layer", 3), "chitinBoots"); // 3 for boots
	}

	public void setupFilters() {
		
		for(String data : ConfigRegistry.sackWhiteList){
			RecipeTransformer.dataToItemStack(data, false);
			nomadSack.whitelist.addAll(RecipeTransformer.dataToItemStack(data, false));
		}
		
		for(String data : ConfigRegistry.sackBlackList){
			RecipeTransformer.dataToItemStack(data, false);
			nomadSack.blacklist.addAll(RecipeTransformer.dataToItemStack(data, false));
		}
	}
	
}
