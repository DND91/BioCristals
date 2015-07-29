package hok.chompzki.biocristals.research.gui;

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
import net.minecraft.item.ItemStack;

public class KnowledgeDescriptions {
	//s += "\n\t\n<"+log+"|2><"+dirt+"><"+log+"|2>\n";
	//s += "\n<"+log+"|4><"+log+"|1><"+log+"|6>\t";
	public static String transformRecipe(ItemStack output){
		String s = "\t\n";
		RecipeContainer con = RecipeRegistry.getRecipreFor(output);
		for(int y = 0; y < con.craftingGrid[0].length; y++){
			s += "\f";
			for(int x = 0; x < con.craftingGrid[0].length; x++){
				
				ItemStack slot = con.idToItem.get(con.craftingGrid[y][x]);
				if(slot == null || slot.getItem() == null)
					slot = new ItemStack(Blocks.air);
				s += transformItemStack(slot, false);
			}
			s += "\n\n";
		}
		return s + "\t";
	}
	
	public static String transformOutput(ItemStack stack){
		String s = "";
		s += "\t\f";
		s += transformItemStack(new ItemStack(Blocks.air), false);
		s += transformItemStack(stack, false);
		return s + "\t";
	}
	
	public static String transformItemStack(ItemStack stack, boolean withTT){
		String s = "";
		if(withTT)
			s += "\t\f";
		s += "<";
		String name = transformName(stack);
		s += (stack.stackSize <= 0 ? 1 : stack.stackSize) + "x" + name + "|" + (stack.getItem() == null ? 0 : stack.getItemDamage());
		s += ">";
		
		return s +  (withTT ? "\t" : "");
	}
	/**
	 *	tile.sapling.oak
	 *	item.BioCristals_itemBioReagent
	 *	#INPUT
	 *	minecraft:sapling
	 *	BioCristals:itemBioReagent
	 */
	public static String transformName(ItemStack stack){
		
		String uname = stack.getItem() == null ? "empty" : stack.getUnlocalizedName();
		String domain = "minecraft";
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
		}
		
		return domain + ":" + name;
	}
	
	private static String transformWeakCristal(ItemStack substance, ItemStack reagent, ItemStack base, ItemStack activator){
		ItemStack air = new ItemStack(Blocks.air);
		String s = "\t\n\f";
		s += transformItemStack(air, false) + transformItemStack(substance, false) + transformItemStack(air, false) + "\n\n";
		s += "\f" + transformItemStack(reagent, false) + transformItemStack(base, false) + transformItemStack(activator, false) + "\n\n";
		return s + "\t";
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
		
		return transformWeakCristal(found.input, new ItemStack(ItemRegistry.bioReagent), new ItemStack(BlockRegistry.biomass), new ItemStack(ItemRegistry.attuner));
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
			s += transformItemStack(stack, false);
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
				return KnowledgeDescriptions.transformRecipe(con.output);
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
				for(ItemStack stack : con.input){
					structure += KnowledgeDescriptions.transformItemStack(stack, false);
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
