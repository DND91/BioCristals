package hok.chompzki.biocristals.registrys;

import net.minecraftforge.common.MinecraftForge;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.GuiHandler;
import hok.chompzki.biocristals.tutorials.data.StorageHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;


public class CommonProxy { //Server sided

	public void preInit(FMLPreInitializationEvent event) {
		ConfigRegistry.preinit(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigRegistry());
		//FMLCommonHandler.instance().bus().register(new StorageHandler());
		MinecraftForge.EVENT_BUS.register(new StorageHandler());
		
		ItemRegistry items = new ItemRegistry();
		items.registerItems();
		BlockRegistry blocks = new BlockRegistry();
		blocks.registerBlocks();
		TileEntityRegistry tileEntity = new TileEntityRegistry();
		tileEntity.registerTileEntities();
		CristalRegistry.registerAll();
		
		TutorialRegistry turt = new TutorialRegistry();
		turt.preInit(event);
	}

    
	public void init(FMLInitializationEvent event) {
    	RecipeRegistry recipes = new RecipeRegistry();
    	recipes.registerRecipes();
    	NetworkRegistry.INSTANCE.registerGuiHandler(BioCristalsMod.instance, new GuiHandler());
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
	
}
