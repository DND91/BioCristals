package hok.chompzki.hivetera.client.slots;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class SlotTokenAssembler extends Slot {
	
	EntityPlayer player = null;
	TileTokenAssembler assembler = null;
	private int amountCrafted;
	private IInventory craftCost;
	
	
	public SlotTokenAssembler(EntityPlayer player, IInventory craftResult, IInventory craftCost, TileTokenAssembler par1iInventory, int par2, int par3, int par4) {
		super(craftResult, par2, par3, par4);
		this.player = player;
		this.assembler = par1iInventory;
		this.craftCost = craftCost;
		
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return false;
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
    {
		return false;
    }

	public ItemStack decrStackSize(int p_75209_1_)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(p_75209_1_, this.getStack().stackSize);
        }

        return super.decrStackSize(p_75209_1_);
    }
	
	protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
    {
        this.amountCrafted += p_75210_2_;
        this.onCrafting(p_75210_1_);
    }
	
	protected void onCrafting(ItemStack p_75208_1_)
    {
        p_75208_1_.onCrafting(this.player.worldObj, this.player, this.amountCrafted);
        this.amountCrafted = 0;
    }

    public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
    {
        this.onCrafting(p_82870_2_);
      //TODO: ADD STUFF FOR PAYING THE STUFF YOU TAKE!
      
    }
	
}

