package hok.chompzki.hivetera.client.slots;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotResult extends Slot {
	
	
    private EntityPlayer thePlayer;
    private int field_75228_b;
    private static final String __OBFID = "CL_00001749";

    public SlotResult(EntityPlayer p_i1813_1_, IInventory p_i1813_2_, int p_i1813_3_, int p_i1813_4_, int p_i1813_5_)
    {
        super(p_i1813_2_, p_i1813_3_, p_i1813_4_, p_i1813_5_);
        this.thePlayer = p_i1813_1_;
    }
    
    public boolean isItemValid(ItemStack p_75214_1_)
    {
        return false;
    }
    
    public ItemStack decrStackSize(int p_75209_1_)
    {
        if (this.getHasStack())
        {
            this.field_75228_b += Math.min(p_75209_1_, this.getStack().stackSize);
        }

        return super.decrStackSize(p_75209_1_);
    }

    public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
    {
        this.onCrafting(p_82870_2_);
        super.onPickupFromSlot(p_82870_1_, p_82870_2_);
    }

    
    protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
    {
        this.field_75228_b += p_75210_2_;
        this.onCrafting(p_75210_1_);
    }
    
    protected void onCrafting(ItemStack p_75208_1_)
    {
        p_75208_1_.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
    }
	
}
