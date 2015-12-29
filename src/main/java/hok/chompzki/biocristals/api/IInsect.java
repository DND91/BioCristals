package hok.chompzki.biocristals.api;

import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.tile_enteties.TileNest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IInsect {
	
	public String getActionText(TileEntity entity, ItemStack stack);
	
	public ItemStack[] getResult(ItemStack stack);
	
	public boolean canUpdate(TileEntity entity, ItemStack stack);
	
	public void tileUpdate(TileEntity entity, ItemStack stack);
	
	public int lifeSpan(ItemStack stack);
	
	public int workSpan(ItemStack stack);
	
	public double getCost(ItemStack stack);
	
	public double getDrain(ItemStack stack);
	
	public EnumResource getFoodType(ItemStack stack);
	
	public double getFood(ItemStack stack);
	
	public void setFood(ItemStack stack, double food);
	
}
