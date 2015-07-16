package hok.chompzki.biocristals.research.logic.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
import hok.chompzki.biocristals.client.GuiCristalRecipe;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.Content;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class WheatCristalisation extends ArticleContent {

	@Override
	public String textOnPage(Content content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Rot was never intressted in spreading many of his findings to others and it was shortly before his death ";
			s += "that many of his notes came into the hands of the field. One of the most notibale notes was the one on ";
			s += "wheat crystallization. On the other side you can find the Ccrystallization activation.";
			break;
		case 1:
			s += "       ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWealCristal(new ItemStack(Items.wheat), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
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
	public int numberOfPages(Content content){
		return 2;
	}
	
	public boolean hasPageSelection(int i){
		return i == 1;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean initSelection(){
		return GuiInventoryOverlay.craftingHelper.contains(new ItemStack(BlockRegistry.wheatCristal));
	}
	
	@SideOnly(Side.CLIENT)
	public void selected(boolean selection){
		if(selection){
			GuiInventoryOverlay.craftingHelper.add(new GuiCristalRecipe(Minecraft.getMinecraft(), ReserchRegistry.wheatCristalisation, new ItemStack(BlockRegistry.wheatCristal), KnowledgeDescriptions.transformWealCristal(new ItemStack(Items.wheat), new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner))));
		}else{
			GuiInventoryOverlay.craftingHelper.remove(new ItemStack(BlockRegistry.wheatCristal));
		}
	}
}
