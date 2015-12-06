package hok.chompzki.biocristals.client.gui;

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
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCrootStickHelper extends GuiCraftingHelper {

	String displayName = "NONE";
	String structure = "";
	String result = "";
	String tool = null;
	
	public GuiCrootStickHelper(Minecraft minecraft, String code, ItemStack tool, ItemStack insect, ItemStack... places) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		
		displayName = insect.getDisplayName() + "\n[Bug hunting]";
		result = "" + KnowledgeDescriptions.transformStrictItemStack(insect, true);
		structure += "\n\t\f";
		for(ItemStack stack : places){
			structure += KnowledgeDescriptions.transformStrictItemStack(stack, false);
		}
		structure += "\t\n";
		
		if(tool == null)
			return;
		this.tool = "\n" + KnowledgeDescriptions.transformStrictItemStack(tool, true);
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = displayName;
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, getWidth()-20, 0);
		
        s = "";
        s += "  ~ Location ~\n";
		s += structure;
		s += "  ~ Chance for ~\n\n";
		s += result;
		if(tool != null){
			s += "\n  ~ With tool ~\n";
			s += tool;
		}
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
