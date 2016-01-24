package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICrootRecipe {
	/**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(World world, PlayerResearch research, IInventory core);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(IInventory inventory);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();
}
