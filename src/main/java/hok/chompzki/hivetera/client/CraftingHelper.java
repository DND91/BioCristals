package hok.chompzki.hivetera.client;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.client.gui.GuiButtonAutoPage;
import hok.chompzki.hivetera.client.gui.GuiButtonHomePage;
import hok.chompzki.hivetera.client.gui.GuiButtonNextPage;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.GuiCrossButtonPage;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.network.MessageInsertCrafting;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class CraftingHelper {
	
	List<GuiCraftingHelper> guis = new ArrayList<GuiCraftingHelper>();
	private int currentGui = 0;
	
	private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private GuiCrossButtonPage buttonClosePage;
    private GuiButtonHomePage buttonHomePage;
    private GuiButtonAutoPage buttonAutoPage;
    
    private boolean mousePress = false;
    
    private GuiArticle nextBookArticle = null;
    
    public CraftingHelper(){
    	this.buttonNextPage = new GuiButtonNextPage(1, 0, 0, true);
        this.buttonPreviousPage = new GuiButtonNextPage(2, 0, 0, false);
        this.buttonClosePage = new GuiCrossButtonPage(3, 0, 0);
        this.buttonHomePage = new GuiButtonHomePage(3, 0, 0);
        this.buttonAutoPage = new GuiButtonAutoPage(4, 0, 0);
    }
	
	public void back(){
		currentGui = (currentGui-1) % guis.size();
		currentGui = currentGui < 0 ? guis.size() - 1 : currentGui;
		this.updateButtons();
	}
	
	public void next(){
		currentGui = (currentGui+1) % guis.size();
		this.updateButtons();
	}
	
	public void drawCurrentSelected(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		if(guis.size() <= 0)
			return;
		RenderHelper.enableGUIStandardItemLighting();
		GuiCraftingHelper gui = guis.get(currentGui);
		gui.rescale(mc, currentScreen);
		gui.drawBackground(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		gui.drawCenteredString(Minecraft.getMinecraft().fontRenderer, (this.currentGui+1) + "/" + this.guis.size(), gui.getWidth() / 2, gui.getHeight(), 0xFFFFFF);
		
		buttonPreviousPage.xPosition = 10;
		buttonPreviousPage.yPosition = gui.getHeight() - 20;
		
		buttonNextPage.xPosition = gui.getWidth() - 30;
		buttonNextPage.yPosition = gui.getHeight() - 20;
		
		buttonClosePage.xPosition = gui.getWidth() - 16;
		buttonClosePage.yPosition = 10;
		
		buttonHomePage.xPosition = gui.getWidth() / 2 - 7;
		buttonHomePage.yPosition = gui.getHeight() - 20;
		
		buttonAutoPage.xPosition = gui.getWidth() - 20;
		buttonAutoPage.yPosition = gui.getHeight() / 2 - 10;
		
		buttonPreviousPage.drawButton(mc, mouseX, mouseY);
		buttonNextPage.drawButton(mc, mouseX, mouseY);
		buttonClosePage.drawButton(mc, mouseX, mouseY);
		buttonHomePage.drawButton(mc, mouseX, mouseY);
		buttonAutoPage.drawButton(mc, mouseX, mouseY);
		
		gui.rescale(mc, currentScreen);
		gui.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		
		
		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        boolean currentMouse = Mouse.isButtonDown(0);
        
        if(mousePress && !currentMouse){
        	if(this.buttonNextPage.mousePressed(mc, mouseX, mouseY)){
        		this.next();
        	}else if(this.buttonPreviousPage.mousePressed(mc, mouseX, mouseY)){
        		this.back();
        	}else if(this.buttonClosePage.mousePressed(mc, mouseX, mouseY)){
        		HiveteraMod.network.sendToServer(new PlayerStorageFaveMessage(player.getGameProfile().getId().toString(), gui.getResearch().getCode()));
        	}else if(this.buttonHomePage.mousePressed(mc, mouseX, mouseY)){
        		 this.nextBookArticle = new GuiArticle(Minecraft.getMinecraft().thePlayer, gui.getResearch(), UUID.fromString(DataHelper.getOwner(currentStack)));
        		 DataHelper.belongsTo(player, player.inventory.getCurrentItem());
        		 
        		 player.closeScreen();
        		 
        		 player.openGui(HiveteraMod.instance, 100, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        	}else if(this.buttonAutoPage.mousePressed(mc, mouseX, mouseY)){
        		if(currentScreen instanceof GuiContainer){
        			if(gui.getInput() == null || gui.getOutput() == null)
        				return;
        			HiveteraMod.network.sendToServer(new MessageInsertCrafting(gui.getOutput()));
        		}
        	}
        	
        	
        	
        }
        
        mousePress = currentMouse;
	}
	
	public GuiArticle getBooked(){
    	GuiArticle t = nextBookArticle;
    	nextBookArticle = null;
    	return t;
    }
	
	public boolean contains(String code) {
		for(GuiCraftingHelper gui : guis){
			if(gui.getResearch().getCode().equals(code))
				return true;
		}
		return false;
	}
	
	public void remove(String code) {
		for(int i = 0; i < guis.size(); i++){
			if(guis.get(i).getResearch().getCode().equals(code))
				guis.remove(i);
		}
		updateButtons();
		this.currentGui = 0;
	}
	
	public void add(GuiCraftingHelper guiCraftRecipe) {
		if(guiCraftRecipe == null)
			return;
		if(!this.contains(guiCraftRecipe.getResearch().getCode()))
			guis.add(guiCraftRecipe);
		updateButtons();
	}
	
	public void removreCurrent(){
		guis.remove(currentGui);
		currentGui = 0;
		updateButtons();
	}
	
	public void updateButtons(){
		this.buttonNextPage.visible = 1 < guis.size();
		this.buttonPreviousPage.visible = 1 < guis.size();
		this.buttonClosePage.visible = 0 < guis.size();
		if(0 < guis.size() && guis.size() <= currentGui)
			currentGui = guis.size() - 1;
		this.buttonAutoPage.visible = 0 < guis.size() && guis.get(currentGui).getInput() != null;
	}
	
	public int place(){
		return this.currentGui;
	}

	public void clear() {
		this.guis.clear();
		this.updateButtons();
	}

	public void setPage(int place) {
		this.currentGui = place;
		if(this.guis.size() != 0)
			currentGui %= this.guis.size();
		else
			currentGui = 0;
		this.currentGui = Math.max(currentGui, 0);
		this.updateButtons();
	}
	
}
