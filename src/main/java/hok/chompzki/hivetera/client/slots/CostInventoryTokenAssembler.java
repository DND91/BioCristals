package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CostInventoryTokenAssembler implements IInventory
{
    /** A list of one item containing the result of the crafting formula */
    private ItemStack[] stackCraft = new ItemStack[33]; // 3 * 11
    private static final String __OBFID = "CL_00001760";
    private Container con = null;
    
    public CostInventoryTokenAssembler(Container con){
    	this.con = con;
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return stackCraft.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.stackCraft[p_70301_1_];
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return "Cost";
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if (this.stackCraft[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.stackCraft[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.stackCraft[p_70298_1_];
                this.stackCraft[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.stackCraft[p_70298_1_].splitStack(p_70298_2_);

                if (this.stackCraft[p_70298_1_].stackSize == 0)
                {
                    this.stackCraft[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (this.stackCraft[p_70304_1_] != null)
        {
            ItemStack itemstack = this.stackCraft[p_70304_1_];
            this.stackCraft[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		this.stackCraft[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
	}

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return Integer.MAX_VALUE;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return true;
    }

    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }
}

