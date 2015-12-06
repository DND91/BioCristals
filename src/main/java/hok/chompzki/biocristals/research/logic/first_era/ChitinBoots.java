package hok.chompzki.biocristals.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.ArticleContent.EnumContent;
import hok.chompzki.biocristals.client.gui.GuiCraft;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;

public class ChitinBoots extends ArticleContent {
	
	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(stack == null)
			stack = new ItemStack(ItemRegistry.chitinPlate);
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.chitinBoots));
		String s = "";
		switch(p){
		case 0:
			s += "Nomads fear the night as undeads rise under the waxing moon. "
			  +  "To protect their feets they combined chitin plates to make boots. "
			  +  "Some speculate that the boots were jugs for drinking that got "
			  +  "two sided use for the nomads. This thou, is only speculation. "
			  +  "We do not support the idea of drinknig from your boots. ";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
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
		return 2;
	}
}
