package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
	}
	
}
