package hok.chompzki.hivetera.client.gui;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerNest;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiNest extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/Nest.png");
    private IInventory hollowInventory;
    private TileNest nest;

	public GuiNest(InventoryPlayer inventory, TileNest tileEntity) {
		super(new ContainerNest(inventory, tileEntity));
		this.hollowInventory = tileEntity;
		this.nest = tileEntity;
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.hollowInventory.hasCustomInventoryName() ? this.hollowInventory.getInventoryName() : I18n.format(this.hollowInventory.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 0xFFFFFF);
        if(this.nest.getStackInSlot(1) != null){
        	ItemStack stack = this.nest.getStackInSlot(1);
        	INestInsect insect = (INestInsect)(stack.getItem());
        	this.fontRendererObj.drawSplitString(insect.getActionText(nest, stack), 9, 37, 103, 0xFFFFFF);
        }
    }
	
	int tick = 0;
	
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	tick++;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(hollowBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if(0 < this.nest.startTime){
	        int i1 = 35 - this.nest.getCookProgressScaled(35);
	        this.drawTexturedModalRect(k + 43, l + 15, 0, 166, i1 + 1, 16);
        }
    }

}
