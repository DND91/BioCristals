package hok.chompzki.biocristals;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import hok.chompzki.biocristals.croot.power.TreeStorage;
import hok.chompzki.biocristals.recipes.RecipeTransformer;
import hok.chompzki.biocristals.registrys.BioEntityRegistry;
import hok.chompzki.biocristals.registrys.BiomeRegistry;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.registrys.CristalRegistry;
import hok.chompzki.biocristals.registrys.GuiHandler;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.registrys.TileEntityRegistry;
import hok.chompzki.biocristals.registrys.VillagerBank;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.events.GameEvents;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;


public class CommonProxy {
	
	public static final String oreBiomaterial = "bioMaterial";

	public void preInit(FMLPreInitializationEvent event) {
		ConfigRegistry.preinit(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigRegistry());
		MinecraftForge.EVENT_BUS.register(new StorageHandler());
		FMLCommonHandler.instance().bus().register(new GameEvents());
		MinecraftForge.EVENT_BUS.register(new GameEvents());
		FMLCommonHandler.instance().bus().register(PlayerStorage.instance(false));
		
		ItemRegistry items = new ItemRegistry();
		items.registerItems();
		BlockRegistry blocks = new BlockRegistry();
		blocks.registerBlocks();
		
		for(String ore : ConfigRegistry.oreDictBioMaterial){
			List<ItemStack> list = OreDictionary.getOres(ore);
			for(ItemStack stack : list){
				OreDictionary.registerOre(oreBiomaterial, stack);
			}
			
			ItemStack stack = RecipeTransformer.dataToItemStack(ore, true).get(0);
			System.out.println("ORE DICT: " + ore + ", " + (stack == null || stack.getItem() == null ? "NULL" : stack));
			OreDictionary.registerOre(oreBiomaterial, stack);
		}
		
		TileEntityRegistry tileEntity = new TileEntityRegistry();
		tileEntity.registerTileEntities();
		CristalRegistry.registerAll();
		ReserchRegistry research = new ReserchRegistry();
		research.preInit(event);
		
		MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.crootBeetle), ConfigRegistry.crootBeetleChance);
		
		BioEntityRegistry er = new BioEntityRegistry();
		er.preInit(event);
	}
	
	
	public void init(FMLInitializationEvent event) {
    	RecipeRegistry recipes = new RecipeRegistry();
    	recipes.registerRecipes();
    	NetworkRegistry.INSTANCE.registerGuiHandler(BioCristalsMod.instance, new GuiHandler());
    	
    	ReserchRegistry research = new ReserchRegistry();
		research.init(event);
    	
    	VillagerBank bank = new VillagerBank();
    	bank.init(event);
    	
    	BiomeRegistry biome = new BiomeRegistry();
    	biome.init(event);
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		TreeStorage.instance();
		ItemRegistry items = new ItemRegistry();
		items.setupFilters();
	}


	public void initSaveHandling() {
		PlayerStorage.instance(true);
	}
	
	
}
