package hok.chompzki.hivetera.croot.building;

import java.util.Comparator;

public class CrootComparator implements Comparator<CrootBlock> {
	
	public CrootComparator(){
		
	}
	
    @Override
    public int compare(CrootBlock a, CrootBlock b) {
    	if(a == b)
    		return 0;
    	if(a.priority < b.priority)
    		return -1;
    	if(b.priority < a.priority)
    		return 1;
    	
    	int ad = a.x * a.x + a.y * a.y + a.z * a.z;
    	int bd = b.x * b.x + b.y * b.y + b.z * b.z;
    	
    	if(ad < bd)
    		return -1;
    	if(bd < ad)
    		return 1;
    	return 0;
    }
}
