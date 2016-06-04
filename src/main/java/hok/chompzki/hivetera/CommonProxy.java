package hok.chompzki.hivetera;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import hok.chompzki.hivetera.client.book.BookParseTree;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.croot.power.TreeStorage;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.registrys.ArmorPatternRegistry;
import hok.chompzki.hivetera.registrys.BioEntityRegistry;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.BreedingRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.CristalRegistry;
import hok.chompzki.hivetera.registrys.DrawbackRegistry;
import hok.chompzki.hivetera.registrys.GuiHandler;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.PotionRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.registrys.TileEntityRegistry;
import hok.chompzki.hivetera.registrys.VillagerBank;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.events.GameEvents;
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
		FMLCommonHandler.instance().bus().register(PlayerResearchStorage.instance(false));
		FMLCommonHandler.instance().bus().register(PlayerHungerStorage.instance(false));
		
		ItemRegistry items = new ItemRegistry();
		items.registerItems();
		BlockRegistry blocks = new BlockRegistry();
		blocks.registerBlocks();
		PotionRegistry potions = new PotionRegistry();
		potions.register();
		
		for(String ore : ConfigRegistry.oreDictBioMaterial){
			List<ItemStack> list = OreDictionary.getOres(ore);
			for(ItemStack stack : list){
				OreDictionary.registerOre(oreBiomaterial, stack);
			}
			
			ItemStack stack = RecipeTransformer.dataToItemStack(ore, true).get(0);
			//System.out.println("ORE DICT: " + ore + ", " + (stack == null || stack.getItem() == null ? "NULL" : stack));
			OreDictionary.registerOre(oreBiomaterial, stack);
		}
		
		TileEntityRegistry tileEntity = new TileEntityRegistry();
		tileEntity.registerTileEntities();
		CristalRegistry.registerAll();
		ReserchRegistry research = new ReserchRegistry();
		research.preInit(event);
		
		
		ItemStack crootBeetle = new ItemStack(ItemRegistry.crootBeetle);
		crootBeetle.getItem().onCreated(crootBeetle, null, null);
		MinecraftForge.addGrassSeed(crootBeetle, ConfigRegistry.crootBeetleChance);
		
		BioEntityRegistry er = new BioEntityRegistry();
		er.preInit(event);
		
		BiomeRegistry biome = new BiomeRegistry();
    	biome.init(event);
	}
	
	
	public void init(FMLInitializationEvent event) {
    	RecipeRegistry recipes = new RecipeRegistry();
    	recipes.registerRecipes();
    	NetworkRegistry.INSTANCE.registerGuiHandler(HiveteraMod.instance, new GuiHandler());
    	
    	ArmorPatternRegistry patterns = new ArmorPatternRegistry();
    	patterns.init(event);
    	
    	ReserchRegistry research = new ReserchRegistry();
		research.init(event);
    	
    	VillagerBank bank = new VillagerBank();
    	bank.init(event);
    	
    	BreedingRegistry breeding = new BreedingRegistry();
    	breeding.init(event);
    	
    	DrawbackRegistry drawbacks = new DrawbackRegistry();
    	drawbacks.init(event);
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		TreeStorage.instance();
		ItemRegistry items = new ItemRegistry();
		items.setupFilters();
		
		if(event.getSide() == Side.CLIENT){
			BookTokenizer tok = new BookTokenizer();
			tok.init(event);
			
			BookParseTree parser = new BookParseTree();
			parser.init(event);
		}
	}


	public void initSaveHandling() {
		PlayerResearchStorage.instance(false);
		PlayerHungerStorage.instance(false);
	}
	
	public static EntityPlayerMP getPlayer(UUID id){
		for(WorldServer world : MinecraftServer.getServer().worldServers){
			EntityPlayerMP player = (EntityPlayerMP) world.func_152378_a(id);
			if(player != null)
				return player;
		}
		return null;
	}
	
}
