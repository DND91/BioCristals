package hok.chompzki.biocristals.croot.dna.logic;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import hok.chompzki.biocristals.croot.dna.data.BranchMemory;
import hok.chompzki.biocristals.croot.dna.data.DNA;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;

public class InterpeterBranches extends Interpeter {
	
	public static Random random = new Random();
	
	private int length; //Length of branches
	private int strength; //How much it can hold together, how big can a crown be
	private int weight; //How much down drag it will recive
	
	private int trunkHeight = 0;
	private int trunkRadius = 0;
	private int currentHeight = 0;
	
	//ceil(sqrt(x)*(32/64-weight)/(strength/64))
	
	//Length, Weight, Twist Chance, 
	//Branch distance, Branch offset, Branch chance 
	
	private double ccb = 0.0D;
	private double gcb = 0.0D;
	
	public InterpeterBranches(DNA dna, int start, InterpeterTrunk trunk) {
		super(dna, start);
		trunkHeight = trunk.getHeight();
		trunkRadius = trunk.getRadius();
		ccb = ((double)dna.next().getInteger()) / 64.0D;
		gcb = ((double)dna.next().getInteger()) / 64.0D;
		
		length = dna.next().getInteger();
		length = 30;
		strength = dna.next().getInteger();
		strength = 20;
		weight = dna.next().getInteger();
		weight = 40;
		
	}
	
	private boolean fits(World world, int x, int y, int z, TreeMemory memory, int radius){
		DNACoord left = new DNACoord(x - radius, y, z, world.provider.dimensionId);
		DNACoord right = new DNACoord(x + radius, y, z, world.provider.dimensionId);
		DNACoord front = new DNACoord(x, y, z + radius, world.provider.dimensionId);
		DNACoord back = new DNACoord(x, y, z - radius, world.provider.dimensionId);
		
		return !(memory.contains(left) && 
				memory.contains(right) && 
				memory.contains(front) && 
				memory.contains(back));
	}
	
	public int getTopCoord(TreeMemory memory, World world, int x, int y, int z){
		
		for(int i = y; i < (y+trunkHeight) && i < 200; i++){
			if(this.fits(world, x, i, z, memory, 0)){
				return i-1;
			}
		}
		return y;
	}
	
	
		
	@Override
	public GrowthAction action(TreeMemory memory, World world, int x, int y, int z) {
		//Generate next branching
		int tc = getTopCoord(memory, world, x, y, z);
		if(currentHeight < y)
			currentHeight = y + 5;
		
		if(this.currentHeight < tc){
			for(;this.currentHeight <= tc; this.currentHeight++){
				
				if(ccb < random.nextGaussian()){
					//Generate branch!
					
					ccb = 1.0D;
					BranchMemory mem = new BranchMemory(currentHeight, trunkRadius, 8, length,random);
					memory.branches.add(mem);
					return new BranchAction(mem, length, currentHeight, weight, strength);
				}
				this.ccb -= this.gcb;
			}
		}
		if(this.currentHeight == trunkHeight){
			BranchMemory mem = new BranchMemory(currentHeight, trunkRadius, 8, length,random);
			memory.branches.add(mem);
			return new BranchAction(mem, length, currentHeight++, weight, strength);
		}
		//Grow branch!!!
		
		
		
		return null;
	}

}












