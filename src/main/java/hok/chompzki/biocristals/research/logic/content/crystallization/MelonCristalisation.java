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

public class MelonCristalisation extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Some industrialized civilisations couldn't acquire pumkins, so they began, what is known today as the ";
			s += "\'Mirosa\' hunt. This quest for a replacement of the pumkin have both been seen as inspiring and convulsing as ";
			s += "the campaign leader commited genocide on villages that wouldn't comply with the mission. ";
			s += "All cristals need to be placed near croot.";
			break;
		case 1:
			s += "       ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.melon), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.melonCristal));
			s += "\n";
			s += "1. Place biomass\n";
			s += "2. Throw biological reagent\n";
			s += "3. Throw melon\n";
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
		return new GuiCristalRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.melonCristal), KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.melon), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner)));
	}
}
