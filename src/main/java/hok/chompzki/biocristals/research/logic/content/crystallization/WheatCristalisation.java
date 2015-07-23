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

public class WheatCristalisation extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Rot was never intressted in spreading many of his findings to others and it was shortly before his death ";
			s += "that many of his notes came into the hands of the field. One of the most notibale notes was the one on ";
			s += "wheat crystallization. On the other side you can find the Ccrystallization activation. All cristals need to be placed ";
			s += "near croot.";
			break;
		case 1:
			s += "       ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.wheat), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.wheatCristal));
			s += "\n";
			s += "1. Place biomass\n";
			s += "2. Throw biological reagent\n";
			s += "3. Throw wheat\n";
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
		return new GuiCristalRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.wheatCristal), KnowledgeDescriptions.transformWeakCristal(new ItemStack(Items.wheat), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner)));
	}
}
