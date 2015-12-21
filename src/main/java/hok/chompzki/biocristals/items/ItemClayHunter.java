package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;

import java.util.Random;

import net.minecraft.item.Item;

public class ItemClayHunter extends Item {
	
	public final static String NAME = "itemClayHunter";
	
	public ItemClayHunter(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(64);
	}
	
}
