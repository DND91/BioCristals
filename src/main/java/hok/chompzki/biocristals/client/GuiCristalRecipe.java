package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuiCristalRecipe extends GuiCraftingHelper {
	
	private String tag;

	public GuiCristalRecipe(Minecraft minecraft, String code, ItemStack result, String tag) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code), result, null);
		this.tag = tag;
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = result.getDisplayName();
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, 0);
		
        s = "";
        s += " ~ Crystallization ~\n";
		s += tag;
		s += "       ~ Creation ~\n\n";
		s += KnowledgeDescriptions.transformOutput(result);
        
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
