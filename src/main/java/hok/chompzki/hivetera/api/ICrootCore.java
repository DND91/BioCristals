package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.croot.power.TreeForm;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import net.minecraft.world.World;

public interface ICrootCore {
	
	public void breakTile();
	
	public TreeForm getForm();
	
	public int getX();
	
	public int getY();
	
	public int getZ();
	
	public int getDim();

	public void onNeighborChange();

	public void addTile();

	WorldCoord getCoords();
	
	public boolean isValid(World world);

	public void print();
	
}
