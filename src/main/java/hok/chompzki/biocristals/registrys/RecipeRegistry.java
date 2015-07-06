package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.recipes.RecipeData;
import hok.chompzki.biocristals.recipes.RecipePurifier;
import hok.chompzki.biocristals.recipes.RecipeTransformer;

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
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	public static List<RecipeContainer> recipes = new ArrayList<RecipeContainer>();
	public static Map<Item, List<RecipePurifier>> purifierRecipes = new HashMap<Item, List<RecipePurifier>>();
	
	public static void register(RecipePurifier pur){
		ItemStack filter = pur.filter();
		if(!purifierRecipes.containsKey(filter.getItem()))
			purifierRecipes.put(filter.getItem(), new ArrayList<RecipePurifier>());
		List<RecipePurifier> lst = purifierRecipes.get(filter.getItem());
		lst.add(pur);
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
		
		register(new RecipePurifier(new ItemStack(ItemRegistry.bioReagent, 8), new ItemStack(Items.stick), new ItemStack(Items.wheat, 4), new ItemStack(Items.slime_ball), new ItemStack(Items.potato), new ItemStack(Blocks.dirt)));
		register(new RecipePurifier(new ItemStack(BlockRegistry.biomass, 1), new ItemStack(Items.stick), new ItemStack(ItemRegistry.bioReagent, 8), new ItemStack(Blocks.dirt, 1)));
	}
	
	public static void load(){
		for(RecipeData data : ConfigRegistry.recipeData){
			
			
			ItemStack output = RecipeTransformer.dataToItemStack(data.output);
			if(output != null)
				output.stackSize = data.quantity;
			ItemStack[] inputs = RecipeTransformer.dataToItemStacks(data.input.split("__"));
			Object[] trueInput = RecipeTransformer.stacksToRecipe(inputs);
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("INPUT: " + data.input);
			for(int i = 0; i < inputs.length; i++)
				System.out.println(inputs[i] == null ? "null" : inputs[i].toString());
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output + " -> " + (output == null ? "NULL" : output.toString()));
			System.out.println("QUANTITY: " + data.quantity);
			
			System.out.println("---------------- TRUE INPUT -----------------");
			for(int i = 0; i < trueInput.length; i++)
				System.out.println(trueInput[i]);
			
			recipes.add(new RecipeContainer(output, trueInput));
		}
		
		for(RecipeContainer con : recipes){
			GameRegistry.addRecipe(new ShapedOreRecipe(con.output, con.input));
		}
	}
	
}
