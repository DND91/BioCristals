package hok.chompzki.biocristals.research.logic.settlement;

import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary.Type;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.ArticleContent.EnumContent;
import hok.chompzki.biocristals.client.gui.ArticleFontRenderer;
import hok.chompzki.biocristals.client.gui.GuiCraft;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.data.BiomeKittehData;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.BiomeRegistry;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;

public class NestingKittehBettle extends ArticleContent {

	private ItemStack stack = null;
	private ItemStack stack2 = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(stack == null)
			stack = new ItemStack(ItemRegistry.kittehBeetle);
		if(stack2 == null)
			stack2 = new ItemStack(BlockRegistry.nest);
		
		String s = "";
		switch(p){
		case 0:
			s += "\n\n"+KnowledgeDescriptions.transformOutput(stack) + "\n";
			s += "The Kitteh Bettle secretes a melon sweet scent that attract "
			   + "bugs. The nomads used it in their settelment to easier collect "
			   + "insects needed for survival. Depending on the biome the bettle "
			   + "will attract diffrent bugs. If she dosn't like the enviorment "
			   + "she will crawl out of the nest after a while. \n";
			s += "\n\n"+KnowledgeDescriptions.transformOutput(stack2) + "\n";
			break;
		default:
			int i = 0;
			Double oldV = 0.0D;
			for(Entry<Type, BiomeKittehData> entry : BiomeRegistry.kittehsBiomes.entrySet()){
				if(i == p){
					Minecraft minecraft = Minecraft.getMinecraft();
					ArticleFontRenderer font = new ArticleFontRenderer(minecraft, minecraft.gameSettings, BioCristalsMod.MODID + ":textures/font/ascii.png", minecraft.renderEngine, false);
					font.setScale(0.8f);
					
					Type biome = entry.getKey();
					BiomeKittehData data = entry.getValue();
					
					s += "Biome: " + biome.name() + "\n";
					s += "Chance & Item\n\n";
					for(Entry<Double, ItemStack> entry2 : data.entrySet()){
						Double v = entry2.getKey();
						ItemStack stack = entry2.getValue();
						
						String row = Math.round(((v-oldV) / data.getTotal()) * 100.D) + " & " + (stack == null ? "NOTHING" : "\t" + KnowledgeDescriptions.transformStrictItemStack(stack, false) + "\t");
						
						oldV = v;
						
						row += "\n\n";
						s += row;
						
					}
					//s += "\n*RCW = Random Chance Weight\n";
					font.setScale(1.0f);
				}
				i++;
			}
		}
		return s;
	}
	
	@Override
	public int numberOfPages(EnumContent content){
		return 0 + BiomeRegistry.kittehsBiomes.size();
	}

}
