package hok.chompzki.hivetera.items;

import hok.chompzki.hivetera.HiveteraMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBioReagent extends Item {
	
	public final static String NAME = "itemBioReagent";
	
	public ItemBioReagent(){
		
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		
		
		//ADD SOMETINHG NEW!
	}
	
	
}
