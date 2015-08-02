package hok.chompzki.biocristals.research.logic.content.lore;

import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;

public class CarlaFleur extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Carla & Fleur is said to have been Rabarberpajs apprentices. ";
			s += "Both had a tragic experience with D'berry under unkown circumstances, ";
			s += "leading to the twins nickname 'The labrats', as they would never LEAVE thier lab. ";
			s += "Both went missing after an explosion that burned thier castle down. They are famous for ";
			s += "the efficency brougth into the field of attunment, most notably 'Biomass'.";
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
		// TODO Auto-generated method stub
		return null;
	}
	
	
}