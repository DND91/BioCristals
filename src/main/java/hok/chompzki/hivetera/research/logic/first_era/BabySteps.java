package hok.chompzki.hivetera.research.logic.first_era;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.GuiCrootStickHelper;
import hok.chompzki.hivetera.client.gui.GuiInventoryOverlay;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.ReserchRegistry;

public class BabySteps extends ArticleContent {
	
	private ItemStack stack = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.crootBeetle);
		String s = "";
		switch(p){
		case 0:
			s += "The first thing to catch our ancestors intrest was the "
				+"web spinning '" + stack.getDisplayName() + "'.\n\n";
			s += KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "When they in desperation for seeds, to eat, ripped up "
			   + "a patch of grass, the first '" + stack.getDisplayName() + "' fell out. "
			   + "First reaction were to eat and it proved to be fatal as it would glue "
			   + "itself stuck on the tounge. This didn't scare them off and they keept on "
			   + "experimenting. They are the early fundation for field of bioculture. "
			   + "You can find some in grass. ";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			
			break;
		case 2:
			
			break;

		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}
	
	/*@Override
	public GuiCraftingHelper getFaved() {
		return new GuiCrootStickHelper(Minecraft.getMinecraft(), code, null, new ItemStack(ItemRegistry.crootBeetle), new ItemStack(Blocks.tallgrass, 1, 1));
	}*/
}
