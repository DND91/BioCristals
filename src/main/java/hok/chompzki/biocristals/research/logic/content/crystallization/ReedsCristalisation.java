package hok.chompzki.biocristals.research.logic.content.crystallization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
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

public class ReedsCristalisation extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "It's said that this form of crystallization was invented by Rot with a baker's helping hands. ";
			s += "The more reasonable path would follow it was invented to saturate the researches need for paper. ";
			s += "Today we can find the crystal often in closeness to temples, but the research behind is unkown. ";
			s += "All cristals need to be placed near croot.";
			break;
		case 1:
			s += "       ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.reeds), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.reedsCristal));
			s += "\n";
			s += "1. Place biomass\n";
			s += "2. Throw biological reagent\n";
			s += "3. Throw suger cane\n";
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
		return new GuiCristalRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.reedsCristal), KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.reeds), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner)));
	}
}
