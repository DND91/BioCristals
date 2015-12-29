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

public class TokenFeeder extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenFeeder));
		String s = "";
		switch(p){
		case 0:
			s += "The Feeder, feeds resources into The Hunger. Diffrent channels can be used to "
				+"control the flow, none goes to any color and none, color only goes to same color and none. "
				+"Note that the feeding will always divided resources evenly between banks "
				+"connected to a feeder, if there are leftovers it won't be smart enought to find a place "
				+"for them, only bring them back. ";
			break;
		case 1:
			s += "How does this really work? Blocks pushes resources into the feeder and they follow "
				+" the shortest paths to connected banks. Any tokens, resources passes over "
				+"gets a chance to act on the resources, size dosn't matter. Resources that "
				+"dosn't fit inside banks or stopped by fileters will bounce back. Some blocks will leak "
				+"this into the atmosphere.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}

}
