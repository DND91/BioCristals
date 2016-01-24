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

public class TokenFeeder extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.tokenFeeder));
		String s = "";
		switch(p){
		case 0:
			s += "The feeder, feeds resources into The Hunger, often via a Sacrifice Pit. "
				+"Note that the feeding will always divided resources evenly between connected banks "
				+", if there are leftovers it won't be smart enought to find a place "
				+"for them, only bring them back. ";
			break;
		case 1:
			s += "How does this really work? Blocks pushes resources into the feeder and they travel "
				+" the shortest paths to connected banks. Any tokens, resources passes over "
				+"gets a chance to act on the resources, size dosn't matter. Resources that "
				+"dosn't fit inside banks or stopped by fileters will bounce back to source. "
				+"This can cause leakage!";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}

}
