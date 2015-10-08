package hok.chompzki.biocristals.croot.dna.logic;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import hok.chompzki.biocristals.croot.dna.data.DNA;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;

public class InterpeterCrown extends Interpeter {
	
	public static Random random = new Random("TRUNK".hashCode());
	
	private int fluffyness; //Number of leaves
	private int stength; //How much it can hold together, how big can a crown be
	private int weight; //How much down drag it will recive
	
	public InterpeterCrown(DNA dna, int start) {
		super(dna, start);
		fluffyness = dna.next().getInteger();
		stength = dna.next().getInteger();
		weight = dna.next().getInteger();
		
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
		
		for(int i = y; i < (y+fluffyness) && i < 200; i++){
			if(this.fits(world, x, i, z, memory, 0)){
				return i-1;
			}
		}
		return y;
	}
	
	public boolean isCompleted(TreeMemory memory, World world, int x, int y, int z, int r){
		int maxHeight = (int) ((calculateHeight(r)));
		for(int i = y; i < (y+maxHeight) && i < 200; i++){
			if(this.fits(world, x, i, z, memory, r)){
				return false;
			}
		}
		return true;
	}
	
	
	public double calculateChance(int cr){
		int r = Math.max(stength, 1);
		int w = Math.max(weight, 1);
		return Math.cos(Math.PI*((double)cr)/(2.0D*((double)r)*((double)w)));
	}
	
	public double calculateHeight(int cr){
		int r = Math.max(stength, 1);
		return Math.cos(Math.PI*((double)cr)/(2.0D*((double)r)*((double)(64-weight))))* (double)fluffyness;
	}
		
	@Override
	public GrowthAction action(TreeMemory memory, World world, int x, int y, int z) {
		//height, radius, weight
		
		//Weighted approach
		
		int cr = 0;
		int cw = 0;
		//int mw = weight;
		
		double dw = (double)weight / 64.0D;
		int topY = this.getTopCoord(memory, world, x, y, z);
		if(random.nextGaussian() <= dw){ //radius
			cr = 1;
			
			for(; cr <= stength; cr++){
				double chance = calculateChance(cr);
				boolean completed = this.isCompleted(memory, world, x, y, z, cr);
				
				if(!completed && random.nextGaussian() <= chance){
					int maxHeight = (int) (calculateHeight(cr));
					return new CrownAction(cr, maxHeight);
				}
			}
			
		}else if (topY < fluffyness){ //height
			for(;!this.fits(world, x, topY, z, memory, cr) && cr <= stength; cr++);
			cr--;
			cr = MathHelper.clamp_int(cr, 0, stength);
			return new CrownAction(cr, fluffyness);
		}
		
		
		
		return null;
	}

}












