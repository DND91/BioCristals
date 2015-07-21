package hok.chompzki.biocristals.blocks.croot;

import java.util.List;
import java.util.Random;

import hok.chompzki.biocristals.api.IGrowthCristal;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockGenerator extends BlockContainer implements IGrowthCristal {
	
	protected BlockGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
        {
            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(2) == 0)
            {
                //this.grow(world, x, y, z);
            }
        }
    }

	@Override
	public boolean isMature(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z) {
		return false;
	}

	@Override
	public void harvest(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z) {
	}

	@Override
	public void harvest(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z, List<ItemStack> list) {
	}

	@Override
	public void grow(World world, int x, int y, int z) {
		TileCrootCore tile = (TileCrootCore) world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		tile.grow();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		if(world.isRemote)
			return;
		
		ICrootPowerGen member = (ICrootPowerGen) world.getTileEntity(x, y, z);
		member.breakTile();
        super.breakBlock(world, x, y, z, block, meta);
    }

}
