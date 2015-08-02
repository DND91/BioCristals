package hok.chompzki.biocristals.recipes;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class CrootRecipeContainer extends RecipeContainer{
	
	public CrootRecipeContainer(String code, ItemStack out, Object[] in){
		super();
		this.code = code;
		this.output = out;
		this.input = in;
		length = ((String)in[0]).length();
		for(int i = length; i < in.length-1; i += 2){
			idToItem.put((Character)in[i], (ItemStack)in[i+1]);
		}
		craftingGrid = new Character[length][length];
		for(int y = 0; y < length; y++){
			for(int x = 0; x < length; x++){
				craftingGrid[y][x] = ((String)in[y]).charAt(x);
			}
		}
	}
	
	public ItemStack getItemStack(int slot){
		int y = slot / length;
		int x = slot % length;
		return idToItem.get(craftingGrid[y][x]);
	}
}