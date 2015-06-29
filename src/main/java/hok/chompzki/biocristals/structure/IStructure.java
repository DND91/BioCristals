package hok.chompzki.biocristals.structure;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IStructure {
	
	public boolean hasResources(ItemStack stack, EntityPlayer player);
	
	public boolean canPlaceAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z);
	
	public void pay(ItemStack stack, EntityPlayer player);
	
	public void construct(ItemStack stack, EntityPlayer player, World world, int x, int y, int z);
	
}
