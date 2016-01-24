package hok.chompzki.hivetera.items;

import hok.chompzki.hivetera.HiveteraMod;
import net.minecraft.item.Item;

public class ItemChitinPlate extends Item {
	
	public final static String NAME = "itemChitinPlate";
	
	public ItemChitinPlate(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(24);
	}
	
}
