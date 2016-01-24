package hok.chompzki.hivetera.recipes;

import net.minecraft.item.ItemStack;

public class PurifierContainer {
	
	public String name;
	public ItemStack filter;
	public String code;
	public ItemStack[] output;
	public Object[] input;
	public Integer time;
	
	public PurifierContainer(String name, ItemStack filter, String code, Object[] input, ItemStack[] output, Integer time){
		this.name = name;
		this.filter = filter;
		this.code = code;
		this.input = input;
		this.output = output;
		this.time = time;
	}
	
}
