package hok.chompzki.biocristals.croot;

import java.util.Arrays;

public class CrootTreeData {
	
	public final String name;
	public final String[] sapling;
	public final String[] spurt;
	public final String[] tree;
	
	public CrootTreeData(String name, String[] sapling, String[] spurt, String[] tree){
		this.name = name;
		this.sapling = sapling;
		this.spurt = spurt;
		this.tree = tree;
	}
	
	public void print(){
		System.out.println("NAME: " + name);
		System.out.println("SAPLING: " + Arrays.toString(sapling));
		System.out.println("SPURT: " + Arrays.toString(spurt));
		System.out.println("TREE: " + Arrays.toString((tree)));
	}
}
