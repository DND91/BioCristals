package hok.chompzki.hivetera.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.containers.ContainerHivebag;
import hok.chompzki.hivetera.containers.ContainerHoneyWidow;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.containers.HoneyWidow;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiHoneyWidow extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/HoneyWidow.png");
	
	ItemStack stack = null;
	int slot = -1;
	EntityPlayer player = null;
	HoneyWidow honeyWidow = null;
	
	public GuiHoneyWidow(EntityPlayer player, int slot) {
		super(new ContainerHoneyWidow(player, slot));
		this.slot = slot;
		this.stack = player.inventory.getStackInSlot(slot);
		this.player = player;
		player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", true);
		honeyWidow = new HoneyWidow(player, slot);
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.honeyWidow.hasCustomInventoryName() ? this.honeyWidow.getInventoryName() : I18n.format(this.honeyWidow.getInventoryName(), new Object[0]);
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
		player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", false);
		this.honeyWidow.isOpen = false;
		super.onGuiClosed();
    }
}
