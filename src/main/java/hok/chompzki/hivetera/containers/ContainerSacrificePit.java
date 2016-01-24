package hok.chompzki.hivetera.containers;

import hok.chompzki.hivetera.client.slots.SlotBreedingResult;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotFeeder;
import hok.chompzki.hivetera.client.slots.SlotFood;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotResult;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileSacrificePit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerSacrificePit extends Container {
	
	private TileSacrificePit hollow = null;
	private int time;
	private int rawFood;

	public ContainerSacrificePit(EntityPlayer player,
			TileSacrificePit tileEntity) {
		hollow = tileEntity;
		this.addSlotToContainer(new SlotEater(hollow, 0, 80, 12));
		this.addSlotToContainer(new SlotFeeder(hollow, 1, 80, 48));
		
		for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 - 19 + j * 18));
            }
        }
		
		for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.hollow.time);
        par1ICrafting.sendProgressBarUpdate(this, 1, (int) this.hollow.rawFood);
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

            if (this.time != this.hollow.time)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.hollow.time);
            }
            
            if (this.rawFood != (int)this.hollow.rawFood)
            {
                icrafting.sendProgressBarUpdate(this, 1, (int) this.hollow.rawFood);
            }
        }

        this.time = this.hollow.time;
        this.rawFood = (int) this.hollow.rawFood;
    }
	
	@Override
	public void updateProgressBar(int par1, int par2)
    {
		if (par1 == 0)
        {
        	this.hollow.time = par2;
        }
        
        if (par1 == 1)
        {
        	this.hollow.rawFood = par2;
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
        int numRows = 2;

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
        }

        return itemstack;
    }
}
