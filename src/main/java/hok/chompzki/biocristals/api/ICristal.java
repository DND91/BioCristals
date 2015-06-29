package hok.chompzki.biocristals.api;

import net.minecraft.world.World;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ICristal {
	
	public boolean isMature(World world, EntityPlayer player, ItemStack stack, int x, int y, int z);
	
	public void harvest(World world, EntityPlayer player, ItemStack stack, int x, int y, int z);
	
}
