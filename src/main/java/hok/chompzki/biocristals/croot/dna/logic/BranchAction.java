package hok.chompzki.biocristals.croot.dna.logic;

import hok.chompzki.biocristals.croot.dna.data.BranchMemory;
import hok.chompzki.biocristals.croot.dna.data.BranchMemory.Branching;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot_old.CrootBlock;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BranchAction extends GrowthAction {
	
	private int height = 0;
	private int radius = 0;
	private double weight = 0.0D;
	private double strength = 0.0D;
	
	private BranchMemory mem;
	
	public BranchAction(BranchMemory mem, int radius, int height, double weight, double strength){
		this.height = height;
		this.radius = radius;
		this.weight = weight / 64.0D;
		this.strength = strength / 64.0D;
		this.mem = mem;
	}
	
	public int getLevel(int range){
		return (int) Math.ceil(Math.sqrt(range)*(32.0D/64.0D-weight)/(strength));
	}
	
	
	@Override
	public void act(World world, int x2, int y2, int z2, TreeMemory memory) {
		
		for(Branching pair : mem.branch_bases){	
			pair.act(world, x2, height, z2, memory, radius, weight, strength);
		}
	}

}
