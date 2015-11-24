package hok.chompzki.biocristals.research.logic.first_era;

import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class CrootClaw extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.crootClaw);
		if(stack2 == null)
			stack2 = new ItemStack(ItemRegistry.crootStick);
		String s = "";
		switch(p){
		case 0:
			s += "The '" + stack.getDisplayName() + "'\n\n";
			s += KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "is to fast and slippery for the normal nomad to catch. "
			   + "With the help of '" + stack2.getDisplayName() + "' they "
			   + "could catche the stealthy cactus living insect. They "
			   + "function to help the nomand to climb up ontop diffrent "
			   + "surfeces. It was highly used by jungle nomads as it made "
			   + "travle between tree tops easy. ";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			
			break;
		case 2:
			
			break;
			
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}
	
	@Override
	public GuiCraftingHelper getFaved() {
		return null; //new GuiCraft(Minecraft.getMinecraft(), code);
	}

}
