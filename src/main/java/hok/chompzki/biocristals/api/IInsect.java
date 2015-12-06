package hok.chompzki.biocristals.api;

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
}
