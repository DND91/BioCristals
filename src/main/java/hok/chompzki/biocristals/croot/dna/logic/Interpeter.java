package hok.chompzki.biocristals.croot.dna.logic;

import net.minecraft.world.World;
import hok.chompzki.biocristals.croot.dna.data.DNA;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;

public abstract class Interpeter {
	
	protected DNA dna = null;
	protected int start = 0;
	
	public Interpeter(DNA dna, int start){
		this.dna = dna;
		this.start = start;
		dna.set(start);
	}
	
	public abstract GrowthAction action(TreeMemory memory, World world, int x, int y, int z);

}
