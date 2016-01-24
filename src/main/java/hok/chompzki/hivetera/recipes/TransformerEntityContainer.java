package hok.chompzki.hivetera.recipes;

import net.minecraft.item.ItemStack;

public class TransformerEntityContainer {
	public ItemStack[] output;
	public String code;
	public Class input;
	
	public TransformerEntityContainer(Class input, String code, ItemStack... output){
		this.input = input;
		this.output = output;
		this.code = code;
	}
}
