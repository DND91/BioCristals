package hok.chompzki.hivetera.croot.power;

import hok.chompzki.hivetera.CrootHelper;
import hok.chompzki.hivetera.api.ICroot;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TreeForm implements Serializable {
	
	public final WorldCoord coreCoord;
	public final HashSet<WorldCoord> crootCoords = new HashSet<WorldCoord>();
	
	public int totalPower = 0;
	public int powerUsage = 0;
	
	private boolean cleaned = false;
	
	public TreeForm(TileEntity te){
		this(te.xCoord, te.yCoord, te.zCoord, te.getWorldObj().provider.dimensionId);
	}
	
	public TreeForm(int x, int y, int z, int dimId){
		this(new WorldCoord(x, y, z, dimId));
	}
	
	public TreeForm(WorldCoord coords){
		coreCoord = coords;
	}
	
	public int getPowerTotal() {
		return totalPower;
	}
	
	public int getPowerUsage(){
		return powerUsage;
	}
	
	public int getFreePower(){
		return this.totalPower - this.powerUsage; 
	}
	
	public boolean getStabel(){
		if(cleaned){
			return false;
		}
		return powerUsage <= totalPower;
	}
	
	public void addPower(int value){
		if(value < 0)
			this.powerUsage -= value;
		else if(0 < value)
			this.totalPower += value;
	}
	
	public void removePower(int value){
		if(value < 0)
			this.powerUsage += value;
		else if(0 < value)
			this.totalPower -= value;
	}

	public boolean unregister(World world, WorldCoord coord, int value) {
		if(cleaned){
			return false;
		}
		if(crootCoords.contains(coord)){
			TileEntity te = coord.getTile(world);
			if(te != null && te instanceof ICroot){
				ICroot croot = (ICroot)te;
				croot.unsetCore();
			}
			crootCoords.remove(coord);
			removePower(value);
			updateAfterRemove(world);
			return true;
		}
		return false;
	}

	public boolean register(World world, WorldCoord coord) {
		if(cleaned){
			return false;
		}
		if(!crootCoords.contains(coord)){
			crootCoords.add(coord);
			updateAfterAdd(world, coord);
			return true;
		}
		return false;
	}

	public void updateAfterRemove(World worldObj) {
		
		TileEntity te = null;
		
		Set<WorldCoord> unsafe = CrootHelper.unsafe(worldObj, crootCoords, coreCoord);
		
		for(WorldCoord coord : unsafe){
			if(coord.equals(coreCoord) || !worldObj.blockExists(coord.x, coord.y, coord.z))
				continue;
			
			te = coord.getTile(worldObj);
			if(te != null && te instanceof ICroot){
				ICroot croot = (ICroot)te;
				this.removePower(croot.getValue());
				croot.unsetCore();
			}
			crootCoords.remove(coord);
		}
	}

	public void updateAfterAdd(World worldObj, WorldCoord added) {
		Set<WorldCoord> unregistered = CrootHelper.unregistered(worldObj, added);
		
		for(WorldCoord coord : unregistered){
			if(coord.equals(coreCoord) || !worldObj.blockExists(coord.x, coord.y, coord.z))
				continue;
			
			TileEntity te = coord.getTile(worldObj);
			
			if(te != null && te instanceof ICroot){
				ICroot croot = (ICroot)te;
				this.addPower(croot.getValue());
				croot.setCore(coreCoord);
			}
			
			crootCoords.add(coord);
		}
	}

	public void updateOnNeighbor(World worldObj) {
		// TODO Auto-generated method stub
		
	}

	public void clean(World worldObj) {
		WorldCoord[] list = this.crootCoords.toArray(new WorldCoord[crootCoords.size()]);
		for(WorldCoord coord : list){
			this.unregister(worldObj, coord, coord.getValue(worldObj));
		}
		cleaned = true;
	}

	public void print() {
		System.out.println("MEMBERS: " + this.crootCoords.size());
		System.out.println("CLEANED: " + this.cleaned);
	}
}
