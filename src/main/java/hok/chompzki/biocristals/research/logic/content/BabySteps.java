package hok.chompzki.biocristals.research.logic.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class BabySteps extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		String s = "";
		switch(p){
		case 0:
			s += "Our journey befall us in a time where resources are scares. Often we find time trinkling away ";
			s += "on little things rather then the big whole. One of the more time consumping aspects are found in areas of ";
			s += "agriculture, where farmers have to tilt fields and feed lifestock by hand.\n";
			s += "In the beginning, many of them actively fought nature and land to survive. From this the attunment field growed. ";
			s += "This field of research was first seen as a way to counquer and rule nature.";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += "When we first study the properties of reality we can notice that our senses are not enought to evoke change. ";
			s += "There is a need to insert energy into or activate systemical change. For this Rabarberpaj, through her experiments, created the attuner and ";
			s += "have to this day been the most popular tool of trade inside the attunment field.";
			break;
		case 2:
			s += KnowledgeDescriptions.getDisplayName(code) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(code);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(code);
			s += "\nNOTE: Croot Beetles can be cuaght in grass.";
			break;

		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 3;
	}

	@Override
	public GuiCraftingHelper getFaved() {
		return new GuiCraft(Minecraft.getMinecraft(), code);
	}
}
