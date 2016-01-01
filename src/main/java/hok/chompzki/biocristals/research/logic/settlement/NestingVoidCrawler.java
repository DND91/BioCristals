package hok.chompzki.biocristals.research.logic.settlement;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.ArticleContent.EnumContent;
import hok.chompzki.biocristals.client.gui.GuiCraft;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;

public class NestingVoidCrawler extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.voidCrawler);
		if(stack2 == null)
			stack2 = new ItemStack(BlockRegistry.nest);
		
		String s = "";
		switch(p){
		case 0:
			s += "\n\n"+KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "The void clawler functions as before, but will not activate if a player comes within "
				+"6 blocks at the same level or above. This was often used for more devious purposes, such as traps.\n";
			s += "\n\n"+KnowledgeDescriptions.transformOutput(stack2) + "\n";
			break;
		case 1:
			break;
		case 2:
			
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}

}
