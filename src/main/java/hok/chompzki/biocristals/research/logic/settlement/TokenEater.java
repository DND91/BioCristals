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

public class TokenEater extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenEater));
		String s = "";
		switch(p){
		case 0:
			s += "The Eater, eats resources from The Hunger. Diffrent channels can be used to "
				+"control the flow, none goes to any color and none, color only goes to same color and none. "
				+"Note that the eating will always divided resource drain evenly between banks "
				+"connected. It won't care to fill the given qouta just try to drain a certain amount. ";
			break;
		case 1:
			s += "How does this really work? Blocks or insects pulls resources from the eater and they follow "
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
