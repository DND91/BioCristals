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

public class TokenBank extends ArticleContent {

	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenBank));
		String s = "";
		switch(p){
		case 0:
			s += "The bank is used to store resources inside and outside The Hunger. "
				+"The most simple network consists of two feeders, one sacrifice pit "
				+"and a bank for raw food. Processing food in the sacrifice pit to raw food "
				+"that is stored inside the bank. ";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}

}
