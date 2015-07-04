package hok.chompzki.biocristals;

import hok.chompzki.biocristals.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class BioCristalTab extends CreativeTabs {

	public BioCristalTab() {
		super("Bio Cristal");
	}

	@Override
	public Item getTabIconItem() {
		return ItemRegistry.attuner;
	}

}
