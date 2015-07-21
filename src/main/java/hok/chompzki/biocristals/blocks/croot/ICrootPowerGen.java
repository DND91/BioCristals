package hok.chompzki.biocristals.blocks.croot;

public interface ICrootPowerGen {
	
	public void breakTile();
	
	public float getFreePower();
	
	public float getTotalPower();
	
	public void unregister(ICrootPowerCon consumer);
	
	public void register(ICrootPowerCon consumer);
	
	public void unregister(ICrootPowerMem member);
	
	public void register(ICrootPowerMem member);
	
	public boolean stable();
	
	public int getX();
	
	public int getY();
	
	public int getZ();
}
