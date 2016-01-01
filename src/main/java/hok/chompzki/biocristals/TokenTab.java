package hok.chompzki.biocristals;

import hok.chompzki.biocristals.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class TokenTab extends CreativeTabs {

	public TokenTab() {
		super("Bio_Cristal_Tokens");
	}
	
	@Override
	public Item getTabIconItem() {
		return ItemRegistry.tokenFeeder;
	}

}