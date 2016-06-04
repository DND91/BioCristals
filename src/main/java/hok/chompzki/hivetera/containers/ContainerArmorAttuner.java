package hok.chompzki.hivetera.containers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.client.slots.SlotAntiHivebag;
import hok.chompzki.hivetera.client.slots.SlotBioArmor;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import hok.chompzki.hivetera.items.armor.ArmorPattern;
import hok.chompzki.hivetera.items.armor.ArmorSocket;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.registrys.ArmorPatternRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerArmorAttuner extends Container {
	
	public EntityPlayer player = null;
	public BioArmor[] bios = new BioArmor[4];
	
	public ContainerArmorAttuner(final EntityPlayer player)
    {
		this.player = player;
		
		
		for (int i = 0; i < 4; ++i)
        {
            final int k = i;
            this.addSlotToContainer(new SlotVisual(player.inventory, player.inventory.getSizeInventory() - 1 - i, 20, 19 + i * 41));
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
        
        for (i = 0; i < 4; ++i)
        {
        	ItemStack armor = player.inventory.getStackInSlot(player.inventory.getSizeInventory() - 1 - i);
        	
        	if(armor == null || !(armor.getItem() instanceof ItemBioModArmor)){
        		bios[i] = null;
        		continue;
        	}
        	
        	bios[i] = new BioArmor(player.inventory.getStackInSlot(player.inventory.getSizeInventory() - 1 - i));
        	
        	String p = NBTHelper.get(armor, "PATTERN", ArmorPatternRegistry.pattern_names[0]);
        	ArmorPattern pattern = ArmorPatternRegistry.patterns.get(p);
        	List<ArmorSocket> sockets = pattern.get(i);
        	
        	j = 0;
        	for(ArmorSocket socket : sockets){
        		this.addSlotToContainer(new SlotBioArmor(socket.type,bios[i], j, 100 + socket.x * 18, 28 + socket.y * 18 + i * 41));
        		j++;
        	}
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
	            
	            
	            if (p_82846_2_ >= 4 && p_82846_2_ < 31)
	            {
	                if (!this.mergeItemStack(itemstack1, 31, 40, false))
	                {
	                    return null;
	                }
	            }
	            else if (p_82846_2_ >= 31 && p_82846_2_ < 40)
	            {
	                if (!this.mergeItemStack(itemstack1, 4, 31, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 4, 40, false))
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
	
	public void detectAndSendChanges()
    {
        for (int i = 0; i < this.inventorySlots.size(); ++i)
        {
            ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);

            if (!(ItemStack.areItemStacksEqual(itemstack1, itemstack) && ItemStack.areItemStackTagsEqual(itemstack1, itemstack)))
            {
                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.crafters.size(); ++j)
                {
                    ((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
                }
            }
        }
    }
}