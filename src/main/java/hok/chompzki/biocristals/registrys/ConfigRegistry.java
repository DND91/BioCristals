package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
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

public class ConfigRegistry {
	
	public static int maxBlocksCollector = 81;
	public static int maxBlocksCatalystInjector = 81;
	public static int weakCristalGrowthChance = 10;
	
	public static int hivebagCookTime = 500;
	public static int hungerDistance = 500;
	public static int hungerDuration = 600;
	public static int hungerAmplifier = 1;
	
	public static String configNumber = "0.3";
	public static Configuration config;
	
	public static List<RecipeData> recipeData = new ArrayList<RecipeData>();
	public static List<CrootRecipeData> crootData = new ArrayList<CrootRecipeData>();
	public static List<TransformData> transformData = new ArrayList<TransformData>();
	public static List<TransformEntityData> transformEntityData = new ArrayList<TransformEntityData>();
	public static List<PurifierData> purifierData = new ArrayList<PurifierData>();
	
	public static final String RECIPE_CATEGORY="Input: ";
	
	public static String[] oreDictBioMaterialDefault={"minecraft:carrot","minecraft:wheat","minecraft:melon","minecraft:pumpkin", "minecraft:potato", "minecraft:reeds","minecraft:vine","treeLeaves"};
    public static String[] oreDictBioMaterial;
	
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
    	
    	maxBlocksCollector = config.getInt("Max Block Search (Collector)", "Track ranges", 81, 10, 500, "");
    	maxBlocksCatalystInjector = config.getInt("Max Block Search (Catalyst Injector)", "Track ranges", 81, 10, 500, "");
    	weakCristalGrowthChance = config.getInt("Weakcristal", "Growth chances", 10, 5, 1000, "");
    	
    	hivebagCookTime = config.getInt("Cook Time", "Hivebag", 500, 100, Integer.MAX_VALUE, "");
    	hungerDistance = config.getInt("Hunger Distance", "Hivebag", 500, 1, Integer.MAX_VALUE, "");
    	hungerDuration = config.getInt("Hunger Duration", "Hivebag", 600, 1, 20000, "");
    	hungerAmplifier = config.getInt("Hunger Amplifier", "Hivebag", 1, 0, 10, "");
    	
    	oreDictBioMaterial = config.get("Bio Material OreDict", "OreDict", oreDictBioMaterialDefault).getStringList();
    	
    	//Workbench!
    	recipeData.clear();
    	ConfigCategory workbenchRecipes = config.getCategory("Recipes");
    	if(newVersion){
    		for(ConfigCategory child : workbenchRecipes.getChildren()){
    			workbenchRecipes.removeChild(child);
    		}
    		workbenchRecipes.clear();
    	}
    	workbenchRecipes.setComment("All recipes for the mod...");
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
    	
