package hok.chompzki.biocristals.research.logic.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public class Reaction extends ArticleContent {
	
	@Override
	public String textOnPage(Content content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		String s = "";
		switch(p){
		case 0:
			s += "To continue earlier work D'berry sought a continues process rather then just applying a trinkle of energy. ";
			s += "In this she soon discovred the mixing of diffrent substances could create a basic biological reagent. D'berry have ";
			s += "sience then been accused to be the creator of both Creepers and Ghasts. ";
			break;
		case 1:
			s += "The most promesing reagent is... \n";
			s += "        ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(ItemRegistry.bioReagent));
			s += "        ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(ItemRegistry.bioReagent));
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
		return GuiInventoryOverlay.craftingHelper.contains(new ItemStack(ItemRegistry.bioReagent));
	}
	
	@SideOnly(Side.CLIENT)
	public void selected(boolean selection){
		if(selection){
			GuiInventoryOverlay.craftingHelper.add(new GuiCraftRecipe(Minecraft.getMinecraft(), ReserchRegistry.reaction, new ItemStack(ItemRegistry.bioReagent)));
		}else{
			GuiInventoryOverlay.craftingHelper.remove(new ItemStack(ItemRegistry.bioReagent));
		}
	}
}
