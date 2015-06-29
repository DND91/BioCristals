package hok.chompzki.biocristals.registrys;

import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.items.ItemAttuner;
import net.minecraft.item.Item;

public class ItemRegistry {
	
	public static Item attuner = null;
	
	public void registerItems(){
		attuner = new ItemAttuner();
		
		GameRegistry.registerItem(attuner, ItemAttuner.NAME, BioCristalsMod.MODID);
	}
	
}
