package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.api.IToken;
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
import net.minecraftforge.oredict.OreDictionary;

public class GuiToken extends GuiCraftingHelper {

	String displayName = "NONE SOTIS";
	ItemStack stack;
	
	
	public GuiToken(Minecraft minecraft, ItemStack stack, String code) {
		super(minecraft, ReserchDataNetwork.instance().getResearch(code));
		if(code.equals("NONE"))
			return;
		this.stack = stack;
		this.displayName = stack.getDisplayName();
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		super.drawGui(currentScreen, mc, world, player, currentStack, mouseX, mouseY, renderPartialTicks);
		float scale = 1.75f;
		String s = displayName;
		this.articleFontRenderer.setScale(scale*xScale);
        this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 10, getWidth()-20, 0);
        
		IToken token = (IToken)stack.getItem();
		switch(token.getType(stack)){
		case FEEDER:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Feeder type is used to supply The Hunger with food. "
			  +  "Through Sacrifce Pit.";
			break;
		case EATER:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Eater type is used to take food from The Hunger. "
				+"Can be used in inventory, machines and sacrifice pit.";
			break;
		case BANK:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Bank type is used to store resources inside and outside The Hunger. "
				+"Can be used in inventory, machines and sacrifice pit.";
			break;
		case BRIDGE:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Bridge type is used to connect tokens inside The Hunger. ";
			break;
		case FILTER:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Filter type is used to filter resources that flows over"
			  +  " connections between tokens inside The Hunger. ";
			break;
		case TRANSFORMER:
			s = KnowledgeDescriptions.transformOutput(stack) + "\n\n";
			s += "The Transformer type is used to process one resource into another inside The Hunger.\n";
			s += "Tier 0: Raw Food, Waste.\n";
			s += "Tier 1: Biomass.\n";
			s += "Tier 2: Psy. Energy, Nuritment, Life Fluids.\n";
			break;
		}
        
        
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
