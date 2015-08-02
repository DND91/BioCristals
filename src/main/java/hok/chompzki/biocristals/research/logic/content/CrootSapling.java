package hok.chompzki.biocristals.research.logic.content;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class CrootSapling extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		String s = "";
		switch(p){
		case 0:
			s += "While Rabarberpaj was trying to find a way to drive her research. She, ";
			s += "by accident and pure fury, found that some saplings with the help of dye ";
			s += "particels could be agitated into a more driving state. The new plant showed new promising features, ";
			s += "such as self reparing and slow growing rather then the normal spurt growths found in many of nature plants. ";
			s += "With the help of strings she found a way to make the tree grow into a psudo house. ";
			break;
		case 1:
			s += KnowledgeDescriptions.getDisplayName(code) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(code);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(code);
			s += "\nNOTE: This plant will grow over time and eat anything in it's path...! (6-8 blocks area)";
			break;
		case 2:
			s += "It takes some time for the sapling to grow into a tree. It will change into a croot core, but will not be done then. \n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.crootCore)) + "\n";
			s += "Use the attuner to view the cores properties and the core is used as a workbench for more complex recipes of the field. ";
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
