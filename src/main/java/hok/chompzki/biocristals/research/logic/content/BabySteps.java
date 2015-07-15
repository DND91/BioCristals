package hok.chompzki.biocristals.research.logic.content;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.Content;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class BabySteps extends ArticleContent {
	
	@Override
	public String textOnPage(Content content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		String s = "";
		switch(p){
		case 0:
			s += "Our journey befall us in a time where resources are scares. Often we find time trinkling away ";
			s += "on little things rather then the big whole. One of the more time consumping aspects are found in areas of ";
			s += "agriculture, where farmers have to tilt fields and feed lifestock by hand.";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += "When we study the properties of reality we soon notice that our senses are not enought to evoke change. ";
			s += "There is a need to insert energy into or activate systemical change. For this Rabarberpaj, through her experiments, created the attuner and ";
			s += "have to this day been the most popular tool of trade.";
			break;
		case 2:
			s += " Rabarberpaj's Attuner\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(ItemRegistry.attuner));
			break;

		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(Content content){
		return 3;
	}
	
	public boolean hasPageSelection(int i){
		return i == 2;
	}
	
	public boolean initSelection(){
		return GuiInventoryOverlay.craftingHelper.contains(new ItemStack(ItemRegistry.attuner));
	}
	
	public void selected(boolean selection){
		if(selection){
			GuiInventoryOverlay.craftingHelper.add(new GuiCraftRecipe(Minecraft.getMinecraft(), ReserchRegistry.babySteps,new ItemStack(ItemRegistry.attuner)));
		}else{
			GuiInventoryOverlay.craftingHelper.remove(new ItemStack(ItemRegistry.attuner));
		}
	}
}
