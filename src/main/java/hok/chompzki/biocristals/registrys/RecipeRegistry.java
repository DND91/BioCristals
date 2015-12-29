package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.items.token.recipes.AttuneEaterRecipe;
import hok.chompzki.biocristals.items.token.recipes.AttuneFeederRecipe;
import hok.chompzki.biocristals.items.token.recipes.AttuneNetworkRecipe;
import hok.chompzki.biocristals.items.token.recipes.AttuneTokenRecipe;
import hok.chompzki.biocristals.recipes.CrootManager;
import hok.chompzki.biocristals.recipes.CrootRecipeContainer;
import hok.chompzki.biocristals.recipes.CrootRecipeData;
import hok.chompzki.biocristals.recipes.OreDictContainer;
import hok.chompzki.biocristals.recipes.PurifierContainer;
import hok.chompzki.biocristals.recipes.PurifierData;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.recipes.RecipeData;
import hok.chompzki.biocristals.recipes.RecipePurifier;
import hok.chompzki.biocristals.recipes.RecipeTransformer;
import hok.chompzki.biocristals.recipes.ShapedOreCrootRecipe;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerResearchStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	public static List<RecipeContainer> recipes = new ArrayList<RecipeContainer>();
	public static List<CrootRecipeContainer> crootRecipes = new ArrayList<CrootRecipeContainer>();
	
	public static List<PurifierContainer> purifierContainers = new ArrayList<PurifierContainer>();
	public static Map<Item, List<RecipePurifier>> purifierRecipes = new HashMap<Item, List<RecipePurifier>>();
	public static Map<String, RecipePurifier> nameToPurifierRecipes = new HashMap<String, RecipePurifier> ();
	
	public static void register(RecipePurifier pur){
		ItemStack filter = pur.filter();
		if(!purifierRecipes.containsKey(filter.getItem()))
			purifierRecipes.put(filter.getItem(), new ArrayList<RecipePurifier>());
		List<RecipePurifier> lst = purifierRecipes.get(filter.getItem());
		lst.add(pur);
		nameToPurifierRecipes.put(pur.name(), pur);
	}
	
	public static RecipePurifier getRecipePurifier(String name){
		return nameToPurifierRecipes.get(name);
	}
	
	public static RecipePurifier getRecipePurifier(List<ItemStack> filters, IInventory[] inputs){
		for(ItemStack filter : filters){
			if(purifierRecipes.containsKey(filter.getItem())){
				List<RecipePurifier> lst = purifierRecipes.get(filter.getItem());
				for(RecipePurifier recp : lst){
					if(recp.affords(inputs))
						return recp;
				}
			}
		}
		
		return null;
	}
	
	public void registerRecipes(){
		load();
		loadCroot();
		loadPurifing();
		
		
	}
	
	public static void load(){
		for(RecipeData data : ConfigRegistry.recipeData){
			
			
			ItemStack output = RecipeTransformer.dataToItemStack(data.output, true).get(0);
			
			Object[] inputs = RecipeTransformer.dataToObjects(data.input);
			Object[] trueInput = RecipeTransformer.stacksToRecipe(inputs);
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("INPUT: ");
			for(Object in : data.input)
				System.out.println(in.toString());
			System.out.println("---");
			for(int i = 0; i < inputs.length; i++)
				System.out.println(inputs[i] == null ? "null" : inputs[i].toString());
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output + " -> " + (output == null ? "NULL" : output.toString()));
			
			System.out.println("---------------- TRUE INPUT -----------------");
			for(int i = 0; i < trueInput.length; i++)
				System.out.println(trueInput[i]);
			
			recipes.add(new RecipeContainer(data.code ,output, trueInput));
		}
		
		for(RecipeContainer con : recipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(con.output, con.input));
		}
		
		
		GameRegistry.addRecipe(new AttuneNetworkRecipe());
		GameRegistry.addRecipe(new AttuneTokenRecipe());
		GameRegistry.addRecipe(new AttuneFeederRecipe());
		GameRegistry.addRecipe(new AttuneEaterRecipe());
	}
	
	public static void loadCroot(){
		for(CrootRecipeData data : ConfigRegistry.crootData){
			ItemStack output = RecipeTransformer.dataToItemStack(data.output, true).get(0);
			
			Object[] inputs = RecipeTransformer.dataToObjects(data.input);
			Object[] trueInput = RecipeTransformer.stacksToRecipe(inputs);
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("INPUT: " + data.input);
			for(int i = 0; i < inputs.length; i++)
				System.out.println(inputs[i] == null ? "null" : inputs[i].toString());
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output + " -> " + (output == null ? "NULL" : output.toString()));
			
			System.out.println("---------------- TRUE INPUT -----------------");
			for(int i = 0; i < trueInput.length; i++)
				System.out.println(trueInput[i] == null || (trueInput[i] instanceof ItemStack && ((ItemStack) trueInput[i]).getItem() == null) ? "null" : trueInput[i]);
			
			crootRecipes.add(new CrootRecipeContainer(data.code ,output, trueInput));
		}
		
		for(CrootRecipeContainer con : crootRecipes){
			CrootManager.instance().register(new ShapedOreCrootRecipe(con.output, con.code, con.input));
		}
	}
	
	public static RecipeContainer getRecipreFor(ItemStack output){
		for(RecipeContainer con : recipes){
			if(con.output.isItemEqual(output)){
				return con;
			}
		}
		return null;
	}
	
	public static void loadPurifing(){
		for(PurifierData data : ConfigRegistry.purifierData){
			
			ItemStack filter = RecipeTransformer.dataToItemStack(data.filter, true).get(0);
			ItemStack[] outputs = RecipeTransformer.dataToItemStacks(data.output.split(" "));
			Object[] inputs = RecipeTransformer.dataToObjects(data.input.split(" "));
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("CODE: " + data.code);
			System.out.println("TIME: " + data.time);
			System.out.println("FILTER: " + (filter == null ? "null" : filter.toString()));
			System.out.println("INPUT: " + data.input);
			for(int i = 0; i < inputs.length; i++)
				System.out.println(inputs[i] == null ? "null" : inputs[i].toString());
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output);
			for(int i = 0; i < outputs.length; i++)
				System.out.println(outputs[i] == null ? "null" : outputs[i].toString());
			
			purifierContainers.add(new PurifierContainer(data.name, filter, data.code, inputs, outputs, data.time));
		}
		
		for(PurifierContainer con : purifierContainers){
			register(new RecipePurifier(con.name, con.filter, con.code, con.input, con.output, con.time));
		}
	}
	
	public static CrootRecipeContainer getCrootRecipreFor(ItemStack output) {
		for(CrootRecipeContainer con : crootRecipes){
			if(con.output.isItemEqual(output)){
				return con;
			}
		}
		return null;
	}
	
}
