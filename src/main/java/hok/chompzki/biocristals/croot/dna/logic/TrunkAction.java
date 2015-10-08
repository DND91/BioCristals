package hok.chompzki.biocristals.croot.dna.logic;

import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot_old.CrootBlock;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TrunkAction extends GrowthAction {
	
	private int maxHeight = 0;
	private int radius = 0;
	
	public TrunkAction(int radius, int maxHeight){
		this.maxHeight = maxHeight;
		this.radius = radius;
	}
	
	private boolean fits(World world, int x, int y, int z, TreeMemory memory){
		
		DNACoord left = new DNACoord(x - radius, y, z, world.provider.dimensionId);
		DNACoord right = new DNACoord(x + radius, y, z, world.provider.dimensionId);
		DNACoord front = new DNACoord(x, y, z + radius, world.provider.dimensionId);
		DNACoord back = new DNACoord(x, y, z - radius, world.provider.dimensionId);
		
		return !memory.contains(left) && 
				!memory.contains(right) && 
				!memory.contains(front) && 
				!memory.contains(back);
	}
	
	@Override
	public void act(World world, int x, int y2, int z, TreeMemory memory) {
		for(int k = 0; k < maxHeight; k++){
			int y = y2 + k;
			if(fits(world, x, y, z, memory)){
				int radiusOutsideSquared = radius * radius;
				int radiusInsideSquared = (radius-1) * (radius-1);
				
		        for(int i = radius * -1; i <= radius; ++i)
		        {
		            for(int j = radius * -1; j <= radius; ++j)
		            {
		            	int distance = ((i * i) + (j * j));
		            	
		        		if(distance <= radiusOutsideSquared){
		        			this.placeBlock(world, Blocks.log, 0, x+i, y, z+j, memory);
		        		}
		            }
		        }
		        return;
			}
		}
	}

}
