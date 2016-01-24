package hok.chompzki.hivetera.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotNestInsect;
import hok.chompzki.hivetera.client.slots.SlotResult;
import hok.chompzki.hivetera.research.data.EnumUnlock;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerNest extends Container {
	
	private TileNest hollow = null;
	private int timeStamp = 0;
	private int startTime = 1;

	public ContainerNest(InventoryPlayer inventory,
			TileNest tileEntity) {
		hollow = tileEntity;
		
		this.addSlotToContainer(new SlotEater(hollow, 0, 8, 15));
		this.addSlotToContainer(new SlotNestInsect(hollow, 1, 26, 15));
		this.addSlotToContainer(new SlotResult(inventory.player, hollow, 2, 80, 15));
		this.addSlotToContainer(new SlotResult(inventory.player, hollow, 3, 98, 15));
		
		this.addSlotToContainer(new Slot(hollow, 4, 134, 15));
		this.addSlotToContainer(new Slot(hollow, 5, 152, 15));
		this.addSlotToContainer(new Slot(hollow, 6, 134, 33));
		this.addSlotToContainer(new Slot(hollow, 7, 152, 33));
		this.addSlotToContainer(new Slot(hollow, 8, 134, 51));
		this.addSlotToContainer(new Slot(hollow, 9, 152, 51));
		
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
	
	public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.hollow.lifeTime);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.hollow.startTime);
        
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
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

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_)
    {
        if (p_75137_1_ == 0)
        {
        	this.hollow.lifeTime = p_75137_2_;
        }
        
        if (p_75137_1_ == 1)
        {
        	this.hollow.startTime = p_75137_2_;
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
        int numRows = 10;
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (p_82846_2_ < numRows)
            {
                if (!this.mergeItemStack(p_82846_1_, itemstack1, numRows, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(p_82846_1_, itemstack1, 0, numRows, false))
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
	
	 protected boolean mergeItemStack(EntityPlayer player, ItemStack stack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
	    {
	        boolean flag1 = false;
	        int k = p_75135_2_;

	        if (p_75135_4_)
	        {
	            k = p_75135_3_ - 1;
	        }

	        Slot slot;
	        ItemStack itemstack1;

	        if (stack.isStackable())
	        {
	            while (stack.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_))
	            {
	                slot = (Slot)this.inventorySlots.get(k);
	                if(slot.isItemValid(stack)){
	                	
		                itemstack1 = slot.getStack();
	
		                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
		                {
		                    int l = itemstack1.stackSize + stack.stackSize;
	
		                    if (l <= stack.getMaxStackSize())
		                    {
		                        stack.stackSize = 0;
		                        itemstack1.stackSize = l;
		                        slot.onSlotChanged();
		                        flag1 = true;
		                        if(k == 0 && !player.worldObj.isRemote){
		                        	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
		                        }
		                    }
		                    else if (itemstack1.stackSize < stack.getMaxStackSize())
		                    {
		                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
		                        itemstack1.stackSize = stack.getMaxStackSize();
		                        slot.onSlotChanged();
		                        flag1 = true;
		                        if(k == 0 && !player.worldObj.isRemote){
		                        	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
		                        }
		                    }
		                }
	                }
	                if (p_75135_4_)
	                {
	                    --k;
	                }
	                else
	                {
	                    ++k;
	                }
	            }
	        }

	        if (stack.stackSize > 0)
	        {
	            if (p_75135_4_)
	            {
	                k = p_75135_3_ - 1;
	            }
	            else
	            {
	                k = p_75135_2_;
	            }

	            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
	            {
	                slot = (Slot)this.inventorySlots.get(k);
	                if(slot.isItemValid(stack)){
		                itemstack1 = slot.getStack();
		                
		                if (itemstack1 == null)
		                {
		                    slot.putStack(stack.copy());
		                    slot.onSlotChanged();
		                    stack.stackSize = 0;
		                    flag1 = true;
		                    if(k == 0 && !player.worldObj.isRemote){
		                    	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
	                        }
		                    break;
		                }
	                }
	                if (p_75135_4_)
	                {
	                    --k;
	                }
	                else
	                {
	                    ++k;
	                }
	            }
	        }

	        return flag1;
	    }
	
    public ItemStack slotClick(int slot, int button, int c, EntityPlayer player)
    {
    	if(!player.worldObj.isRemote && slot == 1  && (button == 0 || button == 1) && player.inventory.getItemStack() != null && this.getSlot(1).isItemValid(player.inventory.getItemStack())){
    		ItemStack held = player.inventory.getItemStack();
    		ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, held);
    	}
    	return super.slotClick(slot, button, c, player);
    }
}
