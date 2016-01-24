package hok.chompzki.hivetera.blocks;

import hok.chompzki.hivetera.croot.power.TreeForm;
import hok.chompzki.hivetera.tile_enteties.TileCroot;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCroot extends BlockContainer {

	protected BlockCroot() {
		super(Material.wood);
		this.setTickRandomly(true);
	}
	
	protected BlockCroot(Material material) {
		super(material);
		this.setTickRandomly(true);
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		
		TileCroot member = (TileCroot) world.getTileEntity(x, y, z);
		member.addTile();
        super.onPostBlockPlaced(world, x, y, z, meta);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		if(world.isRemote)
			return;
		TileCroot member = (TileCroot) world.getTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
		member.breakTile();
        
    }
	
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
		super.updateTick(world, x, y, z, rand);
        if (!world.isRemote){
        	TileCroot member = (TileCroot) world.getTileEntity(x, y, z);
    		member.updateConntection();
        }
    }
	
	public boolean stable(World world, int x, int y, int z)
    {
        if (!world.isRemote){
        	TileCroot member = (TileCroot) world.getTileEntity(x, y, z);
    		TreeForm form = member.getForm();
    		if(form == null)
    			return false;
    		return form.getStabel();
        }
        return false;
    }
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
        if (!world.isRemote){
        	TileCroot member = (TileCroot) world.getTileEntity(x, y, z);
    		member.updateConntection();
        }
	}

}
