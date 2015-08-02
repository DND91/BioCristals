package hok.chompzki.biocristals.recipes;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class RecipeContainer{
	public String code;
	public ItemStack output;
	public Object[] input;
	public HashMap<Character, ItemStack> idToItem = new HashMap<Character, ItemStack>();
	public Character[][] craftingGrid = null;
	public int length = 0;
	
	public RecipeContainer(String code, ItemStack out, Object[] in){
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
	
	public RecipeContainer() {
		// TODO Auto-generated constructor stub
	}

	public ItemStack getItemStack(int slot){
		int y = slot / length;
		int x = slot % length;
		return idToItem.get(craftingGrid[y][x]);
	}
}