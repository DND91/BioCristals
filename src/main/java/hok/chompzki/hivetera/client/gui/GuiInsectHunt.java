package hok.chompzki.hivetera.client.gui;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerInsectHunt;
import hok.chompzki.hivetera.containers.ContainerNest;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiInsectHunt extends GuiContainer {
	
	public static final ResourceLocation insectHuntBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/BugHunt.png");
    private EntityPlayer player;
    private GuiInsectHuntButton[] buttons = new GuiInsectHuntButton[4];
    private int lastSelected = 1;
    private ContainerInsectHunt con;
    
	public GuiInsectHunt(EntityPlayer player) {
		super(new ContainerInsectHunt(player));
		this.con = (ContainerInsectHunt)this.inventorySlots;
		this.player = player;
		this.xSize = 176;
		this.ySize = 183;
	}
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(buttons[0] = new GuiInsectHuntButton(0, this.guiLeft + 137, this.guiTop + 27, 0));
        this.buttonList.add(buttons[1] = new GuiInsectHuntButton(1, this.guiLeft + 137+19, this.guiTop + 27, 1));
        this.buttonList.add(buttons[2] = new GuiInsectHuntButton(2, this.guiLeft + 137+0, this.guiTop + 27+19, 2));
        this.buttonList.add(buttons[3] = new GuiInsectHuntButton(3, this.guiLeft + 137+19, this.guiTop + 27+19, 3));
        buttons[lastSelected].active = true;
    }
	
	protected void actionPerformed(GuiButton btn) {
		if(btn.id == 0){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 0);
			this.con.clone = null;
		} else if(btn.id == 1){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 1);
			this.con.clone = null;
		} else if(btn.id == 2){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 2);
			this.con.clone = null;
		} else if(btn.id == 3){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 3);
			this.con.clone = null;
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = I18n.format("container.insect.hunt", new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, -4, 0xFFFFFF);
        
        this.fontRendererObj.drawString("" + con.insectHunt.points, 139, 9, 0xFFFFFF);
        
        if(this.con.clone != null)
        	itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), this.con.clone, 9, 29);
        
        for(GuiInsectHuntButton btn : this.buttons){
        	if(btn.mousePressed(mc, p_146979_1_, p_146979_2_))
        		btn.renderTooltip(mc, p_146979_1_ - this.guiLeft, p_146979_2_ - this.guiTop);
        }
    }
	
	int tick = 0;
	
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	if(lastSelected != this.con.selected){
    		this.buttons[lastSelected].active = false;
    		this.lastSelected = this.con.selected;
    		this.buttons[lastSelected].active = true;
    	}
    	tick++;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(insectHuntBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
    }

}
