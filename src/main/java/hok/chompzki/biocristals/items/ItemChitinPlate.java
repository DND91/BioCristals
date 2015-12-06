package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.item.Item;

public class ItemChitinPlate extends Item {
	
	public final static String NAME = "itemChitinPlate";
	
	public ItemChitinPlate(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(24);
	}
	
}
