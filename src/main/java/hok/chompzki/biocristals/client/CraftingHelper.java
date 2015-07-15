package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.ArticleContent.Content;
import hok.chompzki.biocristals.research.gui.GuiArticle;
import hok.chompzki.biocristals.research.gui.GuiButtonHomePage;
import hok.chompzki.biocristals.research.gui.GuiButtonNextPage;
import hok.chompzki.biocristals.research.gui.GuiCrossButtonPage;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
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
    
    private boolean mousePress = false;
    
    private GuiArticle nextBookArticle = null;
    
    public CraftingHelper(){
    	this.buttonNextPage = new GuiButtonNextPage(1, 0, 0, true);
        this.buttonPreviousPage = new GuiButtonNextPage(2, 0, 0, false);
        this.buttonClosePage = new GuiCrossButtonPage(3, 0, 0);
        this.buttonHomePage = new GuiButtonHomePage(3, 0, 0);
    }
	
	public void next(){
		currentGui = (currentGui-1) % guis.size();
		currentGui = currentGui < 0 ? guis.size() - 1 : currentGui;
	}
	
	public void back(){
		currentGui = (currentGui+1) % guis.size();
	}
	
	public void drawCurrentSelected(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		if(guis.size() <= 0)
			return;
		RenderHelper.enableGUIStandardItemLighting();
		GuiCraftingHelper gui = guis.get(currentGui);
		gui.rescale(mc, currentScreen);
		gui.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		buttonPreviousPage.xPosition = 10;
		buttonPreviousPage.yPosition = gui.getHeight() - 20;
		
		buttonNextPage.xPosition = gui.getWidth() - 30;
		buttonNextPage.yPosition = gui.getHeight() - 20;
		
		buttonClosePage.xPosition = gui.getWidth() - 16;
		buttonClosePage.yPosition = 10;
		
		buttonHomePage.xPosition = gui.getWidth() / 2 - 7;
		buttonHomePage.yPosition = gui.getHeight() - 20;
		
		buttonPreviousPage.drawButton(mc, mouseX, mouseY);
		buttonNextPage.drawButton(mc, mouseX, mouseY);
		buttonClosePage.drawButton(mc, mouseX, mouseY);
		buttonHomePage.drawButton(mc, mouseX, mouseY);
		
		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        boolean currentMouse = Mouse.isButtonDown(0);
        
        if(mousePress && !currentMouse){
        	if(this.buttonNextPage.mousePressed(mc, mouseX, mouseY)){
        		this.next();
        	}else if(this.buttonPreviousPage.mousePressed(mc, mouseX, mouseY)){
        		this.back();
        	}else if(this.buttonClosePage.mousePressed(mc, mouseX, mouseY)){
        		this.removreCurrent();
        	}else if(this.buttonHomePage.mousePressed(mc, mouseX, mouseY)){
        		 this.nextBookArticle = new GuiArticle(Minecraft.getMinecraft().thePlayer, gui.getResearch());
        		 DataHelper.belongsTo(player, player.inventory.getCurrentItem());
        		 
        		 player.closeScreen();
        		 
        		 player.openGui(BioCristalsMod.instance, 100, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        	}
        }
        
        mousePress = currentMouse;
	}
	
	
	public GuiArticle getBooked(){
    	GuiArticle t = nextBookArticle;
    	nextBookArticle = null;
    	return t;
    }
	
	public boolean contains(ItemStack itemStack) {
		for(GuiCraftingHelper gui : guis){
			if(gui.getResult().isItemEqual(itemStack))
				return true;
		}
		return false;
	}
	
	public void remove(ItemStack itemStack) {
		for(int i = 0; i < guis.size(); i++){
			if(guis.get(i).getResult().isItemEqual(itemStack))
				guis.remove(i);
		}
		updateButtons();
		this.currentGui = 0;
	}
	
	public void add(GuiCraftRecipe guiCraftRecipe) {
		if(!this.contains(guiCraftRecipe.getResult()))
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
	}
	
}
