package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IInsect {
	
	public double getCost(ItemStack stack);
	
	public double getDrain(ItemStack stack);
	
	public EnumResource getFoodType(ItemStack stack);
	
	public double getFood(ItemStack stack);
	
	public void setFood(ItemStack stack, double food);
	
}
