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

public class Settlement extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Summer came and left, winter filled its emtpy shell. Nomads lived a hard life. "
			  +  "Soon some of them came to settle down, when planting seeds into the ground. "
			  +  "But they learned fast that the insects had to come with, so they started "
			  +  "to change the envoierment to fit both parties. In symbiosis they lived. ";
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
