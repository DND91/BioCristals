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

public class TokenAssembler extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(BlockRegistry.tokenAssembler));
		String s = "";
		switch(p){
		case 0:
			s += "One day a shrine priest stared into The Hunger and "
				+"contemplated it's uses. To help him the Token Assembler "
				+"was created. Through genious or guidance is unkown. "
				+"With experimentation and the use of sacrifices he bacame the "
				+"founder of the tokens. ";
			break;
		case 1: 
			s += "The rules for Steve are quite easy. Around the block you place chests, in these chests you place the "
				+"cost of what you want to create. The blocks insides should be "
				+"self-explanatory if you have read about the diffrent tokens. ";
			break;
		case 2: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += KnowledgeDescriptions.getDisplayName(con) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(con);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(con);
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 3;
	}

}
