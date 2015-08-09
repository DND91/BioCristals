package hok.chompzki.biocristals.registrys;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.containers.Hivebag;
import hok.chompzki.biocristals.items.ItemAttuner;
import hok.chompzki.biocristals.items.ItemBioBlob;
import hok.chompzki.biocristals.items.ItemBioReagent;
import hok.chompzki.biocristals.items.ItemCatalystInjector;
import hok.chompzki.biocristals.items.ItemCollector;
import hok.chompzki.biocristals.items.ItemCrootBeetle;
import hok.chompzki.biocristals.items.ItemDebuggerStick;
import hok.chompzki.biocristals.items.ItemHivebag;
import hok.chompzki.biocristals.recipes.RecipeTransformer;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.research.logic.ItemResearchBook;
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
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioReagent, ItemBioReagent.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(collector, ItemCollector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(catalystInjector, ItemCatalystInjector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(researchBook, ItemResearchBook.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioBlob, ItemBioBlob.NAME, BioCristalsMod.MODID);
		
		GameRegistry.registerItem(debuggingStick, ItemDebuggerStick.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(hivebag, ItemHivebag.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(crootBeetle, ItemCrootBeetle.NAME, BioCristalsMod.MODID);
		
	}
	
}
