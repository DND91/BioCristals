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

public class NewPageParseNode extends ParseNode {
	
	private int pageNumber = 0;
	
	private static ArticleFontRenderer fts = null;
	private static Minecraft mc = null;
	
	public NewPageParseNode() {
		super("NP", NewPageParseNode.class);
		if(mc == null)
			mc = Minecraft.getMinecraft();
		if(fts == null)
			this.fts = new ArticleFontRenderer(mc, mc.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		if(collection.stack.peek() == BookTokenizer.endCommand)
			collection.stack.pop();
		
		cursor.y = 0;
		cursor.x = 0;
	}

	@Override
	public void print() {
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- ");
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		this.pageNumber = arc.pageNumber;
		
		cursor.y = 0;
		cursor.x = 0;
		arc.pageNumber++;
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(currPage != this.pageNumber) return;
		
		cursor.y = 0;
		cursor.x = 0;
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(currPage != this.pageNumber) return;
		
		cursor.y = 0;
		cursor.x = 0;
	}
	
	
}










