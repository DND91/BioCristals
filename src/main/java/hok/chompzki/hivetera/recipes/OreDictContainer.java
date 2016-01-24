package hok.chompzki.hivetera.recipes;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictContainer {
	
	public String oreName = null;
	public int quantity = 1;
	public int meta = OreDictionary.WILDCARD_VALUE;
	
	public OreDictContainer(String ore){
		oreName = ore;
	}
	
	public OreDictContainer(String ore, int quantity){
		oreName = ore;
		this.quantity = quantity;
	}
	
	public OreDictContainer(String ore, int quantity, int meta){
		oreName = ore;
		this.quantity = quantity;
		this.meta = meta;
	}
	
	@Override
	public String toString(){
		return this.quantity + "x" + oreName + "@" + (meta == OreDictionary.WILDCARD_VALUE ? "WILDCARD" : meta);
	}
}
