package hok.chompzki.hivetera.client.book;

import hok.chompzki.hivetera.client.book.parseNodes.ParseNode;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;

public class Article {
	public static int maxWidth = GuiArticle.pageImageWidth;
	public static int maxHeight = GuiArticle.pageImageHeight - ArticleFontRenderer.FONT_HEIGHT * 3;
	
	public int pageNumber = 0;
	public ParseNode mainBody = null;
	public String name = "";
	
}
