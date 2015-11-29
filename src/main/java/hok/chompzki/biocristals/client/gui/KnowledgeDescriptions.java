package hok.chompzki.biocristals.client.gui;

import java.awt.event.ItemListener;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import hok.chompzki.biocristals.recipes.CrootRecipeContainer;
import hok.chompzki.biocristals.recipes.OreDictContainer;
import hok.chompzki.biocristals.recipes.PurifierContainer;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.recipes.TransformerContainer;
import hok.chompzki.biocristals.recipes.TransformerEntityContainer;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.CristalRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
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
	
	public static String getDisplayName(String code){
		if(code.equals("NONE"))
			return "NONE";
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals(code)){
				return con.output.getDisplayName() + "\n[Crafting]";
			}
		}
		
		for(CrootRecipeContainer con : RecipeRegistry.crootRecipes){
			if(con.code.equals(code)){
				return con.output.getDisplayName() + "\n[Croot Crafting]";
			}
		}
		
		for(TransformerContainer con : CristalRegistry.transformationContainer){
			if(con.code.equals(code)){
				return con.output.getDisplayName() + "\n[Attuner]";

			}
		}
		
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.code.equals(code)){
				return EntityList.createEntityByName((String) EntityList.classToStringMapping.get(con.input), Minecraft.getMinecraft().theWorld).getCommandSenderName() + "\n[Tuft]";
			}
		}
		
		for(PurifierContainer con : RecipeRegistry.purifierContainers){
			if(con.code.equals(code)){
				return con.name  + "\n[Purifier]";
			}
		}
		return "NONE";
	}
	
	public static String getStructure(String code){
		if(code.equals("NONE"))
			return "NONE";
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformRecipe(con);
			}
		}
		
		for(CrootRecipeContainer con : RecipeRegistry.crootRecipes){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformRecipe(con);
			}
		}
		
		for(TransformerContainer con : CristalRegistry.transformationContainer){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformWeakCristal(Block.getBlockFromItem(con.output.getItem()));

			}
		}
		
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformWeakFlesh(con.input);
			}
		}
		
		for(PurifierContainer con : RecipeRegistry.purifierContainers){
			if(con.code.equals(code)){
				String structure = "\n\nFilter: \t" + KnowledgeDescriptions.transformItemStack(con.filter, false) + "\t\n\n";
				structure += "Input: \t";
				int y = 0;
				for(Object obj : con.input){
					if (obj instanceof String){
						structure += KnowledgeDescriptions.transformItemStack(new ItemStack(Blocks.air), false);
					} else if (obj instanceof ItemStack){
						structure += KnowledgeDescriptions.transformStrictItemStack((ItemStack)obj, false);
					} else if(obj instanceof OreDictContainer){
						structure += transformOreCon((OreDictContainer)obj, false);
					}
					
					y++;
					if(y % 5 == 0)
						structure += "\n\n\f";
				}
				structure += "\t\n\n";
				return structure;
			}
		}
		return "NONE";
	}

	public static String getResult(String code){
		if(code.equals("NONE"))
			return "NONE";
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformOutput(con.output);
			}
		}
		
		for(CrootRecipeContainer con : RecipeRegistry.crootRecipes){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformOutput(con.output);
			}
		}
		
		for(TransformerContainer con : CristalRegistry.transformationContainer){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformOutput(con.output);

			}
		}
		
		for(TransformerEntityContainer con : CristalRegistry.transformationEntityContainer){
			if(con.code.equals(code)){
				return KnowledgeDescriptions.transformOutput(con.input);
			}
		}
		
		for(PurifierContainer con : RecipeRegistry.purifierContainers){
			if(con.code.equals(code)){
				String result = "\t\f";
				int y = 0;
				for(ItemStack stack : con.output){
					result += KnowledgeDescriptions.transformItemStack(stack, false);
					y++;
					if(y % 5 == 0)
						result += "\n\n";
				}
				result += "\t\n\n";
				return result;
			}
		}
		return "NONE";
	}
}
