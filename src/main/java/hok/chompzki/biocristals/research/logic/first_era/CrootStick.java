package hok.chompzki.biocristals.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class CrootStick extends ArticleContent {
	
	private ItemStack stack = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(stack == null)
			stack = new ItemStack(ItemRegistry.crootBeetle);
		String s = "";
		switch(p){
		case 0:
			s += "Our ancestors soon found that the '" + stack.getDisplayName() + "' "
			   + "has a diversy and broad apitiate when it came to other insects. What "
			   + "is central was that the insect catched it's prey with "
			   + "sticky strings and put it in a cacoon for future nuritment. By letting "
			   + "the beetle create a safe heaven and put its home on a stick, they could use it "
			   + "to catch escaping insects that were fleeing from thier invaded homes. ";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += KnowledgeDescriptions.getDisplayName(code) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(code);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(code);
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}
	
	@Override
	public GuiCraftingHelper getFaved() {
		return new GuiCraft(Minecraft.getMinecraft(), code);
	}
}
