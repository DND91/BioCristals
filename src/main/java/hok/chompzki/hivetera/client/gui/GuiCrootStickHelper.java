package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.BookParseTree;
import hok.chompzki.hivetera.client.book.parseNodes.Cursor;
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

public class GuiCrootStickHelper extends GuiCraftingHelper {

	private Article arc = null;
	
	public GuiCrootStickHelper(Minecraft minecraft, String code, ItemStack tool, ItemStack insect, ItemStack... places) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		if(code.equals("NONE"))
			return;

		arc = BookParseTree.articles.get(code);
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		
		arc.mainBody.draw(new Cursor(), 0, (int) (this.xPosition + 10 * scale * xScale), (int) (this.yPosition + 20), this, articleFontRenderer, true, scale * xScale);
		arc.mainBody.drawOverlay(new Cursor(), 0, (int) (this.xPosition + 10 * scale * xScale), (int) (this.yPosition + 20), this, articleFontRenderer, true, scale * xScale, mouseX, mouseY);
		
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
