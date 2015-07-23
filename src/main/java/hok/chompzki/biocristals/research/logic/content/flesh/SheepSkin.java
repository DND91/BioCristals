package hok.chompzki.biocristals.research.logic.content.flesh;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.client.GuiCraftRecipe;
import hok.chompzki.biocristals.client.GuiCraftingHelper;
import hok.chompzki.biocristals.client.GuiCristalRecipe;
import hok.chompzki.biocristals.client.GuiFleshRecipe;
import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleContent.EnumContent;
import hok.chompzki.biocristals.research.gui.KnowledgeDescriptions;

public class SheepSkin extends ArticleContent {

	@Override
	public String textOnPage(EnumContent content, int p){
		String s = "";
		switch(p){
		case 0:
			s += "";
			break;
		case 1:
			s += "    ~ Crystallization ~\n";
			s += KnowledgeDescriptions.transformWeakFlesh(new ItemStack(ItemRegistry.bioReagent), new EntitySheep(Minecraft.getMinecraft().theWorld), new ItemStack(ItemRegistry.attuner));
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.transformOutput(new ItemStack(Blocks.wool));
			s += "\n";
			s += "1. Place Sulphur Tuft\n";
			s += "2. Throw biological reagent\n";
			s += "3. Trap creature\n";
			s += "4. Hold and release attuner on creature\n";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}

	@Override
	public GuiCraftingHelper getFaved() {
		return new GuiFleshRecipe(Minecraft.getMinecraft(), code, new ItemStack(ItemRegistry.bioReagent), new EntitySheep(Minecraft.getMinecraft().theWorld), new ItemStack(ItemRegistry.attuner), new ItemStack(Blocks.wool));
	}
}
