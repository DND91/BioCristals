package hok.chompzki.biocristals.research.logic.content;

import net.minecraft.client.Minecraft;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;

public class BookTutorial extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "§l ~ Gui ~ §r\n";
			s += "The gui is divided in three parts:\n";
			s += "§lLeft bar:§r Chapeters, navigate through tiers and lore.\n";
			s += "§lMiddle area:§r Shows research and is used to navigate through them.\n";
			s += "§lRight bar:§r More information about a selected chapeter or research.\n";
			s += "§lNote:§r Unlocked & favorised researches is shown to others reading your book.\n";
			s += "Can only favorise in your own book!\n";
			break;
		case 1:
			s += "§l ~ Mouse ~ §r\n";
			s += "§lMouse-left + drag§r to move around middle area.\n";
			s += "§lMouse-left + node§r (center area) to select.\n";
			s += "§lMouse-right + node§r (center area) to favorise.\n";
			s += "§lMouse-left + chapeter§r (left bar) to select.\n";
			s += "§lMouse-right + chapeter§r (left bar) to select and open.\n";
			s += "§lMouse-left + box§r (right bar, bottom checkbox), if have recipe, favorise it..\n";
			s += "§lMouse-left + arrow§r (right bar), to change page.\n";
			break;
		case 2:
			s += "§l ~ Keyboard ~ §r\n";
			s += "§lMovement keys§r to move around middle area.\n";
			s += "§l1 key§r to change page to the left (right bar).\n";
			s += "§l2 key§r to favoritise research (right bar).\n";
			s += "§l3 key§r to change page to the right (right bar).\n";
			break;
		case 3:
			s += "§l ~ Crafting ~ §r\n";
			s += "- Hold book and open a inventory to view favorised.\n";
			s += "- In that mode left-click house icon to open book screen with selected research.\n";
			s += "- In that mode left-click grid pattern to place recipe in crafting grid.\n";
			s += "- Use arrows to move between favorised recipes.\n";
			break;
		case 4:
			s += "§l ~ To Start ~ §r\n";
			s += "1. Craft a croot sapling and plant in a big open area far away from anything you like (6-8 blocks).\n";
			s += "2. Craft a attuner.\n";
			s += "3. Explore the research and have fun!\n";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 5;
	}
	
	
	@Override
	public GuiCraftingHelper getFaved() {
		return new GuiCraft(Minecraft.getMinecraft(), code);
	}
	
}
