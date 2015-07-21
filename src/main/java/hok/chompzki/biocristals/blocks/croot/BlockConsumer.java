package hok.chompzki.biocristals.blocks.croot;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockConsumer extends BlockContainer {

	protected BlockConsumer() {
		super(Material.wood);
		this.setTickRandomly(true);
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		
		TileCrootConsumer consumer = (TileCrootConsumer) world.getTileEntity(x, y, z);
		consumer.addTile();
        super.onPostBlockPlaced(world, x, y, z, meta);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		if(world.isRemote)
			return;
		
		TileCrootConsumer consumer = (TileCrootConsumer) world.getTileEntity(x, y, z);
		consumer.breakTile();
        super.breakBlock(world, x, y, z, block, meta);
    }
	
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
		super.updateTick(world, x, y, z, rand);
        if (!world.isRemote){
        	TileCrootConsumer consumer = (TileCrootConsumer) world.getTileEntity(x, y, z);
        	consumer.updateConntection();
        }
    }
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
        if (!world.isRemote){
        	TileCrootConsumer consumer = (TileCrootConsumer) world.getTileEntity(x, y, z);
        	consumer.updateConntection();
        }
	}
}
