package hok.chompzki.biocristals.croot.dna.logic;

import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class GrowthAction {

	public abstract void act(World world, int x, int y, int z, TreeMemory memory);
	
	public void placeBlock(World world, Block block, int i, int x, int y, int z,
			TreeMemory memory) {
		DNACoord coord = new DNACoord(x, y, z, world.provider.dimensionId);
		if(!memory.contains(coord)){
			world.setBlock(x, y, z, block);
			memory.coords.add(coord);
		}
	}
}
