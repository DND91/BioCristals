package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import hok.chompzki.biocristals.research.data.ArticleStorage;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.content.BabySteps;
import hok.chompzki.biocristals.research.logic.content.BioBlob;
import hok.chompzki.biocristals.research.logic.content.BiomassMK1;
import hok.chompzki.biocristals.research.logic.content.CrootHollow;
import hok.chompzki.biocristals.research.logic.content.CrootSapling;
import hok.chompzki.biocristals.research.logic.content.CrootStem;
import hok.chompzki.biocristals.research.logic.content.CubeMass;
import hok.chompzki.biocristals.research.logic.content.Promogenitus;
import hok.chompzki.biocristals.research.logic.content.Purifier;
import hok.chompzki.biocristals.research.logic.content.Reaction;
import hok.chompzki.biocristals.research.logic.content.ServerContent;
import hok.chompzki.biocristals.research.logic.content.SulphurTuft;
import hok.chompzki.biocristals.research.logic.content.TheWorldAroundUs;
import hok.chompzki.biocristals.research.logic.content.crystallization.CarrotCristalisation;
import hok.chompzki.biocristals.research.logic.content.crystallization.MelonCristalisation;
import hok.chompzki.biocristals.research.logic.content.crystallization.PotatoCristalisation;
import hok.chompzki.biocristals.research.logic.content.crystallization.PumpkinCristalisation;
import hok.chompzki.biocristals.research.logic.content.crystallization.ReedsCristalisation;
import hok.chompzki.biocristals.research.logic.content.crystallization.WheatCristalisation;
import hok.chompzki.biocristals.research.logic.content.flesh.BoneWreck;
import hok.chompzki.biocristals.research.logic.content.flesh.DarkWarp;
import hok.chompzki.biocristals.research.logic.content.flesh.FeatherFriend;
import hok.chompzki.biocristals.research.logic.content.flesh.FleshRapture;
import hok.chompzki.biocristals.research.logic.content.flesh.LeatherBeast;
import hok.chompzki.biocristals.research.logic.content.flesh.LeatherHound;
import hok.chompzki.biocristals.research.logic.content.flesh.PayingTaxes;
import hok.chompzki.biocristals.research.logic.content.flesh.PinkBlouse;
import hok.chompzki.biocristals.research.logic.content.flesh.PuddingSplit;
import hok.chompzki.biocristals.research.logic.content.flesh.SheepSkin;
import hok.chompzki.biocristals.research.logic.content.flesh.WidowMaker;
import hok.chompzki.biocristals.research.logic.content.lore.CaraRot;
import hok.chompzki.biocristals.research.logic.content.lore.CarlaFleur;
import hok.chompzki.biocristals.research.logic.content.lore.Dberry;
import hok.chompzki.biocristals.research.logic.content.lore.Rabarberpaj;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class ReserchRegistry {
	
	public static final String babySteps = "babySteps"; //Attuner
	public static final String crootSapling = "crootSapling";
	
	public static final String crootStem = "crootStem";
	public static final String crootHollow = "crootHollow";
	public static final String purifier = "purifier";
	
	public static final String biomassmk1 = "biomassmk1";
	public static final String promogenitus = "promogenitus";
	public static final String bioBlob = "bioBlob";
	
	public static final String cubeMass = "cubeMass"; //Biomass
	public static final String reaction = "reaction"; //BioReagent
	
	public static final String tuft = "tuft";
	
	public static final String wheatCristalisation = "wheatCristalisation"; //Weak Wheat Cristal
	public static final String carrotCristalisation = "carrotCristalisation";
	public static final String reedsCristalisation = "reedsCristalisation";
	public static final String potatoCristalisation = "potatoCristalisation";
	public static final String melonCristalisation = "melonCristalisation";
	public static final String pumpkinCristalisation = "pumpkinCristalisation";
	
	public static final String grungisCroots = "grubgisCroots"; //Grubgi's Croots
	
	public static final String theWorldAroundUs = "theWorldAroundUs";
	
	public static final String sheepSkin = "sheepSkin";
	
	public static final String boneWreck = "boneWreck";
	public static final String darkWarp = "darkWarp";
	public static final String featherFriend = "featherFriend";
	public static final String fleshRapture = "fleshRapture";
	public static final String leatherBeast = "leatherBeast";
	public static final String leatherHound = "leatherHound";
	public static final String payingTaxes = "payingTaxes";
	public static final String pinkBlouse = "pinkBlouse";
	public static final String puddingSplit = "puddingSplit";
	public static final String widowMaker = "widowMaker";
	
	
	public static final String rabarberpaj = "rabarberpaj";
	public static final String cara_rot = "cara_rot";
	public static final String berry = "dberry";
	public static final String carla_fleur = "carla_fleur";
	
	public void preInit(FMLPreInitializationEvent event) {
		ArticleStorage.instance();
		
		boolean side = event.getSide() == Side.SERVER;
		
		//LORE
		ReserchDataNetwork.register(new Research(rabarberpaj, -3, 0, Items.book, new Rabarberpaj(), babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(cara_rot, -3, -2, Items.book, new CaraRot(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(berry, -3, -1, Items.book, new Dberry(), reaction));
		ReserchDataNetwork.register(new Research(carla_fleur, -3, 1, Items.book, new CarlaFleur(), cubeMass));
		
		//RESEARCH
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.researchBook, side ? new ServerContent() : new BabySteps()).setSpecial());
		ReserchDataNetwork.register(new Research(crootSapling, -1, 0, BlockRegistry.crootSapling,  side ? new ServerContent() : new CrootSapling()));
		
		ReserchDataNetwork.register(new Research(crootStem, -1, -1, BlockRegistry.crootStem,  side ? new ServerContent() : new CrootStem(), cubeMass, reaction));
		ReserchDataNetwork.register(new Research(crootHollow, 0, -1, BlockRegistry.crootHollow,  side ? new ServerContent() : new CrootHollow(), cubeMass, reaction));
		ReserchDataNetwork.register(new Research(purifier, -2, -1, BlockRegistry.reagentPurifier,  side ? new ServerContent() : new Purifier(), cubeMass, reaction));
		
		ReserchDataNetwork.register(new Research(biomassmk1, -2, -2, BlockRegistry.biomass,  side ? new ServerContent() : new BiomassMK1(), purifier));
		ReserchDataNetwork.register(new Research(promogenitus, -2, -4, BlockRegistry.primogenitus,  side ? new ServerContent() : new Promogenitus(), purifier));
		ReserchDataNetwork.register(new Research(bioBlob, -2, -3, ItemRegistry.bioBlob,  side ? new ServerContent() : new BioBlob(), purifier));
		
		ReserchDataNetwork.register(new Research(cubeMass, -1, 1, BlockRegistry.biomass,  side ? new ServerContent() : new CubeMass(), babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(reaction, 1, 1, ItemRegistry.bioReagent,  side ? new ServerContent() : new Reaction(), babySteps, crootSapling));
		
		ReserchDataNetwork.register(new Research(tuft, 2, 1, BlockRegistry.sulphurTuft,  side ? new ServerContent() : new SulphurTuft(), reaction));
		
		ReserchDataNetwork.register(new Research(sheepSkin, 3, 1, Blocks.wool,  side ? new ServerContent() : new SheepSkin(), tuft));
		
		ReserchDataNetwork.register(new Research(featherFriend, 3, 0, Items.feather,  side ? new ServerContent() : new FeatherFriend(), sheepSkin));
		ReserchDataNetwork.register(new Research(widowMaker, 3, 2, Items.spider_eye,  side ? new ServerContent() : new WidowMaker(), sheepSkin));
		ReserchDataNetwork.register(new Research(pinkBlouse, 4, 0, Items.carrot,  side ? new ServerContent() : new PinkBlouse(), sheepSkin));
		ReserchDataNetwork.register(new Research(puddingSplit, 4, 2, Items.slime_ball,  side ? new ServerContent() : new PuddingSplit(), sheepSkin));
		
		ReserchDataNetwork.register(new Research(fleshRapture, 4, 1, Items.rotten_flesh,  side ? new ServerContent() : new FleshRapture(), puddingSplit));
		ReserchDataNetwork.register(new Research(boneWreck, 5, 0, Items.bone,  side ? new ServerContent() : new BoneWreck(), puddingSplit));
		ReserchDataNetwork.register(new Research(leatherBeast, 5, 2, Items.leather, side ? new ServerContent() : new LeatherBeast(), puddingSplit));
		ReserchDataNetwork.register(new Research(leatherHound, 5, 1, Items.leather, side ? new ServerContent() : new LeatherHound(), puddingSplit));
		
		ReserchDataNetwork.register(new Research(darkWarp, 6, 0, Items.ender_pearl, side ? new ServerContent() : new DarkWarp(), fleshRapture));
		ReserchDataNetwork.register(new Research(payingTaxes, 6, 2, Items.gold_nugget, side ? new ServerContent() : new PayingTaxes(), leatherBeast));
		
		ReserchDataNetwork.register(new Research(wheatCristalisation, 0, 1, BlockRegistry.wheatCristal, side ? new ServerContent() : new WheatCristalisation(), cubeMass, reaction));
		
		ReserchDataNetwork.register(new Research(carrotCristalisation, -1, 2, BlockRegistry.carrotCristal, side ? new ServerContent() : new CarrotCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(reedsCristalisation, 0, 2, BlockRegistry.reedsCristal, side ? new ServerContent() : new ReedsCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(potatoCristalisation, 1, 2, BlockRegistry.potatoCristal, side ? new ServerContent() : new PotatoCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(melonCristalisation, -1, 3, BlockRegistry.melonCristal, side ? new ServerContent() : new MelonCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(pumpkinCristalisation, 0, 3, BlockRegistry.pumpkinCristal, side ? new ServerContent() : new PumpkinCristalisation(), wheatCristalisation));
		
		
		
	}

    
	public void init(FMLInitializationEvent event) {
		ReserchDataNetwork.build();
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
