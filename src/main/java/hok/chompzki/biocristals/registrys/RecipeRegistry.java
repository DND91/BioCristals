package hok.chompzki.biocristals.registrys;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	
	public static List<RecipeContainer> recipes = new ArrayList<RecipeContainer>();
	
	public void registerRecipes(){
		
		load();
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
