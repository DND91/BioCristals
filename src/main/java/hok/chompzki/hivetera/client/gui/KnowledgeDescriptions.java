package hok.chompzki.hivetera.client.gui;

import java.awt.event.ItemListener;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.hivetera.recipes.CrootRecipeContainer;
import hok.chompzki.hivetera.recipes.OreDictContainer;
import hok.chompzki.hivetera.recipes.PurifierContainer;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.recipes.TransformerContainer;
import hok.chompzki.hivetera.recipes.TransformerEntityContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.CristalRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class KnowledgeDescriptions {
	public static String transformRecipe(RecipeContainer container){
		String s = "\t\n";
		for(int y = 0; y < container.craftingGrid[0].length; y++){
			s += "\f";
			for(int x = 0; x < container.craftingGrid[0].length; x++){
				s += "<";
				s += container.getString(y*container.craftingGrid[0].length + x);
				s += ">";
			}
			s += "\n\n";
		}
		return s + "\t";
	}
	
	public static String transformOutput(ItemStack stack){
		String s = "";
		s += "\t\f";
		s += transformItemStack(new ItemStack(Blocks.air), false);
		s += transformStrictItemStack(stack, false);
		return s + "\t";
	}
	
	public static String transformItemStack(ItemStack stack, boolean withTT){
		String s = "";
		if(withTT)
			s += "\t\f";
		s += "<";
		String name = transformName(stack);
		String meta = "";
		meta = ""+(stack.getItem() == null ? 0 : stack.getItemDamage());
		s += (stack.stackSize <= 0 ? 1 : stack.stackSize) + "x" + name + ":" + meta;
		s += ">";
		
		return s +  (withTT ? "\t" : "");
	}
	
	public static String transformStrictItemStack(ItemStack stack, boolean withTT){
		String s = "";
		if(withTT)
			s += "\t\f";
		s += "<";
		s += stack.stackSize + "x" + GameData.getItemRegistry().getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		s += ">";
		
		return s +  (withTT ? "\t" : "");
	}
	
	public static String transformOreCon(OreDictContainer con, boolean withTT){
		String s = "";
		if(withTT)
			s += "\t\f";
		s += "<";
		String name = con.oreName;
		s += (con.quantity <= 0 ? 1 : con.quantity) + "x" + name;
		s += ">";
		
		return s +  (withTT ? "\t" : "");
	}
	
	public static String getName(ItemStack stack){
		if(stack == null || stack.getItem() == null)
			return "empty";
		String s = "Unknown";
		int[] stacks = OreDictionary.getOreIDs(stack);
		if(0 < stacks.length){
			s = OreDictionary.getOreName(stacks[0]);
		}
		if(s.equals("Unknown")){
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block == Blocks.air){
				s = GameRegistry.findUniqueIdentifierFor(stack.getItem()).toString();
			}else{
				s = GameRegistry.findUniqueIdentifierFor(block).toString();
			}
		}
		return s;
	}
	
	/**
	 *	tile.sapling.oak
	 *	item.BioCristals_itemBioReagent
	 *	#INPUT
	 *	minecraft:sapling
	 *	BioCristals:itemBioReagent
	 */
	public static String transformName(ItemStack stack){
		String uname = getName(stack);
		
		/*String domain = "minecraft";
		String name = "";
		if(uname.startsWith("item.")){
			uname = uname.replaceFirst("item\\.", "");
		}else if(uname.startsWith("tile.")){
			uname = uname.replaceFirst("tile\\.", "");
		}
		
		if(uname.contains("_")){
			domain = uname.split("_")[0];
			name = uname.split("_")[1];
		}else if(uname.contains(".")){
			name = uname.split("\\.")[0];
		}else{
			name = uname;
		}*/
		
		return uname;
	}
	
	public static String transformWeakCristal(Block block){
		if(block == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		TransformerContainer found = null;
		for(TransformerContainer con : CristalRegistry.transformationContainer){
			if(Block.getBlockFromItem(con.output.getItem()) == block){
				found = con;
				break;
			}
		}
		if(found == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		
		return transformWeakCristal(found);
	}
	
	private static String transformWeakCristal(TransformerContainer found) {
		ItemStack air = new ItemStack(Blocks.air);
		ItemStack reagent = new ItemStack(ItemRegistry.bioReagent);
		ItemStack activator = new ItemStack(ItemRegistry.attuner);
		ItemStack base = new ItemStack(BlockRegistry.biomass);
		String s = "\t\n\f";
		s += transformItemStack(air, false) + "<" + found.getInputString() + ">" + transformItemStack(air, false) + "\n\n";
		s += "\f" + transformItemStack(reagent, false) + transformItemStack(base, false) + transformItemStack(activator, false) + "\n\n";
		return s + "\t";
	}

	public static String transformWeakFlesh(EntityLivingBase base){
		String reagent = transformItemStack(new ItemStack(ItemRegistry.bioReagent), false);
		String act = transformItemStack(new ItemStack(ItemRegistry.attuner), false);
		String air = transformItemStack(new ItemStack(Blocks.air), false);
		String tuft = transformItemStack(new ItemStack(BlockRegistry.sulphurTuft), false);
		
		String s = "\r\n\n\f";
		s += "<" + EntityList.getEntityString(base) + ">";
		s += "\r\n";
		
		s += "\t\n\f";
		s += reagent + tuft + act + "\n\n";
		return s + "\t";
	}
	
	public static String transformWeakFlesh(Class base){
		String reagent = transformItemStack(new ItemStack(ItemRegistry.bioReagent), false);
		String act = transformItemStack(new ItemStack(ItemRegistry.attuner), false);
		String air = transformItemStack(new ItemStack(Blocks.air), false);
		String tuft = transformItemStack(new ItemStack(BlockRegistry.sulphurTuft), false);
		
		String s = "\r\n\n\f";
		s += "<" + EntityList.classToStringMapping.get(base) + ">";
		s += "\r\n";
		
		s += "\t\n\f";
		s += reagent + tuft + act + "\n\n";
		return s + "\t";
	}
	
	public static String getWeakFlesh(Class cls){
		if(cls == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		TransformerEntityContainer found = null;
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.input == cls){
				found = con;
				break;
			}
		}
		if(found == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		return transformWeakFlesh(found.input);
	}
	
	public static String transformOutput(Class cls){
		if(cls == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		TransformerEntityContainer found = null;
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.input == cls){
				found = con;
				break;
			}
		}
		if(found == null)
			return transformItemStack(new ItemStack(Blocks.command_block) , true);
		
		String s = "\t\f";
		for(ItemStack stack : found.output){
			s += transformStrictItemStack(stack, false);
		}
		return s + "\t";
	}
	
	public static String getDisplayName(RecipeContainer con){
		if(con == null)
			return "NONE";
		
		return con.output.getDisplayName() + "\n[Crafting]";
	}
	
	public static String getStructure(RecipeContainer con){
		if(con == null)
			return "NONE";
		
		return KnowledgeDescriptions.transformRecipe(con);
	}

	public static String getResult(RecipeContainer con){
		if(con == null)
			return "NONE";
		
		return KnowledgeDescriptions.transformOutput(con.output);
	}
}
