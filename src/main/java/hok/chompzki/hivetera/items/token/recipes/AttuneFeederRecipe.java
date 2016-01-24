package hok.chompzki.hivetera.items.token.recipes;

import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class AttuneFeederRecipe implements IRecipe{

    private static final String __OBFID = "CL_00000094";

    public AttuneFeederRecipe()
    {
    	
    }

    public ItemStack getRecipeOutput()
    {
        return null; //new ItemStack(ItemRegistry.hiveBrain);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
    	ItemStack mainBrain = null;
    	ItemStack feeder = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(itemstack.getItem() != ItemRegistry.hiveBrain && itemstack.getItem() != ItemRegistry.tokenFeeder){
                	return false;
                }
                
                if (itemstack.getItem() == ItemRegistry.hiveBrain && mainBrain == null && DataHelper.hasNetwork(itemstack)){
                    mainBrain = itemstack;
                }else if (itemstack.getItem() == ItemRegistry.tokenFeeder && feeder == null && !DataHelper.hasNetwork(itemstack)){
                	feeder = itemstack;
                } else
                	return false;
            	
            }
        }

        return mainBrain != null && feeder != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
    {	
    	ItemStack mainBrain = null;
    	ItemStack feeder = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77572_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(itemstack.getItem() != ItemRegistry.hiveBrain && itemstack.getItem() != ItemRegistry.tokenFeeder){
                	return null;
                }

                if (itemstack.getItem() == ItemRegistry.hiveBrain && mainBrain == null && DataHelper.hasNetwork(itemstack)){
                    mainBrain = itemstack;
                }else if (itemstack.getItem() == ItemRegistry.tokenFeeder && feeder == null && !DataHelper.hasNetwork(itemstack)){
                	feeder = itemstack.copy();
                } else
                	return null;
            	
            }
        }
        
        feeder.stackTagCompound.setString("NETWORK", mainBrain.stackTagCompound.getString("NETWORK"));
        feeder.stackSize = 1;
        return feeder;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 2;
    }
}