    	//Save
    	config.save();
    }

	private static void createRecipeStandard(ConfigCategory recipes){
		
		ConfigCategory attuner = new ConfigCategory("Attuner", recipes);
		attuner.put("code", new Property("code", ReserchRegistry.babySteps, Property.Type.STRING));
		attuner.put("input", new Property("input", new String[] { "minecraft:stick minecraft:string minecraft:stick", 
																  "minecraft:string empty minecraft:string", 
																  "minecraft:stick treeSapling minecraft:stick"},
																  Property.Type.STRING));
		attuner.put("output", new Property("output", "BioCristals:itemAttuner", Property.Type.STRING));
		
		ConfigCategory crootSapling = new ConfigCategory("Croot Sapling", recipes);
		crootSapling.put("code", new Property("code", ReserchRegistry.crootSapling, Property.Type.STRING));
		crootSapling.put("input", new Property("input", new String[] { "dye minecraft:string dye", 
																	   "minecraft:string treeSapling minecraft:string",
																	   "dye minecraft:string dye" },
																	   Property.Type.STRING));
		crootSapling.put("output", new Property("output", "BioCristals:crootSapling", Property.Type.STRING));
		
		ConfigCategory researchBook = new ConfigCategory("Research Book", recipes);
		researchBook.put("code", new Property("code", "NONE", Property.Type.STRING));
		researchBook.put("input", new Property("input", new String[] {"treeSapling minecraft:book"}, Property.Type.STRING));
		researchBook.put("output", new Property("output", "BioCristals:itemResearchBook", Property.Type.STRING));
		
		
    }
	
	//https://github.com/Piron1991/Builder_tools/blob/master/src/main/java/com/piron1991/builder_tools/handler/ConfigHandler.java#L82
    
    
		private static void createCrootRecipeStandard(ConfigCategory recipes) {
			
			/*
			ConfigCategory collector = new ConfigCategory("Collector", recipes);
	    	collector.put("code", new Property("code", "NONE", Property.Type.STRING));
			collector.put("input", new Property("input", "minecraft:log empty minecraft:log minecraft:log empty minecraft:log minecraft:string minecraft:sapling minecraft:string", Property.Type.STRING));
			collector.put("output", new Property("output", "BioCristals:itemCollector", Property.Type.STRING));
			*/
			
			ConfigCategory bioReagent = new ConfigCategory("Biological Reagent", recipes);
			bioReagent.put("code", new Property("code", ReserchRegistry.reaction, Property.Type.STRING));
			bioReagent.put("input", new Property("input", new String[] { "stickWood treeSapling stickWood",
																		"treeSapling minecraft:dirt treeSapling",
																		"stickWood treeSapling stickWood" },
																		Property.Type.STRING));
			bioReagent.put("output", new Property("output", "4xBioCristals:itemBioReagent", Property.Type.STRING));
			
			ConfigCategory biomass = new ConfigCategory("Biomass", recipes);
			biomass.put("code", new Property("code", ReserchRegistry.cubeMass, Property.Type.STRING));
			biomass.put("input", new Property("input", new String[] {"bioMaterial treeSapling bioMaterial",
																	 "treeSapling logWood treeSapling", 
																	 "bioMaterial treeSapling bioMaterial"},
																	 Property.Type.STRING));
			biomass.put("output", new Property("output", "4xBioCristals:blockBiomass", Property.Type.STRING));
			
			ConfigCategory sulphurTuft = new ConfigCategory("Sulphur Tuft", recipes);
			sulphurTuft.put("code", new Property("code", ReserchRegistry.tuft, Property.Type.STRING));
			sulphurTuft.put("input", new Property("input", new String[] { "minecraft:spider_eye minecraft:spider_eye minecraft:spider_eye", 
																		  "BioCristals:blockBiomass BioCristals:blockBiomass BioCristals:blockBiomass", 
																		  "BioCristals:itemBioReagent BioCristals:itemBioReagent BioCristals:itemBioReagent" },
																		  Property.Type.STRING));
			sulphurTuft.put("output", new Property("output", "BioCristals:blockSulphurTuft", Property.Type.STRING));
			
			ConfigCategory string = new ConfigCategory("String", recipes);
			string.put("code", new Property("code", "NONE", Property.Type.STRING));
			string.put("input", new Property("input", new String[] { "minecraft:wool" }, Property.Type.STRING));
			string.put("output", new Property("output", "4xminecraft:string", Property.Type.STRING));
			
			ConfigCategory crootHollow = new ConfigCategory("Croot Hollow", recipes);
			crootHollow.put("code", new Property("code", ReserchRegistry.crootHollow, Property.Type.STRING));
			crootHollow.put("input", new Property("input", new String[] { "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass",
														   				  "BioCristals:itemBioReagent minecraft:chest BioCristals:itemBioReagent",
														   				  "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass" },
														   				  Property.Type.STRING));
			crootHollow.put("output", new Property("output", "BioCristals:blockCrootHollow", Property.Type.STRING));
			
			ConfigCategory crootStreamStem = new ConfigCategory("Croot Stem", recipes);
			crootStreamStem.put("code", new Property("code", ReserchRegistry.crootStem, Property.Type.STRING));
			crootStreamStem.put("input", new Property("input", new String[] { "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass", 
														   					  "BioCristals:itemBioReagent logWood BioCristals:itemBioReagent",
														   					  "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass" }, 
														   					  Property.Type.STRING));
			crootStreamStem.put("output", new Property("output", "BioCristals:crootStreamStem", Property.Type.STRING));
			
			ConfigCategory reagentPurifier = new ConfigCategory("Reagent Purifier", recipes);
			reagentPurifier.put("code", new Property("code", ReserchRegistry.purifier, Property.Type.STRING));
			reagentPurifier.put("input", new Property("input", new String[] { "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass",
														   					  "BioCristals:itemBioReagent minecraft:furnace BioCristals:itemBioReagent",
														   					  "BioCristals:blockBiomass BioCristals:itemBioReagent BioCristals:blockBiomass" },
														   					  Property.Type.STRING));
			reagentPurifier.put("output", new Property("output", "BioCristals:blockReagentPurifier", Property.Type.STRING));
			
			/*
			
			
			ConfigCategory catalystInjector = new ConfigCategory("Catalyst Injector", recipes);
			catalystInjector.put("code", new Property("code", "NONE", Property.Type.STRING));
			catalystInjector.put("input", new Property("input", "BioCristals:itemBioReagent minecraft:slime_ball BioCristals:itemBioReagent minecraft:slime_ball minecraft:sapling minecraft:slime_ball BioCristals:itemBioReagent minecraft:slime_ball BioCristals:itemBioReagent", Property.Type.STRING));
			catalystInjector.put("output", new Property("output", "BioCristals:itemCatalystInjector", Property.Type.STRING));
			
			
			*/
		}
    
	//CristalRegistry.register(new WeakCristalTransformation((new ItemStack(Blocks.pumpkin)).getItem(), BlockRegistry.wheatCristal, ReserchRegistry.pumpkinCristalisation));
	
    
    private static void createTransformerStandard(ConfigCategory recipes){
    	ConfigCategory wheatCrystal = new ConfigCategory("wheatCrystal", recipes);
    	wheatCrystal.put("input", new Property("input", "minecraft:wheat", Property.Type.STRING));
		wheatCrystal.put("output", new Property("output", "BioCristals:blockWheatCristal", Property.Type.STRING));
		wheatCrystal.put("code", new Property("code", ReserchRegistry.wheatCristalisation, Property.Type.STRING));
		
		ConfigCategory carrotCrystal = new ConfigCategory("carrotCrystal", recipes);
		carrotCrystal.put("input", new Property("input", "minecraft:carrot", Property.Type.STRING));
		carrotCrystal.put("output", new Property("output", "BioCristals:blockCarrotCristal", Property.Type.STRING));
		carrotCrystal.put("code", new Property("code", ReserchRegistry.carrotCristalisation, Property.Type.STRING));
		
		ConfigCategory reedsCrystal = new ConfigCategory("reedsCrystal", recipes);
		reedsCrystal.put("input", new Property("input", "minecraft:reeds", Property.Type.STRING));
		reedsCrystal.put("output", new Property("output", "BioCristals:blockSugerCaneCristal", Property.Type.STRING));
		reedsCrystal.put("code", new Property("code", ReserchRegistry.reedsCristalisation, Property.Type.STRING));
		
		ConfigCategory potatoCrystal = new ConfigCategory("potatoCrystal", recipes);
		potatoCrystal.put("input", new Property("input", "minecraft:potato", Property.Type.STRING));
		potatoCrystal.put("output", new Property("output", "BioCristals:blockPotatoCristal", Property.Type.STRING));
		potatoCrystal.put("code", new Property("code", ReserchRegistry.potatoCristalisation, Property.Type.STRING));
		
		ConfigCategory melonCrystal = new ConfigCategory("melonCrystal", recipes);
		melonCrystal.put("input", new Property("input", "minecraft:melon", Property.Type.STRING));
		melonCrystal.put("output", new Property("output", "BioCristals:blockMelonCristal", Property.Type.STRING));
		melonCrystal.put("code", new Property("code", ReserchRegistry.melonCristalisation, Property.Type.STRING));
		
		ConfigCategory pumpkinCrystal = new ConfigCategory("pumpkinCrystal", recipes);
		pumpkinCrystal.put("input", new Property("input", "minecraft:pumpkin", Property.Type.STRING));
		pumpkinCrystal.put("output", new Property("output", "BioCristals:blockPumpkinCristal", Property.Type.STRING));
		pumpkinCrystal.put("code", new Property("code", ReserchRegistry.pumpkinCristalisation, Property.Type.STRING));
    }
    
    //(String) (EntityList.classToStringMapping.get(entity.getClass())));
    //CristalRegistry.register(new WeakFleshTransformation(EntitySheep.class, ReserchRegistry.sheepSkin, new ItemStack(Blocks.wool)));
    private static void createEntityTransformerStandard(ConfigCategory recipes){
    	ConfigCategory sheep = new ConfigCategory("Sheep", recipes);
    	sheep.put("entity", new Property("entity", "Sheep", Property.Type.STRING));
    	sheep.put("output", new Property("output", "minecraft:wool", Property.Type.STRING));
    	sheep.put("code", new Property("code", ReserchRegistry.sheepSkin, Property.Type.STRING));
    	
    	ConfigCategory cow = new ConfigCategory("Cow", recipes);
    	cow.put("entity", new Property("entity", "Cow", Property.Type.STRING));
    	cow.put("output", new Property("output", "minecraft:leather", Property.Type.STRING));
    	cow.put("code", new Property("code", ReserchRegistry.leatherHound, Property.Type.STRING));
    	
    	ConfigCategory pig = new ConfigCategory("Pig", recipes);
    	pig.put("entity", new Property("entity", "Pig", Property.Type.STRING));
    	pig.put("output", new Property("output", "minecraft:carrot", Property.Type.STRING));
    	pig.put("code", new Property("code", ReserchRegistry.pinkBlouse, Property.Type.STRING));
    	
    	ConfigCategory chicken = new ConfigCategory("Chicken", recipes);
    	chicken.put("entity", new Property("entity", "Chicken", Property.Type.STRING));
    	chicken.put("output", new Property("output", "minecraft:feather minecraft:egg", Property.Type.STRING));
    	chicken.put("code", new Property("code", ReserchRegistry.featherFriend, Property.Type.STRING));
    	
    	ConfigCategory horse = new ConfigCategory("Horse", recipes);
    	horse.put("entity", new Property("entity", "EntityHorse", Property.Type.STRING));
    	horse.put("output", new Property("output", "minecraft:leather", Property.Type.STRING));
    	horse.put("code", new Property("code", ReserchRegistry.leatherBeast, Property.Type.STRING));
    	
    	ConfigCategory villager = new ConfigCategory("Villager", recipes);
    	villager.put("entity", new Property("entity", "Villager", Property.Type.STRING));
    	villager.put("output", new Property("output", "minecraft:gold_nugget", Property.Type.STRING));
    	villager.put("code", new Property("code", ReserchRegistry.payingTaxes, Property.Type.STRING));
    	
    	ConfigCategory zombie = new ConfigCategory("Zombie", recipes);
    	zombie.put("entity", new Property("entity", "Zombie", Property.Type.STRING));
    	zombie.put("output", new Property("output", "minecraft:rotten_flesh", Property.Type.STRING));
    	zombie.put("code", new Property("code", ReserchRegistry.fleshRapture, Property.Type.STRING));
    	
    	ConfigCategory skeleton = new ConfigCategory("Skeleton", recipes);
    	skeleton.put("entity", new Property("entity", "Skeleton", Property.Type.STRING));
    	skeleton.put("output", new Property("output", "minecraft:bone", Property.Type.STRING));
    	skeleton.put("code", new Property("code", ReserchRegistry.boneWreck, Property.Type.STRING));
    	
    	ConfigCategory spider = new ConfigCategory("Spider", recipes);
    	spider.put("entity", new Property("entity", "Spider", Property.Type.STRING));
    	spider.put("output", new Property("output", "minecraft:string minecraft:spider_eye", Property.Type.STRING));
    	spider.put("code", new Property("code", ReserchRegistry.widowMaker, Property.Type.STRING));
    	
    	ConfigCategory slime = new ConfigCategory("Slime", recipes);
    	slime.put("entity", new Property("entity", "Slime", Property.Type.STRING));
    	slime.put("output", new Property("output", "minecraft:slime_ball", Property.Type.STRING));
    	slime.put("code", new Property("code", ReserchRegistry.puddingSplit, Property.Type.STRING));
    	
    	ConfigCategory enderman = new ConfigCategory("Enderman", recipes);
    	enderman.put("entity", new Property("entity", "Enderman", Property.Type.STRING));
    	enderman.put("output", new Property("output", "minecraft:ender_pearl", Property.Type.STRING));
    	enderman.put("code", new Property("code", ReserchRegistry.darkWarp, Property.Type.STRING));
    }
    
    private static void createPurifierStandard(
			ConfigCategory recipes) {
		
    	ConfigCategory biomassMK1 = new ConfigCategory("Biomass MK1", recipes);
    	biomassMK1.put("code", new Property("code", ReserchRegistry.biomassmk1, Property.Type.STRING));
    	biomassMK1.put("filter", new Property("filter", "BioCristals:blockBiomass", Property.Type.STRING));
    	biomassMK1.put("input", new Property("input", "8xminecraft:dirt", Property.Type.STRING));
    	biomassMK1.put("output", new Property("output", "BioCristals:blockBiomass", Property.Type.STRING));
    	biomassMK1.put("time", new Property("time", "200", Property.Type.INTEGER));
    	
    	ConfigCategory biomassMK2 = new ConfigCategory("Biomass MK2", recipes);
    	biomassMK2.put("code", new Property("code", ReserchRegistry.biomassmk2, Property.Type.STRING));
    	biomassMK2.put("filter", new Property("filter", "BioCristals:blockBiomass", Property.Type.STRING));
    	biomassMK2.put("input", new Property("input", "9xbioMaterial", Property.Type.STRING));
    	biomassMK2.put("output", new Property("output", "BioCristals:blockBiomass", Property.Type.STRING));
    	biomassMK2.put("time", new Property("time", "400", Property.Type.INTEGER));
		
    	ConfigCategory bioBlob = new ConfigCategory("Bio Blob", recipes);
    	bioBlob.put("code", new Property("code", ReserchRegistry.bioBlob, Property.Type.STRING));
    	bioBlob.put("filter", new Property("filter", "BioCristals:itemBioReagent", Property.Type.STRING));
    	bioBlob.put("input", new Property("input", "2xminecraft:gold_ingot 2xBioCristals:crootSapling 10xBioCristals:blockBiomass", Property.Type.STRING));
    	bioBlob.put("output", new Property("output", "BioCristals:itemBioBlob", Property.Type.STRING));
    	bioBlob.put("time", new Property("time", "400", Property.Type.INTEGER));
    	
    	ConfigCategory promogenitus = new ConfigCategory("Promogenitus", recipes);
    	promogenitus.put("code", new Property("code", ReserchRegistry.promogenitus, Property.Type.STRING));
    	promogenitus.put("filter", new Property("filter", "BioCristals:itemBioReagent", Property.Type.STRING));
    	promogenitus.put("input", new Property("input", "4xminecraft:iron_block 8xBioCristals:crootSapling 64xminecraft:dirt", Property.Type.STRING));
    	promogenitus.put("output", new Property("output", "BioCristals:blockPromogenitus", Property.Type.STRING));
    	promogenitus.put("time", new Property("time", "1000", Property.Type.INTEGER));
    	
    	ConfigCategory extractor = new ConfigCategory("Extractor", recipes);
    	extractor.put("code", new Property("code", ReserchRegistry.extractor, Property.Type.STRING));
    	extractor.put("filter", new Property("filter", "BioCristals:blockBiomass", Property.Type.STRING));
    	extractor.put("input", new Property("input", "1xBioCristals:blockReagentPurifier 4xBioCristals:blockCrootHollow 10xBioCristals:blockBiomass", Property.Type.STRING));
    	extractor.put("output", new Property("output", "BioCristals:blockExtractor", Property.Type.STRING));
    	extractor.put("time", new Property("time", "200", Property.Type.INTEGER));
	}
}
