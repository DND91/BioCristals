package hok.chompzki.hivetera.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.containers.ContainerArmorAttuner;
import hok.chompzki.hivetera.containers.ContainerHivebag;
import hok.chompzki.hivetera.containers.ContainerHoneyWidow;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.containers.HoneyWidow;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiArmorAttuner extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/BioModArmor.png");
	
	EntityPlayer player = null;
	
	public GuiArmorAttuner(EntityPlayer player) {
		super(new ContainerArmorAttuner(player));
		this.player = player;
		this.xSize = 176;
		this.ySize = 256;
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = I18n.format("container.armor.attuner", new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, -4, 0xFFFFFF);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0xFFFFFF);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(hollowBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
	
    @Override
	public void onGuiClosed()
    {
		super.onGuiClosed();
    }
}
