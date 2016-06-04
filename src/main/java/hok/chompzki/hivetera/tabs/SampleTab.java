package hok.chompzki.hivetera.tabs;

import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class SampleTab extends CreativeTabs {

	public SampleTab() {
		super("Hivetera_Sampels");
	}
	
	@Override
	public Item getTabIconItem() {
		return ItemRegistry.biomeSample;
	}

}