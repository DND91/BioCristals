package hok.chompzki.hivetera.containers;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.recipes.CrootManager;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.network.PlayerStoragePullMessage;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCrootCore extends Container {
	
	private EntityPlayer player = null;
	private TileCrootCore core = null;
	public IInventory craftResult = new InventoryCraftResult();
	private boolean isClient = false;
	private BioInvCrafting fakeInv = null;

	public ContainerCrootCore(InventoryPlayer inventory,
			TileCrootCore tileEntity, boolean isClient) {
		core = tileEntity;
		player = inventory.player;
		this.isClient = isClient;
		this.fakeInv = new BioInvCrafting(tileEntity, this);
		
		core.openContainer(player, this);
		
		this.addSlotToContainer(new SlotCrafting(inventory.player, this.core, this.craftResult, 0, 75, 33));
        int l;
        int i1;
		
        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 3; ++i1)
            {
                this.addSlotToContainer(new Slot(core, i1 + l * 3, 8 + i1 * 18, 15 + l * 18));
            }
        }

		
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
		
		this.onCraftMatrixChanged(this.core);
	}
	
	public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
		PlayerResearch research = PlayerResearchStorage.instance(isClient).get(player.getGameProfile().getId());
		if(isClient && research == null){
			HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(player.getGameProfile().getId()));
			return;
		}
		ItemStack stack = CrootManager.instance().findMatchingRecipe(this.core.getWorldObj(), research, this.core);
		if(stack == null)
			stack = CraftingManager.getInstance().findMatchingRecipe(fakeInv, this.core.getWorldObj());
        this.craftResult.setInventorySlotContents(0, stack);
    }
	
	
	//this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	
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
		this.onCraftMatrixChanged(this.core);
        super.detectAndSendChanges();
    }
	
	@Override
	public void updateProgressBar(int par1, int par2)
    {
        

    }
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.core.isUseableByPlayer(entityplayer);
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

            if (p_82846_2_ == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 10 && p_82846_2_ < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 37 && p_82846_2_ < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
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

    public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
    {
        return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
    
    public void onContainerClosed(EntityPlayer player)
    {
        core.closeContainer(player, this);
    }
}
