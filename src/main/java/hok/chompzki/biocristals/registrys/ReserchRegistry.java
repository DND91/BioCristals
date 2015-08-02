package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleStorage;
import hok.chompzki.biocristals.research.data.Category;
import hok.chompzki.biocristals.research.data.Chapeter;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.content.BabySteps;
import hok.chompzki.biocristals.research.logic.content.BookTutorial;
import hok.chompzki.biocristals.research.logic.content.CrootHollow;
import hok.chompzki.biocristals.research.logic.content.CrootSapling;
import hok.chompzki.biocristals.research.logic.content.CrootStem;
import hok.chompzki.biocristals.research.logic.content.CubeMass;
import hok.chompzki.biocristals.research.logic.content.Purifier;
import hok.chompzki.biocristals.research.logic.content.Reaction;
import hok.chompzki.biocristals.research.logic.content.ServerContent;
import hok.chompzki.biocristals.research.logic.content.SulphurTuft;
import hok.chompzki.biocristals.research.logic.content.TheWorldAroundUs;
import hok.chompzki.biocristals.research.logic.content.chapeter.FirstEra;
import hok.chompzki.biocristals.research.logic.content.chapeter.Lore;
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
import hok.chompzki.biocristals.research.logic.content.purifing.BioBlob;
import hok.chompzki.biocristals.research.logic.content.purifing.BiomassMK1;
import hok.chompzki.biocristals.research.logic.content.purifing.Promogenitus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class ReserchRegistry {
	
	
	private static final boolean isServer = FMLCommonHandler.instance().getSide() == Side.SERVER;
	public  static ArticleContent tutorialContent = isServer ? new ServerContent() : new BookTutorial();
	public static Research tutorialResearch;
	
	//Chapeters
	public static final Chapeter loreChapeter = new Chapeter("loreChapeter", "loreChapeter", isServer ? new ServerContent() : new Lore());
	public static final Chapeter firstEra = new Chapeter("firstEra", "firstEra", isServer ? new ServerContent() : new FirstEra());
	public static final Chapeter ageOfConflict = new Chapeter("ageOfConflict", "ageOfConflict", new ServerContent());
	
	
	
	//Categories
	public static final Category tutorialCat = new Category("tutorial", "tutorial", new ServerContent());
	public static final Category fundamental = new Category("fundamental", "fundamental", new ServerContent());
	public static final Category crystallization = new Category("crystallization", "crystallization", new ServerContent());
	public static final Category flesh = new Category("flesh", "flesh", new ServerContent());
	public static final Category structure = new Category("structure", "structure", new ServerContent());
	public static final Category purifing = new Category("purifing", "purifing", new ServerContent());
	public static final Category lore = new Category("lore", "lore", new ServerContent());
	
	
	
	//Reseaches
	public static final String tutorial = "tutorial";
	
	public static final String babySteps = "babySteps"; //Attuner
	public static final String crootSapling = "crootSapling";
	
	public static final String crootStem = "crootStem";
	public static final String crootHollow = "crootHollow";
	public static final String purifier = "purifier";
	
	public static final String biomassmk1 = "biomassmk1";
	public static final String promogenitus = "promogenitus";
	public static final String bioBlob = "bioBlob";
	public static final String extractor = "extractor";
	
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
		ReserchDataNetwork.register(new Research(rabarberpaj, 0, 0, Items.book, new Rabarberpaj(), loreChapeter, lore, babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(cara_rot, 0, 2, Items.book, new CaraRot(), loreChapeter, lore, wheatCristalisation));
		ReserchDataNetwork.register(new Research(berry, -1, 1, Items.book, new Dberry(), loreChapeter, lore, reaction));
		ReserchDataNetwork.register(new Research(carla_fleur, 1, 1, Items.book, new CarlaFleur(), loreChapeter, lore, cubeMass));
		
		//RESEARCH
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.researchBook, side ? new ServerContent() : new BabySteps(), firstEra, fundamental).setSpecial());
		ReserchDataNetwork.register(new Research(crootSapling, -1, 0, BlockRegistry.crootSapling,  side ? new ServerContent() : new CrootSapling(), firstEra, fundamental));
		
		ReserchDataNetwork.register(new Research(crootStem, -1, -1, BlockRegistry.crootStem,  side ? new ServerContent() : new CrootStem(), firstEra, structure, cubeMass, reaction));
		ReserchDataNetwork.register(new Research(crootHollow, 1, -1, BlockRegistry.crootHollow,  side ? new ServerContent() : new CrootHollow(), firstEra, structure, cubeMass, reaction));
		ReserchDataNetwork.register(new Research(purifier, 0, -1, BlockRegistry.reagentPurifier,  side ? new ServerContent() : new Purifier(), firstEra, structure, cubeMass, reaction).setSpecial());
		
		ReserchDataNetwork.register(new Research(biomassmk1, 0, -2, BlockRegistry.biomass,  side ? new ServerContent() : new BiomassMK1(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(promogenitus, -1, -2, BlockRegistry.primogenitus,  side ? new ServerContent() : new Promogenitus(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(bioBlob, -1, -3, ItemRegistry.bioBlob,  side ? new ServerContent() : new BioBlob(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(extractor, 1, -2, BlockRegistry.extractor,  side ? new ServerContent() : new BioBlob(), firstEra, purifing, purifier));
		
		ReserchDataNetwork.register(new Research(cubeMass, -1, 1, BlockRegistry.biomass,  side ? new ServerContent() : new CubeMass(), firstEra, fundamental, babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(reaction, 1, 1, ItemRegistry.bioReagent,  side ? new ServerContent() : new Reaction(), firstEra, fundamental, babySteps, crootSapling));
		
		ReserchDataNetwork.register(new Research(tuft, 1, 0, BlockRegistry.sulphurTuft,  side ? new ServerContent() : new SulphurTuft(), firstEra, fundamental, reaction));
		
		ReserchDataNetwork.register(new Research(sheepSkin, 2, 0, Blocks.wool,  side ? new ServerContent() : new SheepSkin(), firstEra, flesh, tuft).setSpecial());
		
		ReserchDataNetwork.register(new Research(featherFriend, 2, -1, Items.feather,  side ? new ServerContent() : new FeatherFriend(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(widowMaker, 2, 1, Items.spider_eye,  side ? new ServerContent() : new WidowMaker(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(pinkBlouse, 3, -1, Items.carrot,  side ? new ServerContent() : new PinkBlouse(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(puddingSplit, 3, 1, Items.slime_ball,  side ? new ServerContent() : new PuddingSplit(), firstEra, flesh, sheepSkin));
		
		ReserchDataNetwork.register(new Research(fleshRapture, 3, 0, Items.rotten_flesh,  side ? new ServerContent() : new FleshRapture(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(boneWreck, 4, -1, Items.bone,  side ? new ServerContent() : new BoneWreck(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(leatherBeast, 4, 1, Items.saddle, side ? new ServerContent() : new LeatherBeast(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(leatherHound, 4, 0, Items.leather, side ? new ServerContent() : new LeatherHound(), firstEra, flesh, puddingSplit));
		
		ReserchDataNetwork.register(new Research(darkWarp, 5, -1, Items.ender_pearl, side ? new ServerContent() : new DarkWarp(), firstEra, flesh, fleshRapture));
		ReserchDataNetwork.register(new Research(payingTaxes, 5, 1, Items.gold_nugget, side ? new ServerContent() : new PayingTaxes(), firstEra, flesh, leatherBeast));
		
		ReserchDataNetwork.register(new Research(wheatCristalisation, 0, 1, BlockRegistry.wheatCristal, side ? new ServerContent() : new WheatCristalisation(), firstEra, crystallization, cubeMass, reaction).setSpecial());
		
		ReserchDataNetwork.register(new Research(carrotCristalisation, -1, 2, BlockRegistry.carrotCristal, side ? new ServerContent() : new CarrotCristalisation(), firstEra, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(reedsCristalisation, 0, 2, BlockRegistry.reedsCristal, side ? new ServerContent() : new ReedsCristalisation(), firstEra, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(potatoCristalisation, 1, 2, BlockRegistry.potatoCristal, side ? new ServerContent() : new PotatoCristalisation(), firstEra, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(melonCristalisation, -1, 3, BlockRegistry.melonCristal, side ? new ServerContent() : new MelonCristalisation(), firstEra, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(pumpkinCristalisation, 0, 3, BlockRegistry.pumpkinCristal, side ? new ServerContent() : new PumpkinCristalisation(), firstEra, crystallization, wheatCristalisation));
		
		
		tutorialResearch = new Research(tutorial, 0, 1, Items.book, tutorialContent, loreChapeter, tutorialCat);
		ReserchDataNetwork.register(tutorialResearch);
	}

    
	public void init(FMLInitializationEvent event) {
		ReserchDataNetwork.build();
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
