package hok.chompzki.hivetera.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.recipes.BreedingRecipe;
import hok.chompzki.hivetera.recipes.CrootRecipeContainer;
import hok.chompzki.hivetera.recipes.PurifierContainer;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.TransformerContainer;
import hok.chompzki.hivetera.recipes.TransformerEntityContainer;
import hok.chompzki.hivetera.registrys.BreedingRegistry;
import hok.chompzki.hivetera.registrys.CristalRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class GuiBreeding extends GuiCraftingHelper {

	
	String displayName = "NONE";
	List<String> breeding = new ArrayList<String>();
	int selected = 0;
	private boolean mousePressed = false;
	
	GuiButtonNextPage pre = new GuiButtonNextPage(0, this.getWidth() - 30, this.getHeight() / 2 - 15, false);
	GuiButtonNextPage nxt = new GuiButtonNextPage(0, this.getWidth() - 30, this.getHeight() / 2, true);
	
	public GuiBreeding(Minecraft minecraft, ItemStack stack, String code) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		if(code.equals("NONE"))
			return;
		
		displayName = "Breeding " + stack.getDisplayName();
		for(BreedingRecipe rep : BreedingRegistry.list){
			if(OreDictionary.itemMatches(rep.result, stack, true)){
				String s = "";
				s += "   Will eat " + rep.foodNeed + " " + I18n.format("container."+((IInsect)rep.result.getItem()).getFoodType(rep.result), new Object[0]) + ".\n\n";
				s += "         ~ PARENTS ~   \n\n";
				s += "\t\f" + KnowledgeDescriptions.transformItemStack(new ItemStack((Item)rep.pappa), false);
				s += KnowledgeDescriptions.transformItemStack(rep.nestMaterial, false);
				s += KnowledgeDescriptions.transformItemStack(new ItemStack((Item)rep.mamma), false) + "\t\n\n";
				s += "           ~ BABY~   \n\n";
				s += "\t\f" + KnowledgeDescriptions.transformItemStack(new ItemStack(Blocks.air), false) + KnowledgeDescriptions.transformItemStack(rep.result, false) + "\t\n\n";
				
				breeding.add(s);
			}
		}
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = displayName;
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, getWidth()-20, 0);
		
        if(breeding.size() == 0)
        	return;
        
        this.articleFontRenderer.setMousePos(mouseX, mouseY);
        this.articleFontRenderer.setScale(scale*xScale);
        
        if(1 < breeding.size()){
        	boolean currentMouse = Mouse.isButtonDown(0);
        	s = (selected+1) + "/" + breeding.size();
        	this.articleFontRenderer.drawSplitString(s, this.getWidth() - 25, this.getHeight() / 2 - 30, getWidth(), 0);
        	
        	GuiButtonNextPage pre = new GuiButtonNextPage(0, this.getWidth() - 30, this.getHeight() / 2 - 15, false);
    		GuiButtonNextPage nxt = new GuiButtonNextPage(0, this.getWidth() - 30, this.getHeight() / 2 + 0, true);
    		
    		pre.drawButton(mc, mouseX, mouseY);
    		nxt.drawButton(mc, mouseX, mouseY);
    		
    		if(nxt.mousePressed(mc, mouseX, mouseY) && mousePressed && !currentMouse){
    			selected++;
    			selected %= breeding.size();
    		}else if(pre.mousePressed(mc, mouseX, mouseY) && mousePressed && !currentMouse){
    			selected--;
    			if(selected < 0)
    				selected = breeding.size() - 1;
    		}
    		mousePressed = currentMouse;
        }
        
        s = breeding.get(selected);
		
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
		
		
		
	}
	
}
