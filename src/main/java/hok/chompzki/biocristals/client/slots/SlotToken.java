package hok.chompzki.biocristals.client.slots;

import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.items.insects.ItemHivebag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotToken extends Slot {
	
	private int max = 1;
	
	public SlotToken(IInventory par1iInventory, int max, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		this.max = max;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack.getItem() instanceof IToken);
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
	
	public int getSlotStackLimit()
    {
        return max;
    }
}

