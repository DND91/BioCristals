package hok.chompzki.biocristals.villagers;

import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class BasicResercher implements IVillageTradeHandler {

	@Override
	public void manipulateTradesForVillager(EntityVillager villager,
			MerchantRecipeList recipeList, Random random) {
		// TODO Auto-generated method stub
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.diamond, 1), new ItemStack(BlockRegistry.crootSapling)));
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.gold_ingot, 1), new ItemStack(ItemRegistry.attuner)));
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.apple, 5), new ItemStack(ItemRegistry.bioReagent)));
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(ItemRegistry.researchBook)));
		
	}

}
