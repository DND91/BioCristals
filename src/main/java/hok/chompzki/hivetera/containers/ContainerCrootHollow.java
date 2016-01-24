package hok.chompzki.hivetera.containers;

import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCrootHollow extends Container {
	
	private TileCrootHollow hollow = null;

	public ContainerCrootHollow(InventoryPlayer inventory,
			TileCrootHollow tileEntity) {
		hollow = tileEntity;
		this.addSlotToContainer(new Slot(hollow, 0, 15, 15));
		this.addSlotToContainer(new Slot(hollow, 1, 35, 15));
		this.addSlotToContainer(new Slot(hollow, 2, 55, 15));
		this.addSlotToContainer(new Slot(hollow, 3, 75, 15));
		
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
        
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
    }
	
	@Override
	public void updateProgressBar(int par1, int par2)
    {
        

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
        int numRows = 4;

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
