package hok.chompzki.hivetera.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.containers.ContainerHivebag;
import hok.chompzki.hivetera.containers.Hivebag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiHivebag extends GuiContainer {

	ItemStack stack = null;
	int slot = -1;
	EntityPlayer player = null;
	Hivebag hivebag = null;
	
	public GuiHivebag(EntityPlayer player, int slot) {
		super(new ContainerHivebag(player, slot));
		this.slot = slot;
		this.stack = player.inventory.getStackInSlot(slot);
		this.player = player;
		player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", true);
		hivebag = new Hivebag(player, slot);
	}
	
	@Override
	 protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = I18n.format(this.stack.getDisplayName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2 - 18, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawRect(k, l,k+this.xSize, l+this.ySize, Color.GRAY.getRGB());
		l -= 18;
		
		int i = 0;
		for (i = 0; i < 9; ++i)
        {
        	int x1 = k + 8 + i * 18;
        	int y1 = l + 103 - 20;
        	int x2 = x1 + 16;
        	int y2 = y1 + 16;
        	Color color = Color.BLACK;
        	if(hivebag.inventory[i].slot != null)
        		color = hivebag.getCookProgressColorScale(i, Color.RED, Color.GREEN);
        	
        	this.drawRect(x1, y1,x2, y2, color.getRGB());
        }
		
		
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
            	int x1 = k + 8 + j * 18;
            	int y1 = l + 103 + i * 18;
            	int x2 = x1 + 16;
            	int y2 = y1 + 16;
            	this.drawRect(x1, y1,x2, y2, Color.DARK_GRAY.getRGB());
            }
        }

        for (i = 0; i < 9; ++i)
        {
        	int x1 = k + 8 + i * 18;
        	int y1 = l + 161;
        	int x2 = x1 + 16;
        	int y2 = y1 + 16;
        	this.drawRect(x1, y1,x2, y2, Color.DARK_GRAY.getRGB());
        }
	}
	
	@Override
	public void onGuiClosed()
    {
		player.inventory.getStackInSlot(slot).stackTagCompound.setBoolean("OPEN", false);
		super.onGuiClosed();
    }
}
