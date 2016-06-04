package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.blocks.BlockCrootSapling;
import hok.chompzki.hivetera.recipes.CrootRecipeData;
import hok.chompzki.hivetera.recipes.PurifierData;
import hok.chompzki.hivetera.recipes.RecipeData;
import hok.chompzki.hivetera.recipes.TransformData;
import hok.chompzki.hivetera.recipes.TransformEntityData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;


/**
 * IDEAS
 * 
 * CONFIG OPTIONS FOR NESTING FINDINGS AND CHANCES!
 * 
 * 
 * 
 * 
 * ~ LORE PROBLEMS ~
 * Carla & Fleur invented the extractor for cristals.. so they could automaticly get Resources form it... But Rot invented the cristals like 50 years after thier deaths xD
 * 
 * 
 * 
 * @author Jonathan
 *
 */

public class ConfigRegistry {
	
	public static int maxBlocksCollector = 81;
	public static int maxBlocksCatalystInjector = 81;
	public static int weakCristalGrowthChance = 10;
	
	public static int hivebagCookTime = 500;
	public static int hungerDistance = 500;
	
	public static int crootBeetleChance = 10;
	
	public static int displaceTime = 1000;
	public static int displaceMultiplier = 10;
	
	public static int wsbDamage = 2;
	
	public static String configNumber = "0.692";
	public static Configuration config;
	
	public static List<RecipeData> recipeData = new ArrayList<RecipeData>();
	public static List<CrootRecipeData> crootData = new ArrayList<CrootRecipeData>();
	public static List<TransformData> transformData = new ArrayList<TransformData>();
	public static List<TransformEntityData> transformEntityData = new ArrayList<TransformEntityData>();
	public static List<PurifierData> purifierData = new ArrayList<PurifierData>();
	
	public static List<String> sackWhiteList = new ArrayList<String>();
	public static List<String> sackBlackList = new ArrayList<String>();
	
	public static final String RECIPE_CATEGORY="Input: ";
	
	public static String[] oreDictBioMaterialDefault={"minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:pumpkin_seeds", "minecraft:melon_seeds", "minecraft:wheat_seeds", "minecraft:egg", "minecraft:hay_block", "Hivetera:crootSapling", "minecraft:waterlily", "minecraft:cactus", "minecraft:melon_block", "minecraft:carrot","minecraft:wheat","minecraft:melon","minecraft:pumpkin", "minecraft:potato", "minecraft:reeds","minecraft:vine","treeLeaves"};
    public static String[] oreDictBioMaterial;
	public static int krakenChance = 6;
	public static int wsbChance = 6;
	public static int hivebagChance = 6;
	public static int crootClawChance = 6;
	public static int kittehChance = 6;
	public static int clayHunterChance = 6;
	
	public static int nomadsSackSize = 2500;
	
	public static String[] networkNamesDefault={"Savain The Bullet", 
		"Kitteh The Mascot", 
		"Blueman Group", 
		"Freewind",
		"The Black Beast",
		"Big Daddy",
		"A City Under The Waves",
		"Reed Wahld",
		"Dexagon The Cat",
		"Lead Paint & Baby Formula",
		"Drill Fuel",
		"SHIELD HAT",
		"Pocket Kraken",
		"Blue vs. Red"};
	
    public static String[] networkNames;
	
	
	public static void preinit(File configFile) {
        if (config == null) {
            config = new Configuration(configFile, configNumber);
        }
        loadConfig();
    }
	
