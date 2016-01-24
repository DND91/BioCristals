package hok.chompzki.hivetera.recipes;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipePurifier {
	
	public String name;
	public ItemStack filter;
	public String code;
	public ItemStack[] result;
	public Object[] cost;
	public Integer time;
	
	public RecipePurifier(String name, ItemStack filter, String code, Object[] input, ItemStack[] output, Integer time){
		this.name = name;
		this.filter = filter;
		this.result = output;
		this.cost = input;
		this.code = code;
		this.time = time;
	}
	
	public boolean affordsItemStack(ItemStack stack, IInventory inv){
		
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			if(slot != null && OreDictionary.itemMatches(stack, slot, false) && stack.stackSize <= slot.stackSize){
				return true;
			}
		}
		return false;
	}
	
	public void payItemStack(ItemStack stack, IInventory inv){
		
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			if(slot != null && OreDictionary.itemMatches(stack, slot, false) && stack.stackSize <= slot.stackSize){
				inv.decrStackSize(i, stack.stackSize);
				return;
			}
		}
	}
	
	public boolean affords(IInventory[] inputs){
		for(Object need : cost){
			boolean hasPrice = false;
			
			if(need instanceof String){
			}else if(need instanceof ItemStack){
				ItemStack stack = (ItemStack)need;
				
				for(IInventory inventory : inputs){
					if(affordsItemStack(stack, inventory)){
						hasPrice = true;
						break;
					}
				}
			}else if(need instanceof Item){
				Item item = (Item)need;
				
				for(IInventory inventory : inputs){
					if(affordsItemStack(item, inventory)){
						hasPrice = true;
						break;
					}
				}
			}else if(need instanceof Block){
				Block block = (Block)need;
				for(IInventory inventory : inputs){
					if(affordsItemStack(Item.getItemFromBlock(block), inventory)){
						hasPrice = true;
						break;
					}
				}
			}else if(need instanceof OreDictContainer){
				OreDictContainer oreDict = (OreDictContainer)need;
				for(IInventory inventory : inputs){
					if(affordsItemStack(oreDict, inventory)){
						hasPrice = true;
						break;
					}
				}
			}
			
			if(!hasPrice)
				return false;
		}
		return true;
	}
	
	private boolean affordsItemStack(OreDictContainer oreDict,
			IInventory inv) {
		List<ItemStack> stacks = OreDictionary.getOres(oreDict.oreName);
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			for(ItemStack stack : stacks){
				if(OreDictionary.itemMatches(stack, slot, false) && oreDict.quantity <= slot.stackSize){
					return true;
				}
			}
		}
		return false;
	}

	private boolean affordsItemStack(Item item, IInventory inv) {
		
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			if(slot != null && slot.getItem() == item && 1 <= slot.stackSize){
				return true;
			}
		}
		return false;
	}

	public void pay(IInventory[] inputs){
		for(Object need : cost){
			if(need instanceof String){
			}else if(need instanceof ItemStack){
				ItemStack stack = (ItemStack)need;
				
				for(IInventory inventory : inputs){
					if(affordsItemStack(stack, inventory)){
						payItemStack(stack, inventory);
						break;
					}
				}
			}else if(need instanceof Item){
				Item item = (Item)need;
				
				for(IInventory inventory : inputs){
					if(affordsItemStack(item, inventory)){
						payItemStack(item, inventory);
						break;
					}
				}
			}else if(need instanceof Block){
				Block block = (Block)need;
				for(IInventory inventory : inputs){
					if(affordsItemStack(Item.getItemFromBlock(block), inventory)){
						payItemStack(Item.getItemFromBlock(block), inventory);
						break;
					}
				}
			}else if(need instanceof OreDictContainer){
				OreDictContainer oreDict = (OreDictContainer)need;
				for(IInventory inventory : inputs){
					if(affordsItemStack(oreDict, inventory)){
						payItemStack(oreDict, inventory);
						break;
					}
				}
			}
		}
	}
	
	private void payItemStack(OreDictContainer oreDict, IInventory inv) {
		List<ItemStack> stacks = OreDictionary.getOres(oreDict.oreName);
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			for(ItemStack stack : stacks)
				if(OreDictionary.itemMatches(stack, slot, false) && oreDict.quantity <= slot.stackSize){
					inv.decrStackSize(i, oreDict.quantity);
					return;
				}
		}
	}

	private void payItemStack(Item item, IInventory inv) {
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			if(slot != null && slot.getItem() == item && 1 <= slot.stackSize){
				inv.decrStackSize(i, 1);
				return;
			}
		}
	}

	public ItemStack[] result(){
		return result;
	}
	
	public ItemStack filter(){
		return filter;
	}
	
	public String code(){
		return code;
	}
	
	public Integer time(){
		return time;
	}
	
	public String name(){
		return name;
	}
	
	public static boolean areItemStacksEqual(ItemStack p_77989_0_, ItemStack p_77989_1_)
    {
        return p_77989_0_ == null && p_77989_1_ == null ? true : (p_77989_0_ != null && p_77989_1_ != null ? isItemStackEqual(p_77989_0_, p_77989_1_) : false);
    }
	
    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private static boolean isItemStackEqual(ItemStack p_77989_0_, ItemStack p_77959_1_)
    {
        return p_77989_0_.getItem() != p_77959_1_.getItem() ? false : (p_77989_0_.getItemDamage() != p_77959_1_.getItemDamage() ? false : (p_77989_0_.stackTagCompound == null && p_77959_1_.stackTagCompound != null ? false : p_77989_0_.stackTagCompound == null || p_77989_0_.stackTagCompound.equals(p_77959_1_.stackTagCompound)));
    }
}