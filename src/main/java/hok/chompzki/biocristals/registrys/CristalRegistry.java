package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.api.IEntityTransformation;
import hok.chompzki.biocristals.api.ITransformation;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.recipes.RecipeData;
import hok.chompzki.biocristals.recipes.RecipeTransformer;
import hok.chompzki.biocristals.recipes.TransformData;
import hok.chompzki.biocristals.recipes.TransformEntityData;
import hok.chompzki.biocristals.recipes.TransformerContainer;
import hok.chompzki.biocristals.recipes.TransformerEntityContainer;
import hok.chompzki.biocristals.transformation.WeakCristalTransformation;
import hok.chompzki.biocristals.transformation.WeakFleshTransformation;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CristalRegistry {
	
	public static ArrayList<TransformerEntityContainer> transformationEntityContainer = new ArrayList<TransformerEntityContainer>();
	private static ArrayList<IEntityTransformation> entityTransformations = new ArrayList<IEntityTransformation>();
	
	public static ArrayList<TransformerContainer> transformationContainer = new ArrayList<TransformerContainer>();
	private static ArrayList<ITransformation> transformations = new ArrayList<ITransformation>();
	
	public static void register(IEntityTransformation trans){
		entityTransformations.add(trans);
	}
	
	public static IEntityTransformation get(ItemStack stack, EntityPlayer player, World world, Entity target){
		for(IEntityTransformation trans : entityTransformations){
			if(trans.hasResources(stack, player, target) && trans.canTransform(stack, player, world, target)){
				return trans;
			}
		}
		return null;
	}
	
	public static TransformerEntityContainer get(Class cls){
		if(cls == null)
			return null;
		TransformerEntityContainer found = null;
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.input == cls){
				found = con;
				break;
			}
		}
		return found;
	}
	
	
	
	public static void register(ITransformation struct){
		transformations.add(struct);
	}
	
	public static ITransformation get(ItemStack stack, EntityPlayer player, World world, int x, int y, int z){
		for(ITransformation struct : transformations){
			if(struct.hasResources(stack, player) && struct.canPlaceAt(stack, player, world, x, y, z)){
				return struct;
			}
		}
		return null;
	}
	
	public static void registerAll(){
		loadTransformer();
		loadEntityTransformer();
	}

	private static void loadTransformer() {
		for(TransformData data : ConfigRegistry.transformData){
			ItemStack output = RecipeTransformer.dataToItemStack(data.output);
			ItemStack input = RecipeTransformer.dataToItemStack(data.input);
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("INPUT: " + data.input);
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output + " -> " + (output == null ? "NULL" : output.toString()));
			System.out.println("CODE: " + data.code);
			
			transformationContainer.add(new TransformerContainer(input, output, data.code));
		}
		
		for(TransformerContainer con : transformationContainer){
			CristalRegistry.register(new WeakCristalTransformation(con.input.getItem(), Block.getBlockFromItem(con.output.getItem()), con.code));
		}
	}
	
	private static void loadEntityTransformer() {
		for(TransformEntityData data : ConfigRegistry.transformEntityData){
			Class input = (Class) EntityList.stringToClassMapping.get(data.input);
			ItemStack[] output = RecipeTransformer.dataToItemStacks(data.output.split("__"));
			
			System.out.println("---------------- INPUT -----------------");
			System.out.println("INPUT: " + data.input + " -> " + (input == null ? "NULL" : input.toString()));
			System.out.println("---------------- OUTPUT -----------------");
			System.out.println("OUTPUT: " + data.output + " -> " + (output == null ? "NULL" : output));
			System.out.println("CODE: " + data.code);
			
			transformationEntityContainer.add(new TransformerEntityContainer(input, data.code, output));
		}
		
		for(TransformerEntityContainer con : transformationEntityContainer){
			CristalRegistry.register(new WeakFleshTransformation(con.input, con.code, con.output));
		}
	}
}
