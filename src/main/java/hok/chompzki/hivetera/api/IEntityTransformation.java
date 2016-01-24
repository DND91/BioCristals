package hok.chompzki.hivetera.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IEntityTransformation {
	
public boolean hasResources(ItemStack stack, EntityPlayer player, Entity taget);
	
	public boolean canTransform(ItemStack stack, EntityPlayer player, World world, Entity taget);
	
	public void pay(ItemStack stack, EntityPlayer player, Entity taget);
	
	public void transform(ItemStack stack, EntityPlayer player, World world, Entity taget);
	
}