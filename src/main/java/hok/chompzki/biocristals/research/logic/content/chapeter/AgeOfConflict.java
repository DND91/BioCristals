package hok.chompzki.biocristals.research.logic.content.chapeter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class AgeOfConflict extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "After the fall of the big three the attunment community fall into disarray over what was the rulling aspect; Life or Death. ";
			s += "Many arguments saw there rise and fall, some even lead to violent conflicts. This part of time is famous for dispute, but also ";
			s += "for what can only be described as random sprouting of research. ";
			s += "";
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
		return 2;
	}


	@Override
	public GuiCraftingHelper getFaved() {
		// TODO Auto-generated method stub
		return null;
	}
}