package hok.chompzki.biocristals.research.gui;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.Content;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.Research;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiArticle extends GuiScreen {
	private int currPage = 0;
	private ArticleContent content;
	
	public static final int pageImageWidth = 150;
	public static final int pageImageHeight = 192;
    
    private int updateCount = 0;
    
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private GuiCheckboxPage buttonCheckboxPage;
    
    private ArticleFontRenderer articleFontRenderer;
    
    private GuiResearchBook last = null;
    private Research research = null;
    private EntityPlayer reader = null;
	
	public GuiArticle(EntityPlayer reader, Research research){
		content = research.getContent();
		this.reader = reader;
		this.research = research;
	}
	
	public void setLast(GuiResearchBook last){
		this.last = last;
	}
	
	public Research getResearch(){
		return research;
	}
	
	public void setWorldAndResolution(Minecraft minecraft, int par2, int par3, List list)
    {
        //this.guiParticles = new GuiParticle(minecraft);
        this.mc = minecraft;
        this.articleFontRenderer = new ArticleFontRenderer(minecraft, minecraft.gameSettings, "textures/font/ascii.png", mc.renderEngine, false);
        this.fontRendererObj = minecraft.fontRenderer;
        this.width = par2;
        this.height = par3;
        this.buttonList.clear();
        if(list != null)
        	this.initGui(list);
    }
	
	public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCount;
    }
	
	public void initGui(List list)
    {
        //Keyboard.enableRepeatEvents(true);
        
        int k = this.last.calculateLeft() + this.last.getScreenWidth();
        int b0 = this.last.calculateTop() + 10;
        list.add(this.buttonNextPage = new GuiButtonNextPage(1, k + 110 - 5, b0 + 154, true));
        list.add(this.buttonPreviousPage = new GuiButtonNextPage(2, k + 20, b0 + 154, false));
        list.add(this.buttonCheckboxPage = new GuiCheckboxPage(3, k + 68, b0 + 152));
        this.updateButtons();
    }
	
	public void onGuiClosed()
    {
        //Keyboard.enableRepeatEvents(false);
    }
	
	private void updateButtons()
    {
        this.buttonNextPage.visible = (this.currPage < content.numberOfPages(Content.INTRO) - 1);
        this.buttonPreviousPage.visible = this.currPage > 0;
        
        ItemStack stack = reader.inventory.getCurrentItem();
        
        this.buttonCheckboxPage.visible = this.content.hasPageSelection(currPage) && DataHelper.belongsTo(reader, stack);
        if(this.buttonCheckboxPage.visible){
        	this.buttonCheckboxPage.selected = this.content.initSelection();
        }
    }
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 1)
            {
                if (this.currPage < content.numberOfPages(Content.INTRO) - 1)
                {
                    ++this.currPage;
                }
            }
            else if (par1GuiButton.id == 2)
            {
                if (this.currPage > 0)
                {
                    --this.currPage;
                }
            }
            else if (par1GuiButton.id == 3)
            {
                this.buttonCheckboxPage.selected = !this.buttonCheckboxPage.selected;
                this.content.selected(this.buttonCheckboxPage.selected);
            }
            this.updateButtons();
        }
    }
	
	public void back(){
		currPage = (currPage-1) % content.numberOfPages(Content.INTRO);
    	currPage = currPage < 0 ? content.numberOfPages(Content.INTRO) - 1 : currPage;
    	this.updateButtons();
	}
	
	public void next(){
		currPage = (currPage+1) % content.numberOfPages(Content.INTRO);
    	this.updateButtons();
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
    {
        if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
            return;
        }else if (par2 == this.mc.gameSettings.keyBindLeft.getKeyCode())
        {
        	back();
            return;
        }else if (par2 == this.mc.gameSettings.keyBindRight.getKeyCode())
        {
        	next();
            return;
        }else if (par2 == this.mc.gameSettings.keyBindBack.getKeyCode())
        {
        	this.mc.displayGuiScreen(last);
            return;
        }else
        {
            super.keyTyped(par1, par2);
        }
    }
	
	@Override
	protected void mouseClicked(int x, int y, int btn)
    {
		if(btn == 1){
        	this.mc.displayGuiScreen(last);
        	
        }else{
        	super.mouseClicked(x, y, btn);
        }
    }
	
	public  static void drawBackground(Minecraft mc, GuiResearchBook last, int par1, int par2, float par3){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/book.png"));
        int k = last.calculateLeft() + last.getScreenWidth();
        int b0 = last.calculateTop() + 10;
        last.drawTexturedModalRect(k, b0, 255 - pageImageWidth, 0, pageImageWidth, pageImageHeight);
        
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void drawScreen(int par1, int par2, float par3)
    {
		int k = last.calculateLeft() + last.getScreenWidth();
        int b0 = last.calculateTop() + 10;
		
        String s;
        String s1;
        int l;
        s = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] {Integer.valueOf(this.currPage + 1), Integer.valueOf(content.numberOfPages(Content.INTRO))});
        s1 = "";
        
        s1 = this.content.textOnPage(Content.INTRO, currPage);
        
        l = this.articleFontRenderer.getStringWidth(s);
        this.articleFontRenderer.setMousePos(par1, par2);
        this.articleFontRenderer.setScale(1.0f);
        this.articleFontRenderer.drawString(s, k - l + this.pageImageWidth - 44, b0 + 16, 0);
        
        this.articleFontRenderer.setScale(0.8f);
        this.articleFontRenderer.drawSplitString(s1, k + 10, b0 + 16 + 10, pageImageWidth - 20, 0);
        
        this.articleFontRenderer.setScale(1.0f);
        
        super.drawScreen(par1, par2, par3);
    }
	
	public void drawTooltip(){
		this.articleFontRenderer.setScale(1.0f);
		this.articleFontRenderer.drawMouse(pageImageWidth, pageImageHeight);
	}

	public ArticleContent getContent() {
		return this.content;
	}
}