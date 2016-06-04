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
import net.minecraft.util.ResourceLocation;
import hok.chompzki.hivetera.HiveteraMod;
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
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class RecipeParseNode extends ParseNode {
	
	private static final ResourceLocation background = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/parse/Recipe.png");
	
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
		HashMap<String, String> nbtStrings = new HashMap<String, String>();
		
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
				
				if(0 < nbtStrings.size())
					stack.getItem().onCreated(stack, null, null);
				
				for(Entry<String, String> entry : nbtStrings.entrySet()){
					if(stack.stackTagCompound == null)
						stack.setTagCompound(new NBTTagCompound());
					stack.stackTagCompound.setString(entry.getKey(), entry.getValue());
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
				
				width = 103;
				height = 62;
				
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
		
		int tx = (int)((1.0D / scale)*(x + (cursor.x) - 0));
		int ty = (int)((1.0D / scale)*(y + (cursor.y*scale) - 6));
		
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glScaled(scale, scale, 1.0D);
		
        this.mc.getTextureManager().bindTexture(background);
        Gui.func_146110_a((int) (tx), (int) (ty), 0, 0, 103, 62, 103, 62);
        GL11.glPopMatrix();
		
		GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScaled(scale, scale, 1.0D);
		
		
		this.itemRender.renderWithColor = true;
		
		this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + 77, ty + 23);
		this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + 77, ty + 23);
		
		
		for(int dx = 0; dx < con.length; dx++)
		for(int dy = 0; dy < con.length; dy++){
			List<ItemStack> list = RecipeTransformer.dataToItemStack(con.getString(dx + dy * con.length), false);
			if(list != null && 0 < list.size()){
				int pos = tick;
	    		pos /= 50;
	    		pos %= list.size();
				ItemStack stack = list.get(pos);
				GL11.glDisable(GL11.GL_LIGHTING);
				this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + 10 + dx * 18, ty + 5 + dy * 18);
				this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, tx + 10 + dx * 18, ty + 5 + dy * 18);
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
		
		int x2 = (int)(xt + 77 * scale);
		int y2 = (int)(yt + 23 * scale);
		
		if(x2 <= mosX && mosX <= (x2 + size)
				&& y2 <= mosY && mosY <= (y2 + size)){
			fts.renderToolTip(stack, mosX, mosY, 400, 400);
		}
		
		for(int dx = 0; dx < con.length; dx++)
			for(int dy = 0; dy < con.length; dy++){
				x2 = (int) (xt + (10 + dx * 18) * scale);
				y2 = (int) (yt + (5 + dy * 18) * scale);
				
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










