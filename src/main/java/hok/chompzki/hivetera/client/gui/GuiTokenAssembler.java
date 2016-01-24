package hok.chompzki.hivetera.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiCheckBox;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerNest;
import hok.chompzki.hivetera.containers.ContainerTokenAssembler;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;

public class GuiTokenAssembler extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/TokenAssembler.png");
    private IInventory hollowInventory;
    private TileTokenAssembler assembler;
    
    private int selected = 0;
    
	public GuiTokenAssembler(EntityPlayer player, TileTokenAssembler tileEntity) {
		super(new ContainerTokenAssembler(player, tileEntity));
		this.hollowInventory = tileEntity;
		this.assembler = tileEntity;
		this.xSize = 256;
		this.ySize = 256;
		
	}
	
	public void initGui()
    {
        super.initGui();
        
        int w = (this.width - this.xSize) / 2;
        int h = (this.height - this.ySize) / 2;
        
        this.buttonList.add(new GuiButton(0 ,w + 10, h + 20, 20, 20, "<"));
        this.buttonList.add(new GuiButton(1 ,w + xSize - 110, h + 20, 20, 20, ">"));
        this.buttonList.add(new GuiButton(2 ,w + 10, h + 45, 20, 20, "<"));
        this.buttonList.add(new GuiButton(3 ,w + xSize - 110, h + 45, 20, 20, ">"));
        
        this.buttonList.add(new GuiButton(100 ,w + 194, h + 6, 35, 20, "Craft"));
        
        if(EnumToken.values()[selected] == EnumToken.FILTER){
        	for(int x = 0; x < 2; x++)
    		for(int y = 0; y < 3; y++){
        		String s = I18n.format("container."+EnumResource.values()[x + y * 2].name(), new Object[0]);
        		
        		this.buttonList.add(new GuiCheckBox(4+x + y * 2, w + 10 + x * 95, h + 80 + y * 15, s, this.assembler.values[x + y * 2] == 1));
        	}
		}
        
        if(EnumToken.values()[selected] == EnumToken.BANK){
        	for(int x = 0; x < 2; x++)
    		for(int y = 0; y < 3; y++){
        		String s = I18n.format("container."+EnumResource.values()[x + y * 2].name(), new Object[0]);
        		
        		this.buttonList.add(new GuiCheckBox(4+x + y * 2, w + 10 + x * 95, h + 80 + y * 15, s, this.assembler.values[x + y * 2] == 1));
        	}
        	
        	this.buttonList.add(new GuiButton(10 ,w + 10, h + 140, 20, 20, "-"));
            this.buttonList.add(new GuiButton(11 ,w + xSize - 110, h + 140, 20, 20, "+"));
		}
        
        if(EnumToken.values()[selected] == EnumToken.TRANSFORMER){
        	
        	this.buttonList.add(new GuiButton(4 ,w + 10, h + 70, 20, 20, "<"));
            this.buttonList.add(new GuiButton(5 ,w + xSize - 110, h + 70, 20, 20, ">"));
            
            this.buttonList.add(new GuiButton(6 ,w + 10, h + 95, 20, 20, "<"));
            this.buttonList.add(new GuiButton(7 ,w + xSize - 110, h + 95, 20, 20, ">"));
            
            this.buttonList.add(new GuiButton(8 ,w + 10, h + 120, 20, 20, "<"));
            this.buttonList.add(new GuiButton(9 ,w + xSize - 110, h + 120, 20, 20, ">"));
            
		}
    }
	
	protected void actionPerformed(GuiButton btn) {
		if(btn.id == 0){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 0);
		} else if(btn.id == 1){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 1);
		} else if(btn.id == 2){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 2);
		} else if(btn.id == 3){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 3);
		} else if(btn.id == 100){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 100);
			return;
		}
		
		if(EnumToken.values()[selected] == EnumToken.TRANSFORMER && (btn.id-4) < assembler.values.length){
			if(btn.id == 4){
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 4);
			} else if(btn.id == 5){
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 5);
			} else if(btn.id == 6){
				if(GuiScreen.isShiftKeyDown())
					for(int i = 0; i < 9; i++)
						this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 6);
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 6);
			} else if(btn.id == 7){
				if(GuiScreen.isShiftKeyDown())
					for(int i = 0; i < 9; i++)
						this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 7);
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 7);
			} else if(btn.id == 8){
				if(GuiScreen.isShiftKeyDown())
					for(int i = 0; i < 9; i++)
						this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 8);
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 8);
			} else if(btn.id == 9){
				if(GuiScreen.isShiftKeyDown())
					for(int i = 0; i < 9; i++)
						this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 9);
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, 9);
			}
        }
		
		if(3 < btn.id && EnumToken.values()[selected] == EnumToken.FILTER && (btn.id-4) < assembler.values.length){
			this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, btn.id);
		}
		
		if(3 < btn.id && EnumToken.values()[selected] == EnumToken.BANK && (btn.id-4) < assembler.values.length){
			if(btn.id < 10)
				this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, btn.id);
			else
				if(GuiScreen.isShiftKeyDown())
					for(int i = 0; i < 10; i++)
						this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, btn.id);
				else
					this.mc.playerController.sendEnchantPacket(inventorySlots.windowId, btn.id);
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.hollowInventory.hasCustomInventoryName() ? this.hollowInventory.getInventoryName() : I18n.format(this.hollowInventory.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 0xFFFFFF);
        
        s = "Token";
    	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 25, 0xFFD700);
        s = I18n.format("container."+EnumToken.values()[assembler.selected].name(), new Object[0]);
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 35, 0xFFFFFF);
        
        s = "Channel";
    	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 45, 0xFFD700);
        if(assembler.color == 0)
        	s = "NONE";
        else
        	s = I18n.format("container."+ItemDye.field_150923_a[assembler.color-1], new Object[0]);
        
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 55, 0xFFFFFF);
        
        s = "Cost";
    	this.fontRendererObj.drawString(s, 213 - this.fontRendererObj.getStringWidth(s) / 2, 36, 0xFFD700);
        
        if(EnumToken.values()[selected] == EnumToken.FILTER){
        	s = "What to filter?";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 70, 0xFFD700);
        }
        
        if(EnumToken.values()[selected] == EnumToken.BANK){
        	s = "What to store?";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 70, 0xFFD700);
        	
        	s = "Max storage";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 130, 0xFFD700);
        	s = "" + (this.assembler.values[6] * 100);
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 140, 0xFFFFFF);
        }
        
        if(EnumToken.values()[selected] == EnumToken.TRANSFORMER){
        	int product = this.assembler.values[0];
        	int effiecency = this.assembler.values[1];
        	int balance = this.assembler.values[2];
        	String pro = I18n.format("container."+EnumResource.values()[product], new Object[0]);
        	String was = I18n.format("container."+EnumResource.WASTE, new Object[0]);
        	
        	if(EnumResource.values()[product] == EnumResource.WASTE){
        		was = "lost";
        		pro = "2 x " + pro;
        	}
        	
        	if(EnumResource.values()[product] == EnumResource.RAW_FOOD){
        		was = "lost";
        		pro = "1 x " + pro;
        	}
        	
        	s = "Product";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 70, 0xFFD700);
        	s = I18n.format("container."+EnumResource.values()[product], new Object[0]);
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 80, 0xFFFFFF);
        	
        	s = "Efficiency";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 95, 0xFFD700);
        	s = effiecency + "%";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 105, 0xFFFFFF);
        	
        	s = "Balance";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 120, 0xFFD700);
        	s = was + " / " + pro;
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 130, 0xFFFFFF);
        	s = (100-balance) + "% / " + balance + "%";
        	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 140, 0xFFFFFF);
        }
        
        
    }
	
	int tick = 0;
	
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	if(selected != this.assembler.selected){
    		selected = this.assembler.selected;
    		this.setWorldAndResolution(mc, width, height);
    		this.buttonList.clear();
    		this.initGui();
    	}
    	
    	tick++;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(hollowBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        
    }

}
