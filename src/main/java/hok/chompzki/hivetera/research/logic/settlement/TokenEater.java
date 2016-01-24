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

public class TokenEater extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenEater));
		String s = "";
		switch(p){
		case 0:
			s += "The eater, eats resources from The Hunger. "
				+"Note that the eating will always divided resource drain evenly between connected banks. "
				+"It won't care to fill the given qouta just try to drain a certain amount from each bank. ";
			break;
		case 1:
			s += "How does this really work? Blocks or insects pulls resources from the eater and they travel "
				+" the shortest paths from connected banks to the eater. Any tokens, resources passes over "
				+"gets a chance to act on the resources, size dosn't matter. Resources that "
				+"are stopped by fileters will bounce back to bank. Resources that are not used by block or insect will leak "
				+" into the atmosphere. ";
			break;
		}
		return s;
	}
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}

}
