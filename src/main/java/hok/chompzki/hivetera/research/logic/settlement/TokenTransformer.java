package hok.chompzki.hivetera.research.logic.settlement;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

public class TokenTransformer extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenTransformer));
		String s = "";
		switch(p){
		case 0:
			s += "The transformer takes one type of resource and processes it to a new product. All of it."
				+"The efficiency decides how much of the resource survives to the balancing phase. "
				+"In the balancing phase what is left is divided between product and byproduct.\n"
				+"When insects do this themselves, often less then 12.5% survives. Using The Hunger is more "
				+"effective if used correctly.";
			break;
		case 1:
			s = "Transformation tiers. Raw food comes from food items in the sacrifice pit.\n\n";
			s += "Tier 0: Raw Food, Waste.\n";
			s += "Tier 1: Biomass.\n";
			s += "Tier 2: Psy. Energy, Nuritment, Life Fluids.\n\n";
			s += "Earlier tier can be transformed to the next. Waste is a by product from transformation.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}

}
