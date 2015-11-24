package hok.chompzki.biocristals.croot.dna.data;


import hok.chompzki.biocristals.croot.ICroot;
import hok.chompzki.biocristals.croot.ICrootCore;

import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DNACoord implements Serializable {
	
	public final int x;
	public final int y;
	public final int z;
	public final int dimId;
	private final int hashCode;
	
	public DNACoord(int x, int y, int z, int dimId){
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimId = dimId;
		hashCode = dimId;
	}
	
	@Override
    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if(obj == this)
    		return true;
    	if(!(obj instanceof DNACoord))
    			return false;
    	DNACoord b = (DNACoord) obj;
    	return b.x == x &&
    			b.y == y &&
    			b.z == z &&
    			b.dimId == dimId;
    }
	
	
    
    @Override
    public int hashCode(){
    	return hashCode;
    }

	public TileEntity getTile(World worldObj) {
		return worldObj.getTileEntity(x, y, z);
	}
	
	public int getValue(World worldObj){
		TileEntity te = getTile(worldObj);
		if(te == null || !(te instanceof ICroot))
			return 0;
		return ((ICroot)te).getValue();
	}

	public boolean isValid(World world){
		TileEntity te = getTile(world);
		if(te == null)
			return false;
		if((te instanceof ICroot || te instanceof ICrootCore)){
			return true;
		}
		return false;
	}

	public boolean hasCore(World world) {
		if(!isValid(world))
			return false;
		TileEntity te = getTile(world);
		if(te instanceof ICroot){
			ICroot mem = (ICroot) te;
			return mem.genCore() != null;
		}
		
		return false;
	}
	
	public void print(){
		System.out.println("DIM: " + this.dimId + ", X: " + this.x + ", Y: " + this.y + ", Z: " + z);
	}
}