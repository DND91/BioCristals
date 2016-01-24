package hok.chompzki.hivetera.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.client.slots.SlotAntiHivebag;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHivebag extends Container {
	
	private int[] lastValues;
	
	public Hivebag hivebag = null;
	public EntityPlayer player = null;
	public ItemStack stack;
	public int slot;
	
	public ContainerHivebag(EntityPlayer player, int slot)
    {
		
		this.player = player;
		this.slot = slot;
		this.stack = player.inventory.getStackInSlot(slot);
		if(slot != -1){
			player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", true);
		}
		hivebag = new Hivebag(player, slot);
		hivebag.readFrom();
		this.lastValues = new int[hivebag.getSizeInventory()];
		
		for (int j = 0; j < hivebag.getSizeInventory(); ++j)
        {
            this.addSlotToContainer(new SlotAntiHivebag(hivebag.inventory[j].cookTime, hivebag, j, 8 + j * 18, 103 - 20 - 18));
        }
		
		int i = -18;
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

            if (par2 < this.hivebag.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.hivebag.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.hivebag.getSizeInventory(), false))
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
		super.onContainerClosed(player);
    }
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        for(int i = 0; i < hivebag.getSizeInventory() / 3; i++){
        		par1ICrafting.sendProgressBarUpdate(this, i, this.hivebag.inventory[i].colorValue);
        }
    }
	
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            
            for(int j = 0; j < this.hivebag.getSizeInventory(); j++){
            	if(lastValues[j] != this.hivebag.inventory[j].colorValue){
                	icrafting.sendProgressBarUpdate(this, j, this.hivebag.inventory[j].colorValue);
            	}
            }
        }

        for(int j = 0; j < this.hivebag.getSizeInventory(); j++){
        	lastValues[j] = this.hivebag.inventory[j].colorValue;
        }
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
		this.hivebag.inventory[par1].colorValue = par2;
    }
	
}