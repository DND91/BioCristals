package hok.chompzki.hivetera.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.client.slots.SlotAntiHivebag;
import hok.chompzki.hivetera.client.slots.SlotToken;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHiveBrain extends Container {
	
	public EntityPlayer player = null;
	public PlayerHungerNetwork hunger;
	
	public ContainerHiveBrain(EntityPlayer player, PlayerHungerNetwork hunger)
    {
		
		player.getCurrentEquippedItem().stackTagCompound.setBoolean("OPEN", true);
		
		this.player = player;
		this.hunger = hunger;
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (j = 0; j < 9; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
            	
        		this.addSlotToContainer(new SlotToken(hunger, 1, k + j * 9, 8 + k * 18, 8 + j * 18 + i));
        		
            }
        }
		
		i = 0;
		j = 0;
		k = 0;
		
		for (j = 0; j < 9; ++j)
        {
            for (k = 0; k < 4; ++k)
            {
            	
        		this.addSlotToContainer(new SlotToken(hunger, 64, k + j * 4 + 81, 178 + k * 18, 8 + j * 18 + i));
        		
            }
        }
		
		i = 0;
		j = 0;
		k = 0;
		
		for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
            	if(player.inventory.getStackInSlot(k + j * 9 + 9) != null && player.inventory.getStackInSlot(k + j * 9 + 9).getItem() == ItemRegistry.hiveBrain){
            		this.addSlotToContainer(new SlotVisual(player.inventory, k + j * 9 + 9, 8 + k * 18, 174 + j * 18 + i));
            	}else{
            		this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 174 + j * 18 + i));
        		}
            }
        }
		
        for (j = 0; j < 9; ++j)
        {
        	if(player.inventory.getStackInSlot(j) != null && player.inventory.getStackInSlot(j).getItem() == ItemRegistry.hiveBrain){
        		this.addSlotToContainer(new SlotVisual (player.inventory, j, 8 + j * 18, 232 + i));
        	}else
        		this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 232 + i));
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

            if (par2 < this.hunger.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.hunger.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.hunger.getSizeInventory(), false))
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
		player.getCurrentEquippedItem().stackTagCompound.setBoolean("OPEN", false);
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
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
		
    }
	
}