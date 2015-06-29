package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	
	public void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.attuner, 1), new Object[]{
	    	"STS",
	    	"T T",
	    	"SOS",
	    	'S', Items.stick, 'T', Items.string, 'O', Blocks.sapling
	    	});
		
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.bioReagent, 4), new Object[]{
	    	"STS",
	    	"TDT",
	    	"STS",
	    	'S', Items.stick, 'T', Blocks.sapling, 'D', Blocks.dirt
	    	});
		
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.collector, 1), new Object[]{
	    	"W W",
	    	"W W",
	    	"TOT",
	    	'W', Blocks.log, 'T', Items.string, 'O', Blocks.sapling
	    	});
		
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.biomass, 4), new Object[]{
	    	"FOF",
	    	"OWO",
	    	"FOF",
	    	'F', Items.apple, 'W', Blocks.log, 'O', Blocks.sapling
	    	});
	}
	
}
