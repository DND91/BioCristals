package hok.chompzki.biocristals.client.slots;

import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.items.insects.ItemHivebag;
import hok.chompzki.biocristals.items.token.ItemToken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SlotEater extends Slot {
	
	public SlotEater(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack.getItem() instanceof ItemFood) || (stack.getItem() instanceof ItemToken) && (
				((IToken)stack.getItem()).getType(stack) == EnumToken.EATER
				|| ((IToken)stack.getItem()).getType(stack) == EnumToken.BANK);
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
}

