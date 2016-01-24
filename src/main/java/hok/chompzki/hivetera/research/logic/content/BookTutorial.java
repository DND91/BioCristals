package hok.chompzki.hivetera.research.logic.content;

import net.minecraft.client.Minecraft;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;

public class BookTutorial extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "" + ((char)167) + "l ~ Gui ~ " + ((char)167) + "r\n";
			s += "The gui is divided in three parts:\n";
			s += "" + ((char)167) + "lLeft bar:" + ((char)167) + "r Chapeters, navigate through tiers and lore.\n";
			s += "" + ((char)167) + "lMiddle area:" + ((char)167) + "r Shows research and is used to navigate through them.\n";
			s += "" + ((char)167) + "lRight bar:" + ((char)167) + "r More information about a selected chapeter or research.\n";
			s += "" + ((char)167) + "lNote:" + ((char)167) + "r Unlocked & favorised researches is shown to others reading your book.\n";
			s += "Can only favorise in your own book!\n";
			break;
		case 1:
			s += "" + ((char)167) + "l ~ Mouse ~ " + ((char)167) + "r\n";
			s += "" + ((char)167) + "lMouse-left + drag" + ((char)167) + "r to move around middle area.\n";
			s += "" + ((char)167) + "lMouse-left + node" + ((char)167) + "r (center area) to select.\n";
			s += "" + ((char)167) + "lMouse-right + node" + ((char)167) + "r (center area) to favorise.\n";
			s += "" + ((char)167) + "lMouse-left + chapeter" + ((char)167) + "r (left bar) to select.\n";
			s += "" + ((char)167) + "lMouse-right + chapeter" + ((char)167) + "r (left bar) to select and open.\n";
			s += "" + ((char)167) + "lMouse-left + box" + ((char)167) + "r (right bar, bottom checkbox), if have recipe, favorise it..\n";
			s += "" + ((char)167) + "lMouse-left + arrow" + ((char)167) + "r (right bar), to change page.\n";
			break;
		case 2:
			s += "" + ((char)167) + "l ~ Keyboard ~ " + ((char)167) + "r\n";
			s += "" + ((char)167) + "lMovement keys" + ((char)167) + "r to move around middle area.\n";
			s += "" + ((char)167) + "l1 key" + ((char)167) + "r to change page to the left (right bar).\n";
			s += "" + ((char)167) + "l2 key" + ((char)167) + "r to favoritise research (right bar).\n";
			s += "" + ((char)167) + "l3 key" + ((char)167) + "r to change page to the right (right bar).\n";
			break;
		case 3:
			s += "" + ((char)167) + "l ~ Crafting ~ " + ((char)167) + "r\n";
			s += "- Hold book and open a inventory to view favorised.\n";
			s += "- In that mode left-click house icon to open book screen with selected research.\n";
			s += "- In that mode left-click grid pattern to place recipe in crafting grid.\n";
			s += "- Use arrows to move between favorised recipes.\n";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 4;
	}
	
}
