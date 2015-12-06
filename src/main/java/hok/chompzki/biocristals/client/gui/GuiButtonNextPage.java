package hok.chompzki.biocristals.client.gui;

import hok.chompzki.biocristals.BioCristalsMod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonNextPage extends GuiButton {
	/**
     * True for pointing right (next page), false for pointing left (previous page).
     */
    private final boolean nextPage;

    public GuiButtonNextPage(int par1, int par2, int par3, boolean par4)
    {
        super(par1, par2, par3, 23, 13, "");
        this.nextPage = par4;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            boolean flag = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            par1Minecraft.renderEngine.bindTexture(new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/book.png"));
            int k = 1;
            int l = 193;

            if (flag)
            {
                k += 2 + 17;
            }

            if (!this.nextPage)
            {
                l += 13;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 18, 13);
        }
    }
}