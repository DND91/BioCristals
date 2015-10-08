package hok.chompzki.biocristals.croot.dna.data;

import java.util.ArrayList;
import java.util.List;

public class DNA {
	
	private String originalDNA = "";
	private List<Codon> codons = new ArrayList<Codon>();
	private int currentPos = 0;
	private int use = 0;
	
	public DNA(String dna){
		this.originalDNA = dna;
		String[] splitedDNA = dna.split(" ");
		for(String data : splitedDNA){
			Codon codon = new Codon(data);
			codons.add(codon);
		}
	}
	
	public Codon next(){
		Codon codon = codons.get(currentPos);
		currentPos++;
		currentPos %= codons.size();
		use++;
		return codon;
	}
	
	public void set(int pos){
		currentPos = pos;
		currentPos %= codons.size();
		if(currentPos < 0){
			currentPos = codons.size() + currentPos;
		}
		use = 0;
	}
	
	public int get(){
		return currentPos;
	}

	public int getUse() {
		int u = use;
		use = 0;
		return u;
	}
}
