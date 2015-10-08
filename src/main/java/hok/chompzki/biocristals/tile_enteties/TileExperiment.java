package hok.chompzki.biocristals.tile_enteties;

import hok.chompzki.biocristals.croot.dna.GrowthSystem;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot.dna.logic.GrowthAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileExperiment extends TileEntity {
	
	private TreeMemory memory = new TreeMemory();
	private GrowthSystem growth = new GrowthSystem("TCA CCT TTC AGG CTG");
	
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
