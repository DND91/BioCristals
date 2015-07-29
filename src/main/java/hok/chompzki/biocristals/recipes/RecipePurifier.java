package hok.chompzki.biocristals.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipePurifier {
	
	public String name;
	public ItemStack filter;
	public String code;
	public ItemStack[] result;
	public ItemStack[] cost;
	public Integer time;
	
	public RecipePurifier(String name, ItemStack filter, String code, ItemStack[] input, ItemStack[] output, Integer time){
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
			if(slot != null && areItemStacksEqual(stack, slot) && stack.stackSize <= slot.stackSize)
				return true;
		}
		return false;
	}
	
	public void payItemStack(ItemStack stack, IInventory inv){
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot != null && areItemStacksEqual(stack, slot) && stack.stackSize <= slot.stackSize){
				inv.decrStackSize(i, stack.stackSize);
				return;
			}
		}
	}
	
	public boolean affords(IInventory[] inputs){
		for(ItemStack need : cost){
			boolean hasPrice = false;
			for(IInventory inventory : inputs){
				if(affordsItemStack(need, inventory)){
					hasPrice = true;
					break;
				}
			}
			if(!hasPrice)
				return false;
		}
		return true;
	}
	
	public void pay(IInventory[] inputs){
		for(ItemStack need : cost){
			for(IInventory inventory : inputs){
				if(affordsItemStack(need, inventory)){
					payItemStack(need, inventory);
					break;
				}
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