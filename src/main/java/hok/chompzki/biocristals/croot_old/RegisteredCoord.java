package hok.chompzki.biocristals.croot_old;

import hok.chompzki.biocristals.croot.ICrootCore;
import hok.chompzki.biocristals.croot.ICroot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RegisteredCoord{
	
	public int x;
	public int y;
	public int z;
	private World world;
	
	public RegisteredCoord(World world, int x, int y, int z){
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public TileEntity getTile(){
		return world.getTileEntity(x, y, z);
	}
	
	public boolean isValid(){
		TileEntity te = getTile();
		if(te == null)
			return false;
		if((te instanceof ICroot || te instanceof ICrootCore)){
			return true;
		}
		return false;
	}
	
	public boolean hasCore(){
		if(!isValid())
			return false;
		TileEntity te = getTile();
		if(te instanceof ICroot){
			ICroot mem = (ICroot) te;
			return mem.genCore() != null;
		}
		
		return false;
	}
	
	
	@Override
    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if(obj == this)
    		return true;
    	if(!(obj instanceof RegisteredCoord))
    			return false;
    	RegisteredCoord b = (RegisteredCoord) obj;
    	
    	return b.x == this.x &&
    			b.y == this.y &&
    			b.z == this.z;
    }
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        x = nbt.getInteger("X");
        y = nbt.getInteger("Y");
        z = nbt.getInteger("Z");
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("X",  x);
        nbt.setInteger("Y",  y);
        nbt.setInteger("Z",  z);
    }

	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld(){
		return world;
	}
	
	@Override
	public int hashCode(){
		return y;
	}
}
