package hok.chompzki.biocristals.recipes;

import net.minecraft.item.ItemStack;

public class PurifierContainer {
	
	public String name;
	public ItemStack filter;
	public String code;
	public ItemStack[] output;
	public ItemStack[] input;
	public Integer time;
	
	public PurifierContainer(String name, ItemStack filter, String code, ItemStack[] input, ItemStack[] output, Integer time){
		this.name = name;
		this.filter = filter;
		this.code = code;
		this.input = input;
		this.output = output;
		this.time = time;
	}
	
}