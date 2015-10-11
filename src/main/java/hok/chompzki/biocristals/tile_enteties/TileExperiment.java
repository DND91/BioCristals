package hok.chompzki.biocristals.tile_enteties;

import java.util.Random;

import hok.chompzki.biocristals.croot.dna.GrowthSystem;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot.dna.logic.GrowthAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileExperiment extends TileEntity {
	
	public static Random rand = new Random();
	
	private String generateDNAString(int codons){
        String dna="";
        String alphabet = "ACGT";
        
        for (int i=1;i<=codons;i++){
        	dna += alphabet.charAt(rand.nextInt(alphabet.length()));
        	dna += alphabet.charAt(rand.nextInt(alphabet.length()));
        	dna += alphabet.charAt(rand.nextInt(alphabet.length()));
           	dna += " ";
        }
        System.out.println("HERE:__"+dna);
        return dna;
 
    }
	
	private TreeMemory memory = new TreeMemory();
	private GrowthSystem growth = new GrowthSystem(generateDNAString(8));
	
	private int tick = 0;
	
	public TileExperiment(){
		
	}
	
	public void updateEntity() {
		tick++;
		
		if(tick % 2 == 0 && !worldObj.isRemote){
			if(memory.coords.size() <= 0){
				DNACoord coord = new DNACoord(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		        memory.coords.add(coord);
			}
			
			GrowthAction action = growth.getAction(memory, worldObj, xCoord, yCoord, zCoord);
			if(action != null)
				action.act(worldObj, xCoord, yCoord, zCoord, memory);
		}
	}
	
}
