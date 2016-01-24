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

public class AttuneEaterRecipe implements IRecipe{

    private static final String __OBFID = "CL_00000094";

    public AttuneEaterRecipe()
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
    	ItemStack eater = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(itemstack.getItem() != ItemRegistry.hiveBrain && itemstack.getItem() != ItemRegistry.tokenEater){
                	return false;
                }
                
                if (itemstack.getItem() == ItemRegistry.hiveBrain && mainBrain == null && DataHelper.hasNetwork(itemstack)){
                    mainBrain = itemstack;
                }else if (itemstack.getItem() == ItemRegistry.tokenEater && eater == null && !DataHelper.hasNetwork(itemstack)){
                	eater = itemstack;
                } else
                	return false;
            	
            }
        }
        
        return mainBrain != null && eater != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
    {	
    	ItemStack mainBrain = null;
    	ItemStack eater = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77572_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(itemstack.getItem() != ItemRegistry.hiveBrain && itemstack.getItem() != ItemRegistry.tokenEater){
                	return null;
                }

                if (itemstack.getItem() == ItemRegistry.hiveBrain && mainBrain == null && DataHelper.hasNetwork(itemstack)){
                    mainBrain = itemstack;
                }else if (itemstack.getItem() == ItemRegistry.tokenEater && eater == null && !DataHelper.hasNetwork(itemstack)){
                	eater = itemstack.copy();
                } else
                	return null;
            	
            }
        }
        
        eater.stackTagCompound.setString("NETWORK", mainBrain.stackTagCompound.getString("NETWORK"));
        eater.stackSize = 1;
        return eater;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 2;
    }
}
