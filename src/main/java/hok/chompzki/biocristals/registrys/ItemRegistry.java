package hok.chompzki.biocristals.registrys;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.containers.Hivebag;
import hok.chompzki.biocristals.items.ItemAttuner;
import hok.chompzki.biocristals.items.ItemBioBlob;
import hok.chompzki.biocristals.items.ItemBioReagent;
import hok.chompzki.biocristals.items.ItemCatalystInjector;
import hok.chompzki.biocristals.items.ItemChitinArmor;
import hok.chompzki.biocristals.items.ItemChitinPlate;
import hok.chompzki.biocristals.items.ItemClayHunter;
import hok.chompzki.biocristals.items.ItemCollector;
import hok.chompzki.biocristals.items.ItemCrootBeetle;
import hok.chompzki.biocristals.items.ItemCrootClaw;
import hok.chompzki.biocristals.items.ItemDebuggerStick;
import hok.chompzki.biocristals.items.ItemHivebag;
import hok.chompzki.biocristals.items.ItemKittehBeetle;
import hok.chompzki.biocristals.items.ItemKraKenBug;
import hok.chompzki.biocristals.items.ItemCrootStick;
import hok.chompzki.biocristals.items.ItemNomadSack;
import hok.chompzki.biocristals.items.ItemResearchBook;
import hok.chompzki.biocristals.items.ItemWSB;
import hok.chompzki.biocristals.recipes.RecipeData;
import hok.chompzki.biocristals.recipes.RecipeTransformer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
	
	public static ArmorMaterial chitin_armor = EnumHelper.addArmorMaterial("chitin_armor", 10, new int[] {2, 6, 5, 2}, 2);
	
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
		clayHunter.setContainerItem(clayHunter);
		chitinPlate = new ItemChitinPlate();
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioReagent, ItemBioReagent.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(collector, ItemCollector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(catalystInjector, ItemCatalystInjector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(researchBook, ItemResearchBook.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioBlob, ItemBioBlob.NAME, BioCristalsMod.MODID);
		
		GameRegistry.registerItem(debuggingStick, ItemDebuggerStick.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(hivebag, ItemHivebag.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(crootBeetle, ItemCrootBeetle.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(crootClaw, ItemCrootClaw.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(kraKenBug, ItemKraKenBug.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(crootStick, ItemCrootStick.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(wsb, ItemWSB.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(nomadSack, ItemNomadSack.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(kittehBeetle, ItemKittehBeetle.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(clayHunter, ItemClayHunter.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(chitinPlate, ItemChitinPlate.NAME, BioCristalsMod.MODID);
		
		GameRegistry.registerItem(chitinHelmet = new ItemChitinArmor("chitinHelmet", chitin_armor, "chitin_layer", 0), "chitinHelmet"); //0 for helmet
		GameRegistry.registerItem(chitinChestplate = new ItemChitinArmor("chitinChestplate", chitin_armor, "chitin_layer", 1), "chitinChestplate"); // 1 for chestplate
		GameRegistry.registerItem(chitinLeggings = new ItemChitinArmor("chitinLeggings", chitin_armor, "chitin_layer", 2), "chitinLeggings"); // 2 for leggings
		GameRegistry.registerItem(chitinBoots = new ItemChitinArmor("chitinBoots", chitin_armor, "chitin_layer", 3), "chitinBoots"); // 3 for boots
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
