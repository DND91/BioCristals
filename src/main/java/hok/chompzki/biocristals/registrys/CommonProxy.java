package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.structure.FarmingStructures;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy { //Server sided

	public void preInit(FMLPreInitializationEvent event) {
		ItemRegistry items = new ItemRegistry();
		items.registerItems();
		BlockRegistry blocks = new BlockRegistry();
		blocks.registerBlocks();
		FarmingStructures.registerAll();
	}

    
	public void init(FMLInitializationEvent event) {
    	RecipeRegistry recipes = new RecipeRegistry();
    	recipes.registerRecipes();
	}

    
	public void postInit(FMLPostInitializationEvent event) {
    	
	}
	
	
}
