package hok.chompzki.hivetera.client.gui;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.containers.ContainerCrootBreeder;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerSacrificePit;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileSacrificePit;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiSacrificePit extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/SacrificePit.png");
    private IInventory hollowInventory;
    private TileSacrificePit tileEntity;

	public GuiSacrificePit(EntityPlayer player, TileSacrificePit tileEntity) {
		super(new ContainerSacrificePit(player, tileEntity));
		this.hollowInventory = tileEntity;
		this.tileEntity = tileEntity;
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.hollowInventory.hasCustomInventoryName() ? this.hollowInventory.getInventoryName() : I18n.format(this.hollowInventory.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 0xFFFFFF);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0xFFFFFF);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(hollowBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if(0 < this.tileEntity.time){
	        int i1 = 17;
	        int i2 = this.tileEntity.getCookProgressScaled(26);
	        
	        this.drawTexturedModalRect(k + 79, l + 28, 0, 166, i1 + 1, 27 - i2);
        }
    }

}
