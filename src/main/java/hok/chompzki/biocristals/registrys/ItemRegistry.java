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
import hok.chompzki.biocristals.items.ItemCollector;
import hok.chompzki.biocristals.items.ItemCrootBeetle;
import hok.chompzki.biocristals.items.ItemCrootClaw;
import hok.chompzki.biocristals.items.ItemDebuggerStick;
import hok.chompzki.biocristals.items.ItemHivebag;
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
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


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
		
		/*
		nomadSack.whitelist.add(Blocks.wool);
		nomadSack.whitelist.add(Blocks.lever);
		nomadSack.whitelist.add(Blocks.melon_block);
		nomadSack.whitelist.add(Blocks.pumpkin);
		nomadSack.whitelist.add(Blocks.rail);
		nomadSack.whitelist.add(Blocks.brown_mushroom);
		nomadSack.whitelist.add(Blocks.red_mushroom);
		nomadSack.whitelist.add(Blocks.brewing_stand);
		nomadSack.whitelist.add(Blocks.sapling);
		nomadSack.whitelist.add(Blocks.red_flower);
		nomadSack.whitelist.add(Blocks.redstone_torch);
		nomadSack.whitelist.add(Blocks.waterlily);
		nomadSack.whitelist.add(Blocks.grass);
		nomadSack.whitelist.add(Blocks.leaves);
		nomadSack.whitelist.add(Blocks.yellow_flower);
		nomadSack.whitelist.add(Blocks.vine);
		*/
		/*
		nomadSack.blacklist.add(Items.stick);
		nomadSack.blacklist.add(Items.coal);
		nomadSack.blacklist.add(Items.bed);
		nomadSack.blacklist.add(Items.map);
		nomadSack.blacklist.add(Items.compass);
		nomadSack.blacklist.add(Items.cauldron);
		nomadSack.blacklist.add(Items.boat);
		nomadSack.blacklist.add(Items.minecart);
		nomadSack.blacklist.add(Items.chest_minecart);
		nomadSack.blacklist.add(Items.furnace_minecart);
		nomadSack.blacklist.add(Items.arrow);
		nomadSack.blacklist.add(Items.redstone);
		nomadSack.blacklist.add(Items.glowstone_dust);
		nomadSack.blacklist.add(Items.clock);
		nomadSack.blacklist.add(Items.hopper_minecart);
		nomadSack.blacklist.add(Items.tnt_minecart);
		nomadSack.blacklist.add(Items.filled_map);
		nomadSack.blacklist.add(ItemRegistry.crootStick);
		nomadSack.blacklist.add(ItemRegistry.researchBook);
		*/
	}
	
}
