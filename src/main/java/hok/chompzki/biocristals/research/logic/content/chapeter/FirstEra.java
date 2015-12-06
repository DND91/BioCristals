package hok.chompzki.biocristals.research.logic.content.chapeter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.ArticleContent.EnumContent;
import hok.chompzki.biocristals.client.gui.GuiCraft;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.GuiInventoryOverlay;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;

public class FirstEra extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "The 'First Era' befall in a time when resources are scares. Often we find time trinkling away "
			  +  "on the little actions for survival and a constant hunt for food. The 'First Era' is only known "
			  +  "to have ended when the nomads setteled. Looking back we see a clear connection between "
			  +  "humans and nature, as they started to travel, nature started bend after their creativity "
			  + "and skill.";
			break;
		case 1: 
			
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
}