	@SubscribeEvent
    public void OnConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(HiveteraMod.MODID)) {
            loadConfig();
        }

    }
	
    private static void loadConfig() {
    	
    	boolean newVersion = config.getLoadedConfigVersion() == null || !config.getLoadedConfigVersion().equals(configNumber);
    	
    	maxBlocksCollector = 81; //config.getInt("Max Block Search (Collector)", "Track ranges", 81, 10, 500, "");
    	maxBlocksCatalystInjector = 81; // config.getInt("Max Block Search (Catalyst Injector)", "Track ranges", 81, 10, 500, "");
    	weakCristalGrowthChance = 5; //config.getInt("Weakcristal", "Growth chances", 10, 5, 1000, "");
    	
    	hivebagCookTime = config.getInt("Cook Time", "Hivebag", 500, 100, Integer.MAX_VALUE, "");
    	hungerDistance = config.getInt("Hunger Distance", "Hivebag", 500, 1, Integer.MAX_VALUE, "");
    	
    	crootBeetleChance = config.getInt("Croot Beetle", "Chance In Grass", 10, 1, 100, "");
    	nomadsSackSize = config.getInt("Max Size", "Nomads Sack", 2500, 1, Integer.MAX_VALUE, "");
    	
    	krakenChance = config.getInt("Kraken Bug (Food)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	wsbChance  = config.getInt("Water Shielded Bug (Range Attack)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	hivebagChance   = config.getInt("Hivebag (Pocket Smelting)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	crootClawChance    = config.getInt("Croot Claw (Climbing Tool)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	kittehChance    = config.getInt("Kitteh Beetle (Food)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	clayHunterChance    = config.getInt("Clay Hunter(Producer)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	
    	wsbDamage = config.getInt("Damage", "Water Shielded Bug (Range Attack)", 2, 1, 100, "");
    	
    	displaceTime = config.getInt("Time", "Block displacer", 1000, 100, 10000, "How long blocks are displaced");
    	displaceMultiplier = config.getInt("Multiplier", "Block displacer", 10, 1, 100, "How fast the displaced area collapses");
    	
    	oreDictBioMaterial = config.get("Bio Material OreDict", "OreDict", oreDictBioMaterialDefault, "first texture/croot name then block needed to make it!").getStringList();
    	
    	networkNames = config.get("The Hunger", "Network Names", networkNamesDefault, "Public Networks that can be bought from villagers").getStringList();
    	
    	
    	//Workbench!
    	recipeData.clear();
    	ConfigCategory workbenchRecipes = config.getCategory("Recipes");
    	if(newVersion){
    		for(ConfigCategory child : workbenchRecipes.getChildren()){
    			workbenchRecipes.removeChild(child);
    		}
    		workbenchRecipes.clear();
    	}
    	workbenchRecipes.setComment("All recipes for the mod... if you want a slot to be empty in a recipe just write 'empty' and it shall work :) Look at the attunment recipe in croot recipes for an example. ");
    	workbenchRecipes.setRequiresMcRestart(true);
    	
    	if(workbenchRecipes.getChildren().size() <= 0){
    		createRecipeStandard(workbenchRecipes);
    	}
    	
    	for(ConfigCategory recipe : workbenchRecipes.getChildren()){
    		recipeData.add(new RecipeData(recipe.get("code").getString(),recipe.get("input").getStringList(), recipe.get("output").getString()));
    	}
    	
    	//Croot Workbench!
    	crootData.clear();
    	ConfigCategory crootRecipes = config.getCategory("Croot Recipes");
    	if(newVersion){
    		for(ConfigCategory child : crootRecipes.getChildren()){
    			crootRecipes.removeChild(child);
    		}
    		crootRecipes.clear();
    	}
    	crootRecipes.setComment("All croot recipes for the mod...");
    	crootRecipes.setRequiresMcRestart(true);
    	
    	if(crootRecipes.getChildren().size() <= 0){
    		createCrootRecipeStandard(crootRecipes);
    	}
    	
    	for(ConfigCategory recipe : crootRecipes.getChildren()){
    		crootData.add(new CrootRecipeData(recipe.get("code").getString(),recipe.get("input").getStringList(), recipe.get("output").getString()));
    	}
    	
    	//Block Transformation
    	transformData.clear();
    	ConfigCategory transformRecipes = config.getCategory("Transformation Recipes");
    	if(newVersion){
    		for(ConfigCategory child : transformRecipes.getChildren()){
    			transformRecipes.removeChild(child);
    		}
    		transformRecipes.clear();
    	}
    	transformRecipes.setComment("All block transformations recipes for the mod...");
    	transformRecipes.setRequiresMcRestart(true);
    	
    	if(transformRecipes.getChildren().size() <= 0){
    		createTransformerStandard(transformRecipes);
    	}
    	
    	for(ConfigCategory recipe : transformRecipes.getChildren()){
    		transformData.add(new TransformData(recipe.get("input").getString(), recipe.get("output").getString(), recipe.get("code").getString()));
    	}
    	
    	//Entity Transformation
    	transformEntityData.clear();
    	ConfigCategory transformEntityRecipes = config.getCategory("Entity Transformation Recipes");
    	if(newVersion){
    		for(ConfigCategory child : transformEntityRecipes.getChildren()){
    			transformEntityRecipes.removeChild(child);
    		}
    		transformEntityRecipes.clear();
    	}
    	transformEntityRecipes.setComment("All entity transformations recipes for the mod...");
    	transformEntityRecipes.setRequiresMcRestart(true);
    	
    	if(transformEntityRecipes.getChildren().size() <= 0){
    		createEntityTransformerStandard(transformEntityRecipes);
    	}
    	
    	for(ConfigCategory recipe : transformEntityRecipes.getChildren()){
    		transformEntityData.add(new TransformEntityData(recipe.get("entity").getString(), recipe.get("output").getString(), recipe.get("code").getString()));
    	}
    	
    	//Purifier Data
    	purifierData.clear();
    	ConfigCategory purifierRecipes = config.getCategory("Purifier Recipes");
    	if(newVersion){
    		for(ConfigCategory child : purifierRecipes.getChildren()){
    			purifierRecipes.removeChild(child);
    		}
    		purifierRecipes.clear();
    	}
    	purifierRecipes.setComment("All purifier recipes for the mod...");
    	purifierRecipes.setRequiresMcRestart(true);
    	
    	if(purifierRecipes.getChildren().size() <= 0){
    		createPurifierStandard(purifierRecipes);
    	}
    	
    	for(ConfigCategory recipe : purifierRecipes.getChildren()){
    		purifierData.add(new PurifierData(recipe.getName() ,recipe.get("filter").getString(), recipe.get("code").getString(), recipe.get("input").getString(), recipe.get("output").getString(), recipe.get("time").getInt()));
    	}
    	
    	//Whitelist Sack Data
    	sackWhiteList.clear();
    	ConfigCategory whitelist = config.getCategory("Nomads Sack Whitelist");
    	if(newVersion){
    		for(ConfigCategory child : whitelist.getChildren()){
    			whitelist.removeChild(child);
    		}
    		whitelist.clear();
    	}
    	whitelist.setComment("The whitelist of blocks for the nomads sack.");
    	whitelist.setRequiresMcRestart(true);
    	
    	if(whitelist.getChildren().size() <= 0){
    		createNomadsSackWhitelistStandard(whitelist);
    	}
    	
    	for(ConfigCategory block : whitelist.getChildren()){
    		sackWhiteList.add(block.get("BLOCK").getString());
    	}
    	
    	//Blacklist Sack Data
    	sackBlackList.clear();
    	ConfigCategory blacklist = config.getCategory("Nomads Sack Blacklist");
    	if(newVersion){
    		for(ConfigCategory child : blacklist.getChildren()){
    			blacklist.removeChild(child);
    		}
    		blacklist.clear();
    	}
    	blacklist.setComment("The Blacklist of items for the nomads sack.");
    	blacklist.setRequiresMcRestart(true);
    	
    	if(blacklist.getChildren().size() <= 0){
    		createNomadsSackBlacklistStandard(blacklist);
    	}
    	
    	for(ConfigCategory item : blacklist.getChildren()){
    		sackBlackList.add(item.get("ITEM").getString());
    	}
    	
    	//Save
    	config.save();
    }

	private static void createNomadsSackBlacklistStandard(
			ConfigCategory blacklist) {
		
		registerSingle(blacklist, "Stick", "ITEM", "minecraft:stick");
		registerSingle(blacklist, "Coal", "ITEM", "minecraft:coal");
		registerSingle(blacklist, "Bed", "ITEM", "minecraft:bed");
		registerSingle(blacklist, "Map", "ITEM", "minecraft:map");
		registerSingle(blacklist, "Compass", "ITEM", "minecraft:compass");
		registerSingle(blacklist, "Cauldron", "ITEM", "minecraft:cauldron");
		registerSingle(blacklist, "Boat", "ITEM", "minecraft:boat");
		registerSingle(blacklist, "Minecart", "ITEM", "minecraft:minecart");
		registerSingle(blacklist, "Chest Minecart", "ITEM", "minecraft:chest_minecart");
		registerSingle(blacklist, "Furnace Minecart", "ITEM", "minecraft:furnace_minecart");
		registerSingle(blacklist, "Arrow", "ITEM", "minecraft:arrow");
		registerSingle(blacklist, "Redstone", "ITEM", "minecraft:redstone");
		registerSingle(blacklist, "Glowstone Dust", "ITEM", "minecraft:glowstone_dust");
		registerSingle(blacklist, "Clock", "ITEM", "minecraft:clock");
		registerSingle(blacklist, "Hopper Minecart", "ITEM", "minecraft:hopper_minecart");
		registerSingle(blacklist, "TNT Minecart", "ITEM", "minecraft:tnt_minecart");
		registerSingle(blacklist, "Filled Map", "ITEM", "minecraft:filled_map");
		registerSingle(blacklist, "Croot Stick", "ITEM", "Hivetera:itemCrootStick");
		registerSingle(blacklist, "Research Book", "ITEM", "Hivetera:itemResearchBook");
		
	}
	
	private static void registerSingle(ConfigCategory c, String name, String code, String value){
		ConfigCategory stuff = new ConfigCategory(name, c);
		stuff.put(code, new Property(code, value, Property.Type.STRING));
	}

	private static void createNomadsSackWhitelistStandard(
			ConfigCategory whitelist) { 
		registerSingle(whitelist, "Wool", "BLOCK", "minecraft:wool:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Lever", "BLOCK", "minecraft:lever");
		registerSingle(whitelist, "Melon Block", "BLOCK", "minecraft:melon_block");
		registerSingle(whitelist, "Pumpkin", "BLOCK", "minecraft:pumpkin");
		registerSingle(whitelist, "Rail", "BLOCK", "minecraft:rail");
		registerSingle(whitelist, "Brown Mushroom", "BLOCK", "minecraft:brown_mushroom");
		registerSingle(whitelist, "Red Mushroom", "BLOCK", "minecraft:red_mushroom");
		registerSingle(whitelist, "Brewing Stand", "BLOCK", "minecraft:brewing_stand");
		registerSingle(whitelist, "Sapling", "BLOCK", "minecraft:sapling:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Red Flower", "BLOCK", "minecraft:red_flower:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Redstone Torch", "BLOCK", "minecraft:redstone_torch");
		registerSingle(whitelist, "Waterlily", "BLOCK", "minecraft:waterlily");
		registerSingle(whitelist, "Grass", "BLOCK", "minecraft:grass:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Leaves", "BLOCK", "minecraft:leaves:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Yellow Flower", "BLOCK", "minecraft:yellow_flower:"+OreDictionary.WILDCARD_VALUE);
		registerSingle(whitelist, "Vine", "BLOCK", "minecraft:vine:"+OreDictionary.WILDCARD_VALUE);
	}
	
	private static void createRecipeStandard(ConfigCategory recipes){
		
		ConfigCategory attuner = new ConfigCategory("Attuner", recipes);
		attuner.put("code", new Property("code", "NONE", Property.Type.STRING));
		attuner.put("input", new Property("input", new String[] { "minecraft:stick Hivetera:itemCrootBeetle minecraft:stick", 
																  "Hivetera:itemCrootBeetle empty Hivetera:itemCrootBeetle", 
																  "minecraft:stick treeSapling minecraft:stick"},
																  Property.Type.STRING));
		attuner.put("output", new Property("output", "Hivetera:itemAttuner", Property.Type.STRING));
		
		ConfigCategory armorAttuner = new ConfigCategory("Armor Attuner", recipes);
		armorAttuner.put("code", new Property("code", "NONE", Property.Type.STRING));
		armorAttuner.put("input", new Property("input", new String[] { "empty Hivetera:itemChitinPlate Hivetera:itemChitinPlate", 
																  "Hivetera:itemChitinPlate minecraft:coal_block Hivetera:itemChitinPlate", 
																  "Hivetera:itemChitinPlate Hivetera:itemChitinPlate empty"},
																  Property.Type.STRING));
		armorAttuner.put("output", new Property("output", "Hivetera:itemArmorAttuner", Property.Type.STRING));
		
		ConfigCategory crootSapling = new ConfigCategory("Croot Sapling", recipes);
		crootSapling.put("code", new Property("code", ReserchRegistry.crootSapling, Property.Type.STRING));
		crootSapling.put("input", new Property("input", new String[] { "dye Hivetera:itemCrootBeetle dye", 
																	   "Hivetera:itemCrootBeetle treeSapling Hivetera:itemCrootBeetle",
																	   "dye Hivetera:itemCrootBeetle dye" },
																	   Property.Type.STRING));
		crootSapling.put("output", new Property("output", "Hivetera:crootSapling", Property.Type.STRING));
		
		ConfigCategory researchBook = new ConfigCategory("Research Book", recipes);
		researchBook.put("code", new Property("code", "NONE", Property.Type.STRING));
		researchBook.put("input", new Property("input", new String[] {"logWood minecraft:book"}, Property.Type.STRING));
		researchBook.put("output", new Property("output", "Hivetera:itemResearchBook", Property.Type.STRING));
		
		ConfigCategory crootStick = new ConfigCategory("Croot Stick", recipes);
		crootStick.put("code", new Property("code", ReserchRegistry.crootStick, Property.Type.STRING));
		crootStick.put("input", new Property("input", new String[] { "treeSapling Hivetera:itemCrootBeetle", 
																	   "minecraft:stick treeSapling"},
																	   Property.Type.STRING));
		crootStick.put("output", new Property("output", "Hivetera:itemCrootStick", Property.Type.STRING));
		
		ConfigCategory nomadsSack = new ConfigCategory("Nomads Sack", recipes);
		nomadsSack.put("code", new Property("code", ReserchRegistry.nomadsSack, Property.Type.STRING));
		nomadsSack.put("input", new Property("input", new String[] { "treeSapling Hivetera:itemCrootBeetle", 
																	   "Hivetera:itemCrootBeetle treeSapling"},
																	   Property.Type.STRING));
		nomadsSack.put("output", new Property("output", "Hivetera:itemNomandsSack", Property.Type.STRING));
		
		ConfigCategory chitinPlate = new ConfigCategory("Chitin Plate", recipes);
		chitinPlate.put("code", new Property("code", ReserchRegistry.chitinPlate, Property.Type.STRING));
		chitinPlate.put("input", new Property("input", new String[] { "Hivetera:itemClayHunter treeSapling", 
																	   "minecraft:clay_ball Hivetera:itemKraKenBug"},
																	   Property.Type.STRING));
		chitinPlate.put("output", new Property("output", "8xHivetera:itemChitinPlate", Property.Type.STRING));
		
		ConfigCategory chitinHelmet = new ConfigCategory("Chitin Helmet", recipes);
		chitinHelmet.put("code", new Property("code", ReserchRegistry.chitinHelmet, Property.Type.STRING));
		chitinHelmet.put("input", new Property("input", new String[] { "Hivetera:itemChitinPlate Hivetera:itemChitinPlate Hivetera:itemChitinPlate", 
																	   "Hivetera:itemChitinPlate Hivetera:itemClayHunter Hivetera:itemChitinPlate"},
																	   Property.Type.STRING));
		chitinHelmet.put("output", new Property("output", "Hivetera:chitinHelmet", Property.Type.STRING));
		
		ConfigCategory chitinChestplate = new ConfigCategory("Chitin Chestplate", recipes);
		chitinChestplate.put("code", new Property("code", ReserchRegistry.chitinChestplate, Property.Type.STRING));
		chitinChestplate.put("input", new Property("input", new String[] { "Hivetera:itemChitinPlate Hivetera:itemClayHunter Hivetera:itemChitinPlate", 
																	   "Hivetera:itemChitinPlate Hivetera:itemChitinPlate Hivetera:itemChitinPlate",
																	   "Hivetera:itemChitinPlate Hivetera:itemChitinPlate Hivetera:itemChitinPlate"},
																	   Property.Type.STRING));
		chitinChestplate.put("output", new Property("output", "Hivetera:chitinChestplate", Property.Type.STRING));
		
		ConfigCategory chitinLeggings = new ConfigCategory("Chitin Leggings", recipes);
		chitinLeggings.put("code", new Property("code", ReserchRegistry.chitinLeggings, Property.Type.STRING));
		chitinLeggings.put("input", new Property("input", new String[] { "Hivetera:itemChitinPlate Hivetera:itemChitinPlate Hivetera:itemChitinPlate", 
																	   "Hivetera:itemChitinPlate Hivetera:itemClayHunter Hivetera:itemChitinPlate",
																	   "Hivetera:itemChitinPlate empty Hivetera:itemChitinPlate"},
																	   Property.Type.STRING));
		chitinLeggings.put("output", new Property("output", "Hivetera:chitinLeggings", Property.Type.STRING));
		
		ConfigCategory chitinBoots = new ConfigCategory("Chitin Boots", recipes);
		chitinBoots.put("code", new Property("code", ReserchRegistry.chitinBoots, Property.Type.STRING));
		chitinBoots.put("input", new Property("input", new String[] { "Hivetera:itemChitinPlate Hivetera:itemClayHunter Hivetera:itemChitinPlate", 
																	   "Hivetera:itemChitinPlate empty Hivetera:itemChitinPlate"},
																	   Property.Type.STRING));
		chitinBoots.put("output", new Property("output", "Hivetera:chitinBoots", Property.Type.STRING));
		
		ConfigCategory nest = new ConfigCategory("Nest", recipes);
		nest.put("code", new Property("code", ReserchRegistry.nest, Property.Type.STRING));
		nest.put("input", new Property("input", new String[] { "logWood Hivetera:itemChitinPlate logWood", 
															   "logWood Hivetera:itemCrootStick logWood",
															   "minecraft:stone_slab:3 minecraft:stone_slab:3 minecraft:stone_slab:3"},
																	   Property.Type.STRING));
		nest.put("output", new Property("output", "Hivetera:blockNest", Property.Type.STRING));
		
		ConfigCategory crootBreeder = new ConfigCategory("Croot Breeder", recipes);
		crootBreeder.put("code", new Property("code", ReserchRegistry.crootBreeder, Property.Type.STRING));
		crootBreeder.put("input", new Property("input", new String[] { "logWood Hivetera:itemChitinPlate logWood", 
															   "plankWood Hivetera:itemKittehBeetle plankWood",
															   "minecraft:stone_slab:3 minecraft:stone_slab:3 minecraft:stone_slab:3"},
																	   Property.Type.STRING));
		crootBreeder.put("output", new Property("output", "Hivetera:blockCrootBreeder", Property.Type.STRING));
		
		ConfigCategory woodenCrootHoe = new ConfigCategory("Wooden Croot Hoe", recipes);
		woodenCrootHoe.put("code", new Property("code", ReserchRegistry.crootHoe, Property.Type.STRING));
		woodenCrootHoe.put("input", new Property("input", new String[] { "minecraft:string Hivetera:itemCrootStick", 
															   			  "minecraft:wooden_hoe:0 minecraft:string"},
																	   Property.Type.STRING));
		woodenCrootHoe.put("output", new Property("output", "Hivetera:itemCrootHoe", Property.Type.STRING));
		
		ConfigCategory hungerPortal = new ConfigCategory("Hunger Portal", recipes);
		hungerPortal.put("code", new Property("code", ReserchRegistry.hungerPortal, Property.Type.STRING));
		hungerPortal.put("input", new Property("input", new String[] { "Hivetera:itemCrootBeetle Hivetera:itemCrootBeetle Hivetera:itemCrootBeetle", 
				   													   "Hivetera:itemCrootBeetle Hivetera:itemKraKenBug Hivetera:itemCrootBeetle",
				   													   "Hivetera:itemCrootBeetle Hivetera:itemCrootBeetle Hivetera:itemCrootBeetle"},
																	   Property.Type.STRING));
		hungerPortal.put("output", new Property("output", "Hivetera:itemHiveBrain", Property.Type.STRING));
		
		ConfigCategory sacrificePit = new ConfigCategory("Sacrifice Pit", recipes);
		sacrificePit.put("code", new Property("code", ReserchRegistry.sacrificePit, Property.Type.STRING));
		sacrificePit.put("input", new Property("input", new String[] { "logWood Hivetera:itemChitinPlate logWood", 
															   "plankWood Hivetera:itemHiveBrain plankWood",
															   "minecraft:stone_slab:3 minecraft:stone_slab:3 minecraft:stone_slab:3"},
																	   Property.Type.STRING));
		sacrificePit.put("output", new Property("output", "Hivetera:blockSacrificePit", Property.Type.STRING));
		
		ConfigCategory tokenAssembler = new ConfigCategory("Token Assembler", recipes);
		tokenAssembler.put("code", new Property("code", ReserchRegistry.tokenAssembler, Property.Type.STRING));
		tokenAssembler.put("input", new Property("input", new String[] { "logWood Hivetera:itemChitinPlate logWood", 
															   "plankWood Hivetera:itemClayHunter plankWood",
															   "minecraft:stone_slab:3 minecraft:stone_slab:3 minecraft:stone_slab:3"},
																	   Property.Type.STRING));
		tokenAssembler.put("output", new Property("output", "Hivetera:blockTokenAssembler", Property.Type.STRING));
    }
	
	//https://github.com/Piron1991/Builder_tools/blob/master/src/main/java/com/piron1991/builder_tools/handler/ConfigHandler.java#L82
    
    
		private static void createCrootRecipeStandard(ConfigCategory recipes) {
			
			
		}
    
    private static void createTransformerStandard(ConfigCategory recipes){
    	
    }
    
    //(String) (EntityList.classToStringMapping.get(entity.getClass())));
    //CristalRegistry.register(new WeakFleshTransformation(EntitySheep.class, ReserchRegistry.sheepSkin, new ItemStack(Blocks.wool)));
    private static void createEntityTransformerStandard(ConfigCategory recipes){
    	
    }
    
    private static void createPurifierStandard(
			ConfigCategory recipes) {
		
	}
}
