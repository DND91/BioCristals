package hok.chompzki.biocristals.items.insects;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.hunger.logic.EnumResource;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemClayHunter extends ItemInsect {
	
	public final static String NAME = "itemClayHunter";
	
	public ItemClayHunter(){
		super(EnumResource.BIOMASS, 5.0D, 10.0D);
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ItemStack[] getResult(ItemStack stack) {
		return new ItemStack[] {new ItemStack(Items.clay_ball)};
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int lifeSpan(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int workSpan(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
