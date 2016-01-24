package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IArticle;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.data.Chapeter;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStoragePullMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncHandler;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiResearchBook extends GuiScreen {
	
	public static final ResourceLocation bioBookBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/GuiBioBookBG.png");
	public static final ResourceLocation bioSidebarBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/book_sidebar.png");
	public static final ResourceLocation sparkleBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/icons/sparkle.png");
	
	private static final int cellSize = 28;
	
	private static final int guiMapTop = -7 * cellSize - 112;
	private static final int guiMapLeft = -7 * cellSize - 112;
	
	private static final int guiMapBottom = 16 * cellSize - 77;
	private static final int guiMapRight = 16 * cellSize - 77;
	
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
	
	private Chapeter selectedChapeter = Chapeter.chapeters.get(0);
	private Chapeter tooltipChapeter = null;
	
	public GuiResearchBook(EntityPlayer player) {
		this.reader = player;
		this.book = player.inventory.getCurrentItem();
		this.player = UUID.fromString(DataHelper.getOwner(book));
		short short1 = 141;
        short short2 = 141;
		this.field_74117_m = this.guiMapX = (double)(0 * cellSize - short1 / 2 - 12);
        this.field_74115_n = this.guiMapY = (double)(0 * cellSize - short2 / 2);
        this.article = GuiInventoryOverlay.craftingHelper.getBooked();
        if(article != null){
        	article.setLast(this);
        	article.setWorldAndResolution(Minecraft.getMinecraft(), width, height, null);
        }else{
        	article = new GuiArticle(reader, ReserchRegistry.tutorialResearch, this.player);
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
        		HiveteraMod.network.sendToServer(new PlayerStorageFaveMessage(reader.getGameProfile().getId().toString(), article.getArticle().getCode()));
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
        	//TODO: if(tooltipResearch.getContent().getFaved() == null)
        	//	return;
        	
        	HiveteraMod.network.sendToServer(new PlayerStorageFaveMessage(reader.getGameProfile().getId().toString(), tooltipResearch.getCode()));
        	//tooltipResearch.getContent().selected(!b);
			
        }else if(btn == 1 && this.tooltipChapeter != null){
        	this.selectedChapeter = this.tooltipChapeter;
        	article = new GuiArticle(reader, tooltipChapeter, player);
			article.setLast(this);
			article.setWorldAndResolution(mc, width, height, null);
			this.initGui();
        	
        }else if(btn == 0 && this.tooltipChapeter != null){
        	this.selectedChapeter = this.tooltipChapeter;
        }  else{
        	super.mouseClicked(x, y, btn);
        }
    }
	
	public void addToMapPos(double x, double y){
		this.guiMapX += x;
        this.guiMapY += y;
        
        this.field_74117_m = this.guiMapX;
        this.field_74115_n = this.guiMapY;
        
        if (this.guiMapY-20 < (double)guiMapTop)
        {
        	this.guiMapY = (double)guiMapTop + 20;
            
        }

        if (this.guiMapX-20 < (double)guiMapLeft)
        {
        	this.guiMapX = (double)guiMapLeft + 20;
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
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
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
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		this.drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		this.genBioBookBackground(par1, par2, par3);
		//Draw Relations
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		this.drawIcons(par1, par2, par3);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    	GuiArticle.drawBackground(mc, this, par1, par2, par3);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		this.drawOverlay(par1, par2, par3);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		super.drawScreen(par1, par2, par3);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		
		this.drawChapeterSidebar(par1, par2, par3);
		
		if(article != null){
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			article.drawScreen(par1, par2, par3);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			article.drawTooltip();
			IArticle research = article.getArticle();
			int k = this.calculateLeft() + this.getScreenWidth();
	        int b0 = this.calculateTop();
	        
	        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
	        this.renderTitle(research, k + 15, b0);
	        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
	        if(research instanceof Research)
	        	this.drawIcon((Research) research, k, b0 - 13, true, true);
	        if(research instanceof Chapeter)
	        	this.drawIcon((Chapeter) research, k, b0 - 13, true, true);
		}
		
		if(tooltipResearch != null){
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
			this.drawTooltip(par1, par2, par3);
		}
		
		if(this.tooltipChapeter != null){
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
			this.drawChapeterTooltip(par1, par2, par3);
		}
		
		
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }
	
	 public void drawChapeterTooltip(int par1, int par2, float par3){
		 this.renderChapeterToolTip(this.tooltipChapeter, par1, par2);
	 }
	 
	protected void renderChapeterToolTip(Chapeter hoveringChapeter2, int p_146285_2_, int p_146285_3_)
    {
        List list = new ArrayList();
        list.add(EnumChatFormatting.GOLD + hoveringChapeter2.getTitle());
        list.add(hoveringChapeter2.getDesc());
        
        drawHoveringText(list, p_146285_2_, p_146285_3_, Minecraft.getMinecraft().fontRenderer);
    }
	

	private void drawChapeterSidebar(int par1, int par2, float par3) {
		//TODO: WORK HERE!
		int i1 = calculateLeft() - 10;
        int j1 = calculateTop() + 14;
		
        int i = 1;
        
        PlayerResearch player = PlayerResearchStorage.instance(true).get(this.player);
        if(player == null){
        	HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(this.player));
        	return;
        }
        
        boolean owner = this.player.equals(this.reader.getGameProfile().getId());
        
        for(Chapeter chap : Chapeter.chapeters){
        	if(!ResearchLogicNetwork.instance().hasUnlocked(player, chap))
        		continue;
        	
        	this.mc.renderEngine.bindTexture(chap.getIcon());
        	this.func_146110_a(i1, j1, this.selectedChapeter == chap ? 26 : 0, 0, 26, 26, 52, 26);
        	
        	if(chap != this.selectedChapeter && owner && ResearchLogicNetwork.instance().hasNew(player, chap)){
        		this.mc.renderEngine.bindTexture(sparkleBG);
            	this.func_146110_a(i1, j1, 0, 0, 26, 26, 26, 26);
        	}
        	Rectangle rect = new Rectangle(i1, j1, 26, 26);
            if(rect.contains(new Rectangle(par1, par2, 2, 2))){
            	this.tooltipChapeter = chap;
            }
        	
        	j1 += 26;
        }
		
		this.mc.renderEngine.bindTexture(this.bioBookBG);
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

	        for (i3 = 0; i3 * 16 - l2 < 155; i3++)
	        {
	            float f1 = 0.6F - (float)(j2 + i3) / 25.0F * 0.3F;
	            GL11.glColor4f(f1, f1, f1, 1.0F);

	            for (k3 = 0; k3 * 16 - k2 < 224; k3++)
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
        tooltipChapeter = null;
        PlayerResearch res = PlayerResearchStorage.instance(true).get(player);
        if(res == null){
        	this.drawString(fontRendererObj, "Syncing...", k1, l1, 0xFFFFFF);
        	HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(player));
        	return;
        }
        for(Research knowledge : ResearchLogicNetwork.instance().getOpenResearches(this.selectedChapeter, res)){
        	int colum = knowledge.displayColumn * cellSize - k;
            int row = knowledge.displayRow * cellSize - l;
            if (colum >= -10 && row >= -10 && colum <= 224 && row <= 155)
            {
            	i5 = k1 + colum;
                l4 = l1 + row;
                
                PlayerResearch r = PlayerResearchStorage.instance(true).get(player);
                if(res == null){
                	HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(player));
                	return;
                }
                
                this.drawIcon(knowledge, i5, l4, r.hasCompleted(knowledge.getCode()), true);
                Rectangle rect = new Rectangle(i5, l4, 26, 26);
                if(rect.contains(new Rectangle(par1, par2, 2, 2))){
                	tooltipResearch = knowledge;
                }
            }
            
        }
        if(this.article != null && this.article.getArticle() instanceof Research){
        	Research knowledge = (Research)this.article.article;
        	
        	if(!knowledge.getChapeter().getCode().equals(this.selectedChapeter.getCode()))
        		return;
        	
        	int colum = knowledge.displayColumn * cellSize - k;
            int row = knowledge.displayRow * cellSize - l;
            i5 = k1 + colum;
            l4 = l1 + row;
            
            if (!(colum >= -10 && row >= -10 && colum <= 224 && row <= 155))
            	return;
            
            Minecraft mc = Minecraft.getMinecraft();
   		 	RenderHelper.enableGUIStandardItemLighting();
           	GL11.glEnable(GL11.GL_BLEND);
   		 	float f2 = 1.0F;
        	GL11.glColor4f(f2, f2, f2, 1.0F);
        	
        	
        	if (knowledge.getSpecial())
            {
                mc.renderEngine.bindTexture(bioBookBG);
            	GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
            	this.drawTexturedModalRect(i5 - 3, l4 - 3, 52, 202 + 26, 28, 28);
            	GL11.glColor4f(f2, f2, f2, 1.0F); 
            }
            else
            {
            	mc.renderEngine.bindTexture(bioBookBG);
             	GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
             	this.drawTexturedModalRect(i5 - 3, l4 - 2, 52, 202, 26, 26);
             	GL11.glColor4f(f2, f2, f2, 1.0F); 
            }
        	
           
           	RenderHelper.disableStandardItemLighting();
           	
           	
           	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
	 }
	 
	 public void drawIcon(Research research, int x, int y, boolean isCompleted, boolean notGreyed){
		 Minecraft mc = Minecraft.getMinecraft();
		 RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL11.GL_BLEND);
		 float f2 = 1.0F;
     	GL11.glColor4f(f2, f2, f2, 1.0F);
     	
     	//mc.renderEngine.bindTexture(bioBookBG);
        
         float f3 = 1.0F;
         if(!isCompleted || !notGreyed)
        	 f3 = 0.3F;
          
          GL11.glColor4f(f3, f3, f3, 1.0F);
          
         PlayerResearch res = PlayerResearchStorage.instance(true).get(player);
         if(res == null){
        	 HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(player));
        	 return;
         }
         boolean owner = player.equals(this.reader.getGameProfile().getId());
         
         if (research.getSpecial())
         {
        	 this.mc.renderEngine.bindTexture(research.getCategory().getIcon()); 
        	 
        	 
             this.func_146110_a(x - 2, y - 2, 26, 0, 26, 26, 52, 26);
             
             mc.renderEngine.bindTexture(bioBookBG);
             
             if(owner && PlayerStorageSyncHandler.totallyNew.contains(research.getCode())){
            	 GL11.glColor4f(0.0F, 0.0F, 1.0F, 1.0F);
            	 this.drawTexturedModalRect(x - 3, y - 2, 52, 202 + 26, 28, 28);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
             }
             
             if(res != null && res.hasFaved(research.getCode())){
            	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	 this.drawTexturedModalRect(x - 2, y - 2, 26, 202 + 26, 26, 26);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
          	}
         }
         else
         {
        	 this.mc.renderEngine.bindTexture(research.getCategory().getIcon()); 
        	 
        	 this.func_146110_a(x - 2, y - 2, 0, 0, 26, 26, 52, 25);
             
             mc.renderEngine.bindTexture(bioBookBG);
             if(owner && PlayerStorageSyncHandler.totallyNew.contains(research.getCode())){
            	 GL11.glColor4f(0.0F, 0.0F, 1.0F, 1.0F);
            	 this.drawTexturedModalRect(x - 3, y - 2, 52, 202, 26, 26);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
             }
             
             
             if(res != null && res.hasFaved(research.getCode())){
            	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	 drawTexturedModalRect(x - 2, y - 3, 0, 202 + 26, 26, 26);
            	 GL11.glColor4f(f3, f3, f3, 1.0F);
          	}
             
         }

          
          renderitem.renderWithColor = true;
          renderitem.zLevel = -50.0f;
          
         GL11.glDisable(GL11.GL_LIGHTING); //Forge: Make sure Lighting is disabled. Fixes MC-33065
         GL11.glEnable(GL11.GL_CULL_FACE);
         renderitem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, research.getIconStack(), x + 3, y + 3);
         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(GL11.GL_ALPHA_TEST);
         
         RenderHelper.disableStandardItemLighting();
         
         
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	 }
	 
	 public void drawIcon(Chapeter chapeter, int x, int y, boolean isCompleted, boolean notGreyed){
		 Minecraft mc = Minecraft.getMinecraft();
		 RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL11.GL_BLEND);
        
		 float f2 = 1.0F;
     	GL11.glColor4f(f2, f2, f2, 1.0F);
     	
     	this.mc.renderEngine.bindTexture(chapeter.getIcon());
    	this.func_146110_a(x, y, 26, 0, 26, 26, 52, 26);
         
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_BLEND);
         
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
		
		//TODO: WORK WORK!
		
		this.drawString(fontRendererObj, this.selectedChapeter.getTitle(), i1+20, j1+5, 0xFFFFFF);
		this.drawString(fontRendererObj, DataHelper.getOwnerName(player, Minecraft.getMinecraft().theWorld) + "'s book", i1 + 20, j1 + this.knowledgePaneHeight - 20, 0xFFFFFF);
		
		
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
	
	protected void renderTitle(IArticle tooltipKnowledge, int p_146285_2_, int p_146285_3_)
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
			HiveteraMod.network.sendToServer(new PlayerStorageDelissenMessage(observer, subject));
		}else{
			PlayerStorageSyncHandler.totallyNew.clear();
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false; //IF TRUE YOU CANNOT FAVORISE STUFF!!! FFS!
    }
	
	public GuiResearchBook load() {
		GuiArticle a = GuiInventoryOverlay.craftingHelper.getBooked();
        if(a != null){
        	article = a;
        	article.setLast(this);
        	article.setWorldAndResolution(Minecraft.getMinecraft(), width, height, null);
        }
		return this;
	}
}
