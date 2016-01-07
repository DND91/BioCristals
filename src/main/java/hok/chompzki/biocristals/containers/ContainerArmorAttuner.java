package hok.chompzki.biocristals.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.client.slots.SlotAntiHivebag;
import hok.chompzki.biocristals.client.slots.SlotEater;
import hok.chompzki.biocristals.client.slots.SlotInsect;
import hok.chompzki.biocristals.client.slots.SlotVisual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerArmorAttuner extends Container {
	
	public EntityPlayer player = null;
	
	public ContainerArmorAttuner(final EntityPlayer player)
    {
		this.player = player;
		
		
		for (int i = 0; i < 4; ++i)
        {
            final int k = i;
            this.addSlotToContainer(new Slot(player.inventory, player.inventory.getSizeInventory() - 1 - i, 20, 19 + i * 41)
            {
                private static final String __OBFID = "CL_00001755";
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
                 */
                public boolean isItemValid(ItemStack p_75214_1_)
                {
                    if (p_75214_1_ == null) return false;
                    return p_75214_1_.getItem().isValidArmor(p_75214_1_, k, player);
                }
            });
        }
		
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 174 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
    		this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 232 + i));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		 ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
	        
	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();
	            
	            
	            if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(0 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
	            {
	                int j = 0 + ((ItemArmor)itemstack.getItem()).armorType;
	                
	                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
	                {
	                    return null;
	                }
	            }else if (p_82846_2_ >= 4 && p_82846_2_ < 32)
	            {
	                if (!this.mergeItemStack(itemstack1, 32, 41, false))
	                {
	                    return null;
	                }
	            }
	            else if (p_82846_2_ >= 32 && p_82846_2_ < 41)
	            {
	                if (!this.mergeItemStack(itemstack1, 4, 31, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 4, 41, false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(p_82846_1_, itemstack1);
	        }

	        return itemstack;
    }
	
	@Override
	public void onContainerClosed(EntityPlayer player)
    {
		super.onContainerClosed(player);
    }
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
    }
	
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
    }
}