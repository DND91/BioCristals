package hok.chompzki.biocristals.croot;

import java.util.List;
import java.util.Random;

import hok.chompzki.biocristals.api.IGrowthCristal;
import hok.chompzki.biocristals.blocks.BlockCrootSapling;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCore extends BlockContainer implements IGrowthCristal {
	
	protected BlockCore(Material p_i45386_1_) {
		super(p_i45386_1_);
		this.setTickRandomly(true);
	}
	
	public boolean canBlockStay(World world, int x, int y, int z)
    {
    	if(world.isAirBlock(x, y-1, z))
    		return false;
    	Block block = world.getBlock(x, y-1, z);
    	if(!CrootHelper.startsOn(block))
    		return false;
    	TileEntity tile = world.getTileEntity(x, y, z);
    	if(CrootHelper.hasZoneOwner((TileCore) tile, world, x, y, z, 16))
    		return false;	
    	
        return  true;
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }
    
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        ICrootCore core = (ICrootCore) p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
		core.onNeighborChange();
        this.checkAndDropBlock(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }
    
    protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
        }
    }
	
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
        {
        	
        	this.checkAndDropBlock(world, x, y, z);
        	
            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(2) == 0)
            {
                this.grow(world, x, y, z);
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
		TileCore tile = (TileCore) world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		tile.grow();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		if(world.isRemote)
			return;
		
		ICrootCore core = (ICrootCore) world.getTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
		core.breakTile();
    }
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		
		ICrootCore core = (ICrootCore) world.getTileEntity(x, y, z);
		core.addTile();
        super.onPostBlockPlaced(world, x, y, z, meta);
	}

}
