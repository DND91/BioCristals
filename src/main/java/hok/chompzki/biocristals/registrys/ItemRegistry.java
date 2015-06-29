package hok.chompzki.biocristals.registrys;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.items.ItemAttuner;
import hok.chompzki.biocristals.items.ItemBioReagent;
import net.minecraft.item.Item;

public class ItemRegistry {
	
	public static Item attuner = null;
	public static Item bioReagent = null;
	
	public void registerItems(){
		attuner = new ItemAttuner();
		bioReagent = new ItemBioReagent();
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, BioCristalsMod.MODID);
		GameRegistry.registerItem(bioReagent, ItemBioReagent.NAME, BioCristalsMod.MODID);
	}
	
}
