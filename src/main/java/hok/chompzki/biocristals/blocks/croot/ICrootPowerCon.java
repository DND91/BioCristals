package hok.chompzki.biocristals.blocks.croot;

public interface ICrootPowerCon {
	
	public float getConsumption();
	
	public int getX();
	
	public int getY();
	
	public int getZ();

	void setCore(int xCoord, int yCoord, int zCoord);

	ICrootPowerGen genCore();

	void updateConntection();

	void unsetCore();
	
	public boolean hasPower();
}
