package hok.chompzki.hivetera.tabs;

import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class TokenTab extends CreativeTabs {

	public TokenTab() {
		super("Hivetera_Tokens");
	}
	
	@Override
	public Item getTabIconItem() {
		return ItemRegistry.tokenFeeder;
	}

}