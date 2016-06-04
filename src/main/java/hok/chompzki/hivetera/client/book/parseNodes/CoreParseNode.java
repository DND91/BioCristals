package hok.chompzki.hivetera.client.book.parseNodes;

import net.minecraft.client.gui.Gui;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class CoreParseNode extends ParseNode {
	
	/**
	 * CoreParseNode handles the main content of the Article. It consists of these nodes:
	 * 	- SUMMARY
	 * 	- BODY
	 */
	
	private String name = null;
	private ParseNode summary = null;
	private ParseNode body = null;
	
	public CoreParseNode() {
		super("CORE", CoreParseNode.class);
		
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		ParseNode current = null;
		name = arc.name;
		
		while(!collection.stack.isEmpty()){
			Token token = collection.stack.pop();
			
			if(token == BookTokenizer.startCommand){
				token = collection.stack.pop();
				String command = (String)token.value;
				command = command.toLowerCase();
				if(command.equals("summary") || command.equals("body")){
					if(command.equals("summary")) {
						if(summary == null){
							current = summary = ParseNode.get((String)token.value);
						} else {
							current = summary;
						}
					} else if (command.equals("body")){
						if(body == null){
							current = body = ParseNode.get((String)token.value);
						} else {
							current = body;
						}
					}
					
					if(current == null){
						System.err.println(command.toUpperCase() + ": Missing parser node for " + token.value);
						return;
					}
					
					current.parse(cursor, arc, collection);
				} else {
					System.err.println(arc.name + ":" + command.toUpperCase() + ": Wanted a command (SUMMARY or BODY) but got " + token.value);
					return;
				}
			} else if(token == BookTokenizer.lineEnd) {
				//WILL IGNORE END LINES
			} else {
				System.err.println(command.toUpperCase() + ": Wanted a start command token ('<') but got " + token.value);
				return;
			}
		}
		
		if(body == null || summary == null){
			System.err.println(command.toUpperCase() + ": Uncomplete core missing either summary of body commands!");
			body = null;
			summary = null;
			return;
		}
	}
	
	@Override
	public void print() {
		if(body == null || summary == null){
			System.err.println(command.toUpperCase() + ": Uncomplete core missing either summary of body commands!");
			return;
		}
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- NAME: " + this.name);
		ParseNode.addTab();
		summary.print();
		body.print();
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		if(body == null || summary == null){
			System.err.println(command.toUpperCase() + ": Uncomplete core missing either summary of body commands!");
			return;
		}
		
		cursor.x = 0;
		cursor.y = 0;
		arc.pageNumber = 0;
		summary.caulculate(cursor, arc, collection);
		
		cursor.x = 0;
		cursor.y = 0;
		arc.pageNumber = 0;
		body.caulculate(cursor, arc, collection);
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(body == null || this.summary == null){
			return;
		}
		
		if(summary)
			this.summary.draw(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale);
		else
			this.body.draw(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale);
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(body == null || this.summary == null){
			return;
		}
		
		if(summary)
			this.summary.drawOverlay(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale, mosX, mosY);
		else
			this.body.drawOverlay(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale, mosX, mosY);
	}
}
