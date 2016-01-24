package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.items.token.ItemToken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SlotFeeder extends Slot {
	
	public SlotFeeder(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack.getItem() instanceof ItemToken) && (
				((IToken)stack.getItem()).getType(stack) == EnumToken.FEEDER
				|| ((IToken)stack.getItem()).getType(stack) == EnumToken.BANK);
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
}

