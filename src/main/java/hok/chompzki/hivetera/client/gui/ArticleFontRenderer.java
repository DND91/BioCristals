package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.recipes.RecipeTransformer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ibm.icu.text.Bidi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ArticleFontRenderer {
	
	protected static HashMap<Class, EntityLivingBase> template = new HashMap<Class, EntityLivingBase>();
	
    public static int tick = 0;
	protected boolean entityMode = false;
	protected boolean itemMode = false;
	protected boolean itemDmgMode = false;
	protected RenderItem itemRender;
	protected String nameID = null;
	protected int itemDMG = 0;
	
	/** Array of width of all the characters in default.png */
    private int[] charWidth = new int[256];

    /** the height in pixels of default text */
    public static final int FONT_HEIGHT = 16;
    public Random fontRandom = new Random();

    /**
     * Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory.
     */
    private byte[] glyphWidth = new byte[65536];

    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private int[] colorCode = new int[32];
    private final String fontTextureName;

    /** The RenderEngine used to load and setup glyph textures. */
    private final TextureManager renderEngine;
    protected final ResourceLocation locationFontTexture;

    /** Current X coordinate at which to draw the next character. */
    private float posX;

    /** Current Y coordinate at which to draw the next character. */
    private float posY;

    /**
     * If true, strings should be rendered with Unicode fonts instead of the default.png font
     */
    private boolean unicodeFlag;

    /**
     * If true, the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    private boolean bidiFlag;

    /** Used to specify new red value for the current color. */
    private float red;

    /** Used to specify new blue value for the current color. */
    private float blue;

    /** Used to specify new green value for the current color. */
    private float green;

    /** Used to speify new alpha value for the current color. */
    private float alpha;

    /** Text color of the currently rendering string. */
    private int textColor;

    /** Set if the "k" style (random) is active in currently rendering string */
    private boolean randomStyle = false;

    /** Set if the "l" style (bold) is active in currently rendering string */
    private boolean boldStyle = false;

    /** Set if the "o" style (italic) is active in currently rendering string */
    private boolean italicStyle = false;

    /**
     * Set if the "n" style (underlined) is active in currently rendering string
     */
    private boolean underlineStyle = false;

    /**
     * Set if the "m" style (strikethrough) is active in currently rendering string
     */
    private boolean strikethroughStyle = false;
    
    private Minecraft theGame;
    private ItemStack itemStack = new ItemStack(Items.golden_apple);
    private Entity entity = null;
    
    private ItemStack stackBucket = null;
    private EntityLivingBase entityBucket = null;
    private int mosX;
    private int mosY;
    
    private float scale = 1.0f;

    private ArticleFontRenderer()
    {
        this.renderEngine = null;
        this.fontTextureName = null;
        this.locationFontTexture = null;
    }

    public ArticleFontRenderer(Minecraft minecraft, GameSettings par1GameSettings, String par2Str, TextureManager par3RenderEngine, boolean par4)
    {
        this.fontTextureName = par2Str;
        this.locationFontTexture = new ResourceLocation(par2Str);
        this.renderEngine = par3RenderEngine;
        this.unicodeFlag = par4;
        this.itemRender = new RenderItem();
        this.theGame = minecraft;
        this.readFontData();
        
        par3RenderEngine.bindTexture(new ResourceLocation(par2Str));

        for (int i = 0; i < 32; ++i)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i >> 0 & 1) * 170 + j;

            if (i == 6)
            {
                k += 85;
            }

            if (par1GameSettings.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
        this.readGlyphSizes();
    }
    
    public void setScale(float s){
    	this.scale = s;
    }

    public void readFontData()
    {
        this.readGlyphSizes();
        this.readFontTexture();
    }

    private void readFontTexture()
    {
        BufferedImage bufferedimage;

        try
        {
        	bufferedimage = ImageIO.read(getResourceInputStream(this.locationFontTexture));
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }

        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] aint = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        int k = j / 16;
        int l = i / 16;
        byte b0 = 1;
        float f = 8.0F / (float)l;
        int i1 = 0;

        while (i1 < 256)
        {
            int j1 = i1 % 16;
            int k1 = i1 / 16;

            if (i1 == 32)
            {
                this.charWidth[i1] = 3 + b0;
            }

            int l1 = l - 1;

            while (true)
            {
                if (l1 >= 0)
                {
                    int i2 = j1 * l + l1;
                    boolean flag = true;

                    for (int j2 = 0; j2 < k && flag; ++j2)
                    {
                        int k2 = (k1 * l + j2) * i;

                        if ((aint[i2 + k2] >> 24 & 255) != 0)
                        {
                            flag = false;
                        }
                    }

                    if (flag)
                    {
                        --l1;
                        continue;
                    }
                }

                ++l1;
                this.charWidth[i1] = (int)(0.5D + (double)((float)l1 * f)) + b0;
                ++i1;
                break;
            }
        }
    }

    private void readGlyphSizes()
    {
        try
        {
        	InputStream inputstream = getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
            inputstream.read(this.glyphWidth);
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
    }
    
    
    /**
     * Pick how to render a single character and return the width used.
     */
    private void resetScale(){
    	GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f);
    }
    
    private float renderCharAtPos(int id, char c, boolean par3)
    {
    	GL11.glScalef(scale, scale, 1.0f);
    	
    	//ENTITIES!
    	if(!this.itemMode && c == '\r'){
    		this.entityMode = !this.entityMode;
    		resetScale();
    		return 0.f;
    	}else if(this.entityMode && c == '<'){
    		this.nameID = "";
    		resetScale();
    		return 0.f;
    	}else if(this.entityMode && c == '>' && this.nameID != null){
            this.entity = EntityList.createEntityByName(nameID, theGame.theWorld);
            if(entity != null && entity instanceof EntityLivingBase){
            	EntityLivingBase living = (EntityLivingBase)entity;
            	if(template.containsKey(living.getClass())){
            		living = template.get(living.getClass());
            	}else{
            		template.put(living.getClass(), living);
            	}
	            
            	living.renderYawOffset = 0.0f;
            	living.rotationYaw = 0.0f;
            	living.rotationPitch = 0.0f;
            	living.rotationYawHead = entity.rotationYaw;
            	living.prevRotationYawHead = entity.rotationYaw;
            	
            	
	            float k = this.posX;
	            float l = this.posY;
	            float offsetX = 8;// * scale * xScale;
	            float offsetY = 8;// * scale * yScale;
	            
	            renderEntity((int)(k + offsetX),(int)(l + offsetY), 14, (float)(k + offsetX) - mosX, (float)(l + offsetY - 50) - mosY, living);
            }
            
            //(int)this.posX + 0, (int)this.posY - 8
            
            Rectangle rect = new Rectangle((int)(this.posX * scale), (int)((this.posY - 8) * scale), (int)(16 * scale), (int)(16 * scale));
            if(rect.contains(new Rectangle(mosX, mosY, 2, 2)) && entity instanceof EntityLivingBase){
            	entityBucket = (EntityLivingBase)entity;
            }
            
    		this.nameID = null;
    		resetScale();
    		return 16.0f;
    	}else if(this.entityMode && c == '\f'){
    		//this.posY += 16;
    		resetScale();
    		return 56.0f;
    	}else if(this.entityMode){
			this.nameID += c;
    		resetScale();
    		return 0.f;
    	}
    	
    	//ITEMS & BLOCKS!
    	if(!this.entityMode && c == '\t'){
    		this.itemMode = !this.itemMode;
    		resetScale();
    		return 0.f;
    	}else if(this.itemMode && c == '<'){
    		this.nameID = "";
    		this.itemDMG = 0;
    		this.itemDmgMode = false;
    		resetScale();
    		return 0.f;
    	}else if(this.itemMode && c == '>' && this.nameID != null){
    		List<ItemStack> list = RecipeTransformer.dataToItemStack(this.nameID, false);
    		int pos = tick;
    		pos /= 50;
    		pos %= list.size();
            this.itemStack = list.get(pos);
            
            if(itemStack != null && itemStack.getItem() == null)
            	itemStack = null;
            
            if(itemStack != null){
	            GL11.glPushMatrix();
	            RenderHelper.enableGUIStandardItemLighting();
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            GL11.glDisable(GL11.GL_LIGHTING);
	            try{
	            	this.itemRender.renderWithColor = true;
		    		this.itemRender.renderItemAndEffectIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.itemStack, (int)this.posX + 0, (int)this.posY - 8);
		    		this.itemRender.renderItemOverlayIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.itemStack, (int)this.posX + 0, (int)this.posY - 8);
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            	Tessellator tessellator = Tessellator.instance;
	            	try { tessellator.draw(); } catch (Exception ex3) {}
	            	/*
	            	itemStack.setItemDamage(0);
	            	try{
			    		this.itemRender.renderItemAndEffectIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.itemStack, (int)this.posX + 0, (int)this.posY - 8);
			    		this.itemRender.renderItemOverlayIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.itemStack, (int)this.posX + 0, (int)this.posY - 8);
		            } catch (Exception ex2) {
		            	try { tessellator.draw(); } catch (Exception ex3) {}
		            }*/
	            }
	            
	            //RenderHelper.disableStandardItemLighting();
	            //RenderHelper.enableGUIStandardItemLighting();
	    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    		//GL11.glDepthFunc(GL11.GL_TEXTURE_2D);
	    		
	    		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                
	            GL11.glPopMatrix();
            }
            
            //(int)this.posX + 0, (int)this.posY - 8
            
            Rectangle rect = new Rectangle((int)(this.posX * scale), (int)((this.posY - 8) * scale), (int)(16 * scale), (int)(16 * scale));
            if(rect.contains(new Rectangle(mosX, mosY, 2, 2))){
            	stackBucket = itemStack;
            }
            
    		this.nameID = null;
    		this.itemDMG = 0;
    		this.itemDmgMode = false;
    		resetScale();
    		return 16.0f;
    	}else if(this.itemMode && c == '|'){
    		this.itemDmgMode = true;
    		resetScale();
    		return 0.f;
    	}else if(this.itemMode && c == '\f'){
    		//this.posY += 16;
    		resetScale();
    		return 40.0f;
    	}else if(this.itemMode){
    		if(!itemDmgMode){
    			this.nameID += c;
    		}else{
    			this.itemDMG *= 10;
    			this.itemDMG += c-48;
    		}
    		resetScale();
    		return 0.f;
    	}
    	
    	float f = c == 32 ? 4.0F : ("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c) != -1 && !this.unicodeFlag ? this.renderDefaultChar(id, par3) : this.renderUnicodeChar(c, par3));
    	
    	
    	
    	resetScale();
    	return f;
    }
    private static float t = 0.0f;
    protected void renderEntity(int x, int y, int scale, float yawY, float pitchX, EntityLivingBase entity)
    {
    	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        //GL11.glMatrixMode(GL11.GL_PROJECTION);
        
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef((float)(-scale), (float)scale, (float)scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
       // GL11.glRotatef(-((float)Math.atan((double)(pitchX / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(25.0F, 0.0F, 1.0F, 0.0F);
        
       //entity.renderYawOffset = (float)Math.atan((double)(yawY / 40.0F)) * 20.0F;
       // entity.rotationYaw = (float)Math.atan((double)(yawY / 40.0F)) * 40.0F;
       // entity.rotationPitch = -((float)Math.atan((double)(pitchX / 40.0F))) * 20.0F;
       entity.rotationYawHead = 0.0f;
       entity.prevRotationYawHead = 0.0f;
        
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        t += 1.0F;
        
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        
        GL11.glTranslatef(0.0F, -0.22F, 0.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 255.0F * 0.8F, 255.0F * 0.8F);
        Tessellator.instance.setBrightness(240);
        
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Render a single character with the default.png font at current (posX,posY) location...
     */
    private float renderDefaultChar(int par1, boolean par2)
    {
        float f = (float)(par1 % 16 * 8);
        float f1 = (float)(par1 / 16 * 8);
        float f2 = par2 ? 1.0F : 0.0F;
        this.renderEngine.bindTexture(new ResourceLocation(this.fontTextureName));
        float f3 = (float)this.charWidth[par1] - 0.01F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f2, this.posY, 0.0F);
        GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX - f2, this.posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f + f3) / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f3 + f2, this.posY, 0.0F);
        GL11.glTexCoord2f((f + f3) / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX + f3 - f2, this.posY + 7.99F, 0.0F);
        GL11.glEnd();
        return (float)this.charWidth[par1];
    }

    /**
     * Load one of the /font/glyph_XX.png into a new GL texture and store the texture ID in glyphTextureName array.
     */
    private void loadGlyphTexture(int par1)
    {
        String s = String.format("/font/glyph_%02X.png", new Object[] {Integer.valueOf(par1)});
        this.renderEngine.bindTexture(new ResourceLocation(s));
    }

    /**
     * Render a single Unicode character at current (posX,posY) location using one of the /font/glyph_XX.png files...
     */
    private float renderUnicodeChar(char par1, boolean par2)
    {
        if (this.glyphWidth[par1] == 0)
        {
            return 0.0F;
        }
        else
        {
            int i = par1 / 256;
            this.loadGlyphTexture(i);
            int j = this.glyphWidth[par1] >>> 4;
            int k = this.glyphWidth[par1] & 15;
            float f = (float)j;
            float f1 = (float)(k + 1);
            float f2 = (float)(par1 % 16 * 16) + f;
            float f3 = (float)((par1 & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = par2 ? 1.0F : 0.0F;
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
            GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
            GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
            GL11.glEnd();
            return (f1 - f) / 2.0F + 1.0F;
        }
    }

    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String par1Str, int par2, int par3, int par4)
    {
        return this.drawString(par1Str, par2, par3, par4, true);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String par1Str, int par2, int par3, int par4)
    {
        return this.drawString(par1Str, par2, par3, par4, false);
    }

    /**
     * Draws the specified string. Args: string, x, y, color, dropShadow
     */
    public int drawString(String par1Str, int par2, int par3, int par4, boolean par5)
    {
        this.resetStyles();

        if (this.bidiFlag)
        {
            par1Str = this.bidiReorder(par1Str);
        }

        int l;

        if (par5)
        {
            l = this.renderString(par1Str, par2 + 1, par3 + 1, par4, true);
            l = Math.max(l, this.renderString(par1Str, par2, par3, par4, false));
        }
        else
        {
            l = this.renderString(par1Str, par2, par3, par4, false);
        }

        return l;
    }

    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidiReorder(String par1Str)
    {
        if (par1Str != null && Bidi.requiresBidi(par1Str.toCharArray(), 0, par1Str.length()))
        {
            Bidi bidi = new Bidi(par1Str, -2);
            byte[] abyte = new byte[bidi.getRunCount()];
            String[] astring = new String[abyte.length];
            int i;

            for (int j = 0; j < abyte.length; ++j)
            {
                int k = bidi.getRunStart(j);
                i = bidi.getRunLimit(j);
                int l = bidi.getRunLevel(j);
                String s1 = par1Str.substring(k, i);
                abyte[j] = (byte)l;
                astring[j] = s1;
            }

            String[] astring1 = (String[])astring.clone();
            Bidi.reorderVisually(abyte, 0, astring, 0, abyte.length);
            StringBuilder stringbuilder = new StringBuilder();
            i = 0;

            while (i < astring.length)
            {
                byte b0 = abyte[i];
                int i1 = 0;

                while (true)
                {
                    if (i1 < astring1.length)
                    {
                        if (!astring1[i1].equals(astring[i]))
                        {
                            ++i1;
                            continue;
                        }

                        b0 = abyte[i1];
                    }

                    if ((b0 & 1) == 0)
                    {
                        stringbuilder.append(astring[i]);
                    }
                    else
                    {
                        for (i1 = astring[i].length() - 1; i1 >= 0; --i1)
                        {
                            char c0 = astring[i].charAt(i1);

                            if (c0 == 40)
                            {
                                c0 = 41;
                            }
                            else if (c0 == 41)
                            {
                                c0 = 40;
                            }

                            stringbuilder.append(c0);
                        }
                    }

                    ++i;
                    break;
                }
            }

            return stringbuilder.toString();
        }
        else
        {
            return par1Str;
        }
    }

    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles()
    {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String par1Str, boolean par2)
    {
        for (int i = 0; i < par1Str.length(); ++i)
        {
            char c0 = par1Str.charAt(i);
            int j;
            int k;

            if (c0 == 167 && i + 1 < par1Str.length())
            {
                j = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(i + 1));

                if (j < 16)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;

                    if (j < 0 || j > 15)
                    {
                        j = 15;
                    }

                    if (par2)
                    {
                        j += 16;
                    }

                    k = this.colorCode[j];
                    this.textColor = k;
                    GL11.glColor4f((float)(k >> 16) / 255.0F, (float)(k >> 8 & 255) / 255.0F, (float)(k & 255) / 255.0F, this.alpha);
                }
                else if (j == 16)
                {
                    this.randomStyle = true;
                }
                else if (j == 17)
                {
                    this.boldStyle = true;
                }
                else if (j == 18)
                {
                    this.strikethroughStyle = true;
                }
                else if (j == 19)
                {
                    this.underlineStyle = true;
                }
                else if (j == 20)
                {
                    this.italicStyle = true;
                }
                else if (j == 21)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            }
            else
            {
                boolean randj = ChatAllowedCharacters.isAllowedCharacter(c0);
                j = c0;
                if (this.randomStyle && randj)
                {
                    do
                    {
                        k = this.fontRandom.nextInt(ChatAllowedCharacters.allowedCharacters.length);
                    }
                    while (this.charWidth[c0 + 32] != this.charWidth[k + 32]);

                    j = k;
                }

                float f = this.unicodeFlag ? 0.5F : 1.0F;
                boolean flag1 = (c0 <= 0 || this.unicodeFlag) && par2;

                if (flag1)
                {
                    this.posX -= f;
                    this.posY -= f;
                }

                float f1 = this.renderCharAtPos(j, c0, this.italicStyle);

                if (flag1)
                {
                    this.posX += f;
                    this.posY += f;
                }

                if (this.boldStyle)
                {
                    this.posX += f;

                    if (flag1)
                    {
                        this.posX -= f;
                        this.posY -= f;
                    }

                    this.renderCharAtPos(j, c0, this.italicStyle);
                    this.posX -= f;

                    if (flag1)
                    {
                        this.posX += f;
                        this.posY += f;
                    }

                    ++f1;
                }

                Tessellator tessellator;

                if (this.strikethroughStyle)
                {
                    tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    tessellator.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    tessellator.addVertex((double)(this.posX + f1), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    tessellator.addVertex((double)(this.posX + f1), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    tessellator.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                if (this.underlineStyle)
                {
                    tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int l = this.underlineStyle ? -1 : 0;
                    tessellator.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    tessellator.addVertex((double)(this.posX + f1), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    tessellator.addVertex((double)(this.posX + f1), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    tessellator.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.posX += (float)((int)f1);
            }
        }
    }

    /**
     * Render string either left or right aligned depending on bidiFlag
     */
    private int renderStringAligned(String par1Str, int par2, int par3, int par4, int par5, boolean par6)
    {
        if (this.bidiFlag)
        {
            par1Str = this.bidiReorder(par1Str);
            int i1 = this.getStringWidth(par1Str);
            par2 = par2 + par4 - i1;
        }

        return this.renderString(par1Str, par2, par3, par5, par6);
    }

    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String par1Str, int par2, int par3, int par4, boolean par5)
    {
        if (par1Str == null)
        {
            return 0;
        }
        else
        {
            if ((par4 & -67108864) == 0)
            {
                par4 |= -16777216;
            }

            if (par5)
            {
                par4 = (par4 & 16579836) >> 2 | par4 & -16777216;
            }

            this.red = (float)(par4 >> 16 & 255) / 255.0F;
            this.blue = (float)(par4 >> 8 & 255) / 255.0F;
            this.green = (float)(par4 & 255) / 255.0F;
            this.alpha = (float)(par4 >> 24 & 255) / 255.0F;
            GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
            this.posX = (float)par2 / scale;
            this.posY = (float)par3 / scale;
            this.renderStringAtPos(par1Str, par5);
            return (int)this.posX;
        }
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int getStringWidth(String par1Str)
    {
        if (par1Str == null)
        {
            return 0;
        }
        else
        {
            int i = 0;
            boolean flag = false;
            boolean flag_itemMode = false;

            for (int j = 0; j < par1Str.length(); ++j)
            {
                char c0 = par1Str.charAt(j);
                
                if(c0 == '\t')
                	flag_itemMode = !flag_itemMode;
                
                if(flag_itemMode)
                	continue;
                
                int k = this.getCharWidth(c0);

                if (k < 0 && j < par1Str.length() - 1)
                {
                    ++j;
                    c0 = par1Str.charAt(j);
                    
                    if (c0 != 108 && c0 != 76)
                    {
                        if (c0 == 114 || c0 == 82)
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = true;
                    }

                    k = 0;
                }

                i += k;

                if (flag)
                {
                    ++i;
                }
            }

            return i;
        }
    }

    /**
     * Returns the width of this character as rendered.
     */
    public int getCharWidth(char par1)
    {
        if (par1 == 167)
        {
            return -1;
        }
        else if (par1 == 32)
        {
            return 4;
        }
        else
        {
            boolean acci = ChatAllowedCharacters.isAllowedCharacter(par1);

            if (acci && !this.unicodeFlag)
            {
            	int i = ((char)par1) + 32;
            	i = i <= this.charWidth.length ? i : par1;
                return this.charWidth[i];
            }
            else if (this.glyphWidth[par1] != 0)
            {
                int j = this.glyphWidth[par1] >>> 4;
                int k = this.glyphWidth[par1] & 15;

                if (k > 7)
                {
                    k = 15;
                    j = 0;
                }

                ++k;
                return (k - j) / 2 + 1;
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Trims a string to fit a specified Width.
     */
    public String trimStringToWidth(String par1Str, int par2)
    {
        return this.trimStringToWidth(par1Str, par2, false);
    }

    /**
     * Trims a string to a specified width, and will reverse it if par3 is set.
     */
    public String trimStringToWidth(String par1Str, int par2, boolean par3)
    {
        StringBuilder stringbuilder = new StringBuilder();
        int j = 0;
        int k = par3 ? par1Str.length() - 1 : 0;
        int l = par3 ? -1 : 1;
        boolean flag1 = false;
        boolean flag2 = false;

        for (int i1 = k; i1 >= 0 && i1 < par1Str.length() && j < par2; i1 += l)
        {
            char c0 = par1Str.charAt(i1);
            int j1 = this.getCharWidth(c0);

            if (flag1)
            {
                flag1 = false;

                if (c0 != 108 && c0 != 76)
                {
                    if (c0 == 114 || c0 == 82)
                    {
                        flag2 = false;
                    }
                }
                else
                {
                    flag2 = true;
                }
            }
            else if (j1 < 0)
            {
                flag1 = true;
            }
            else
            {
                j += j1;

                if (flag2)
                {
                    ++j;
                }
            }

            if (j > par2)
            {
                break;
            }

            if (par3)
            {
                stringbuilder.insert(0, c0);
            }
            else
            {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    /**
     * Remove all newline characters from the end of the string
     */
    private String trimStringNewline(String par1Str)
    {
        while (par1Str != null && par1Str.endsWith("\n"))
        {
            par1Str = par1Str.substring(0, par1Str.length() - 1);
        }

        return par1Str;
    }

    /**
     * Splits and draws a String with wordwrap (maximum length is parameter k)
     */
    public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5)
    {
        this.resetStyles();
        this.textColor = par5;
        par1Str = this.trimStringNewline(par1Str);
        this.renderSplitString(par1Str, par2, par3, par4, false);
    }

    /**
     * Perform actual work of rendering a multi-line string with wordwrap and with darker drop shadow color if flag is
     * set
     */
    private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5)
    {
    	par4 /= scale;
        List list = this.listFormattedStringToWidth(par1Str, par4);

        for (Iterator iterator = list.iterator(); iterator.hasNext(); par3 += this.FONT_HEIGHT * scale)
        {
            String s1 = (String)iterator.next();
            this.renderStringAligned(s1, par2, par3, par4, this.textColor, par5);
        }
    }

    /**
     * Returns the width of the wordwrapped String (maximum length is parameter k)
     */
    public int splitStringWidth(String par1Str, int par2)
    {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(par1Str, par2).size();
    }

    /**
     * Set unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public void setUnicodeFlag(boolean par1)
    {
        this.unicodeFlag = par1;
    }

    /**
     * Get unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public boolean getUnicodeFlag()
    {
        return this.unicodeFlag;
    }

    /**
     * Set bidiFlag to control if the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    public void setBidiFlag(boolean par1)
    {
        this.bidiFlag = par1;
    }

    /**
     * Breaks a string into a list of pieces that will fit a specified width.
     */
    public List listFormattedStringToWidth(String par1Str, int par2)
    {
        return Arrays.asList(this.wrapFormattedStringToWidth(par1Str, par2).split("\n"));
    }

    /**
     * Inserts newline and formatting into a string to wrap it within the specified width.
     */
    String wrapFormattedStringToWidth(String par1Str, int par2)
    {
        int j = this.sizeStringToWidth(par1Str, par2);

        if (par1Str.length() <= j)
        {
            return par1Str;
        }
        else
        {
            String s1 = par1Str.substring(0, j);
            char c0 = par1Str.charAt(j);
            boolean flag = c0 == 32 || c0 == 10;
            String s2 = getFormatFromString(s1) + par1Str.substring(j + (flag ? 1 : 0));
            return s1 + "\n" + this.wrapFormattedStringToWidth(s2, par2);
        }
    }

    /**
     * Determines how many characters from the string will fit into the specified width.
     */
    public int sizeStringToWidth(String par1Str, int par2)
    {
        int j = par1Str.length();
        int k = 0;
        int l = 0;
        int i1 = -1;
        boolean flag_itemMode = false;

        for (boolean flag = false; l < j; ++l)
        {
            char c0 = par1Str.charAt(l);
            
            if(c0 == '\t')
            	flag_itemMode = !flag_itemMode;
            if(flag_itemMode)
            	continue;
            
            switch (c0)
            {
                case 10:
                    --l;
                    break;
                case 167:
                    if (l < j - 1)
                    {
                        ++l;
                        char c1 = par1Str.charAt(l);

                        if (c1 != 108 && c1 != 76)
                        {
                            if (c1 == 114 || c1 == 82 || isFormatColor(c1))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = true;
                        }
                    }

                    break;
                case 32:
                    i1 = l;
                default:
                    k += this.getCharWidth(c0);

                    if (flag)
                    {
                        ++k;
                    }
            }

            if (c0 == 10)
            {
                ++l;
                i1 = l;
                break;
            }

            if (k > par2)
            {
                break;
            }
        }

        return l != j && i1 != -1 && i1 < l ? i1 : l;
    }
    
    /**
     * Checks if the char code is a hexadecimal character, used to set colour.
     */
    private static boolean isFormatColor(char par0)
    {
        return par0 >= 48 && par0 <= 57 || par0 >= 97 && par0 <= 102 || par0 >= 65 && par0 <= 70;
    }

    /**
     * Checks if the char code is O-K...lLrRk-o... used to set special formatting.
     */
    private static boolean isFormatSpecial(char par0)
    {
        return par0 >= 107 && par0 <= 111 || par0 >= 75 && par0 <= 79 || par0 == 114 || par0 == 82;
    }

    /**
     * Digests a string for nonprinting formatting characters then returns a string containing only that formatting.
     */
    private static String getFormatFromString(String par0Str)
    {
        String s1 = "";
        int i = -1;
        int j = par0Str.length();

        while ((i = par0Str.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = par0Str.charAt(i + 1);

                if (isFormatColor(c0))
                {
                    s1 = "\u00a7" + c0;
                }
                else if (isFormatSpecial(c0))
                {
                    s1 = s1 + "\u00a7" + c0;
                }
            }
        }

        return s1;
    }

    /**
     * Get bidiFlag that controls if the Unicode Bidirectional Algorithm should be run before rendering any string
     */
    public boolean getBidiFlag()
    {
        return this.bidiFlag;
    }
    
    protected InputStream getResourceInputStream(ResourceLocation location) throws IOException
    {
        return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
    }

	public void setMousePos(int x, int y) {
		stackBucket = null;
		entityBucket = null;
		mosX = x;
		mosY = y;
	}
	
	public void drawMouse(int pageImageWidth, int pageImageHeight){
		tick++;
		if(stackBucket != null){
			this.renderToolTip(stackBucket, mosX, mosY, pageImageWidth, pageImageHeight);
			stackBucket = null;
		}
		if(entityBucket != null){
			this.renderToolTip(entityBucket, mosX, mosY, pageImageWidth, pageImageHeight);
			entityBucket = null;
		}
	}
	
	private void renderToolTip(Entity entity, int x, int y,
			int pageImageWidth, int pageImageHeight) {
		
		Minecraft mc = Minecraft.getMinecraft();
        List list = new ArrayList();
        list.add(entity.getCommandSenderName());

        for (int k = 0; k < list.size(); ++k)
        {
            if (k == 0)
            {
                list.set(k, EnumChatFormatting.GOLD + (String)list.get(k));
            }
            else
            {
                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
            }
        }
        
        FontRenderer font = null;
        if(font == null)
        	font = Minecraft.getMinecraft().fontRenderer;
        drawHoveringText(list, x, y, font, pageImageWidth, pageImageHeight);
	}

	public void renderToolTip(ItemStack p_146285_1_, int x, int y, int pageImageWidth, int pageImageHeight)
    {
		Minecraft mc = Minecraft.getMinecraft();
        List list = p_146285_1_.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k)
        {
            if (k == 0)
            {
                list.set(k, p_146285_1_.stackSize + "x " +p_146285_1_.getRarity().rarityColor + (String)list.get(k));
            }
            else
            {
                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
            }
        }

        FontRenderer font = p_146285_1_.getItem().getFontRenderer(p_146285_1_);
        if(font == null)
        	font = Minecraft.getMinecraft().fontRenderer;
        drawHoveringText(list, x, y, font, pageImageWidth, pageImageHeight);
    }
	
	protected void drawHoveringText(List p_146283_1_, int x, int y, FontRenderer font, int pageImageWidth, int pageImageHeight)
    {
        if (!p_146283_1_.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = x + 12;
            int k2 = y - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            if (j2 + k > pageImageWidth)
            {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > pageImageHeight)
            {
                k2 = pageImageHeight - i1 - 6;
            }

            itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	protected void drawGradientRect(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, int p_73733_5_, int p_73733_6_)
    {
        float f = (float)(p_73733_5_ >> 24 & 255) / 255.0F;
        float f1 = (float)(p_73733_5_ >> 16 & 255) / 255.0F;
        float f2 = (float)(p_73733_5_ >> 8 & 255) / 255.0F;
        float f3 = (float)(p_73733_5_ & 255) / 255.0F;
        float f4 = (float)(p_73733_6_ >> 24 & 255) / 255.0F;
        float f5 = (float)(p_73733_6_ >> 16 & 255) / 255.0F;
        float f6 = (float)(p_73733_6_ >> 8 & 255) / 255.0F;
        float f7 = (float)(p_73733_6_ & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        float zLevel = 300.0F;
        tessellator.addVertex((double)p_73733_3_, (double)p_73733_2_, (double)zLevel);
        tessellator.addVertex((double)p_73733_1_, (double)p_73733_2_, (double)zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)p_73733_1_, (double)p_73733_4_, (double)zLevel);
        tessellator.addVertex((double)p_73733_3_, (double)p_73733_4_, (double)zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
