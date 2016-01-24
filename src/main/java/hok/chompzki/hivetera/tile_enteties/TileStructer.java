package hok.chompzki.hivetera.tile_enteties;

import hok.chompzki.hivetera.blocks.BlockStructer;
import hok.chompzki.hivetera.croot.building.CrootBlock;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileStructer extends TileEntity {
	
	private int tick = 0;
	
	public TileStructer() {}
	
	protected static int reverse(int i){
		i = MathHelper.clamp_int(i, -1, 1);
		return Math.abs(i) == 1 ? 0 : 1;
	}
	
	public static int getX(ForgeDirection dir, int x, int y, int z){
		int rx = dir.offsetX;
		int ry = dir.offsetY;
		int rz = dir.offsetZ;
		
		return (reverse(rx)*x)+rx*y+(rx*ry)*x;
	}
	
	public static int getY(ForgeDirection dir, int x, int y, int z){
		int rx = dir.offsetX;
		int ry = dir.offsetY;
		int rz = dir.offsetZ;
		
		return rx*x+ry*y+rz*z+(reverse(rx+ry+rz)*y);
	}
	
	public static int getZ(ForgeDirection dir, int x, int y, int z){
		int rx = dir.offsetX;
		int ry = dir.offsetY;
		int rz = dir.offsetZ;
		
		return (reverse(rz)*z)+rz*y+(rz*ry)*z;
	}
	
	public void updateEntity() {
		tick++;
		this.blockType = worldObj.getBlock(xCoord, yCoord, zCoord);
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection dir = ForgeDirection.getOrientation(meta);
        
		if(tick % 100 == 0 && this.blockType != null){
			CrootModule structure = ((BlockStructer)this.blockType).getStructure();
			
			for(CrootBlock block : structure.blocks){
				int tx = getX(dir, block.x, block.y, block.z);
				int ty = getY(dir, block.x, block.y, block.z);
				int tz = getZ(dir, block.x, block.y, block.z);
				if(!worldObj.isAirBlock(xCoord+tx, yCoord+ty, zCoord+tz))
					continue;
				worldObj.setBlock(xCoord+tx, yCoord+ty, zCoord+tz, BlockRegistry.ghost);
				TileGhost ghost = (TileGhost) worldObj.getTileEntity(xCoord+tx, yCoord+ty, zCoord+tz);
				ghost.setBlock(block.block);
			}
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int meta)
    {
		if(world.isRemote)
        	return;
		CrootModule structure = ((BlockStructer)this.blockType).getStructure();
		//int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection dir = ForgeDirection.getOrientation(meta);
        
		for(CrootBlock block : structure.blocks){
			int tx = getX(dir, block.x, block.y, block.z);
			int ty = getY(dir, block.x, block.y, block.z);
			int tz = getZ(dir, block.x, block.y, block.z);
			if(world.isAirBlock(x+tx, y+ty, z+tz))
				continue;
			
			Block b = world.getBlock(x+tx, y+ty, z+tz);
			if(b == BlockRegistry.ghost)
				world.setBlockToAir(x+tx, y+ty, z+tz);
		}
        
    }
}






