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

public class Nest extends ArticleContent {

	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.nomadSack);
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(BlockRegistry.nest));
		String s = "";
		switch(p){
		case 0:
			s += "The nomads wanted to protect their insects so they built "
			  +  "nests. They soon noticed that the insects thanked them by "
			  +  "working the envoirment in their favor. All the insects "
			  +  "have their own way to help the nomads. What is intressting "
			  +  "is the sleep they enter when there is no help to be given. ";
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
		return 2;
	}

}
