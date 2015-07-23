package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCraftRecipe extends GuiCraftingHelper {

	public GuiCraftRecipe(Minecraft minecraft, String code, ItemStack result) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code), result, RecipeRegistry.getRecipreFor(result));
		
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = result.getDisplayName();
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, 0);
		
        s = "";
        s += "       ~ Structure ~\n";
		s += KnowledgeDescriptions.transformRecipe(result);
		s += "       ~ Creation ~\n\n";
		s += KnowledgeDescriptions.transformOutput(result);
        
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
