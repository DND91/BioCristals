package hok.chompzki.biocristals.structure.farming;

import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.structure.IStructure;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WheatCristalStructure implements IStructure {

	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canPlaceAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z) {// AxisAlignedBB
		Block block = world.getBlock(x, y, z);
		if(block != BlockRegistry.biomass)
			return false;
		
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(10, 10, 10));
		for(EntityItem item : list){
			if(item.getEntityItem().getItem() == Items.wheat)
				return true;
		}
		return false;
	}
	
	@Override
	public void pay(ItemStack stack, EntityPlayer player) {
		
	}
	
	@Override
	public void construct(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z) {
		if (!world.isRemote)
	        return;
	        
		world.setBlock(x, y, z, BlockRegistry.wheatCristal);
		
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(10, 10, 10));
		for(EntityItem item : list){
			if(item.getEntityItem().getItem() == Items.wheat){
				item.getEntityItem().stackSize--;
				if(item.getEntityItem().stackSize <= 0)
					item.setDead();
				return;
			}
		}
		
	}

}
