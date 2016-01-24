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

public class CrootBreeder extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(BlockRegistry.crootBreeder));
		String s = "";
		switch(p){
		case 0:
			s += "As time went on, the toiling of the fiels became more and more "
				+"a central aspect of the families lives. The insects became less "
				+"diverse and harder to find as they stop traveling. Families died "
				+"with the insects they lost. In share desperation one family created "
				+"a breeding box and started experimenting. They soon noted that insects "
				+"can be breeded but for a price. ";
			break;
		case 1:
			s += "For Stevie this means that the box needs two parent insects for gene "
				+"swaping. One food source either bank, eater or food. Lastly, some nesting "
				+"materials. The insects won't care where or how you place the box, just that "
				+"it has what they need. Remember using normal food will be expensiv.";
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
