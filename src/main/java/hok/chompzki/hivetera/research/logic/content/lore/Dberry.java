package hok.chompzki.hivetera.research.logic.content.lore;

import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;

public class Dberry extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "D'berry is controversial name in the circle of attunment. She is famous for the creation of the ";
			s += "biological reagent, but there are suspicions that she also have birthed the creeper and ghast. ";
			s += "She babbeled deeply into the arts of change between the living and other forces. ";
			s += "To build uppon earlier suspicions D'berry was known to have had adopted a enderman. ";
			s += "After mystical tradgedies in a villages she was thrown through a nether portal by the population. ";
			s += "To this day there have been no sign of her.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}
	
}
