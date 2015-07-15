package hok.chompzki.biocristals.research.logic.content;

import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.Content;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;
import net.minecraft.item.ItemStack;

public class TheWorldAroundUs extends ArticleContent {
	
	@Override
	public String textOnPage(Content content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass
		String s = "";
		switch(p){
		case 0:
			s += "Our journey befall us in a time where resources are scares and we have often found that time is more spent ";
			s += "on little things rather then the big whole. One of the more time consumping aspects are found in areas of ";
			s += "agriculture, as all of us have to eat and that food have to come from somewhere.  ";
			break;
		case 1:
			s += "As we study surface properties of the woodlands, ";
			s += "we can notice the inhernt property of attunment between the dead and the living. ";
			s += "By using somekind of attuner we should be able to perform somekind of abuse between ";
			s += "what is and what can be. From this we have conducted a study on what this object could be.";
			s += "\n\n";
			s += "- Hawk";
			break;
		case 2:
			s += "From our studies of the subject we propose a hypothes about the attuner's structure:\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(ItemRegistry.attuner));
			break;
		case 3:
			s += "To continue on our work we have found that the attunment processes is in need of ";
			s += ", what Hawk desribed as, a little help along the way. We found a few reagents that ";
			s += "functions as a base for reaction between matter A and substance B. "; 
			break;
		case 4:
			s += "The most promesing reagent was... \n";
			s += "           ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(ItemRegistry.bioReagent));
			s += "           ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(ItemRegistry.bioReagent));
			break;
		case 5:
			s += "After finding a promesing reagent we took a focus on the matter and substances for attunment. ";
			s += "Here we found that many substances was fit for the process of attunment, be they dead or alive. ";
			s += "Thou in the center of the process there was a need for a stable high life containing matter. ";
			break;
		case 6:
			s += "The most promesing mater was... \n";
			s += "           ~ Structure ~\n";
			s += KnowledgeDescriptions.transformRecipe(new ItemStack(BlockRegistry.biomass));
			s += "           ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(BlockRegistry.biomass));
			break;
		case 7:
			s += "Steps for activation:\n";
			s += "1. Place biomass\n";
			s += "2. Place bio. reagent close\n";
			s += "3. Place substance close, ex. Wheat\n";
			s += "4. Hold Attuner, bow it, release when sunny onto biomass";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(Content content){
		return 8;
	}
	
}
