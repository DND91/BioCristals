package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.entity.EntityFruitSpider;
import hok.chompzki.hivetera.entity.EntitySSB;
import hok.chompzki.hivetera.entity.EntityWSB;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class BioEntityRegistry {
	
	
	public void preInit(FMLPreInitializationEvent event) {
		
		EntityRegistry.registerModEntity(EntityWSB.class, "Water Shield Beetle", 4, HiveteraMod.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityFruitSpider.class, "Fruit Spider", 5, HiveteraMod.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntitySSB.class, "Stone Shield Beetle", 6, HiveteraMod.instance, 80, 3, true);
		
	}
}
