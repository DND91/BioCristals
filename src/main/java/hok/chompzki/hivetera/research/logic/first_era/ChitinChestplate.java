package hok.chompzki.hivetera.research.logic.first_era;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

public class ChitinChestplate extends ArticleContent {
	
	private ItemStack stack = null;
	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(stack == null)
			stack = new ItemStack(ItemRegistry.chitinChestplate);
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.chitinChestplate));
		String s = "";
		switch(p){
		case 0:
			s += "Nomads fear the night as undeads rise under the waxing moon. "
			  +  "To protect their chests they combined chitin plates to make a chestplate. "
			  +  "It is said that the first chestplate was made by letting ocelots play "
			  +  "with Clay Hunters. Some speculate that cats, for this reason, was the worlds first "
			  +  "mummies.";
			break;
		case 1: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += KnowledgeDescriptions.getDisplayName(con) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(con);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(con);
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 2;
	}
}
