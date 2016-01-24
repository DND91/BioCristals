package hok.chompzki.hivetera.api;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface INestInsect extends IInsect {
	
	public String getActionText(TileEntity entity, ItemStack stack);
	
	public boolean canUpdate(TileEntity entity, ItemStack stack);
	
	public void tileUpdate(TileEntity entity, ItemStack stack);
	
	public int workSpan(ItemStack stack);
	
}
