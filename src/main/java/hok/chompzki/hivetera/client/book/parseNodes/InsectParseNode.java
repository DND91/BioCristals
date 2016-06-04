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
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary.Type;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.book.tokens.NumberToken;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.data.BiomeKittehData;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.recipes.BreedingRecipe;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.registrys.BreedingRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.GuiCoord;

public class InsectParseNode extends ParseNode {
	
	private static final ResourceLocation background = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/parse/Insect.png");
	
	private int width = 16;
	private int height = 16;
	
	private int pageNumber = 0;
	private ItemStack stack = null;
	private int tick = 0;
	private int length = 3;
	
	private Minecraft mc = null;
	private static RenderItem itemRender = null;
	private static ArticleFontRenderer fts = null;
	
	public ArrayList<ItemStack> list = new ArrayList<ItemStack>();
	
	public InsectParseNode() {
		super("INSECT", InsectParseNode.class);
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
				
				for(Entry<Type, BiomeKittehData> entry : BiomeRegistry.kittehsBiomes.entrySet()){
					BiomeKittehData data = entry.getValue();
					for(Entry<Double, ItemStack> entry2 : data.entrySet()){
						ItemStack output = entry2.getValue();
						if(output != null && output.getItem() == stack.getItem() && output.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, output)){
							int id = BiomeRegistry.biomeToId.get(entry.getKey());
							ItemStack sample = new ItemStack(ItemRegistry.biomeSample, 1, id);
							this.list.add(sample);
							break;
						}
					}
				}
				
				
				width = 72;
				height = 32;
				
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
		for(int i = 0; i < (list.size()); i++){
			System.out.println(ParseNode.getTabs() + list.get(i).toString());
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
		
		ItemStack sample = null;
		if(list.size() != 0){
			int selected = (tick / 200) % list.size();
			sample = list.get(selected);
		}
		
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
        Gui.func_146110_a((int) (tx), (int) (ty), 0, 0, 72, 32, 72, 32);
        GL11.glPopMatrix();
        
		GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScaled(scale, scale, 1.0D);
		
		
		
		
		
		this.itemRender.renderWithColor = true;
		
		this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, (int)(tx + 27), (int)(ty + 7));
		this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack,   (int)(tx + 27), (int)(ty + 7));
		
		if(sample != null){
			this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, sample, (int)(tx + 6), (int)(ty + 7));
			this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, sample,   (int)(tx + 6), (int)(ty + 7));
			
			this.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, sample, (int)(tx + 48), (int)(ty + 7));
			this.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, sample,   (int)(tx + 48), (int)(ty + 7));
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
		tick++;
		
		ItemStack sample = null;
		if(list.size() != 0){
			int selected = (tick / 200) % list.size();
			sample = list.get(selected);
		}
		
		int size = (int) (fts.FONT_HEIGHT * scale);
		int xt = (int)((x + (cursor.x) - 0));
		int yt = (int)((y + (cursor.y*scale) - 6));
		
		int x2 = (int)(xt + 27 * scale);
		int y2 = (int)(yt + 7 * scale);
		
		if(x2 <= mosX && mosX <= (x2 + size)
				&& y2 <= mosY && mosY <= (y2 + size)){
			fts.renderToolTip(stack, mosX, mosY, 400, 400);
		}
		
		if(sample != null){
			x2 = (int)(xt + 6 * scale);
			y2 = (int)(yt + 7 * scale);
			
			if(x2 <= mosX && mosX <= (x2 + size)
					&& y2 <= mosY && mosY <= (y2 + size)){
				fts.renderToolTip(sample, mosX, mosY, 400, 400);
			}
			
			x2 = (int)(xt + 48 * scale);
			y2 = (int)(yt + 7 * scale);
			
			if(x2 <= mosX && mosX <= (x2 + size)
					&& y2 <= mosY && mosY <= (y2 + size)){
				fts.renderToolTip(sample, mosX, mosY, 400, 400);
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










