package hok.chompzki.hivetera.containers;

import hok.chompzki.hivetera.client.slots.SlotBreedingResult;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotFood;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotResult;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCrootBreeder extends Container {
	
	private TileCrootBreeder hollow = null;
	private int timeStamp;
	private int startTime;

	public ContainerCrootBreeder(InventoryPlayer inventory,
			TileCrootBreeder tileEntity) {
		hollow = tileEntity;
		this.addSlotToContainer(new SlotInsect(hollow, 0, 42, 25));
		this.addSlotToContainer(new SlotInsect(hollow, 1, 117, 25));
		this.addSlotToContainer(new Slot(hollow, 2, 71, 35));
		this.addSlotToContainer(new SlotEater(hollow, 3, 90, 35));
		this.addSlotToContainer(new SlotBreedingResult(inventory.player, hollow, 4, 80, 56));
		
		for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 103 - 19 + j * 18));
            }
        }
		
		for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.hollow.lifeTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.hollow.startTime);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.timeStamp != this.hollow.lifeTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.hollow.lifeTime);
            }
            
            if (this.startTime != this.hollow.startTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.hollow.startTime);
            }
        }

        this.timeStamp = this.hollow.lifeTime;
        this.startTime = this.hollow.startTime;
    }
	
	@Override
	public void updateProgressBar(int par1, int par2)
    {
		if (par1 == 0)
        {
        	this.hollow.lifeTime = par2;
        }
        
        if (par1 == 1)
        {
        	this.hollow.startTime = par2;
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.hollow.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
        int numRows = 5;

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < numRows)
            {
                if (!this.mergeItemStack(itemstack1, numRows, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, numRows, false))
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
            slot.onPickupFromSlot(p_82846_1_, itemstack);
        }

        return itemstack;
    }
}
