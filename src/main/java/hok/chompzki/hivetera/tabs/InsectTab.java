package hok.chompzki.hivetera.tabs;

import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class InsectTab extends CreativeTabs {

	public InsectTab() {
		super("Hivetera_Insect");
	}
	
	@Override
	public Item getTabIconItem() {
		return ItemRegistry.honeyWidow;
	}

}