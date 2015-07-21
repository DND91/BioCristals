package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.recipes.RecipeData;

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
	
	public static Configuration config;
	
	public static List<RecipeData> recipeData = new ArrayList<RecipeData>();
	
	
	public static void preinit(File configFile) {

        if (config == null) {
            config = new Configuration(configFile);}
        loadConfig();
    }
	
	@SubscribeEvent
    public void OnConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(BioCristalsMod.MODID)) {
            loadConfig();
        }

    }
	
    private static void loadConfig() {
    	maxBlocksCollector = config.getInt("Max Block Search (Collector)", "Track ranges", 81, 10, 500, "");
    	maxBlocksCatalystInjector = config.getInt("Max Block Search (Catalyst Injector)", "Track ranges", 81, 10, 500, "");
    	weakCristalGrowthChance = config.getInt("Weakcristal", "Growth chances", 10, 5, 1000, "");
    	
    	recipeData.clear();
    	ConfigCategory recipes = config.getCategory("Recipes");
    	recipes.setComment("All recipes for the mod...");
    	recipes.setRequiresMcRestart(true);
    	
    	if(recipes.getChildren().size() <= 0){
    		createStandard(recipes);
    	}
    	
    	for(ConfigCategory recipe : recipes.getChildren()){
    		recipeData.add(new RecipeData(recipe.get("input").getString(), recipe.get("output").getString(), recipe.get("quantity").getInt()));
    	}
    	
    	config.save();
    }
    
    private static void createStandard(ConfigCategory recipes){
    	ConfigCategory collector = new ConfigCategory("Collector", recipes);
		collector.put("input", new Property("input", "minecraft:log__empty__minecraft:log__minecraft:log__empty__minecraft:log__minecraft:string__minecraft:sapling__minecraft:string", Property.Type.STRING));
		collector.put("output", new Property("output", "BioCristals:itemCollector", Property.Type.STRING));
		collector.put("quantity", new Property("quantity", "1", Property.Type.INTEGER));
		
		ConfigCategory attuner = new ConfigCategory("Attuner", recipes);
		attuner.put("input", new Property("input", "minecraft:stick__minecraft:string__minecraft:stick__minecraft:string__empty__minecraft:string__minecraft:stick__minecraft:sapling__minecraft:stick", Property.Type.STRING));
		attuner.put("output", new Property("output", "BioCristals:itemAttuner", Property.Type.STRING));
		attuner.put("quantity", new Property("quantity", "1", Property.Type.INTEGER));
		
		ConfigCategory bioReagent = new ConfigCategory("Biological Reagent", recipes);
		bioReagent.put("input", new Property("input", "minecraft:stick__minecraft:sapling__minecraft:stick__minecraft:sapling__minecraft:dirt__minecraft:sapling__minecraft:stick__minecraft:sapling__minecraft:stick", Property.Type.STRING));
		bioReagent.put("output", new Property("output", "BioCristals:itemBioReagent", Property.Type.STRING));
		bioReagent.put("quantity", new Property("quantity", "4", Property.Type.INTEGER));
		
		ConfigCategory biomass = new ConfigCategory("Biomass", recipes);
		biomass.put("input", new Property("input", "minecraft:apple__minecraft:sapling__minecraft:apple__minecraft:sapling__minecraft:log__minecraft:sapling__minecraft:apple__minecraft:sapling__minecraft:apple", Property.Type.STRING));
		biomass.put("output", new Property("output", "BioCristals:blockBiomass", Property.Type.STRING));
		biomass.put("quantity", new Property("quantity", "4", Property.Type.INTEGER));
		
		ConfigCategory catalystInjector = new ConfigCategory("Catalyst Injector", recipes);
		catalystInjector.put("input", new Property("input", "BioCristals:itemBioReagent__minecraft:slime_ball__BioCristals:itemBioReagent__minecraft:slime_ball__minecraft:sapling__minecraft:slime_ball__BioCristals:itemBioReagent__minecraft:slime_ball__BioCristals:itemBioReagent", Property.Type.STRING));
		catalystInjector.put("output", new Property("output", "BioCristals:itemCatalystInjector", Property.Type.STRING));
		catalystInjector.put("quantity", new Property("quantity", "1", Property.Type.INTEGER));
		
		ConfigCategory sulphurTuft = new ConfigCategory("Sulphur Tuft", recipes);
		sulphurTuft.put("input", new Property("input", "minecraft:spider_eye__minecraft:spider_eye__minecraft:spider_eye__BioCristals:blockBiomass__BioCristals:blockBiomass__BioCristals:blockBiomass__BioCristals:itemBioReagent__BioCristals:itemBioReagent__BioCristals:itemBioReagent", Property.Type.STRING));
		sulphurTuft.put("output", new Property("output", "BioCristals:blockSulphurTuft", Property.Type.STRING));
		sulphurTuft.put("quantity", new Property("quantity", "1", Property.Type.INTEGER));
		
		
    }
}
