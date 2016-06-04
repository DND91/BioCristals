package hok.chompzki.hivetera.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.containers.ContainerArmorAttuner;
import hok.chompzki.hivetera.containers.ContainerHivebag;
import hok.chompzki.hivetera.containers.ContainerHoneyWidow;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.containers.HoneyWidow;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.SocketType;
import hok.chompzki.hivetera.items.token.ItemBank;
import hok.chompzki.hivetera.items.token.ItemEater;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
        
        this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
        
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1)
        {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i1);
            if(slot.getHasStack() && slot.getStack().getItem() instanceof IArmorInsect){
            	IArmorInsect armor = (IArmorInsect)slot.getStack().getItem();
            	
            	GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
        		GL11.glEnable(GL11.GL_BLEND);
        		
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        		
        		IIcon icon = ItemBioModArmor.getBackgroundIcon(armor.getType());
        		
        		Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition, this.guiTop + slot.yDisplayPosition + 16, zLevel, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition + 16, this.guiTop + slot.yDisplayPosition + 16, zLevel, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition + 16, this.guiTop + slot.yDisplayPosition, zLevel, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition, this.guiTop + slot.yDisplayPosition, zLevel, icon.getMinU(), icon.getMinV());
				tessellator.draw();
				
                GL11.glPopMatrix();
            } else if(slot.getHasStack() && (slot.getStack().getItem() instanceof ItemBank || slot.getStack().getItem() instanceof ItemEater)){
            	GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
        		GL11.glEnable(GL11.GL_BLEND);
        		
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        		
        		IIcon icon = ItemBioModArmor.getBackgroundIcon(SocketType.EATER);
        		
        		Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition, this.guiTop + slot.yDisplayPosition + 16, zLevel, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition + 16, this.guiTop + slot.yDisplayPosition + 16, zLevel, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition + 16, this.guiTop + slot.yDisplayPosition, zLevel, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(this.guiLeft + slot.xDisplayPosition, this.guiTop + slot.yDisplayPosition, zLevel, icon.getMinU(), icon.getMinV());
				tessellator.draw();
				
                GL11.glPopMatrix();
            }
        }
    }
	
    @Override
	public void onGuiClosed()
    {
		super.onGuiClosed();
    }
    
    
    
}
