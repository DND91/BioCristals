package hok.chompzki.hivetera.items.token.recipes;

import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class AttuneTokenRecipe implements IRecipe{
    
    private static final String[] dyes =
        {
            "dyeBlack",
            "dyeRed",
            "dyeGreen",
            "dyeBrown",
            "dyeBlue",
            "dyePurple",
            "dyeCyan",
            "dyeLightGray",
            "dyeGray",
            "dyePink",
            "dyeLime",
            "dyeYellow",
            "dyeLightBlue",
            "dyeMagenta",
            "dyeOrange",
            "dyeWhite"
        };

    public AttuneTokenRecipe()
    {
    	
    }

    public ItemStack getRecipeOutput()
    {
        return null; //new ItemStack(ItemRegistry.hiveBrain);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    
    public boolean isDye(ItemStack stack){
    	return OreDictionary.itemMatches(new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE), stack, false);
    }
    
    public int dyeColor(ItemStack stack){
    	int[] ids = OreDictionary.getOreIDs(stack);
    	for(int id : ids){
    		for(int i = 0; i < dyes.length; i++){
    			if(OreDictionary.getOreName(id).equals(dyes[i])){
    				return i;
    			}
    		}
    	}
    	
    	return -1;
    }
    
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
    	ItemStack dye = null;
    	ItemStack token = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(!(itemstack.getItem() instanceof IToken) && !isDye(itemstack)){
                	return false;
                }
                
                if (isDye(itemstack)){
                	if(dye == null)
                		dye = itemstack;
                	else
                		return false;
                }else if (itemstack.getItem() instanceof IToken){
                	if(token == null)
                		token = itemstack;
                	else if(token.getItem() != itemstack.getItem() || !token.stackTagCompound.equals(itemstack.stackTagCompound))
                		return false;
                }
            	
            }
        }
        int color = dye == null ? 0 : dyeColor(dye) + 1;
        
        return token != null && token.getItemDamage() != color;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
    {	
    	ItemStack dye = null;
    	ItemStack token = null;
    	int n = 1;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77572_1_.getStackInRowAndColumn(j, i);
                if(itemstack == null)
                	continue;
                
                if(!(itemstack.getItem() instanceof IToken) && !isDye(itemstack)){
                	return null;
                }
                
                if (isDye(itemstack)){
                	if(dye == null)
                		dye = itemstack;
                	else
                		return null;
                }else if (itemstack.getItem() instanceof IToken){
                	if(token == null){
                		token = itemstack;
                	}else if(token.getItem() != itemstack.getItem() || !token.stackTagCompound.equals(itemstack.stackTagCompound))
                		return null;
                	else
                		n++;
                }
            	
            }
        }
        
        ItemStack result = token.copy();
        result.stackSize = n;
        if(dye != null){
	        int color = dyeColor(dye);
	        result.stackTagCompound.setString("CHANNEL", ItemDye.field_150923_a[color]);
	        result.setItemDamage(color+1);
        }else{
	        result.stackTagCompound.setString("CHANNEL", "NONE");
	        result.setItemDamage(0);
        }
        
        return result;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 2;
    }
}
