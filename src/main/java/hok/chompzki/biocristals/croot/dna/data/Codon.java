package hok.chompzki.biocristals.croot.dna.data;

import java.util.Random;

public class Codon {
	
	//TCAG
	
	private static Random random = new Random("CODON".hashCode());
	
	private String data = "";
	private int value = 0;
	
	public Codon(String data) {
		this.data = data;
		this.value = dataToInt(data);
		
	}
	
	private int charToInt(char c){
		switch (c) {
		case 'T':
				return 0;
		case 'C':
				return 1;
		case 'A':
				return 2;
		case 'G':
				return 3;
		}
		return 0;
	}
	
	private int dataToInt(String s){
		int a = charToInt(s.charAt(0));
		int b = charToInt(s.charAt(1));
		int c = charToInt(s.charAt(2));
		return a + b * 4 + c * 16;
	}
	
	public String getString(){
		return data;
	}
	
	public int getInteger(){
		return value;
	}
	
	public char first(){
		return data.charAt(0);
	}
	
	public char second(){
		return data.charAt(1);
	}
	
	public char third(){
		return data.charAt(2);
	}
	
	public int random(){
		return random.nextInt(value + 1);
	}
}
