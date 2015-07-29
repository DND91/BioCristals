package hok.chompzki.biocristals.research.logic.content;

import net.minecraft.client.Minecraft;
import hok.chompzki.biocristals.client.GuiCraft;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class Purifier extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Carla & Fleur built on Raberberpaj's notes of 'Croot Stem' and 'Croot Hollow'. They created what is known as a purifier. ";
			s += "The output side was painted yellow, while the filter side was painted purple and input red. ";
			s += "The purifier is used for complex attunment processes and can be rotated with a hard twist. ";
			s += "\nNOTE: Right-click + sneak it to mark owner! Most do this to get research from it. ";
			
			break;
		case 1:
			s += KnowledgeDescriptions.getDisplayName(code) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(code);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(code);
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
		return new GuiCraft(Minecraft.getMinecraft(), code);
	}

}
