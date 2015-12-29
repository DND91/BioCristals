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

public class SacrificePit extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(BlockRegistry.sacrificePit));
		String s = "";
		switch(p){
		case 0:
			s += "Soon after the discovery of The Hunger, families started to build shrines. "
				+"In the center they had the sacrifice pit and around it nests, all in the "
				+"glory of hungry insects. Each year they poured food into the portal in hope "
				+"that the comming year would be free from hunger and blessed with a good harvest. ";
			break;
		case 1:
			s += "The rules for Steve are qutie simple. Place block. In the top slot you can "
				+"either place food, eaters or banks. In the bottom slot you can place feeders "
				+" or banks. The block will transform food into raw food. Raw food will be darined "
				+"from top to bottom slot. This is a slow process. Yes, you can move raw food "
				+"between networks. On food items you can see the efficency between the hunger and "
				+"normal insertion of food into blocks. ";
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
