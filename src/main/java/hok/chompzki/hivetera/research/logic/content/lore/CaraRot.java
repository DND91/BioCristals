package hok.chompzki.hivetera.research.logic.content.lore;

import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;

public class CaraRot extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){ //Carra'Rot 
		case 0:
			s += "After D'berry, Rabarberpaj and Carla & Fleur, the field of attunment went into conflict over ";
			s += " what was the central aspect that brought change into a system of attunment; The Living or The Dead. ";
			s += "Tierd of the conflict Carra'Rot started to bring in new substances into process. Rot soon found many ways ";
			s += "to cristalize The Dead and harvest the biomass from The Living. He noted that the two aspects didn't rule the system. ";
			s += "Rot is most famous for creation of The Nether and The End hypotheses.";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1;
	}
	
	
}
