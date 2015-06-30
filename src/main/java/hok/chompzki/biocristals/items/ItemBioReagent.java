package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBioReagent extends Item {
	
	public final static String NAME = "itemBioReagent";
	
	public ItemBioReagent(){
		
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(CreativeTabs.tabMisc);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		
		
		//ADD SOMETINHG NEW!
	}
	
	
}
