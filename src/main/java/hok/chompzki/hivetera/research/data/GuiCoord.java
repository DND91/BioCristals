package hok.chompzki.hivetera.research.data;


import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiCoord {
	
	public final int x;
	public final int y;
	public final String chapeter;
	private final int hashCode;
	
	public GuiCoord(int x, int y, String chapeter){
		this.x = x;
		this.y = y;
		this.chapeter = chapeter;
		hashCode = y;
	}
	
	@Override
    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if(obj == this)
    		return true;
    	if(!(obj instanceof GuiCoord))
    			return false;
    	GuiCoord b = (GuiCoord) obj;
    	return b.x == x &&
    			b.y == y &&
    			b.chapeter.equals(chapeter);
    }
    
    @Override
    public int hashCode(){
    	return hashCode;
    }
}
