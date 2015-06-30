package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigRegistry {
	
	public static int maxBlocksCollector = 81;
	
	public static Configuration config;
	
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
    	
    }
}
