package hok.chompzki.biocristals.research.logic.content;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
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
			s += "While Rabarberpaj was trying to find a way to drive the cristalisation process she, ";
			s += "by accident and pure fury, found that some saplings with the help of rose and dandelion ";
			s += "particels could be agitated into a more driving state. The new plant showed new promising features, ";
			s += "such as self reparing and slow growing rather then the normal spurt growths found in many of nature plants. ";
			break;
		case 1:
			s += " Croot Sapling\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(BlockRegistry.crootSapling));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.crootSapling));
			s += "\nNOTE: This plant will grow over time and eat anything in it's path...!";
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
		return new GuiCraftRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.crootSapling));
	}
	
}
