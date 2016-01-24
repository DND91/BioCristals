package hok.chompzki.hivetera.client.slots;

import hok.chompzki.hivetera.containers.ContainerInsectHunt;
import hok.chompzki.hivetera.containers.InsectHunt;
import hok.chompzki.hivetera.registrys.ItemRegistry;
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

public class SlotInsectHunt extends Slot {
	
	
    private EntityPlayer thePlayer;
    private int field_75228_b;
    private static final String __OBFID = "CL_00001749";
    private InsectHunt hunt;
    private ContainerInsectHunt con;

    public SlotInsectHunt(ContainerInsectHunt con, InsectHunt inv, int p_i1813_3_, int p_i1813_4_, int p_i1813_5_)
    {
        super(inv, p_i1813_3_, p_i1813_4_, p_i1813_5_);
        this.hunt = inv;
        this.con = con;
    }
    
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() == ItemRegistry.biomeSample;
    }
    

    public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
    {
        this.onCrafting(p_82870_2_);
        super.onPickupFromSlot(p_82870_1_, p_82870_2_);
    }
    
    public boolean canTakeStack(EntityPlayer p_82869_1_)
    {
        return this.getStack() != null && this.getStack().getItem() != ItemRegistry.biomeSample;
    }
	
}
