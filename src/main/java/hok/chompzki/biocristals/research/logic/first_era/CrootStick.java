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

public class CrootStick extends ArticleContent {
	
	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(stack == null)
			stack = new ItemStack(ItemRegistry.crootBeetle);
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.crootStick));
		String s = "";
		switch(p){
		case 0:
			s += "Our ancestors soon found that the '" + stack.getDisplayName() + "' "
			   + "has a diversy and broad apitiate when it came to other insects. What "
			   + "is central was that the insect catched it's prey with "
			   + "sticky strings and put it in a cacoon for future nuritment. By letting "
			   + "the beetle create a safe heaven and put its home on a stick, they could use it "
			   + "to catch escaping insects that were fleeing from thier invaded homes. "
			   + "Will consume croot bugs for more special bugs.";
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
