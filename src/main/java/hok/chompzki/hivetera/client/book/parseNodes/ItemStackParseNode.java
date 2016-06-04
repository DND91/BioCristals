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
import net.minecraft.nbt.NBTTagCompound;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.NumberToken;
import hok.chompzki.hivetera.client.book.tokens.StringToken;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class ItemStackParseNode extends ParseNode {
	
	private int pageNumber = 0;
	public static final int width = 16;
	
	private ItemStack stack = null;
	private Minecraft mc = null;
	private static RenderItem itemRender = null;
	private static ArticleFontRenderer fts = null;
	
	public ItemStackParseNode() {
		super("ITEMSTACK", ItemStackParseNode.class);
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
		int size = 1;
		HashMap<String, String> nbtStrings = new HashMap<String, String>();
		
		while(!collection.stack.isEmpty()){
			Token token = collection.stack.pop();
			if(token == BookTokenizer.endCommand){
				if(name.toLowerCase().equals("empty")){
					stack = null;
					return;
				}
				stack = RecipeTransformer.dataToItemStack(domain + ":" + name, false).get(0);
				if(stack != null && stack.getItem() != null){
					stack = stack.copy();
					stack.stackSize = size;
					stack.setItemDamage(damage);
				} else {
					stack = new ItemStack(Blocks.command_block);
					System.err.println(arc.name + ":" +command + ": Didn't find item " + domain + ":" + name);
				}
				
				for(Entry<String, String> entry : nbtStrings.entrySet()){
					if(stack.stackTagCompound == null)
						stack.setTagCompound(new NBTTagCompound());
					stack.stackTagCompound.setString(entry.getKey(), entry.getValue());
				}
				
				if(arc.maxWidth < cursor.x + width){
					cursor.x = 0;
					cursor.y += fts.FONT_HEIGHT;
					if(arc.maxHeight < cursor.y){
						cursor.y = 0;
					}
				} else {
					cursor.x += width;
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
				case "size":
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
					size = (int)((double)token.value);
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
				case "nbt_string":
					token = collection.stack.pop();
					if(token != BookTokenizer.equals){
						System.err.println(command + ": Wanted a equals but got " + token.value);
						return;
					}
					token = collection.stack.pop();
					if(!(token instanceof StringToken)){
						System.err.println(command + ": Wanted a string (\" \") but got " + token.value);
						return;
					}
					String[] arr = ((String)token.value).split(" ");
					if(arr.length % 2 != 0){
						System.err.println(command + ": Wanted a even number of commands but got " + token.value);
						return;
					}
					for(int i = 0; i < arr.length; i += 2){
						String keyString = arr[i];
						String valueString = arr[i+1];
						nbtStrings.put(keyString, valueString);
					}
					
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
		System.out.println(ParseNode.getTabs() + stack);
		ParseNode.subTab();
	}
	
	@Override
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		this.pageNumber = arc.pageNumber;
		
		if(arc.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
			if(arc.maxHeight < cursor.y){
				arc.pageNumber++;
				cursor.y = 0;
			}
		} else {
			cursor.x += width;
		}
	}
	
	@Override
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		if(currPage != this.pageNumber) return;
		
		if(stack != null){
			GL11.glPushMatrix();
	        RenderHelper.enableGUIStandardItemLighting();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScaled(scale, scale, 1.0D);
			this.itemRender.renderWithColor = true;
			this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, (int)((1.0D / scale)*(x + (cursor.x) - 0)), (int)((1.0D / scale)*(y + (cursor.y*scale) - 6)));
			this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, (int)((1.0D / scale)*(x + (cursor.x) - 0)), (int)((1.0D / scale)*(y + (cursor.y*scale) - 6)));
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	        
	        GL11.glPopMatrix();
		}
		
		if(Article.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
		} else {
			cursor.x += width;
		}
	}
	
	@Override
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		if(currPage != this.pageNumber) return;
		
		int size = (int) (width * scale);
		int xt = (int) (x + (cursor.x) + 0);
		int yt = (int) (y + (cursor.y*scale) - 6);
		
		
		if(stack != null && xt <= mosX && mosX <= (xt + size)
				&& yt <= mosY && mosY <= (yt + size)){
			fts.renderToolTip(stack, mosX, mosY, 400, 400);
		}
		
		if(Article.maxWidth < cursor.x + width){
			cursor.x = 0;
			cursor.y += fts.FONT_HEIGHT;
		} else {
			cursor.x += width;
		}
	}
	
	
}










