package hok.chompzki.biocristals.registrys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeTransformer {
	
	
	
	public static ItemStack dataToItemStack(String data){
		
		String name;
	    String domain = null;
	    int meta = 0;

	    String item;
	    int colonIndex;
	    int metaIndex;
		
		if (data.equals("empty")){
            return null;
        }else if (data.contains(":")) {

            colonIndex = data.indexOf(':');
            domain = data.substring(0, colonIndex);
            item = data.substring(colonIndex + 1, data.length());
            metaIndex= item.indexOf(':');

            if (metaIndex >= 0) {
                meta = Integer.parseInt(item.substring(meta + 1, item.length()));
                name = item.substring(0, meta);
            } else {
                name = item;
                meta = 0;
            }
        }else if (!data.contains(":") ||!data.contains(".")) {
            name = data;
        }else{
        	return null;
        }
		
		
		ItemStack stack = new ItemStack(Blocks.command_block);
        if (name != null && domain == null) {
            ArrayList<ItemStack> orename = OreDictionary.getOres(name);
            if (orename.size() > 0) {
            	stack = orename.get(0);
            }
        } else if(name != null && domain != null) {
        	Item i = (Item)Item.itemRegistry.getObject(domain + ":" + name);
        	if(i == null){
        		i = GameRegistry.findItem(domain, name);
        	}
        	
        	stack = new ItemStack(i, 1, meta);
        	if(stack.getItem() == null)
        		return null;
        }else{
        	stack=null;
        }
        return stack;
	}
	
	public static ItemStack[] dataToItemStacks(String[] data){ 
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for(String str : data){
			stacks.add(dataToItemStack(str));
		}
		
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	private static final int leepstep = 1000000;
	
	public static Object[] stacksToRecipe(ItemStack[] stacks){
		HashMap<ItemStack, Integer> itemToID = new HashMap<ItemStack, Integer> ();
		HashMap<Integer, Character> idToName = new HashMap<Integer, Character>();
		Character chr = 'A';
		for(ItemStack stack: stacks){
			if(stack == null) continue;
			int id = -1;
			int[] array = OreDictionary.getOreIDs(stack);
			if(array.length <= 0){
				id = Item.getIdFromItem(stack.getItem()); 
				id += leepstep;
			}else{
				id = array[0];
			}
			
			itemToID.put(stack, id);
			idToName.put(id, chr);
			chr++;
		}
		
		int craftSize = (int) Math.ceil(Math.sqrt(stacks.length));
		StringBuilder[] craftingPattern = new StringBuilder[craftSize];
		for(int i = 0; i < craftSize; i++)
			craftingPattern[i] = new StringBuilder(StringUtils.leftPad(" ", craftSize));
		
		for(int i = 0; i < stacks.length; i++){
			if(stacks[i] == null) continue;
			int y = i / craftSize;
			int x = i % craftSize;
			char c = idToName.get(itemToID.get(stacks[i]));
			craftingPattern[y].setCharAt(x, c);
		}
		
		ArrayList<Object> recipe = new ArrayList<Object>();
		for(StringBuilder str : craftingPattern)
			recipe.add(str.toString());
		
		
		
		for(Entry<Integer, Character> entry : idToName.entrySet()){
			ItemStack stack = null;
			int id = entry.getKey();
			
			if(leepstep <= id){
				stack = new ItemStack(Item.getItemById(id - leepstep));
			}else{
				stack = OreDictionary.getOres(entry.getKey()).get(0);
			}
			
			Character c = entry.getValue();
			recipe.add(c);
			recipe.add(stack);
		}
		
		return recipe.toArray();
	}
}
