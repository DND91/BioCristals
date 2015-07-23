package hok.chompzki.biocristals.research.gui;

import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.logic.content.BookTutorial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

public class GuiTutorialArticle extends GuiArticle {

	public GuiTutorialArticle(EntityPlayer reader) {
		this.reader = reader;
		content = new BookTutorial();
		research = new Research("BookTutorial", -10, -10, BlockRegistry.crootSapling, content).setSpecial();
		//BookTutorial
	}

}
