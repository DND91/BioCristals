package hok.chompzki.hivetera.client.gui;

import java.util.List;
import java.util.UUID;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.IArticle;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookParseTree;
import hok.chompzki.hivetera.client.book.parseNodes.Cursor;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStoragePullMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncMessage;

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
	protected int currPage = 0;
	
	public static final int pageImageWidth = 150;
	public static final int pageImageHeight = 192;
    
    protected int updateCount = 0;
    
    protected GuiButtonNextPage buttonNextPage;
    protected GuiButtonNextPage buttonPreviousPage;
    protected GuiCheckboxPage buttonCheckboxPage;
    
    protected ArticleFontRenderer articleFontRenderer;
    
    protected GuiResearchBook last = null;
    protected IArticle article = null;
    protected EntityPlayer reader = null;
	protected UUID owner = null;
    
    public GuiArticle(){
    	
    }
    
	public GuiArticle(EntityPlayer reader, IArticle article, UUID owner){
		this.reader = reader;
		this.article = article;
		this.owner = owner;
	}
	
	public void setLast(GuiResearchBook last){
		this.last = last;
	}
	
	public IArticle getArticle(){
		return article;
	}
	
	public void setWorldAndResolution(Minecraft minecraft, int par2, int par3, List list)
    {
        //this.guiParticles = new GuiParticle(minecraft);
        this.mc = minecraft;
        this.articleFontRenderer = new ArticleFontRenderer(minecraft, minecraft.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", mc.renderEngine, false);
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
	
	private int getMaxPage(){
		return BookParseTree.articles.get(article.getCode()) != null ? BookParseTree.articles.get(article.getCode()).pageNumber+1 : 1;
	}
	
	private void updateButtons()
    {
        this.buttonNextPage.visible = this.currPage < (getMaxPage()-1);
        this.buttonPreviousPage.visible = this.currPage > 0;
        
        ItemStack stack = reader.inventory.getCurrentItem();
        
        this.buttonCheckboxPage.visible = DataHelper.belongsTo(reader, stack); //TODO: this.article.getContent().getFaved() != null && this.content.getFaved() != null && 
        if(this.buttonCheckboxPage.visible){
        	if(PlayerResearchStorage.instance(true).get(owner) == null){
        		HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(owner));
        	}else {
        		this.buttonCheckboxPage.selected = PlayerResearchStorage.instance(true).get(owner).hasFaved(article.getCode());
        	}
        }
    }
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 1)
            {
                if (this.currPage < getMaxPage() - 1)
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
                HiveteraMod.network.sendToServer(new PlayerStorageFaveMessage(reader.getGameProfile().getId().toString(), article.getCode()));
            }
            this.updateButtons();
        }
    }
	
	public void back(){
		currPage = (currPage-1) % getMaxPage();
    	currPage = currPage < 0 ? getMaxPage() - 1 : currPage;
    	this.updateButtons();
	}
	
	public void next(){
		currPage = (currPage+1) % getMaxPage();
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
        mc.renderEngine.bindTexture(new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/book.png"));
        int k = last.calculateLeft() + last.getScreenWidth();
        int b0 = last.calculateTop() + 10;
        last.drawTexturedModalRect(k, b0, 255 - pageImageWidth, 0, pageImageWidth, pageImageHeight);
        
        //GL11.glDepthFunc(GL11.GL_LEQUAL);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void drawScreen(int par1, int par2, float par3)
    {
		this.buttonCheckboxPage.visible = reader.getGameProfile().getId().equals(owner); //this.content.getFaved() != null && 
        if(this.buttonCheckboxPage.visible){
        	if(PlayerResearchStorage.instance(true).get(owner) == null){
        		this.buttonCheckboxPage.selected = false;
        		HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(owner));
        		return;
        	}else {
        		this.buttonCheckboxPage.selected = PlayerResearchStorage.instance(true).get(owner).hasFaved(article.getCode());
        	}
        }
        
		int k = last.calculateLeft() + last.getScreenWidth();
        int b0 = last.calculateTop() + 10;
		
        String s;
        String s1;
        int l;
        s = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] {Integer.valueOf(this.currPage + 1), Integer.valueOf(getMaxPage())});
        s1 = "";
        
        l = this.articleFontRenderer.getStringWidth(s);
        this.articleFontRenderer.setMousePos(par1, par2);
        this.articleFontRenderer.setScale(1.0f);
        this.articleFontRenderer.drawString(s, k - l + this.pageImageWidth - 44, b0 + 16, 0);
        
        
        Article arc = BookParseTree.articles.get(this.article.getCode());
        if(arc != null){
        	arc.mainBody.draw(new Cursor(), currPage, k + 10, b0 + 16 + 16, this, articleFontRenderer, false, 0.8D);
        	arc.mainBody.drawOverlay(new Cursor(), currPage, k + 10, b0 + 16 + 16, this, articleFontRenderer, false, 0.8D, par1, par2);
        	this.articleFontRenderer.setScale(1.0f);
        }
        
        //TODO
        /*
        
        
        this.articleFontRenderer.setScale(0.8f);
        this.articleFontRenderer.drawSplitString(s1, k + 10, b0 + 16 + 10, pageImageWidth - 20, 0);
        
        this.articleFontRenderer.setScale(1.0f);
        */
        
        super.drawScreen(par1, par2, par3);
    }
	
	public void drawTooltip(){
		this.articleFontRenderer.setScale(1.0f);
		this.articleFontRenderer.drawMouse(pageImageWidth, pageImageHeight);
	}
}
