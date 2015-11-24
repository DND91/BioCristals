package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.entity.EntityWSB;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class BioEntityRegistry {
	
	
	public void preInit(FMLPreInitializationEvent event) {
		
		EntityRegistry.registerModEntity(EntityWSB.class, "Water Shield Beetle", 4, BioCristalsMod.instance, 80, 3, true);
		
	}
}
