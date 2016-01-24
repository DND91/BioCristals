package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.HiveteraMod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonHomePage extends GuiButton {

    public GuiButtonHomePage(int par1, int par2, int par3)
    {
        super(par1, par2, par3, 23, 13, "");
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
            par1Minecraft.renderEngine.bindTexture(new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/book.png"));
            int k = 38;
            int l = 205;

            if (flag)
            {
                k += 14;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 13, 13);
        }
    }
}
