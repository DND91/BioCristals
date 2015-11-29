package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.blocks.BlockCrootSapling;
import hok.chompzki.biocristals.recipes.CrootRecipeData;
import hok.chompzki.biocristals.recipes.PurifierData;
import hok.chompzki.biocristals.recipes.RecipeData;
import hok.chompzki.biocristals.recipes.TransformData;
import hok.chompzki.biocristals.recipes.TransformEntityData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


/**
 * IDEAS
 * 
 * 
 * THINGS TO FIX: 
 * 
 * Croot Hollow MK2 each slot inside represents a specific side, symbols bind sides... slot to side with symbols!
 * 
 * Lore to explain the croot power system!!!!!
 * Need a better and more explaining tutorial that shows the player things! :)
 * Need to look into som text bugs for the NEI overlay... text gets a bit compact....
 * Need to add lore for the Croot Beetle!
 * Add multitextures for Croot System! Make it dynamic with config! Make a recipe so that you can transform croot saplings to diffrent croot saplings! :)
 * 
 * Slimes scoots of a working tuft when they are effected by it...
 * Bio Blob checks for weakness potion effect, need to change that so it checks for somethign unique, that can only be applyied by the tuft... like the negativ jump boost.
 * Bio Blob dosn't work on wither as potion effects dosn't work on it... as it should :)
 * Bio Blob should be one time use!
 * Need to add progressbar to the progmenitus!!! Like with the purifier!
 * The progmenitus has the same bug as the insert into crafting grid has... the methods have diffrent names between debug mode and IRL...
 * 
 * At the moment there only exist "normal".. but i'm going to add so you can say "sandstone", then ad textures for that and then you take a croot sapling plus a piece of sandstone and then that kind of tree will grow... :3
 * On top of that i will add so that you can decide how each of the trees should grow and what they will drop.... but that will have to come later ^^
 * 
 * - URGENTS!
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
	public static int hungerDuration = 600;
	public static int hungerAmplifier = 1;
	
	public static int hungerDurationTP = 100;
	public static int hungerAmplifierTP = 0;
	
	public static int crootBeetleChance = 10;
	
	public static int wsbDamage = 2;
	
	public static String configNumber = "0.666";
	public static Configuration config;
	
	public static List<RecipeData> recipeData = new ArrayList<RecipeData>();
	public static List<CrootRecipeData> crootData = new ArrayList<CrootRecipeData>();
	public static List<TransformData> transformData = new ArrayList<TransformData>();
	public static List<TransformEntityData> transformEntityData = new ArrayList<TransformEntityData>();
	public static List<PurifierData> purifierData = new ArrayList<PurifierData>();
	
	public static List<String> sackWhiteList = new ArrayList<String>();
	public static List<String> sackBlackList = new ArrayList<String>();
	
	public static final String RECIPE_CATEGORY="Input: ";
	
	public static String[] oreDictBioMaterialDefault={"minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:pumpkin_seeds", "minecraft:melon_seeds", "minecraft:wheat_seeds", "minecraft:egg", "minecraft:hay_block", "BioCristals:crootSapling", "minecraft:waterlily", "minecraft:cactus", "minecraft:melon_block", "minecraft:carrot","minecraft:wheat","minecraft:melon","minecraft:pumpkin", "minecraft:potato", "minecraft:reeds","minecraft:vine","treeLeaves"};
    public static String[] oreDictBioMaterial;
	public static int krakenChance = 6;
	public static int wsbChance = 6;
	public static int hivebagChance = 6;
	public static int crootClawChance = 6;
	public static int nomadsSackSize = 2500;
	
	public static void preinit(File configFile) {
        if (config == null) {
            config = new Configuration(configFile, configNumber);
        }
        loadConfig();
    }
	
	@SubscribeEvent
    public void OnConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(BioCristalsMod.MODID)) {
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
    	hungerDuration = config.getInt("Hunger Duration", "Hivebag", 600, 1, 20000, "");
    	hungerAmplifier = config.getInt("Hunger Amplifier", "Hivebag", 1, 0, 10, "");
    	
    	crootBeetleChance = config.getInt("Croot Beetle", "Chance In Grass", 10, 1, 100, "");
    	nomadsSackSize = config.getInt("Max Size", "Nomads Sack", 2500, 1, Integer.MAX_VALUE, "");
    	
    	hungerDurationTP = config.getInt("Hunger Duration", "Croot Claw", 100, 1, 20000, "");
    	hungerAmplifierTP = config.getInt("Hunger Amplifier", "Croot Claw", 0, 0, 10, "");
    	
    	krakenChance = config.getInt("Kraken Bug (Food)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	wsbChance  = config.getInt("Water Shielded Bug (Range Attack)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	hivebagChance   = config.getInt("Hivebag (Pocket Smelting)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	crootClawChance    = config.getInt("Croot Claw (Climbing Tool)", "Chance for bug (1 in X chance)", 6, 1, 20000, "");
    	
    	wsbDamage = config.getInt("Damage", "Water Shielded Bug (Range Attack)", 2, 1, 100, "");
    	
    	
    	oreDictBioMaterial = config.get("Bio Material OreDict", "OreDict", oreDictBioMaterialDefault, "first texture/croot name then block needed to make it!").getStringList();
    	
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
		registerSingle(blacklist, "Hopper Minecart", "ITEM", "minecraft:hopper_minecrat");
		registerSingle(blacklist, "TNT Minecart", "ITEM", "minecraft:tnt_minecrat");
		registerSingle(blacklist, "Filled Map", "ITEM", "minecraft:filled_map");
		registerSingle(blacklist, "Croot Stick", "ITEM", "BioCristals:itemCrootStick");
		registerSingle(blacklist, "Research Book", "ITEM", "BioCristals:itemResearchBook");
		
	}
	
	private static void registerSingle(ConfigCategory c, String name, String code, String value){
		ConfigCategory stuff = new ConfigCategory(name, c);
		stuff.put(code, new Property(code, value, Property.Type.STRING));
	}

	private static void createNomadsSackWhitelistStandard(
			ConfigCategory whitelist) {
		registerSingle(whitelist, "Wool", "BLOCK", "minecraft:wool");
		registerSingle(whitelist, "Lever", "BLOCK", "minecraft:lever");
		registerSingle(whitelist, "Melon Block", "BLOCK", "minecraft:melon_block");
		registerSingle(whitelist, "Pumpkin", "BLOCK", "minecraft:pumpkin");
		registerSingle(whitelist, "Rail", "BLOCK", "minecraft:rail");
		registerSingle(whitelist, "Brown Mushroom", "BLOCK", "minecraft:brown_mushroom");
		registerSingle(whitelist, "Red Mushroom", "BLOCK", "minecraft:red_mushroom");
		registerSingle(whitelist, "Brewing Stand", "BLOCK", "minecraft:brewing_stand");
		registerSingle(whitelist, "Sapling", "BLOCK", "minecraft:sapling");
		registerSingle(whitelist, "Red Flower", "BLOCK", "minecraft:red_flower");
		registerSingle(whitelist, "Redstone Torch", "BLOCK", "minecraft:redstone_torch");
		registerSingle(whitelist, "Waterlily", "BLOCK", "minecraft:waterlily");
		registerSingle(whitelist, "Grass", "BLOCK", "minecraft:grass");
		registerSingle(whitelist, "Leaves", "BLOCK", "minecraft:leaves");
		registerSingle(whitelist, "Yellow Flower", "BLOCK", "minecraft:yellow_flower");
		registerSingle(whitelist, "Vine", "BLOCK", "minecraft:vine");
	}
	
	private static void createRecipeStandard(ConfigCategory recipes){
		
		ConfigCategory attuner = new ConfigCategory("Attuner", recipes);
		attuner.put("code", new Property("code", "NONE", Property.Type.STRING));
		attuner.put("input", new Property("input", new String[] { "minecraft:stick BioCristals:itemCrootBeetle minecraft:stick", 
																  "BioCristals:itemCrootBeetle empty BioCristals:itemCrootBeetle", 
																  "minecraft:stick treeSapling minecraft:stick"},
																  Property.Type.STRING));
		attuner.put("output", new Property("output", "BioCristals:itemAttuner", Property.Type.STRING));
		
		ConfigCategory crootSapling = new ConfigCategory("Croot Sapling", recipes);
		crootSapling.put("code", new Property("code", ReserchRegistry.crootSapling, Property.Type.STRING));
		crootSapling.put("input", new Property("input", new String[] { "dye BioCristals:itemCrootBeetle dye", 
																	   "BioCristals:itemCrootBeetle treeSapling BioCristals:itemCrootBeetle",
																	   "dye BioCristals:itemCrootBeetle dye" },
																	   Property.Type.STRING));
		crootSapling.put("output", new Property("output", "BioCristals:crootSapling", Property.Type.STRING));
		
		ConfigCategory researchBook = new ConfigCategory("Research Book", recipes);
		researchBook.put("code", new Property("code", "NONE", Property.Type.STRING));
		researchBook.put("input", new Property("input", new String[] {"logWood minecraft:book"}, Property.Type.STRING));
		researchBook.put("output", new Property("output", "BioCristals:itemResearchBook", Property.Type.STRING));
		
		ConfigCategory crootStick = new ConfigCategory("Croot Stick", recipes);
		crootStick.put("code", new Property("code", ReserchRegistry.crootStick, Property.Type.STRING));
		crootStick.put("input", new Property("input", new String[] { "treeSapling BioCristals:itemCrootBeetle", 
																	   "minecraft:stick treeSapling"},
																	   Property.Type.STRING));
		crootStick.put("output", new Property("output", "BioCristals:itemCrootStick", Property.Type.STRING));
		
		ConfigCategory nomadsSack = new ConfigCategory("Nomads Sack", recipes);
		nomadsSack.put("code", new Property("code", ReserchRegistry.nomadsSack, Property.Type.STRING));
		nomadsSack.put("input", new Property("input", new String[] { "treeSapling BioCristals:itemCrootBeetle", 
																	   "BioCristals:itemCrootBeetle treeSapling"},
																	   Property.Type.STRING));
		nomadsSack.put("output", new Property("output", "BioCristals:itemNomandsSack", Property.Type.STRING));
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
