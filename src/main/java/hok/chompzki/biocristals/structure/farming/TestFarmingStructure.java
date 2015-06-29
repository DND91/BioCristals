package hok.chompzki.biocristals.structure.farming;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import hok.chompzki.biocristals.structure.IStructure;

public class TestFarmingStructure implements IStructure {

	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player) {
		
		return true;
	}

	@Override
	public boolean canPlaceAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		
		return block == Blocks.crafting_table;
	}

	@Override
	public void pay(ItemStack stack, EntityPlayer player) {
		
	}

	@Override
	public void construct(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z) {
		
		world.setBlock(x, y, z, Blocks.anvil);
	}

}
