package hok.chompzki.biocristals.croot.dna.data;

import java.util.ArrayList;
import java.util.HashSet;

public class TreeMemory {
	
	public HashSet<DNACoord> coords = new HashSet<DNACoord>();
	public ArrayList<BranchMemory> branches = new ArrayList<BranchMemory>();
	 
	public boolean contains(DNACoord coord){
		return coords.contains(coord);
	}
	
}
