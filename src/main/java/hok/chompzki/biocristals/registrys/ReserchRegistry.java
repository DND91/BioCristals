package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import hok.chompzki.biocristals.research.data.ArticleStorage;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.content.BabySteps;
import hok.chompzki.biocristals.research.logic.content.CaraRot;
import hok.chompzki.biocristals.research.logic.content.CarlaFleur;
import hok.chompzki.biocristals.research.logic.content.CubeMass;
import hok.chompzki.biocristals.research.logic.content.Dberry;
import hok.chompzki.biocristals.research.logic.content.Rabarberpaj;
import hok.chompzki.biocristals.research.logic.content.Reaction;
import hok.chompzki.biocristals.research.logic.content.TheWorldAroundUs;
import hok.chompzki.biocristals.research.logic.content.WheatCristalisation;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ReserchRegistry {
	
	public static final String babySteps = "babySteps"; //Attuner
	
	public static final String cubeMass = "cubeMass"; //Biomass
	public static final String reaction = "reaction"; //BioReagent
	
	public static final String wheatCristalisation = "wheatCristalisation"; //Weak Wheat Cristal
	
	public static final String theWorldAroundUs = "theWorldAroundUs";
	
	public static final String rabarberpaj = "rabarberpaj";
	public static final String cara_rot = "cara_rot";
	public static final String berry = "dberry";
	public static final String carla_fleur = "carla_fleur";
	
	public void preInit(FMLPreInitializationEvent event) {
		PlayerStorage.instance();
		ArticleStorage.instance();
		
		//LORE
		ReserchDataNetwork.register(new Research(rabarberpaj, -3, 0, Items.book, new Rabarberpaj(), babySteps));
		ReserchDataNetwork.register(new Research(cara_rot, -3, -2, Items.book, new CaraRot(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(berry, -3, -1, Items.book, new Dberry(), reaction));
		ReserchDataNetwork.register(new Research(carla_fleur, -3, 1, Items.book, new CarlaFleur(), cubeMass));
		
		
		//RESEARCH
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.researchBook, new BabySteps()).setSpecial());
		ReserchDataNetwork.register(new Research(cubeMass, -1, 1, BlockRegistry.biomass, new CubeMass(), babySteps));
		ReserchDataNetwork.register(new Research(reaction, 1, 1, ItemRegistry.bioReagent, new Reaction(), babySteps));
		ReserchDataNetwork.register(new Research(wheatCristalisation, 0, 1, BlockRegistry.wheatCristal, new WheatCristalisation(), cubeMass, reaction));
		
		//ReserchDataNetwork.register(new Research(theWorldAroundUs, 4, 0, Blocks.grass, new TheWorldAroundUs()).setSpecial());
		
		/*ReserchDataNetwork.register(new Research("babySteps"));
		ReserchDataNetwork.register(new Research("raptorHeads"));
		ReserchDataNetwork.register(new Research("loneWolf"));
		
		ReserchDataNetwork.register(new Research("cristalization", "babySteps"));
		ReserchDataNetwork.register(new Research("salt", "babySteps"));
		ReserchDataNetwork.register(new Research("carp", "babySteps"));
		ReserchDataNetwork.register(new Research("boonzai", "cristalization", "raptorHeads"));
		ReserchDataNetwork.register(new Research("steel", "boonzai"));*/
		
	}

    
	public void init(FMLInitializationEvent event) {
		ReserchDataNetwork.build();
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
