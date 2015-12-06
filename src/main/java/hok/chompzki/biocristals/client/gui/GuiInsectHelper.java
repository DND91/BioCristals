package hok.chompzki.biocristals.client.gui;

import hok.chompzki.biocristals.api.IInsect;
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

public class GuiInsectHelper extends GuiCraftingHelper {

	String displayName = "NONE";
	String structure = "";
	String result = "";
	
	public GuiInsectHelper(Minecraft minecraft, String code, ItemStack stack) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		
		displayName = stack.getDisplayName() + "\n[Nesting]";
		result = "" + KnowledgeDescriptions.transformStrictItemStack(stack, true);
		structure += "";
		IInsect insect = (IInsect)stack.getItem();
		structure += insect.getActionText(null, stack);
		structure += "\n";
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = displayName;
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, getWidth()-20, 0);
		
        s = "";
        s += "  ~ Insect ~\n\n";
        s += result;
        s += "\n  ~ Does ~\n\n";
		s += structure;
		
		
		
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
