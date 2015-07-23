package hok.chompzki.biocristals.research.logic.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class CubeMass extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		String s = "";
		switch(p){
		case 0:
			s += "A few years afer Rabarberpaj's discovery Carla & Fleur sprouted into the field. ";
			s += "They saw inefficiencies and hankered after something to ease thier laboratory work. ";
			s += "After many experiments involving rabbits, hawks and cabageheads (all extinct) they found 'Biomass'.";
			break;
		case 1:
			s += "Carla & Fleur's biomass\n\n";
			s += "        ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(BlockRegistry.biomass));
			s += "        ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.biomass));
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
		return new GuiCraftRecipe(Minecraft.getMinecraft(), code, new ItemStack(BlockRegistry.biomass));
	}
	
}
