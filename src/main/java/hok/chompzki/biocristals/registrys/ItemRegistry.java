package hok.chompzki.biocristals.registrys;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.items.ItemAttuner;
import hok.chompzki.biocristals.items.ItemBioReagent;
import hok.chompzki.biocristals.items.ItemCatalystInjector;
import hok.chompzki.biocristals.items.ItemCollector;
import hok.chompzki.biocristals.items.ItemDebuggerStick;
import hok.chompzki.biocristals.tutorials.logic.ItemBioBook;
import net.minecraft.item.Item;


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
	public static Item bioBook = null;
	public static Item debuggingStick = null;
	
	public void registerItems(){
		attuner = new ItemAttuner();
		bioReagent = new ItemBioReagent();
		collector = new ItemCollector();
		catalystInjector = new ItemCatalystInjector();
		bioBook = new ItemBioBook();
		debuggingStick = new ItemDebuggerStick();
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioReagent, ItemBioReagent.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(collector, ItemCollector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(catalystInjector, ItemCatalystInjector.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioBook, ItemBioBook.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(debuggingStick, ItemDebuggerStick.NAME, BioCristalsMod.MODID);
	}
	
}
