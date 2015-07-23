package hok.chompzki.biocristals.research.gui;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.DataPlayer;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.FMLCommonHandler;
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

public class GuiResearchBook extends GuiScreen {
	
	public static final ResourceLocation bioBookBG = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/GuiBioBookBG.png");
	
	private static final int guiMapTop = -7 * 24 - 112;
	private static final int guiMapLeft = -7 * 24 - 112;
	
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
	
	ItemStack book = null;
	UUID player = null;
	Research tooltipResearch = null;
	
	static Random random = new Random();
	
	private GuiArticle article = null;
	
	private EntityPlayer reader = null;
	
	public GuiResearchBook(EntityPlayer player) {
		this.reader = player;
		this.book = player.inventory.getCurrentItem();
		this.player = UUID.fromString(DataHelper.getOwner(book));
		short short1 = 141;
        short short2 = 141;
		this.field_74117_m = this.guiMapX = (double)(0 * 24 - short1 / 2 - 12);
        this.field_74115_n = this.guiMapY = (double)(0 * 24 - short2 / 2);
        this.article = GuiInventoryOverlay.craftingHelper.getBooked();
        if(article != null){
        	article.setLast(this);
        	article.setWorldAndResolution(Minecraft.getMinecraft(), width, height, null);
        }else{
        	article = new GuiTutorialArticle(player);
        	article.setLast(this);
        	article.setWorldAndResolution(Minecraft.getMinecraft(), width, height, null);
        }
	}
	
