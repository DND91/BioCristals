package hok.chompzki.biocristals.recipes;

import net.minecraft.item.ItemStack;

public class RecipeContainer{
	public ItemStack output;
	public Object[] input;
	
	public RecipeContainer(ItemStack out, Object[] in){
		this.output = out;
		this.input = in;
	}
}