package hok.chompzki.biocristals.research.logic.content.lore;

import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class Rabarberpaj extends ArticleContent {
	
	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "Rabarberpaj is known as the attunment fields mother in agriculture. ";
			s += "She theorised that the inherited traits of both living and dead could be ";
			s += "cristalized and be turned into a self replicating process. Sadly enought she never lived to prove this, ";
			s += "but her most famous invenstion still livs on to this day: The Attuner. The item is one of the main instruments ";
			s += "of the field.";
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