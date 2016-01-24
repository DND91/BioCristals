package hok.chompzki.hivetera.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.GuiCrootStickHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.registrys.ItemRegistry;

public class KraKen extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.kraKenBug);
		if(stack2 == null)
			stack2 = new ItemStack(ItemRegistry.crootStick);
		String s = "";
		switch(p){
		case 0:
			s += "The '" + stack.getDisplayName() + "'\n\n";
			s += KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "is to fast and slippery for the normal nomad to catch. "
			   + "With the help of '" + stack2.getDisplayName() + "' they "
			   + "could catch the stealthy leaf living insect. Even "
			   + "appering harmless they have a tendency to steer things up "
			   + "if left alone for to long. The insect is used as a main "
			   + "food source for nomads.";
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
}
