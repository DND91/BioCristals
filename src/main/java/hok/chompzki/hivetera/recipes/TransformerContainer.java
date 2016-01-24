package hok.chompzki.hivetera.recipes;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TransformerContainer {
	
	public ItemStack output;
	public String code;
	public Object input;
	private List<ItemStack> trueInput = new ArrayList<ItemStack>();
	
	public TransformerContainer(Object input, ItemStack output, String code){
		this.input = input;
		this.output = output;
		this.code = code;
		
		if(input instanceof ItemStack){
			trueInput.add((ItemStack)input);
		} else if(input instanceof String){
			String str = (String)input;
			if(str.equals("empty")){
				System.out.println("TRANSOFMRATION HAS EMPTY INPUT!!!!");
			} else{
				trueInput = OreDictionary.getOres(str, true);
			}
		} else if (input instanceof OreDictContainer){
			trueInput = OreDictionary.getOres(((OreDictContainer)input).oreName);
		} else if (input instanceof Item){
			trueInput.add(new ItemStack((Item)input));
		} else if (input instanceof Block){
			trueInput.add(new ItemStack((Block)input));
		} else
			System.out.println("MISS! " + input);
	}
	
	public String getInputString(){
		if(input instanceof String)
			return (String)input;
		else if(input instanceof Item){
			Item item = (Item)input;
			return GameData.getItemRegistry().getNameForObject(item);
		} else if(input instanceof Block){
			Block block = (Block)input;
			return GameData.getBlockRegistry().getNameForObject(block);
		} else if(input instanceof ItemStack){
			ItemStack stack = (ItemStack)input;
			return stack.stackSize + "x" + GameData.getItemRegistry().getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		} else if(input instanceof OreDictContainer){
			OreDictContainer ore = (OreDictContainer)input;
			return ore.quantity + "x" + ore.oreName;
		}
		
		return "empty";
	}
	
	public boolean matchInput(ItemStack stack){
		List<ItemStack> stacks = trueInput;
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
	
	public List<ItemStack> getTrueInput(){
		return trueInput;
	}
	
}
