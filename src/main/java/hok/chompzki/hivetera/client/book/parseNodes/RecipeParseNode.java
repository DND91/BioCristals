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
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class RecipeParseNode extends ParseNode {
	
	
	private int width = 16;
	private int height = 16;
	
	private int pageNumber = 0;
	private RecipeContainer con = null;
	private ItemStack stack = null;
	private int tick = 0;
	
	private Minecraft mc = null;
	private static RenderItem itemRender = null;
	private static ArticleFontRenderer fts = null;
	
	
	
	public RecipeParseNode() {
		super("RECIPE", RecipeParseNode.class);
		if(mc == null)
			mc = Minecraft.getMinecraft();
		if(itemRender == null)
			this.itemRender = new RenderItem();
		if(fts == null)
			this.fts = new ArticleFontRenderer(mc, mc.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
	}
	
	public void parse(Cursor cursor, Article arc, TokenCollection collection) {
		
		String name = KnowledgeDescriptions.getName(new ItemStack(Blocks.command_block));
		String domain = "minecraft";
		int damage = 0;
		
		while(!collection.stack.isEmpty()){
			Token token = collection.stack.pop();
			if(token == BookTokenizer.endCommand){
				stack = RecipeTransformer.dataToItemStack(domain + ":" + name, false).get(0);
				if(stack != null && stack.getItem() != null){
					stack.stackSize = 1;
					stack.setItemDamage(damage);
				} else {
					stack = new ItemStack(Blocks.command_block);
					System.err.println(command + ": Didn't find item " + domain + ":" + name);
				}
				
				for(RecipeContainer con : RecipeRegistry.recipes){
					ItemStack output = con.output;
					if(output.getItem() == stack.getItem() && output.getItemDamage() == stack.getItemDamage()){
						this.con = con;
						break;
					}
				}
				int length = 0;
				if(con != null)
					length = con.length;
				
				width = ItemStackParseNode.width * (2 + length);
				height = fts.FONT_HEIGHT * (length-1);
				
				if(arc.maxWidth < cursor.x + width){
					cursor.x = 0;
					cursor.y += height;
					if(arc.maxHeight < cursor.y){
						cursor.y = 0;
					}
				} else {
					cursor.x += width;
					cursor.y += height;
					if(arc.maxHeight < cursor.y){
						cursor.y = 0;
					}
				}
				
				return;
			}else if(token instanceof WordToken){
				String c = (String)token.value;
				switch(c.toLowerCase()){
				case "name":
					token = collection.stack.pop();
					if(token != BookTokenizer.equals){
						System.err.println(command + ": Wanted a equals but got " + token.value);
						return;
					}
					token = collection.stack.pop();
					if(!(token instanceof WordToken)){
						System.err.println(command + ": Wanted a word but got " + token.value);
						return;
					}
					name = (String) token.value;
					break;
				case "domain":
					token = collection.stack.pop();
					if(token != BookTokenizer.equals){
						System.err.println(command + ": Wanted a equals but got " + token.value);
						return;
					}
					token = collection.stack.pop();
					if(!(token instanceof WordToken)){
						System.err.println(command + ": Wanted a word but got " + token.value);
						return;
					}
					domain = (String) token.value;
					break;
				case "damage":
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
					damage = (int)((double)token.value);
					break;
				}
			} else {
				System.err.println(command + ": Wanted a word but got " + token.value);
				return;
			}
		}
		
		System.err.println(command + ": Something went wrong with an ItemStack!");
	}
	
	@Override
	public void print() {
		System.out.println(ParseNode.getTabs() + " -- " + command.toUpperCase() + " -- ");
		ParseNode.addTab();
		System.out.println(ParseNode.getTabs() + "OUTPUT: " + stack);
		for(int y = 0; y < (con.length); y++){
			String s = "";
			for(int x = 0; x < (con.length); x++){
				s += con.getString(x + y * con.length) + " ";
			}
			System.out.println(ParseNode.getTabs() + s);
		}
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		this.pageNumber = arc.pageNumber;
		
		if(arc.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += height;
			if(arc.maxHeight < cursor.y){
				cursor.y = 0;
			}
		} else {
			cursor.x += width;
			cursor.y += height;
			if(arc.maxHeight < cursor.y){
				cursor.y = 0;
			}
		}
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(currPage != this.pageNumber) return;
		tick++;
		GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScaled(scale, scale, 1.0D);
		
		
		int tx = (int)((1.0D / scale)*(x + (cursor.x) - 0));
		int ty = (int)((1.0D / scale)*(y + (cursor.y*scale) - 6));
		
		
		this.itemRender.renderWithColor = true;
		
		this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx, (int)(ty + fts.FONT_HEIGHT / con.length * scale));
		this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx, (int)(ty + fts.FONT_HEIGHT / con.length * scale));
		
		
		for(int dx = 0; dx < con.length; dx++)
		for(int dy = 0; dy < con.length; dy++){
			List<ItemStack> list = RecipeTransformer.dataToItemStack(con.getString(dx + dy * con.length), false);
			if(list != null && 0 < list.size()){
				int pos = tick;
	    		pos /= 50;
	    		pos %= list.size();
				ItemStack stack = list.get(pos);
				this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + (dx+2) * fts.FONT_HEIGHT, (int)(ty + dy * (fts.FONT_HEIGHT+2) * scale));
				this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + (dx+2) * fts.FONT_HEIGHT, (int)(ty + dy * (fts.FONT_HEIGHT+2) * scale));
			}
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        
        GL11.glPopMatrix();
		
        if(Article.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += height;
			if(Article.maxHeight < cursor.y){
				cursor.y = 0;
			}
		} else {
			cursor.x += width;
			cursor.y += height;
			if(Article.maxHeight < cursor.y){
				cursor.y = 0;
			}
		}
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(currPage != this.pageNumber) return;
		
		int size = (int) (fts.FONT_HEIGHT * scale);
		int xt = (int)((x + (cursor.x) - 0));
		int yt = (int)((y + (cursor.y*scale) - 6));
		
		
		int y2 = (int)(yt + fts.FONT_HEIGHT / con.length * scale);
		
		if(xt <= mosX && mosX <= (xt + size)
				&& y2 <= mosY && mosY <= (y2 + size)){
			fts.renderToolTip(stack, mosX, mosY, 400, 400);
		}
		
		for(int dx = 0; dx < con.length; dx++)
			for(int dy = 0; dy < con.length; dy++){
				int x2 = (int) (xt + (dx+2) * fts.FONT_HEIGHT * scale);
				y2 = (int)(yt + dy * (fts.FONT_HEIGHT+2) * scale);
				
				if(x2 <= mosX && mosX <= (x2 + size)
						&& y2 <= mosY && mosY <= (y2 + size)){
					
					List<ItemStack> list = RecipeTransformer.dataToItemStack(con.getString(dx + dy * con.length), false);
					if(list != null && 0 < list.size()){
						int pos = tick;
			    		pos /= 50;
			    		pos %= list.size();
						ItemStack stack = list.get(pos);
						if(stack != null)
							fts.renderToolTip(stack, mosX, mosY, 400, 400);
					}
				}
			}
		
		if(Article.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += height;
			if(Article.maxHeight < cursor.y){
				cursor.y = 0;
			}
		} else {
			cursor.x += width;
			cursor.y += height;
			if(Article.maxHeight < cursor.y){
				cursor.y = 0;
			}
		}
	}
	
	
}










