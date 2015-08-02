package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.recipes.CrootRecipeContainer;
import hok.chompzki.biocristals.recipes.PurifierContainer;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.recipes.TransformerContainer;
import hok.chompzki.biocristals.recipes.TransformerEntityContainer;
import hok.chompzki.biocristals.registrys.CristalRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCraft extends GuiCraftingHelper {

	String displayName = "NONE";
	String structure = "";
	String result = "";
	
	public GuiCraft(Minecraft minecraft, String code) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		if(code.equals("NONE"))
			return;
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals(code)){
				displayName = KnowledgeDescriptions.getDisplayName(code);
				structure = KnowledgeDescriptions.transformRecipe(con.output);
				result = KnowledgeDescriptions.transformOutput(con.output);
				this.input = con;
				this.output = con.output;
				return;
			}
		}
		
		for(CrootRecipeContainer con : RecipeRegistry.crootRecipes){
			if(con.code.equals(code)){
				displayName = KnowledgeDescriptions.getDisplayName(code);
				structure = KnowledgeDescriptions.transformCrootRecipe(con.output);
				result = KnowledgeDescriptions.transformOutput(con.output);
				this.input = con;
				this.output = con.output;
				return;
			}
		}
		
		for(TransformerContainer con : CristalRegistry.transformationContainer){
			if(con.code.equals(code)){
				displayName = KnowledgeDescriptions.getDisplayName(code);
				structure = KnowledgeDescriptions.transformWeakCristal(Block.getBlockFromItem(con.output.getItem()));
				result = KnowledgeDescriptions.transformOutput(con.output);
				return;
			}
		}
		
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.code.equals(code)){
				displayName = KnowledgeDescriptions.getDisplayName(code);
				structure = KnowledgeDescriptions.transformWeakFlesh(con.input);
				result = KnowledgeDescriptions.transformOutput(con.input);
				return;
			}
		}
		
		for(PurifierContainer con : RecipeRegistry.purifierContainers){
			if(con.code.equals(code)){
				displayName = KnowledgeDescriptions.getDisplayName(code);
				structure = KnowledgeDescriptions.getStructure(code);
				result = KnowledgeDescriptions.getResult(code);
				return;
			}
		}
		
		
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = displayName;
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, getWidth()-20, 0);
		
        s = "";
        s += "       ~ Structure ~\n";
		s += structure;
		s += "       ~ Creation ~\n\n";
		s += result;
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
