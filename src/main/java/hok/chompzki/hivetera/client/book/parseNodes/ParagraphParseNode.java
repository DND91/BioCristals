package hok.chompzki.hivetera.client.book.parseNodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.NumberToken;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class ParagraphParseNode extends ParseNode {
	
	private String current = "";
	private List<String> list = new ArrayList<String>();
	private HashMap<Integer, Integer> pages = new HashMap<Integer, Integer>();
	private static ArticleFontRenderer fts = null;
	
	private int startPage = 0;
	private int endPage = 0;
	
	public ParagraphParseNode() {
		super("PARAGRAPH", ParagraphParseNode.class);
		Minecraft mc = Minecraft.getMinecraft();
		if(fts == null)
			this.fts = new ArticleFontRenderer(mc, mc.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
	}

	public ParagraphParseNode(Cursor cursor, WordToken first) {
		super("PARAGRAPH", ParagraphParseNode.class);
		current = (String)first.value + " ";
		Minecraft mc = Minecraft.getMinecraft();
		if(fts == null)
			this.fts = new ArticleFontRenderer(mc, mc.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
		cursor.x += fts.getStringWidth((String)first.value + " ");
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		if(collection.stack.peek() == BookTokenizer.endCommand)
				collection.stack.pop();
		
		while(!collection.stack.isEmpty()){
			if(collection.stack.peek() == BookTokenizer.startCommand){
				if(current != null && 0 < current.length()){
					current = current.substring(0, current.length()-1);
				}
				if(current != null){
					list.add(current.trim());
				}
				return;
			}
			
			Token token = collection.stack.pop();
			
			if(token == BookTokenizer.lineEnd){
				if(current == null)
					current = "";
				list.add(current.trim());
				cursor.x = 0;
				cursor.y += fts.FONT_HEIGHT;;
				current = "";
			}else if(token instanceof WordToken || token instanceof NumberToken){
				if(current == null)
					current = "";
				int s = fts.getStringWidth(current);
				int oldL = cursor.x - s;
				current += token.value + " ";
				
				try{
					cursor.x += fts.getStringWidth(token.value + " ");
				}catch (Exception ex){
					System.err.println("[PARSING ERROR]" + arc.name + ": " + token.value);
					throw ex;
				}
				
				if(Article.maxWidth < cursor.x){
					int j = fts.sizeStringToWidth(current, Article.maxWidth - oldL);
					list.add(current.substring(0, j).trim());
					current = current.substring(j, current.length());
					current = current.trim() + " ";
					cursor.y += fts.FONT_HEIGHT;
					cursor.x = fts.getStringWidth(current);
				}
			} else {
				System.err.println(arc.name + ":" + command.toUpperCase() + ": Wanted a word or number but got " + token.value);
				return;
			}
		}
		
		if(current == null)
			current = "";
		list.add(current.trim());
		cursor.x = fts.getStringWidth(current);
		cursor.y += fts.FONT_HEIGHT;;
		current = "";
	}

	@Override
	public void print() {
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- ");
		System.out.println(ParseNode.getTabs() + "Start Page: " + this.startPage);
		System.out.println(ParseNode.getTabs() + "End Page: " + this.endPage);
		ParseNode.addTab();
		for(int i = 0; i < list.size(); i++){
			if(this.pages.containsValue(i)){
				for(Entry<Integer, Integer> entry : pages.entrySet())
					if(entry.getValue() == i){
						System.out.println(ParseNode.getTabs() + "# PAGE: " + entry.getKey());
						break;
					}
			}
			System.out.println(ParseNode.getTabs() + list.get(i));
		}
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		this.startPage = arc.pageNumber;
		this.pages.put(this.startPage, 0);
		
		for(int i = 0; i < list.size()-1; i++){
			cursor.y += fts.FONT_HEIGHT;
			
			if(Article.maxHeight < cursor.y){
				arc.pageNumber++;
				cursor.y -= Article.maxHeight;
				this.pages.put(arc.pageNumber, i);
			}
		}
		
		cursor.x = fts.getStringWidth(this.list.get(list.size()-1));
		
		this.endPage = arc.pageNumber;
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(currPage < startPage || endPage < currPage)
			return;
		int sl = this.pages.get(currPage);
		int el = this.list.size();
		if(this.pages.containsKey(currPage+1))
			el = this.pages.get(currPage+1);
		
		int xd = 0;
		articleFontRenderer.setScale((float)scale);
		for(int i = sl; i < el; i++){
			articleFontRenderer.drawString(list.get(i), (int)(cursor.x * scale) + x, y + (int)(cursor.y * scale), 0x000000);
			xd = this.fts.getStringWidth(list.get(i));
			if(i != (el-1))
				cursor.y += fts.FONT_HEIGHT;
			cursor.x = 0;
		}
		articleFontRenderer.setScale(1.0F);
		
		cursor.x = xd;
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(currPage < startPage || endPage < currPage)
			return;
		int sl = this.pages.get(currPage);
		int el = this.list.size();
		if(this.pages.containsKey(currPage+1))
			el = this.pages.get(currPage+1);
		
		int xd = 0;
		for(int i = sl; i < el; i++){
			xd = this.fts.getStringWidth(list.get(i));
			if(i != (el-1))
				cursor.y += fts.FONT_HEIGHT;
			cursor.x = 0;
		}
		
		cursor.x = xd;
	}
}










