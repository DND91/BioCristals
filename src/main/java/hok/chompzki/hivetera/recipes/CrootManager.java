package hok.chompzki.hivetera.recipes;

import hok.chompzki.hivetera.api.ICrootRecipe;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CrootManager {
	
	private static final CrootManager instance = new CrootManager();
	
	public static final CrootManager instance(){
		return instance;
	}
	
	private List recipes = new ArrayList();
	
	public void register(ICrootRecipe recipe){
		recipes.add(recipe);
	}
	
	  public ItemStack findMatchingRecipe(World world, PlayerResearch research, TileCrootCore core)
	    {
	        int i = 0;
	        ItemStack itemstack = null;
	        ItemStack itemstack1 = null;
	        int j;

	        for (j = 0; j < core.getSizeInventory(); ++j)
	        {
	            ItemStack itemstack2 = core.getStackInSlot(j);

	            if (itemstack2 != null)
	            {
	                if (i == 0)
	                {
	                    itemstack = itemstack2;
	                }

	                if (i == 1)
	                {
	                    itemstack1 = itemstack2;
	                }

	                ++i;
	            }
	        }

	        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
	        {
	            Item item = itemstack.getItem();
	            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
	            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
	            int l = j1 + k + item.getMaxDamage() * 5 / 100;
	            int i1 = item.getMaxDamage() - l;

	            if (i1 < 0)
	            {
	                i1 = 0;
	            }

	            return new ItemStack(itemstack.getItem(), 1, i1);
	        }
	        else
	        {
	            for (j = 0; j < this.recipes.size(); ++j)
	            {
	                ICrootRecipe irecipe = (ICrootRecipe)this.recipes.get(j);
	                
	                if (irecipe.matches(world, research, core))
	                {
	                    return irecipe.getCraftingResult(core);
	                }
	            }
	            
	            return null;
	        }
	    }
}
