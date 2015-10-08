package hok.chompzki.biocristals.croot.dna;

import hok.chompzki.biocristals.croot.dna.data.DNA;
import hok.chompzki.biocristals.croot.dna.logic.Interpeter;
import hok.chompzki.biocristals.croot.dna.logic.InterpeterBranches;
import hok.chompzki.biocristals.croot.dna.logic.InterpeterTrunk;

import java.util.ArrayList;
import java.util.List;

public class GenomeHandler {
	//Handle what interpeter should be next in action! :)
	private DNA dna = null;
	private int currentPos = 0;
	private List<Interpeter> interpeters = new ArrayList<Interpeter>();
	
	
	public GenomeHandler(String d){
		this.dna = new DNA(parseDNA(d));
		int length = 0;
		InterpeterTrunk trunk = new InterpeterTrunk(dna, length); 
		interpeters.add(trunk);
		length += dna.getUse();
		//interpeters.add(new InterpeterCrown(dna, length));
		//length += dna.getUse();
		interpeters.add(new InterpeterBranches(dna, length, trunk));
		length += dna.getUse();
	}
	
	private String parseDNA(String d){
		return d;
	}
	
	public Interpeter next(){
		Interpeter interpeter = interpeters.get(currentPos);
		currentPos++;
		currentPos %= interpeters.size();
		return interpeter;
	}
	
	
}
