package hok.chompzki.biocristals.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.ArticleContent.EnumContent;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.GuiCrootStickHelper;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.registrys.ItemRegistry;

public class KittehBeetle extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.kittehBeetle);
		if(stack2 == null)
			stack2 = new ItemStack(ItemRegistry.crootStick);
		String s = "";
		switch(p){
		case 0:
			s += "The '" + stack.getDisplayName() + "'\n\n";
			s += KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "is to fast and slippery for the normal nomad to catch. "
			   + "With the help of '" + stack2.getDisplayName() + "' they "
			   + "could catch the slimy melon living insect. They are harmless "
			   + "and if squeezed will give you a cake as present, then "
			   + "fly away in happyness. At least that is what the  "
			   + "nomads tell's their kids. ";
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
