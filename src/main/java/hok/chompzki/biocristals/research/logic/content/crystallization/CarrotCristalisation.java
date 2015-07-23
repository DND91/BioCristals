package hok.chompzki.biocristals.research.logic.content.crystallization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiCristalRecipe;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class CarrotCristalisation extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "As Rot's experiments keept on, more and more biomatter experienced his attunment process. ";
			s += "The carrot cristal was first found near a race called elves, no where to be found, and worked ";
			s += "as one of thier primary food sources. When researcher toutched the cristals relations become hostile. ";
			s += "All cristals need to be placed near croot.";
			break;
		case 1:
			s += "       ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.carrot), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.carrotCristal));
			s += "\n";
			s += "1. Place biomass\n";
			s += "2. Throw biological reagent\n";
			s += "3. Throw carrot\n";
			s += "4. Hold and release attuner on biomass\n";
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
		return new GuiCristalRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.carrotCristal), KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.carrot), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner)));
	}
}
