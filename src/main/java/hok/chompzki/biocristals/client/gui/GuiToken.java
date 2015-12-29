package hok.chompzki.biocristals.client.gui;

import hok.chompzki.biocristals.api.IToken;
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
			s += "The Transformer type is used to process one resource into another inside The Hunger. ";
			break;
		}
        
        
		this.articleFontRenderer.setMousePos(mouseX, mouseY);
		this.articleFontRenderer.setScale(scale*xScale);
		this.articleFontRenderer.drawSplitString(s, (int) (this.xPosition + 10 * scale * xScale), this.yPosition + 30, getWidth()-20, 0);
		this.articleFontRenderer.setScale(1.0f);
		
		this.articleFontRenderer.drawMouse(10000, getHeight());
	}
	
}
