package hok.chompzki.biocristals.tutorials.gui;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.tutorials.data.description.DescriptionFontRenderer;
import hok.chompzki.biocristals.tutorials.data.description.IDescription;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiDescription extends GuiScreen {
	private int currPage = 0;
	private IDescription cDescription;
	
	private int pageImageWidth = 220; //192;
    private int pageImageHeight = 192;
    
    private int updateCount = 0;
    
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private GuiButton buttonDone;
    
    private DescriptionFontRenderer hiveFontRenderer;
    
    private GuiScreen last = null;
	
	public GuiDescription(GuiScreen last, IDescription page){
		cDescription = page;
		this.last = last;
	}
	
	@Override
	public void setWorldAndResolution(Minecraft minecraft, int par2, int par3)
    {
        //this.guiParticles = new GuiParticle(minecraft);
        this.mc = minecraft;
        this.hiveFontRenderer = new DescriptionFontRenderer(minecraft, minecraft.gameSettings, "textures/font/ascii.png", mc.renderEngine, false);
        this.width = par2;
        this.height = par3;
        this.buttonList.clear();
        this.initGui();
    }
	
	public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCount;
    }
	
	public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.pageImageHeight, 200, 20, StatCollector.translateToLocal("gui.done")));

        int i = (this.width - this.pageImageWidth) / 2;
        byte b0 = 2;
        this.buttonList.add(this.buttonNextPage = new GuiButtonNextPage(1, i + 120, b0 + 154, true));
        this.buttonList.add(this.buttonPreviousPage = new GuiButtonNextPage(2, i + 38, b0 + 154, false));
        this.updateButtons();
    }
	
	public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
	
	private void updateButtons()
    {
        this.buttonNextPage.visible = (this.currPage < cDescription.numberOfPages() - 1);
        this.buttonPreviousPage.visible = this.currPage > 0;
        this.buttonDone.visible = true;
    }
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 0)
            {
                this.mc.displayGuiScreen(last);
            }
            else if (par1GuiButton.id == 1)
            {
                if (this.currPage < cDescription.numberOfPages() - 1)
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
            this.updateButtons();
        }
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
        	currPage = (currPage-1) % cDescription.numberOfPages();
        	currPage = Math.abs(currPage);
        	this.updateButtons();
            return;
        }else if (par2 == this.mc.gameSettings.keyBindRight.getKeyCode())
        {
        	currPage = (currPage+1) % cDescription.numberOfPages();
        	this.updateButtons();
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
	
	
	public void drawScreen(int par1, int par2, float par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/book.png"));
        int k = (this.width - this.pageImageWidth) / 2;
        byte b0 = 2;
        this.drawTexturedModalRect(k, b0, 0, 0, this.pageImageWidth, this.pageImageHeight);
        String s;
        String s1;
        int l;
        s = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] {Integer.valueOf(this.currPage + 1), Integer.valueOf(cDescription.numberOfPages())});
        s1 = "";
        
        s1 = this.cDescription.textOnPage(currPage);

        l = this.hiveFontRenderer.getStringWidth(s);
        this.hiveFontRenderer.setMousePos(par1, par2);
        this.hiveFontRenderer.drawString(s, k - l + this.pageImageWidth - 44, b0 + 16, 0);
        this.hiveFontRenderer.drawSplitString(s1, k + 20, b0 + 16 + 16, pageImageWidth - 20, 0);
        
        super.drawScreen(par1, par2, par3);
        
        this.hiveFontRenderer.drawMouse(pageImageWidth, pageImageHeight);
    }
}
