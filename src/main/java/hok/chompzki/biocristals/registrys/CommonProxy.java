package hok.chompzki.biocristals.registrys;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import hok.chompzki.biocristals.BioCristalChunkloadCallback;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.GuiHandler;
import hok.chompzki.biocristals.croot.TreeStorage;
import hok.chompzki.biocristals.croot_old.CrootRegistry;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.StorageHandler;
import hok.chompzki.biocristals.research.events.CraftingEvents;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;


public class CommonProxy { //Server sided

	public void preInit(FMLPreInitializationEvent event) {
		ConfigRegistry.preinit(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigRegistry());
		MinecraftForge.EVENT_BUS.register(new StorageHandler());
		FMLCommonHandler.instance().bus().register(new CraftingEvents());
		FMLCommonHandler.instance().bus().register(PlayerStorage.instance(false));
		ItemRegistry items = new ItemRegistry();
		items.registerItems();
		BlockRegistry blocks = new BlockRegistry();
		blocks.registerBlocks();
		TileEntityRegistry tileEntity = new TileEntityRegistry();
		tileEntity.registerTileEntities();
		CristalRegistry.registerAll();
		
		ReserchRegistry research = new ReserchRegistry();
		research.preInit(event);
	}
	
	
	public void init(FMLInitializationEvent event) {
    	RecipeRegistry recipes = new RecipeRegistry();
    	recipes.registerRecipes();
    	NetworkRegistry.INSTANCE.registerGuiHandler(BioCristalsMod.instance, new GuiHandler());
    	
    	ReserchRegistry research = new ReserchRegistry();
		research.init(event);
    	
    	VillagerBank bank = new VillagerBank();
    	bank.init(event);
    	CrootRegistry croot = new CrootRegistry();
    	croot.register();
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		TreeStorage.instance();
	}


	public void initSaveHandling() {
		PlayerStorage.instance(true);
	}
	
	
}
