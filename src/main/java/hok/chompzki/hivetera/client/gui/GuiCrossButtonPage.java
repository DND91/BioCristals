package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ArticleContent;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiCrossButtonPage extends GuiButton {
	/**
     * True for pointing right (next page), false for pointing left (previous page).
     */
    public boolean selected;

    public GuiCrossButtonPage(int par1, int par2, int par3)
    {
        super(par1, par2, par3, 23, 13, "");
        this.selected = false;
    }
    
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            boolean flag = selected;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            par1Minecraft.renderEngine.bindTexture(new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/book.png"));
            int k = 38;
            int l = 192;

        	this.drawTexturedModalRect(this.xPosition, this.yPosition, k+14, l, 13, 13);
        }
    }
}
