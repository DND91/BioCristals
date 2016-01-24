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

public class TokenBridge extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenBridge));
		String s = "";
		switch(p){
		case 0:
			s += "The bridge token connects tokens inside The Hunger. It is not a requirement "
				+"for a network to function. It's good to have when you are building complex "
				+"networks.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}

}
