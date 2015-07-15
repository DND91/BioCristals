package hok.chompzki.biocristals.research.gui;

import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import net.minecraft.init.Blocks;
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
		s += name + "|" + (stack.getItem() == null ? 0 : stack.getItemDamage());
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
}
