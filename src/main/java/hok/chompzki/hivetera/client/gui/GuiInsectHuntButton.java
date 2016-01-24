package hok.chompzki.hivetera.client.gui;

import java.util.Iterator;
import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.ItemRegistry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiInsectHuntButton extends GuiButton {

	private int type;
	public boolean active;
	
    public GuiInsectHuntButton(int id, int x, int y, int type)
    {
        super(id, x, y, 15, 15, "");
        this.type = type;
    }
    
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            boolean flag = active; //par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            par1Minecraft.renderEngine.bindTexture(GuiInsectHunt.insectHuntBG);
            int k = 176;
            int l = type * 15;
            
            if (flag)
            {
                k += 15;
            }
            
            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, this.width, this.height);
            
        }
    }
    
    public void renderTooltip(Minecraft par1Minecraft, int par2, int par3){
    	switch(type){
    	case 0:
    		this.renderToolTip("Sacrifice sample (Gain 1 pt)", par2, par3, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    		break;
    	case 1:
    		this.renderToolTip("Open sample", par2, par3, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    		break;
    	case 2:
    		this.renderToolTip("Buy random sample (Costs 2 pts)", par2, par3, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    		break;
    	case 3:
    		this.renderToolTip("Clone sample (Costs 4 pts)", par2, par3, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    		break;
    	}
    }
    
    protected void renderToolTip(String text, int x, int y, int pageImageWidth, int pageImageHeight)
    {
		Minecraft mc = Minecraft.getMinecraft();

		text = EnumChatFormatting.WHITE + text;

        FontRenderer font = ItemRegistry.crootStick.getFontRenderer(null);
        if(font == null)
        	font = Minecraft.getMinecraft().fontRenderer;
        drawHoveringText(text, x, y, font, pageImageWidth, pageImageHeight);
    }
    
    protected void drawHoveringText(String text, int x, int y, FontRenderer font, int pageImageWidth, int pageImageHeight)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int k = 0;

        String s = text;
        int l = font.getStringWidth(s);

        if (l > k)
        {
            k = l;
        }

        int j2 = x + 12;
        int k2 = y - 12;
        int i1 = 8;

        if (j2 + k > pageImageWidth)
        {
            j2 -= 28 + k;
        }

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

        String s1 = text;
        
        font.drawStringWithShadow(s1, j2, k2, -1);
        
        k2 += 2;

        k2 += 10;

        /*GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);*/
    }
}
