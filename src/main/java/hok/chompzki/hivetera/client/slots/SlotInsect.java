package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInsect extends Slot {
	
	public SlotInsect(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack.getItem() instanceof IInsect);
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
}

