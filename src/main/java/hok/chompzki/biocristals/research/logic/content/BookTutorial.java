package hok.chompzki.biocristals.research.logic.content;

import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;

public class BookTutorial extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){ //Carra'Rot 
		case 0:
			s += "Mouse-left + drag or movement keys to move around the left area.\n";
			s += "Mouse-left click on any of the green nodes to select.\n";
			s += "Mouse-right click on any of the green nodes to favorise for crafting, you can have many.\n";
			s += "Hold book and open a inventory to view favorised.\n";
			s += "In that mode click house icon to open book screen with selected research.\n";
			s += "In that mode click grid pattern to place items in selected research recipe pattern.\n";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}


	@Override
	public GuiCraftingHelper getFaved() {
		return null;
	}
	
}
