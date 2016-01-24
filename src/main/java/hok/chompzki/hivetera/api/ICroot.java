package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.croot.power.TreeForm;
import hok.chompzki.hivetera.croot.power.WorldCoord;


public interface ICroot {

	public void setCore(WorldCoord coord);

	public int getValue();
	
	public ICrootCore genCore();
	
	public void updateConntection();
	
	public void unsetCore();

	public int getX();

	public int getY();

	public int getZ();
	
	public int getDim();
	
	public WorldCoord getCoords();

	public void print();

	public boolean stable();

	public TreeForm getForm();
}
