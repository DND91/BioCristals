package hok.chompzki.biocristals.recipes;

import net.minecraft.item.ItemStack;

public class TransformerContainer {
	
	public ItemStack output;
	public String code;
	public ItemStack input;
	
	public TransformerContainer(ItemStack input, ItemStack output, String code){
		this.input = input;
		this.output = output;
		this.code = code;
	}
}
