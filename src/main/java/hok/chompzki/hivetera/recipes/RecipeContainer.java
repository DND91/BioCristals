package hok.chompzki.hivetera.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeContainer{
	public String code;
	public ItemStack output;
	public Object[] input;
	public HashMap<Character, Object> idToObject = new HashMap<Character, Object>();
	public HashMap<Character, List<ItemStack>> idToItem = new HashMap<Character, List<ItemStack>>();
	public Character[][] craftingGrid = null;
	public int length = 0;
	
	public RecipeContainer(String code, ItemStack out, Object[] in){
		this.code = code;
		this.output = out;
		this.input = in;
		length = ((String)in[0]).length(); //TODO: ERROR IN LENGTH CALCULATION LEADS TO CRASH!
		for(int i = length; i < in.length-1; i += 2){
			//System.out.println("RESOURCE: " + in[i] + "; " + in[i+1]);
			if(in[i+1] instanceof ItemStack){
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				list.add((ItemStack)in[i+1]);
				idToItem.put((Character)in[i], list);
				idToObject.put((Character)in[i], in[i+1]);
			} else if(in[i+1] instanceof String){
				String str = (String)in[i+1];
				if(str.equals("empty")){
					idToItem.put((Character)in[i], null);
					idToObject.put((Character)in[i], "empty");
				} else{
					List<ItemStack> list = OreDictionary.getOres(str, true);
					idToItem.put((Character)in[i], list);
					idToObject.put((Character)in[i], str);
				}
			} else if (in[i+1] instanceof OreDictContainer){
				ArrayList<ItemStack> list = OreDictionary.getOres(((OreDictContainer)in[i+1]).oreName);
				idToItem.put((Character)in[i], list);
				idToObject.put((Character)in[i], in[i+1]);
			} else if (in[i+1] instanceof Item){
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				list.add(new ItemStack((Item)in[i+1]));
				idToItem.put((Character)in[i], list);
				idToObject.put((Character)in[i], in[i+1]);
			} else if (in[i+1] instanceof Block){
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				list.add(new ItemStack((Block)in[i+1]));
				idToItem.put((Character)in[i], list);
				idToObject.put((Character)in[i], in[i+1]);
			} //else
				//System.out.println("MISS! " + in[i] + ": " + in[i+1]);
			
			
		}
		craftingGrid = new Character[length][length];
		for(int y = 0; y < length; y++){
			for(int x = 0; x < length; x++){
				craftingGrid[y][x] = ((String)in[y]).charAt(x);
			}
		}
	}

	public boolean fitsInSlot(int slot, ItemStack stack){
		int y = slot / length;
		int x = slot % length;
		
		Character c = craftingGrid[y][x];
		List<ItemStack> stacks = idToItem.get(c);
		if(stacks == null && stack == null)
			return true;
		if(stacks == null)
			return false;
		
		for(ItemStack s : stacks){
			if(OreDictionary.itemMatches(stack, s, false))
				return true;
		}
		
		return false;
	}
	
	public String getString(int slot){
		int y = slot / length;
		int x = slot % length;
		Object obj = idToObject.get(craftingGrid[y][x]);
		
		if(obj instanceof String)
			return (String)obj;
		else if(obj instanceof Item){
			Item item = (Item)obj;
			return GameData.getItemRegistry().getNameForObject(item);
		} else if(obj instanceof Block){
			Block block = (Block)obj;
			return GameData.getBlockRegistry().getNameForObject(block);
		} else if(obj instanceof ItemStack){
			ItemStack stack = (ItemStack)obj;
			return stack.stackSize + "x" + GameData.getItemRegistry().getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		} else if(obj instanceof OreDictContainer){
			OreDictContainer ore = (OreDictContainer)obj;
			return ore.quantity + "x" + ore.oreName;
		}
		
		return "empty";
	}

	public List<ItemStack> getItemStack(int slot) {
		int y = slot / length;
		int x = slot % length;
		if(length <= y)
			return null;
		return idToItem.get(craftingGrid[y][x]);
	}
}