	@Override
	public void initGui(){
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, calculateLeft() + knowledgePaneWidth - 95, calculateTop() + knowledgePaneHeight - 25, 80, 20, StatCollector.translateToLocal("gui.done")));
		if(article != null)
			this.article.initGui(this.buttonList);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft minecraft, int par2, int par3)
    {
        //this.guiParticles = new GuiParticle(minecraft);
		this.fontRendererObj = minecraft.fontRenderer;
        this.mc = minecraft;
        this.width = par2;
        this.height = par3;
        this.buttonList.clear();
        this.initGui();
    }
	
	@Override
	protected void actionPerformed(GuiButton button){
		if (button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }else if(1 <= button.id && button.id <= 3){
        	if(this.article != null){
        		this.article.actionPerformed(button);
        		return;
        	}
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
        }else if(article != null){
        	if(par2 == Keyboard.KEY_1){
        		article.back();
        		return;
        	}else if(par2 == Keyboard.KEY_2 && DataHelper.belongsTo(reader, reader.getCurrentEquippedItem())){
        		PlayerStorage.instance().sendToServer(new PlayerStorageFaveMessage(reader.getGameProfile().getId().toString(), article.getResearch().getCode()));
        		return;
        	}else if(par2 == Keyboard.KEY_3){
        		article.next();
        		return;
        	}
        	
        }
        super.keyTyped(par1, par2);
    }
	
	@Override
	protected void mouseClicked(int x, int y, int btn)
    {
		if(btn == 0 && this.tooltipResearch != null){
        	//this.mc.displayGuiScreen(new GuiDescription(this, tooltipKnowledge.getDescription()));
			article = new GuiArticle(reader, tooltipResearch, player);
			article.setLast(this);
			article.setWorldAndResolution(mc, width, height, null);
			this.initGui();
        }else if(btn == 1 && this.tooltipResearch != null && DataHelper.belongsTo(reader, reader.getCurrentEquippedItem())){
        	PlayerStorage.instance().sendToServer(new PlayerStorageFaveMessage(reader.getGameProfile().getId().toString(), tooltipResearch.getCode()));
        	//tooltipResearch.getContent().selected(!b);
			
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
            int k = calculateLeft();
            int l = calculateTop();
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
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPushMatrix();
    	GuiArticle.drawBackground(mc, this, par1, par2, par3);
        GL11.glPopMatrix();
        
		this.drawOverlay(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		if(article != null){
			article.drawScreen(par1, par2, par3);
			article.drawTooltip();
			Research research = article.getResearch();
			int k = this.calculateLeft() + this.getScreenWidth();
	        int b0 = this.calculateTop();
	        
	        this.renderTitle(research, k + 13, b0);
	        this.drawIcon(research, k, b0 - 13, true, true);
		}
		
		if(tooltipResearch != null)
			this.drawTooltip(par1, par2, par3);
		
    }
	
	@Override
	public void updateScreen()
    {
		super.updateScreen();
    }
	
	 protected void genBioBookBackground(int par1, int par2, float par3)
	    {
	        int k = (int) this.guiMapX;
	        int l = (int) this.guiMapY;
	        
	        int i1 = calculateLeft();
	        int j1 = calculateTop();
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
	 
	 public int calculateLeft(){
		 return (this.width - this.knowledgePaneWidth - GuiArticle.pageImageWidth) / 2;
	 }
	 
	 public int calculateTop(){
		 return (this.height - this.knowledgePaneHeight) / 2;
	 }
	 
	 public int getScreenWidth(){
		 return this.knowledgePaneWidth;
	 }
	 
	 public int getScreenHeight(){
		 return this.knowledgePaneHeight;
	 }
	 
	 public void drawIcons(int par1, int par2, float par3){
		int k = (int) this.guiMapX;
        int l = (int) this.guiMapY;
	    
        int i1 = calculateLeft();
        int j1 = calculateTop();
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        
		
        //GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int l4;
        int i5;
        tooltipResearch = null;
        for(Research knowledge : ResearchLogicNetwork.instance().getOpenResearches(PlayerStorage.instance().get(player))){
        	int colum = knowledge.displayColumn * 24 - k;
            int row = knowledge.displayRow * 24 - l;
            if (colum >= -10 && row >= -10 && colum <= 224 && row <= 155)
            {
            	i5 = k1 + colum;
                l4 = l1 + row;
        		
                this.drawIcon(knowledge, i5, l4, PlayerStorage.instance().get(player).hasCompleted(knowledge.getCode()), true);
                Rectangle rect = new Rectangle(i5 + 3, l4 + 3, 16, 16);
                if(rect.contains(new Rectangle(par1, par2, 2, 2))){
                	tooltipResearch = knowledge;
                }
            }
            
        }
	 }
	 
	 public void drawIcon(Research research, int x, int y, boolean isCompleted, boolean notGreyed){
		 Minecraft mc = Minecraft.getMinecraft();
		 RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
		 float f2 = 1.0F;
     	GL11.glColor4f(f2, f2, f2, 1.0F);
     	
     	mc.renderEngine.bindTexture(bioBookBG);
        
         float f3 = 1.0F;
         if(!isCompleted || !notGreyed)
        	 f3 = 0.3F;
          
          GL11.glColor4f(f3, f3, f3, 1.0F);
          
         if (research.getSpecial())
         {
             this.drawTexturedModalRect(x - 2, y - 2, 26, 202, 26, 26);
             if(PlayerStorage.instance().get(player).hasFaved(research.getCode())){
            	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	 this.drawTexturedModalRect(x - 2, y - 2, 26, 202 + 26, 26, 26);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
          	}
         }
         else
         {
             drawTexturedModalRect(x - 2, y - 2, 0, 202, 26, 26);
             if(PlayerStorage.instance().get(player).hasFaved(research.getCode())){
            	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	 drawTexturedModalRect(x - 2, y - 2, 0, 202 + 26, 26, 26);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
          	}
         }

          
          renderitem.renderWithColor = false;
          renderitem.zLevel = -50.0f;
          
         //GL11.glDisable(GL11.GL_LIGHTING); //Forge: Make sure Lighting is disabled. Fixes MC-33065
         //GL11.glEnable(GL11.GL_CULL_FACE);
         renderitem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, research.getIconStack(), x + 3, y + 3);
         //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(GL11.GL_ALPHA_TEST);
         
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	 }
	 
	 public void drawOverlay(int par1, int par2, float par3){
		
		int i1 = calculateLeft();
        int j1 = calculateTop();
        //this.zLevel = 200.0f;
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		
		this.mc.getTextureManager().bindTexture(bioBookBG);
		this.drawTexturedModalRect(i1, j1, 0, 0, 256, 202);
		 
		this.zLevel = 0.0F;
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
		 
		
		//this.zLevel = 200.0f;
		GL11.glPushMatrix();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_BLEND);
		UUID id = UUID.fromString(DataHelper.getOwner(book));
		
		this.drawString(fontRendererObj, DataHelper.getOwnerName(id, Minecraft.getMinecraft().theWorld) + "'s book", i1+20, j1+5, 0xFFFFFF);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPopMatrix();
		//this.zLevel = 0.0f;
	 }
	 
	 public void drawTooltip(int par1, int par2, float par3){
		 this.renderToolTip(tooltipResearch, par1, par2);
	 }
	 
	protected void renderToolTip(Research tooltipKnowledge, int p_146285_2_, int p_146285_3_)
    {
        List list = new ArrayList();
        list.add(EnumChatFormatting.GOLD + tooltipKnowledge.getTitle());
        list.add(tooltipKnowledge.getDesc());
        
        drawHoveringText(list, p_146285_2_, p_146285_3_, Minecraft.getMinecraft().fontRenderer);
    }
	
	protected void renderTitle(Research tooltipKnowledge, int p_146285_2_, int p_146285_3_)
    {
        List list = new ArrayList();
        list.add(EnumChatFormatting.GOLD + tooltipKnowledge.getTitle());
        list.add(tooltipKnowledge.getDesc());
        
        drawHoveringTitle(list, p_146285_2_, p_146285_3_, Minecraft.getMinecraft().fontRenderer);
    }
	
	protected void drawHoveringTitle(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
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

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
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

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	@Override
	public void onGuiClosed(){
		UUID observer = reader.getGameProfile().getId();
		UUID subject = UUID.fromString(DataHelper.getOwner(book));
		if(observer.compareTo(subject) != 0){
			PlayerStorage.instance().getNetwork().sendToServer(new PlayerStorageDelissenMessage(observer, subject));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
