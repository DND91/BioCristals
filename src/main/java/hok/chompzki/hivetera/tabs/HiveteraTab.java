package hok.chompzki.hivetera.tabs;

import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class HiveteraTab extends CreativeTabs {

	public HiveteraTab() {
		super("Hivetera");
	}

	@Override
	public Item getTabIconItem() {
		return ItemRegistry.attuner;
	}

}