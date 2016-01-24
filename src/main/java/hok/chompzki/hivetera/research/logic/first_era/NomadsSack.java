package hok.chompzki.hivetera.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

public class NomadsSack extends ArticleContent {

	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.nomadSack);
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.nomadSack));
		String s = "";
		switch(p){
		case 0:
			s += "The '" + stack.getDisplayName() + "'\n\n";
			s += KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "was the go to container for the nomad as they traveled "
			   + "between their outposts. It's ability to hold a big amount of "
			   + "items might be chocking. The content is not easly "
			   + "accasable, as it inhabitants defende it viciously. "
			   + "The sack almost disapeared after nomads started to settle, "
			   + "as it cannot hold anything big or tools.";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += KnowledgeDescriptions.getDisplayName(con) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(con);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(con);
			break;
		case 2:
			s += "   ~ USE ~\n"
			   + "To fill, hold it in hand.\n"
			   + "To empty, shift + right click on container.\n"
			   + "   ~ RULES ~\n"
			   + "It will not accept blocks, exception plantables and wool.\n"
			   + "It will not accept tools, food, sticks, coal and research book.\n"
			   + "Anything else goes in. Hotbar not included.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 3;
	}
}
