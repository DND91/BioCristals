package hok.chompzki.biocristals.blocks.croot;

public interface ICrootPowerMem {

	public void setCore(int xCoord, int yCoord, int zCoord);

	public int getProduction();
	
	public ICrootPowerGen genCore();
	
	public void updateConntection();
	
	public void unsetCore();

	public int getX();

	public int getY();

	public int getZ();

}
