package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCrootBeetle extends Slot {
	
	public SlotCrootBeetle(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack.getItem() == ItemRegistry.crootBeetle);
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
}

