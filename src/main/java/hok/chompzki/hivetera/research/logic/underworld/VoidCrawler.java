package hok.chompzki.hivetera.research.logic.underworld;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.BreedingRecipe;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.BreedingRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

public class VoidCrawler extends ArticleContent {

	private ItemStack stack = null;
	private List<String> breeding = new ArrayList<String>();
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.voidCrawler);
		if(breeding != null && breeding.size() == 0){
			for(BreedingRecipe rep : BreedingRegistry.list){
				if(OreDictionary.itemMatches(rep.result, stack, true)){
					String s = "";
					s += "   Will eat " + rep.foodNeed + " " + I18n.format("container."+((IInsect)rep.result.getItem()).getFoodType(rep.result), new Object[0]) + ".\n\n";
					s += "         ~ PARENTS ~   \n\n";
					s += "\t\f" + KnowledgeDescriptions.transformItemStack(new ItemStack((Item)rep.pappa), false);
					s += KnowledgeDescriptions.transformItemStack(rep.nestMaterial, false);
					s += KnowledgeDescriptions.transformItemStack(new ItemStack((Item)rep.mamma), false) + "\t\n\n";
					s += "           ~ BABY~   \n\n";
					s += "\t\f" + KnowledgeDescriptions.transformItemStack(new ItemStack(Blocks.air), false) + KnowledgeDescriptions.transformItemStack(rep.result, false) + "\t\n\n";
					
					breeding.add(s);
				}
			}
		}
		
		
		String s = "";
		switch(p){
		case 0:
			s += "The Void Crawler is a must for any underworld explorer. "
				+"When used it will displace none-ore blocks into another dimenstion, "
				+"chests and so on will still be left. The insect will help you find "
				+"any ores. ";
			break;
		default:
			s = breeding.get(p-1);
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 1 + this.breeding.size();
	}

}
