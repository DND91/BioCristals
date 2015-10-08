package hok.chompzki.biocristals.croot.dna;

import net.minecraft.world.World;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot.dna.logic.GrowthAction;
import hok.chompzki.biocristals.croot.dna.logic.Interpeter;

public class GrowthSystem {
	
	private GenomeHandler genome = null;
	
	public GrowthSystem(String dna){
		genome = new GenomeHandler(dna);
		
	}
	
	public GrowthAction getAction(TreeMemory memory, World world, int x, int y, int z){
		Interpeter interpeter = genome.next();
		return interpeter.action(memory, world, x, y, z);
	}
}
