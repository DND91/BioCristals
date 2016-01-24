package hok.chompzki.hivetera.client.book.parseNodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.NumberToken;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class TabParseNode extends ParseNode {
	
	private int pageNumber = 0;
	private int spaces = 5;
	
	private static ArticleFontRenderer fts = null;
	private static Minecraft mc = null;
	
	public TabParseNode() {
		super("TAB", TabParseNode.class);
		if(mc == null)
			mc = Minecraft.getMinecraft();
		if(fts == null)
			this.fts = new ArticleFontRenderer(mc, mc.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		int n = spaces;
		
		while(!collection.stack.isEmpty()){
			Token token = collection.stack.pop();
			if(token == BookTokenizer.endCommand){
				this.spaces = n;
				String spt = "";
				for(int i = 0; i < spaces; i++)
					spt += " ";
				
				this.spaces = fts.getStringWidth(spt);
				
				if(arc.maxWidth < cursor.x + spaces){
					cursor.x = 0;
					cursor.y += fts.FONT_HEIGHT;
					if(arc.maxHeight < cursor.y){
						cursor.y = 0;
					}
				} else {
					cursor.x += spaces;
				}
				
				return;
			}else if(token instanceof WordToken){
				String c = (String)token.value;
				switch(c.toLowerCase()){
				case "space":
					token = collection.stack.pop();
					if(token != BookTokenizer.equals){
						System.err.println(command + ": Wanted a equals but got " + token.value);
						return;
					}
					token = collection.stack.pop();
					if(!(token instanceof NumberToken)){
						System.err.println(command + ": Wanted a number but got " + token.value);
						return;
					}
					n = (int)(double) token.value;
					break;
				}
			} else {
				System.err.println(command + ": Wanted a word or end command ('>') but got " + token.value);
				return;
			}
		}
		
		System.err.println(command + ": Something went wrong with an tab!");
	}

	@Override
	public void print() {
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- ");
		ParseNode.addTab();
		System.out.println(ParseNode.getTabs() + "SPACES: " + spaces);
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		this.pageNumber = arc.pageNumber;
		
		if(arc.maxWidth < cursor.x + spaces){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
			if(arc.maxHeight < cursor.y){
				arc.pageNumber++;
				cursor.y = 0;
			}
		} else {
			cursor.x += spaces;
		}
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(currPage != this.pageNumber) return;
		
		if(Article.maxWidth < cursor.x + spaces){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
		} else {
			cursor.x += spaces;
		}
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(currPage != this.pageNumber) return;
		
		if(Article.maxWidth < cursor.x + spaces){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
		} else {
			cursor.x += spaces;
		}
	}
	
	
}










