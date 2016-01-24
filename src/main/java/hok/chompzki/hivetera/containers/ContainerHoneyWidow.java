package hok.chompzki.hivetera.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.client.slots.SlotAntiHivebag;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHoneyWidow extends Container {
	
	public HoneyWidow honeyWidow = null;
	public EntityPlayer player = null;
	public ItemStack stack;
	public int slot;
	
	public ContainerHoneyWidow(EntityPlayer player, int slot)
    {
		this.player = player;
		this.slot = slot;
		this.stack = player.inventory.getStackInSlot(slot);
		if(slot != -1){
			player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", true);
		}
		honeyWidow = new HoneyWidow(player, slot);
		honeyWidow.readFrom();
		
		
		
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new SlotInsect(honeyWidow, k, 8 + k * 18, 8));
        }
        
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new SlotEater(honeyWidow, k + 9, 8 + k * 18, 28));
        }
        
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new SlotInsect(honeyWidow, k + 18, 8 + k * 18, 48));
        }
		
		int i = -19;
		int j = 0;
		int k = 0;
		
		for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
        	if(j == slot){
        		this.addSlotToContainer(new SlotVisual (player.inventory, j, 8 + j * 18, 161 + i));
        	}else
        		this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 161 + i));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.honeyWidow.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.honeyWidow.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.honeyWidow.getSizeInventory(), false))
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
        }

        return itemstack;
    }
	
	@Override
	public void onContainerClosed(EntityPlayer player)
    {
		player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", false);
		this.honeyWidow.isOpen = false;
		this.honeyWidow.writeTo();
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