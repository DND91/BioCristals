package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.recipes.CrootRecipeContainer;
import hok.chompzki.hivetera.recipes.PurifierContainer;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.TransformerContainer;
import hok.chompzki.hivetera.recipes.TransformerEntityContainer;
import hok.chompzki.hivetera.registrys.CristalRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
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
		INestInsect insect = (INestInsect)stack.getItem();
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
