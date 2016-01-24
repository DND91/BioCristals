package hok.chompzki.hivetera.client.book.parseNodes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class BodyParseNode extends ParseNode {
	
	/**
	 * SummaryParseNode handles the summary of the Article.
	 */
	
	private List<ParseNode> list = new ArrayList<ParseNode>();
	
	public BodyParseNode() {
		super("BODY", BodyParseNode.class);
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		ParseNode current = null;
		Token token = collection.stack.pop();
		
		if(token == BookTokenizer.endCommand){
			
		} else {
			System.err.println(command.toUpperCase() + ": Wanted a end command token ('>') but got " + token.value);
			return;
		}
		
		while(!collection.stack.isEmpty()){
			token = collection.stack.pop();
			
			if(token == BookTokenizer.startCommand){
				token = collection.stack.pop();
				String command = (String)token.value;
				command = command.toLowerCase();
				if(command.equals("summary")){
					collection.stack.addFirst(token);
					collection.stack.addFirst(BookTokenizer.startCommand);
					return;
				}
				current = ParseNode.get((String)token.value);
				if(current == null){
					System.err.println(command.toUpperCase() + ": Wanted a command name but got " + token.value);
					return;
				}
				
				this.list.add(current);
				current.parse(cursor, arc, collection);
			} else if (token instanceof WordToken){
				current = new ParagraphParseNode(cursor, (WordToken)token);
				
				this.list.add(current);
				current.parse(cursor, arc, collection);
			} else if(token == BookTokenizer.lineEnd) {
				current = new NewLineParseNode();
				this.list.add(current);
				current.parse(cursor, arc, collection);
			} else {
				System.err.println(command.toUpperCase() + ": Wanted a start command token ('<') or word but got " + token.value);
				return;
			}
		}
	}

	@Override
	public void print() {
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- ");
		ParseNode.addTab();
		for(ParseNode node : list)
			node.print();
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		for(ParseNode node : list)
			node.caulculate(cursor, arc, collection);
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		
		for(ParseNode node : list)
			node.draw(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale);
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		
		for(ParseNode node : list)
			node.drawOverlay(cursor, currPage, x, y, guiArticle, articleFontRenderer, summary, scale, mosX, mosY);
	}
}

