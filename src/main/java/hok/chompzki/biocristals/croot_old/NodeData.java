package hok.chompzki.biocristals.croot_old;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;

/*
 * http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 * 
 */

final class NodeData { 

    private final TileEntity nodeId;

    private double g;  // g is distance from the source
    private double h;  // h is the heuristic of destination.
    private double f;  // f = g + h 

    public NodeData (TileEntity nodeId) {
        this.nodeId = nodeId;
        this.g = Double.MAX_VALUE; 
    }

    public TileEntity getNodeId() {
        return nodeId;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }
    
    public double calcH(TileEntity d){
    	return (nodeId.xCoord * d.xCoord + nodeId.yCoord * d.yCoord + nodeId.zCoord * d.zCoord);
    }

    public void calcF(TileEntity destination) {
        this.h = calcH(destination);
        this.f = g + h;
    } 

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }
    
    @Override
    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if(obj == this)
    		return true;
    	if(!(obj instanceof NodeData))
    			return false;
    	NodeData b = (NodeData) obj;
    	TileEntity entity = b.getNodeId();
    	if(entity == this.nodeId)
    		return true;
    	if(entity == null)
    		return false;
    	return entity.xCoord == nodeId.xCoord &&
    			entity.yCoord == nodeId.yCoord &&
    			entity.zCoord == nodeId.zCoord;
    }
    
    @Override
    public int hashCode(){
    	return nodeId.yCoord;
    }
 }
