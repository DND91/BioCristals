package hok.chompzki.biocristals.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeTransformer {
	
	
	/**
	 * 
	 * @param data domain:name or name
	 * @return
	 */
	
	private static int numberOf(String s, Character c){
		int i = 0;
		for(int j = 0; j < s.length(); j++)
			if(s.charAt(j) == c)
				i++;
		return i;
	}
	
	public static List<ItemStack> dataToItemStack(String data){
		List<ItemStack> list = new ArrayList<ItemStack>();
		String name;
	    String domain = null;
	    int meta = -1;
	    int qunatity = 1;

	    String item;
	    int colonIndex;
	    int metaIndex = -1;
		
		if(Character.isDigit(data.charAt(0))){
			String num = "";
			while(Character.isDigit(data.charAt(0))){
				num += data.charAt(0);
				data = data.substring(1);
			}
			
			if(data.charAt(0) == 'x'){
				data = data.substring(1);
			}
			qunatity = Integer.parseInt(num);
		}
		if(qunatity <= 0){
			qunatity = 1;
		}
		
		if(Character.isDigit(data.charAt(data.length()-1))){
			int p = data.length()-1;
			while(Character.isDigit(data.charAt(p))){
				p--;
			}
			
			String t = data.substring(p+1, data.length());
			data = data.substring(0, p);
			
			meta = Integer.parseInt(t);
		}else if(data.charAt(data.length()-1) == '@'){
			data = data.substring(0, data.length()-2);
			metaIndex = -1;
			meta = -1;
		}
		
		int num = numberOf(data, ':');
		
		if (num == 1) {
            colonIndex = data.indexOf(':');
            domain = data.substring(0, colonIndex);
            name = data.substring(colonIndex + 1, data.length());
        }else {
            name = data;
        }
		
		if (data.equals("empty")){
			list.add(null);
            return list;
        }
		
		if(domain == null)
			domain = "minecraft";
		
		String fullname = domain + ":" + name;
		
		Side side = FMLCommonHandler.instance().getSide();
		
		List<ItemStack> orename = OreDictionary.getOres(name, true);
		if(0 < orename.size()){
			for(ItemStack stack : orename){
				if(side != Side.SERVER && stack.getHasSubtypes() && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || meta == OreDictionary.WILDCARD_VALUE)){
					stack.stackSize = qunatity;
					stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
					list.add(stack.copy());
					
					List<ItemStack> llist = new ArrayList<ItemStack>();
					
					stack.getItem().getSubItems(stack.getItem(), CreativeTabs.tabAllSearch, llist);
					
					for(ItemStack ss : llist){
						ItemStack s = ss.copy();
						s.stackSize = qunatity;
						list.add(s);
					}
				}else{
					ItemStack s = stack.copy();
					s.stackSize = qunatity;
					if(0 <= meta)
						s.setItemDamage(meta);
					list.add(s);
				}
			}
		}
		
		
		Item i = (Item)Item.itemRegistry.getObject(fullname);
    	if(i == null){
    		i = GameRegistry.findItem(domain, name);
    	}
    	if(i != null && list.size() <= 0){
    		ItemStack stack = null;
    		if(0 <= meta)
    			stack = new ItemStack(i, qunatity, meta);
    		else
    			stack = new ItemStack(i, qunatity);
    		
    		if(side != Side.SERVER && i.getHasSubtypes() && metaIndex < 0){
				List<ItemStack> llist = new ArrayList<ItemStack>();
				
				i.getSubItems(stack.getItem(), CreativeTabs.tabAllSearch, llist);
				
				
				for(ItemStack ss : llist){
					ItemStack s = ss.copy();
					s.stackSize = qunatity;
					list.add(s);
				}
			}else
				list.add(stack);
    	}
    	
    	Block b = (Block)Block.blockRegistry.getObject(fullname);
    	if(b == null){
    		b = GameRegistry.findBlock(domain, name);
    	}
    	if(b != null && list.size() <= 0){
    		ItemStack stack = null;
    		if(0 <= meta)
    			stack = new ItemStack(b, qunatity, meta);
    		else
    			stack = new ItemStack(b, qunatity);
    		
    		if(side != Side.SERVER && stack.getItem() != null && stack.getHasSubtypes() && metaIndex < 0){
				List<ItemStack> llist = new ArrayList<ItemStack>();
				
				b.getSubBlocks(stack.getItem(), CreativeTabs.tabAllSearch, llist);
				
				for(ItemStack ss : llist){
					ItemStack s = ss.copy();
					s.stackSize = qunatity;
					list.add(s);
				}
			}else
				list.add(stack);
    	}
        
        for(ItemStack stack : list){
        	stack.stackSize = qunatity;
        }
        
        if(1 < list.size()){
        	for(int j = 0; j < list.size();){
        		if(list.get(j) == null || list.get(j).getItem() == null)
        			list.remove(j);
        		else
        			j++;
        	}
        }
        
        return list;
	}
	
	public static ItemStack[] dataToItemStacks(String[] data){ 
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for(String str : data){
			List<ItemStack> list = dataToItemStack(str);
			if(list.size() <= 1)
				stacks.add(list.get(0));
			else{
				ItemStack stack = list.get(0);
				stacks.add(stack);
			}
		}
		
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	
	
	public static Object[] dataToObjects(String[] data){ 
		ArrayList<Object> stacks = new ArrayList<Object>();
		
		for(String str : data){
			stacks.add(dataToObject(str));
		}
		
		return stacks.toArray(new Object[stacks.size()]);
	}
	
	//domain:name:meta
	//Om det finns i ore diconary måste det användas...
	//
	//
	//
	public static Object dataToObject(String d){ 
		//TODO FIX!
		String[] data = d.split(":");
		String name = null;
		String domain = null;
		int meta = 0;
		
		if(1 < data.length){
			
		}else{
			name = data[0];
		}
		return null;
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
