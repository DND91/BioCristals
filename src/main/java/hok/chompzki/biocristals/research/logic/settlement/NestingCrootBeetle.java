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

public class NestingCrootBeetle extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.crootBeetle);
		if(stack2 == null)
			stack2 = new ItemStack(BlockRegistry.nest);
		
		String s = "";
		switch(p){
		case 0:
			s += "\n\n"+KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "The first insect to be protected was the croot beetle, the "
			  +  "nomads were suprised to see that it helped their crops grow "
			  +  "in return. The insects silky web protects and supports the plants.\n";
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
