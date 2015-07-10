package hok.chompzki.biocristals.tutorials.gui;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.tutorials.data.DataBook;
import hok.chompzki.biocristals.tutorials.data.DataPlayer;
import hok.chompzki.biocristals.tutorials.data.DataPlayerProgression;
import hok.chompzki.biocristals.tutorials.data.StorageHandler;
import hok.chompzki.biocristals.tutorials.data.knowledges.Knowledge;
import hok.chompzki.biocristals.tutorials.data.knowledges.Knowledges;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiBioBook extends GuiScreen {
	
	public static final ResourceLocation bioBookBG = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/GuiBioBookBG.png");
	
	private static final int guiMapTop = -8 * 24 - 112;
	private static final int guiMapLeft = -8 * 24 - 112;
	
	private static final int guiMapBottom = 16 * 24 - 77;
	private static final int guiMapRight = 16 * 24 - 77;
	
	protected int knowledgePaneWidth = 256;
	protected int knowledgePaneHeight = 202;
	
	protected double field_74117_m;
	protected double field_74115_n;
	
	protected double guiMapX;
	protected double guiMapY;
	
	protected int mouseX = 0;
	protected int mouseY = 0;
	private int isMouseButtonDown = 0;
	private int lastMouseButtonDown = 0;
	
	protected final float speed = 10.0f;
	
	DataBook book = null;
	DataPlayer player = null;
	Knowledge tooltipKnowledge = null;
	
	static Random random = new Random();
	
	public GuiBioBook(EntityPlayer player) {
		this.book = new DataBook(player.inventory.getCurrentItem());
		this.player = new DataPlayer(player);
		short short1 = 141;
        short short2 = 141;
		this.field_74117_m = this.guiMapX = (double)(0 * 24 - short1 / 2 - 12);
        this.field_74115_n = this.guiMapY = (double)(0 * 24 - short2 / 2);
	}
	
	@Override
	public void initGui(){
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button){
		if (button.id == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
    {
        if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
            return;
        } else
        {
            super.keyTyped(par1, par2);
        }
    }
	
	@Override
	protected void mouseClicked(int x, int y, int btn)
    {
		if(btn == 0 && this.tooltipKnowledge != null){
        	this.mc.displayGuiScreen(new GuiDescription(this, tooltipKnowledge.getDescription()));
        	
        }else{
        	super.mouseClicked(x, y, btn);
        }
    }
	
	public void addToMapPos(double x, double y){
		this.guiMapX += x;
        this.guiMapY += y;
        
        this.field_74117_m = this.guiMapX;
        this.field_74115_n = this.guiMapY;
        
        if (this.guiMapY < (double)guiMapTop)
        {
        	this.guiMapY = (double)guiMapTop + 1;
            
        }

        if (this.guiMapX < (double)guiMapLeft)
        {
        	this.guiMapX = (double)guiMapLeft + 1;
        }

        if (this.guiMapY >= (double)guiMapBottom)
        {
        	this.guiMapY = (double)(guiMapBottom - 1);
        }
        	
        if (this.guiMapX >= (double)guiMapRight)
        {
        	this.guiMapX = (double)(guiMapRight - 1);
        }
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
    {
		float speedY = 0.0f;
		float speedX = 0.0f;
		
		if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode())){
			speedY -= speed * par3;
		}
		if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode())){
			speedY += speed * par3;
		}
		if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode())){
			speedX -= speed * par3;
		}
		if(Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode())){
			speedX += speed * par3;
		}
		
		this.addToMapPos(speedX, speedY);
		
		if (Mouse.isButtonDown(0))
        {
            int k = (this.width - this.knowledgePaneWidth) / 2;
            int l = (this.height - this.knowledgePaneHeight) / 2;
            int i1 = k + 8;
            int j1 = l + 17;

            if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && par1 >= i1 && par1 < i1 + 224 && par2 >= j1 && par2 < j1 + 155)
            {
                if (this.isMouseButtonDown == 0)
                {
                    this.isMouseButtonDown = 1;
                    
                }
                else
                {
                	this.addToMapPos(-(double)(par1 - this.mouseX), -(double)(par2 - this.mouseY));
                }

                this.mouseX = par1;
                this.mouseY = par2;
            }

        }
        else
        {
            this.isMouseButtonDown = 0;
        }
		
		this.drawDefaultBackground();
		this.genBioBookBackground(par1, par2, par3);
		//Draw Relations
		this.drawIcons(par1, par2, par3);
		this.drawOverlay(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
		if(tooltipKnowledge != null)
			this.drawTooltip(par1, par2, par3);
    }
	
	@Override
	public void updateScreen()
    {
		super.updateScreen();
    }
	
	 protected void genBioBookBackground(int par1, int par2, float par3)
	    {
	        int k = MathHelper.floor_double(this.field_74117_m + (this.guiMapX - this.field_74117_m) * (double)par3);
	        int l = MathHelper.floor_double(this.field_74115_n + (this.guiMapY - this.field_74115_n) * (double)par3);

	        if (k < guiMapTop)
	        {
	            k = guiMapTop;
	        }

	        if (l < guiMapLeft)
	        {
	            l = guiMapLeft;
	        }

	        if (k >= guiMapBottom)
	        {
	            k = guiMapBottom - 1;
	        }

	        if (l >= guiMapRight)
	        {
	            l = guiMapRight - 1;
	        }
	        
	        int i1 = (this.width - this.knowledgePaneWidth) / 2;
	        int j1 = (this.height - this.knowledgePaneHeight) / 2;
	        int k1 = i1 + 16;
	        int l1 = j1 + 17;
	        //this.zLevel = 0.0F;
	        GL11.glPushMatrix();
	        this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	        int i2 = k + 288 >> 4;
	        int j2 = l + 288 >> 4;
	        int k2 = (k + 288) % 16;
	        int l2 = (l + 288) % 16;
	        
	        int i3;
	        int j3;
	        int k3;

	        for (i3 = 0; i3 * 16 - l2 < 155; ++i3)
	        {
	            float f1 = 0.6F - (float)(j2 + i3) / 25.0F * 0.3F;
	            GL11.glColor4f(f1, f1, f1, 1.0F);

	            for (k3 = 0; k3 * 16 - k2 < 224; ++k3)
	            {
	                random.setSeed((long)(1234 + i2 + k3));
	                random.nextInt();
	                j2 = Math.max(0, j2);
	                i3 = Math.max(0, i3);
	                j3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
	                IIcon icon = Blocks.sand.getIcon(0, 0);

	                if (j3 <= 37 && j2 + i3 != 35)
	                {
	                    if (j3 == 22)
	                    {
	                        if (random.nextInt(2) == 0)
	                        {
	                            icon = Blocks.sand.getIcon(0, 0);
	                        }
	                        else
	                        {
	                            icon = Blocks.gravel.getIcon(0, 0);
	                        }
	                    }
	                    else if (j3 == 10)
	                    {
	                        icon = Blocks.log.getIcon(2, 0);
	                    }
	                    else if (j3 == 8)
	                    {
	                        icon = Blocks.log.getIcon(0, 0);
	                    }
	                    else if (j3 > 4)
	                    {
	                        icon = Blocks.dirt.getIcon(0, 0);
	                    }
	                    else if (j3 > 0)
	                    {
	                        icon = Blocks.grass.getIcon(1, 0);
	                    }
	                }
	                else
	                {
	                    icon = Blocks.stone.getIcon(0, 0);
	                }
	                
	                GL11.glColor3f(1.0f, 1.0f, 1.0f);
	                this.drawTexturedModelRectFromIcon(k1 + k3 * 16 - k2, l1 + i3 * 16 - l2, icon, 16, 16);
	                
	            }
	            
	        }
	        GL11.glPopMatrix();
	        
	        /*
	        for (i3 = 0; i3 < knowledgeList.size(); ++i3)
	        {
	        	Knowledge knowledge = knowledgeList.get(i3);

	            if (knowledge.parentKnowledge != null && knowledgeList.contains(knowledge.parentKnowledge))
	            {
	                k3 = knowledge.displayColumn * 24 - k + 11 + k1;
	                j3 = knowledge.displayRow * 24 - l + 11 + l1;
	                j4 = knowledge.parentKnowledge.displayColumn * 24 - k + 11 + k1;
	                l3 = knowledge.parentKnowledge.displayRow * 24 - l + 11 + l1;
	                
	                
	                
	                
	                boolean flag = KnowledgeAppedix.hasKnowledgeUnlocked(compound, knowledge);
	                boolean flag1 = KnowledgeAppedix.canUnlockKnowledge(compound, knowledge);
	                
	                i4 = Math.sin((double)(Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
	                int k4 = -16777216;

	                if (flag)
	                {
	                    k4 = -9408400;
	                }
	                else if (flag1)
	                {
	                    k4 = 65280 + (i4 << 24);
	                }

	                this.drawHorizontalLine(k3, j4, j3, k4);
	                this.drawVerticalLine(j4, j3, l3, k4);
	            }
	        }
	        */
			
	        
	        
	        /*
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.renderEngine.bindTexture("/mods/dnd91/minecraft/hivecraft/textures/gui/KnowledgesBG.png");
	        this.drawTexturedModalRect(i1, j1, 0, 0, this.knowledgePaneWidth, this.knowledgePaneHeight);
	        GL11.glPopMatrix();
	        this.zLevel = 0.0F;
	        GL11.glDepthFunc(GL11.GL_LEQUAL);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        super.drawScreen(par1, par2, par3);

	        if (knowledge1 != null)
	        {
	            String s = knowledge1.getTitle();
	            String s1 = knowledge1.getDesc();
	            j4 = par1 + 12;
	            l3 = par2 - 4;
	            
	            if (KnowledgeAppedix.canUnlockKnowledge(compound, knowledge1))
	            {
	                i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
	                l4 = this.fontRenderer.splitStringWidth(s1, i5);

	                if (KnowledgeAppedix.hasKnowledgeUnlocked(compound, knowledge1))
	                {
	                    l4 += 12;
	                }

	                this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + l4 + 3 + 12, -1073741824, -1073741824);
	                this.fontRenderer.drawSplitString(s1, j4, l3 + 12, i5, -6250336);

	                if (KnowledgeAppedix.hasKnowledgeUnlocked(compound, knowledge1))
	                {
	                	
	                	if(lastMouseButtonDown == 1 && this.isMouseButtonDown == 0 && this.mc.theWorld.isRemote){
	                		System.out.println("Mouse up over a knowledge!");
	                		player.openGui(HiveCraft.instance, knowledge1.myPage+500, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
	                	}
	                	lastMouseButtonDown = isMouseButtonDown;
	                    this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("knowledge.taken"), j4, l3 + l4 + 4, -7302913);
	                }
	            }
	            else
	            {
	                i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
	                String s2 = StatCollector.translateToLocalFormatted("achievement.requires", new Object[] {knowledge1.parentKnowledge.getTitle()});
	                i4 = this.fontRenderer.splitStringWidth(s2, i5);
	                this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + i4 + 12 + 3, -1073741824, -1073741824);
	                this.fontRenderer.drawSplitString(s2, j4, l3 + 12, i5, -9416624);
	            }

	            this.fontRenderer.drawStringWithShadow(s, j4, l3, KnowledgeAppedix.canUnlockKnowledge(compound, knowledge1) ? (knowledge1.getSpecial() ? -128 : -1) : (knowledge1.getSpecial() ? -8355776 : -8355712));
	        }
	        */
	        
	        
	        
	        //GL11.glEnable(GL11.GL_DEPTH_TEST);
	        //GL11.glEnable(GL11.GL_LIGHTING);
	        //RenderHelper.disableStandardItemLighting();
	    }
	 
	 public void drawIcons(int par1, int par2, float par3){
		 int k = MathHelper.floor_double(this.field_74117_m + (this.guiMapX - this.field_74117_m) * (double)par3);
	     int l = MathHelper.floor_double(this.field_74115_n + (this.guiMapY - this.field_74115_n) * (double)par3);

        if (k < guiMapTop)
        {
            k = guiMapTop;
        }

        if (l < guiMapLeft)
        {
            l = guiMapLeft;
        }

        if (k >= guiMapBottom)
        {
            k = guiMapBottom - 1;
        }

        if (l >= guiMapRight)
        {
            l = guiMapRight - 1;
        }
		  
	    
        int i1 = (this.width - this.knowledgePaneWidth) / 2;
        int j1 = (this.height - this.knowledgePaneHeight) / 2;
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        
		RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        //GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int l4;
        int i5;
        tooltipKnowledge = null;
        for(Knowledge knowledge : Knowledges.knowledges){
        	int colum = knowledge.displayColumn * 24 - k;
            int row = knowledge.displayRow * 24 - l;
            if (colum >= -10 && row >= -10 && colum <= 224 && row <= 155)
            {
            	float f2 = 1.0F;
            	GL11.glColor4f(f2, f2, f2, 1.0F);
            	
            	this.mc.renderEngine.bindTexture(bioBookBG);
                i5 = k1 + colum;
                l4 = l1 + row;

                if (knowledge.getSpecial())
                {
                    this.drawTexturedModalRect(i5 - 2, l4 - 2, 26, 202, 26, 26);
                }
                else
                {
                    this.drawTexturedModalRect(i5 - 2, l4 - 2, 0, 202, 26, 26);
                }

	             float f3 = 1.0F;
	             GL11.glColor4f(f3, f3, f3, 1.0F);
	             renderitem.renderWithColor = false;
	             renderitem.zLevel = -50.0f;
                //GL11.glEnable(GL11.GL_LIGHTING);
                //GL11.glEnable(GL11.GL_CULL_FACE);
                renderitem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, knowledge.getIconStack(), i5 + 3, l4 + 3);
                //GL11.glDisable(GL11.GL_LIGHTING);
                
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                
                Rectangle rect = new Rectangle(i5 + 3, l4 + 3, 16, 16);
                if(rect.contains(new Rectangle(par1, par2, 2, 2))){
                	tooltipKnowledge = knowledge;
                }
            }
            
        }
	 }
	 
	 public void drawOverlay(int par1, int par2, float par3){
		
		int i1 = (this.width - this.knowledgePaneWidth) / 2;
        int j1 = (this.height - this.knowledgePaneHeight) / 2;
        //this.zLevel = 200.0f;
		GL11.glPushMatrix();
		this.mc.getTextureManager().bindTexture(bioBookBG);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_BLEND);
		this.drawTexturedModalRect(i1, j1, 0, 0, 255, 202);
		GL11.glDisable(GL11.GL_BLEND);
		 
		GL11.glPopMatrix();
		 
		
		//this.zLevel = 200.0f;
		GL11.glPushMatrix();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_BLEND);
		this.drawString(fontRendererObj, book.getOwner() + "'s book", i1+20, j1+5, 0xFFFFFF);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPopMatrix();
		//this.zLevel = 0.0f;
	 }
	 
	 public void drawTooltip(int par1, int par2, float par3){
		 this.renderToolTip(tooltipKnowledge, par1, par2);
	 }
	 
	protected void renderToolTip(Knowledge tooltipKnowledge, int p_146285_2_, int p_146285_3_)
    {
        List list = new ArrayList();
        list.add(EnumChatFormatting.GOLD + tooltipKnowledge.getTitle());
        list.add(tooltipKnowledge.getDesc());
        
        drawHoveringText(list, p_146285_2_, p_146285_3_, Minecraft.getMinecraft().fontRenderer);
    }
}
