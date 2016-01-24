package hok.chompzki.hivetera.api;

import java.util.List;

import net.minecraft.world.World;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IGrowthCristal {
	
	public boolean isMature(World world, EntityPlayer player, ItemStack stack, int x, int y, int z);
	
	public void harvest(World world, EntityPlayer player, ItemStack stack, int x, int y, int z);
	
	public void harvest(World world, EntityPlayer player, ItemStack stack, int x, int y, int z, List<ItemStack> list);

	public void grow(World world, int x, int y, int z);
}
