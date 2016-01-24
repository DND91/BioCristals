package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.items.insects.ItemHivebag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAntiHivebag extends Slot {
	
	int cookTime = 0;
	
	public SlotAntiHivebag(int cookTime, IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		this.cookTime = cookTime;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return !(stack.getItem() instanceof ItemHivebag) && cookTime == 0;
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return cookTime == 0;
    }
}

