package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.research.data.ArticleStorage;
import hok.chompzki.biocristals.research.data.Category;
import hok.chompzki.biocristals.research.data.Chapeter;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ResearchUnlocks;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.content.BookTutorial;
import hok.chompzki.biocristals.research.logic.content.ServerContent;
import hok.chompzki.biocristals.research.logic.content.chapeter.AgeOfConflict;
import hok.chompzki.biocristals.research.logic.content.chapeter.FirstEra;
import hok.chompzki.biocristals.research.logic.content.chapeter.Lore;
import hok.chompzki.biocristals.research.logic.content.chapeter.Settlement;
import hok.chompzki.biocristals.research.logic.content.chapeter.Underworld;
import hok.chompzki.biocristals.research.logic.content.lore.CaraRot;
import hok.chompzki.biocristals.research.logic.content.lore.CarlaFleur;
import hok.chompzki.biocristals.research.logic.content.lore.Dberry;
import hok.chompzki.biocristals.research.logic.content.lore.Rabarberpaj;
import hok.chompzki.biocristals.research.logic.first_era.BabySteps;
import hok.chompzki.biocristals.research.logic.first_era.ChitinBoots;
import hok.chompzki.biocristals.research.logic.first_era.ChitinChestplate;
import hok.chompzki.biocristals.research.logic.first_era.ChitinHelmet;
import hok.chompzki.biocristals.research.logic.first_era.ChitinLeggings;
import hok.chompzki.biocristals.research.logic.first_era.ChitinPlate;
import hok.chompzki.biocristals.research.logic.first_era.ClayHunter;
import hok.chompzki.biocristals.research.logic.first_era.CrootClaw;
import hok.chompzki.biocristals.research.logic.first_era.CrootStick;
import hok.chompzki.biocristals.research.logic.first_era.Hivebag;
import hok.chompzki.biocristals.research.logic.first_era.KittehBeetle;
import hok.chompzki.biocristals.research.logic.first_era.KraKen;
import hok.chompzki.biocristals.research.logic.first_era.NomadsSack;
import hok.chompzki.biocristals.research.logic.first_era.WSB;
import hok.chompzki.biocristals.research.logic.settlement.Nest;
import hok.chompzki.biocristals.research.logic.settlement.NestingCrootBeetle;
import hok.chompzki.biocristals.research.logic.settlement.NestingCrootClaw;
import hok.chompzki.biocristals.research.logic.settlement.NestingFruitSpider;
import hok.chompzki.biocristals.research.logic.settlement.NestingKittehBettle;
import hok.chompzki.biocristals.research.logic.settlement.NestingKraKen;
import hok.chompzki.biocristals.research.logic.settlement.NestingWSB;
import hok.chompzki.biocristals.research.logic.underworld.IronCrootPickaxe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class ReserchRegistry {
	
	/**
	 * 
	 * DONE - Croot Beetle - Works like bonemeal on plants around it.
	 * DONE - WSB - Turret 10 damage over 500 ticks.
	 * DONE - Croot Claw - Hopper collector
	 * DONE KraKen - Make Animals mature faster.
	 * Kitteh Beetle - Depending on grass around it.. it will spawn random insects as a result?
	 * Clay Hunter - Processing for next stage of blocks, Tier 1.
	 * Hivebag - Processing for next stage of blocks, Tier 2.
	 * 
	 */
	
	
	private static final boolean isServer = FMLCommonHandler.instance().getSide() == Side.SERVER;
	public  static ArticleContent tutorialContent = isServer ? new ServerContent() : new BookTutorial();
	public static Research tutorialResearch;
	
	//Chapeters
	public static final Chapeter loreChapeter = new Chapeter("loreChapeter", "loreChapeter", isServer ? new ServerContent() : new Lore());
	public static final Chapeter firstEra = new Chapeter("firstEra", "firstEra", isServer ? new ServerContent() : new FirstEra());
	public static final Chapeter settlement = new Chapeter("settlement", "settlement", isServer ? new ServerContent() : new Settlement());
	public static final Chapeter underworld = new Chapeter("underworld", "underworld", isServer ? new ServerContent() : new Underworld());
	
	
	//public static final Chapeter ageOfConflict = new Chapeter("ageOfConflict", "ageOfConflict", isServer ? new ServerContent() : new AgeOfConflict());
	
	
	
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
	
	public static final String babySteps = "babySteps";
	public static final String crootStick = "crootStick";
	
	public static final String kraken = "kraken";
	public static final String wsb = "wsb";
	public static final String crootClaw = "crootClaw";
	public static final String hivebag = "hivebag";
	public static final String kittehBeetle = "kittehBeetle";
	public static final String clayHunter = "clayHunter";
	public static final String nomadsSack = "nomadsSack";
	
	public static final String chitinPlate = "chitinPlate";
	public static final String chitinHelmet = "chitinHelmet";
	public static final String chitinChestplate = "chitinChestplate";
	public static final String chitinLeggings = "chitinLeggings";
	public static final String chitinBoots = "chitinBoots";
	
	public static final String nest = "nest";
	public static final String nestCrootBeeltel = "nestCrootBeeltel";
	public static final String nestWSB = "nestWSB";
	public static final String nestCrootClaw = "nestCrootClaw";
	public static final String nestKraKen = "nestKraKen";
	public static final String nestKittehBeetle = "nestKittehBeetle";
	public static final String nestFruitSpider = "nestFruitSpider";
	
	public static String crootBreeder = "crootBreeder";
	
	public static final String crootIronPickaxe = "crootIronPickaxe";
	
	
	public static final String crootSapling = "crootSapling";
	
	
	public static final String rabarberpaj = "rabarberpaj";
	public static final String cara_rot = "cara_rot";
	public static final String berry = "dberry";
	public static final String carla_fleur = "carla_fleur";
	
	public void preInit(FMLPreInitializationEvent event) {
		ArticleStorage.instance();
		
		boolean side = event.getSide() == Side.SERVER;
		
		//LORE
		//ReserchDataNetwork.register(new Research(rabarberpaj, 0, 0, Items.book, new Rabarberpaj(), loreChapeter, lore, babySteps, crootSapling));
		//ReserchDataNetwork.register(new Research(cara_rot, 0, 0, Items.book, new CaraRot(), loreChapeter, lore, wheatCristalisation));
		//ReserchDataNetwork.register(new Research(berry, 0, 0, Items.book, new Dberry(), loreChapeter, lore, reaction));
		//ReserchDataNetwork.register(new Research(carla_fleur, 0, 0, Items.book, new CarlaFleur(), loreChapeter, lore, cubeMass));
		
		/** FIRST AGE **/
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.crootBeetle, side ? new ServerContent() : new BabySteps(), firstEra, fundamental).setSpecial());
		ReserchDataNetwork.register(new Research(crootStick, 0, 1, ItemRegistry.crootStick, side ? new ServerContent() : new CrootStick(), firstEra, fundamental, babySteps));
		
		ReserchDataNetwork.register(new Research(kraken, -1, 0, ItemRegistry.kraKenBug, side ? new ServerContent() : new KraKen(), firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(kittehBeetle, 0, 1, ItemRegistry.kittehBeetle, side ? new ServerContent() : new KittehBeetle(), firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(wsb, 1, 0, ItemRegistry.wsb, side ? new ServerContent() : new WSB(), firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(clayHunter, -1, -1, ItemRegistry.clayHunter, side ? new ServerContent() : new ClayHunter(), firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(crootClaw, -1, 1, ItemRegistry.crootClaw, side ? new ServerContent() : new CrootClaw(), firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(hivebag, 1, 1, ItemRegistry.hivebag, side ? new ServerContent() : new Hivebag(), firstEra, fundamental, crootStick));

		ReserchDataNetwork.register(new Research(chitinPlate, -1, 0, ItemRegistry.chitinPlate, side ? new ServerContent() : new ChitinPlate(), firstEra, fundamental, clayHunter));
		ReserchDataNetwork.register(new Research(chitinHelmet, -1, -1, ItemRegistry.chitinHelmet, side ? new ServerContent() : new ChitinHelmet(), firstEra, fundamental, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinChestplate, -1, 0, ItemRegistry.chitinChestplate, side ? new ServerContent() : new ChitinChestplate(), firstEra, fundamental, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinLeggings, -1, 1, ItemRegistry.chitinLeggings, side ? new ServerContent() : new ChitinLeggings(), firstEra, fundamental, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinBoots, -1, 2, ItemRegistry.chitinBoots, side ? new ServerContent() : new ChitinBoots(), firstEra, fundamental, chitinPlate));
		
		ReserchDataNetwork.register(new Research(nomadsSack, 0, -1, ItemRegistry.nomadSack, side ? new ServerContent() : new NomadsSack(), firstEra, fundamental, babySteps));
		
		//FAMILY ERA
		ReserchDataNetwork.register(new Research(nest, 0, 0, BlockRegistry.nest, side ? new ServerContent() : new Nest(), settlement, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(nestCrootBeeltel, 0, 0, ItemRegistry.crootBeetle, side ? new ServerContent() : new NestingCrootBeetle(), settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestWSB, 0, 0, ItemRegistry.wsb, side ? new ServerContent() : new NestingWSB(), settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestCrootClaw, 0, 0, ItemRegistry.crootClaw, side ? new ServerContent() : new NestingCrootClaw(), settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestKraKen, 0, 0, ItemRegistry.kraKenBug, side ? new ServerContent() : new NestingKraKen(), settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestKittehBeetle, 0, 0, ItemRegistry.kittehBeetle, side ? new ServerContent() : new NestingKittehBettle(), settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestFruitSpider, 0, 0, ItemRegistry.fruitSpider, side ? new ServerContent() : new NestingFruitSpider(), settlement, fundamental, nest));
		
		//UNDERWORLD ERA
		ReserchDataNetwork.register(new Research(crootIronPickaxe, 0, 0, ItemRegistry.crootIronPickaxe, side ? new ServerContent() : new IronCrootPickaxe(), underworld, fundamental, nest));
		
		
		//RESEARCH
		
		/*
		ReserchDataNetwork.register(new Research(babySteps, -1, 0, ItemRegistry.researchBook, side ? new ServerContent() : new BabySteps(), firstEra, fundamental).setSpecial());
		ReserchDataNetwork.register(new Research(crootSapling, 1, 0, BlockRegistry.crootSapling,  side ? new ServerContent() : new CrootSapling(), firstEra, fundamental).setSpecial());
		
		ReserchDataNetwork.register(new Research(cubeMass, 1, 1, BlockRegistry.biomass,  side ? new ServerContent() : new CubeMass(), firstEra, fundamental, babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(reaction, 1, -1, ItemRegistry.bioReagent,  side ? new ServerContent() : new Reaction(), firstEra, fundamental, babySteps, crootSapling));
		
		ReserchDataNetwork.register(new Research(crootStem, 0, 0, BlockRegistry.crootStem,  side ? new ServerContent() : new CrootStem(), firstEra, structure, crootSapling, cubeMass, reaction));
		ReserchDataNetwork.register(new Research(crootHollow, 0, 0, BlockRegistry.crootHollow,  side ? new ServerContent() : new CrootHollow(), firstEra, structure, crootSapling, cubeMass, reaction));
		ReserchDataNetwork.register(new Research(purifier, 2, 0, BlockRegistry.reagentPurifier,  side ? new ServerContent() : new Purifier(), firstEra, structure, crootSapling, cubeMass, reaction).setSpecial());
		
		ReserchDataNetwork.register(new Research(biomassmk1, 0, 0, BlockRegistry.biomass,  side ? new ServerContent() : new BiomassMK1(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(biomassmk2, 0, -1, BlockRegistry.biomass,  side ? new ServerContent() : new BiomassMK2(), firstEra, purifing, biomassmk1));
		ReserchDataNetwork.register(new Research(promogenitus, 0, 0, BlockRegistry.primogenitus,  side ? new ServerContent() : new Promogenitus(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(bioBlob, 0, 0, ItemRegistry.bioBlob,  side ? new ServerContent() : new BioBlob(), firstEra, purifing, purifier));
		ReserchDataNetwork.register(new Research(extractor, 0, 0, BlockRegistry.extractor,  side ? new ServerContent() : new BioBlob(), firstEra, purifing, purifier));
		
		
		ReserchDataNetwork.register(new Research(tuft, 0, -1, BlockRegistry.sulphurTuft,  side ? new ServerContent() : new SulphurTuft(), firstEra, fundamental, reaction));
		
		ReserchDataNetwork.register(new Research(sheepSkin, 0, -1, Blocks.wool,  side ? new ServerContent() : new SheepSkin(), firstEra, flesh, tuft).setSpecial());
		
		ReserchDataNetwork.register(new Research(featherFriend, 0, 0, Items.feather,  side ? new ServerContent() : new FeatherFriend(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(widowMaker, 0, 0, Items.spider_eye,  side ? new ServerContent() : new WidowMaker(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(pinkBlouse, 0, 0, Items.carrot,  side ? new ServerContent() : new PinkBlouse(), firstEra, flesh, sheepSkin));
		ReserchDataNetwork.register(new Research(puddingSplit, 0, 0, Items.slime_ball,  side ? new ServerContent() : new PuddingSplit(), firstEra, flesh, sheepSkin));
		
		ReserchDataNetwork.register(new Research(fleshRapture, 0, 0, Items.rotten_flesh,  side ? new ServerContent() : new FleshRapture(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(boneWreck, 0, 0, Items.bone,  side ? new ServerContent() : new BoneWreck(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(leatherBeast, 0, 0, Items.saddle, side ? new ServerContent() : new LeatherBeast(), firstEra, flesh, puddingSplit));
		ReserchDataNetwork.register(new Research(leatherHound, 0, 0, Items.leather, side ? new ServerContent() : new LeatherHound(), firstEra, flesh, puddingSplit));
		
		ReserchDataNetwork.register(new Research(darkWarp, 0, 0, Items.ender_pearl, side ? new ServerContent() : new DarkWarp(), firstEra, flesh, fleshRapture));
		ReserchDataNetwork.register(new Research(payingTaxes, 0, 0, Items.gold_nugget, side ? new ServerContent() : new PayingTaxes(), firstEra, flesh, leatherBeast));
		
		ReserchDataNetwork.register(new Research(wheatCristalisation, 0, 0, BlockRegistry.wheatCristal, side ? new ServerContent() : new WheatCristalisation(), ageOfConflict, crystallization, cubeMass, reaction).setSpecial());
		
		ReserchDataNetwork.register(new Research(carrotCristalisation, 0, 0, BlockRegistry.carrotCristal, side ? new ServerContent() : new CarrotCristalisation(), ageOfConflict, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(reedsCristalisation, 0, 0, BlockRegistry.reedsCristal, side ? new ServerContent() : new ReedsCristalisation(), ageOfConflict, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(potatoCristalisation, 0, 0, BlockRegistry.potatoCristal, side ? new ServerContent() : new PotatoCristalisation(), ageOfConflict, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(melonCristalisation, 0, 0, BlockRegistry.melonCristal, side ? new ServerContent() : new MelonCristalisation(), ageOfConflict, crystallization, wheatCristalisation));
		ReserchDataNetwork.register(new Research(pumpkinCristalisation, 0, 0, BlockRegistry.pumpkinCristal, side ? new ServerContent() : new PumpkinCristalisation(), ageOfConflict, crystallization, wheatCristalisation));
		*/
		
		tutorialResearch = new Research(tutorial, 0, 0, Items.book, tutorialContent, loreChapeter, tutorialCat);
		ReserchDataNetwork.register(tutorialResearch);
	}
	
    
	public void init(FMLInitializationEvent event) {
		ReserchDataNetwork.build();
		
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.crootStick), crootStick);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.nomadSack), nomadsSack);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(BlockRegistry.nest), nest);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.chitinPlate), chitinPlate);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.chitinHelmet), chitinHelmet);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.chitinChestplate), chitinChestplate);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.chitinLeggings), chitinLeggings);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.chitinBoots), chitinBoots);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.crootIronPickaxe), crootIronPickaxe);
		
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.crootBeetle), babySteps, null, new ItemStack(ItemRegistry.crootBeetle), new ItemStack(Blocks.tallgrass, 1, 1));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.clayHunter), clayHunter, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.clayHunter), new ItemStack(Blocks.clay));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.kraKenBug), kraken, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.kraKenBug), new ItemStack(Blocks.leaves));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.crootClaw), crootClaw, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.crootClaw), new ItemStack(Blocks.cactus));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.kittehBeetle), kittehBeetle, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.kittehBeetle), new ItemStack(Blocks.melon_block));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.hivebag), hivebag, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.hivebag), new ItemStack(Items.reeds));
		ResearchUnlocks.addPickUpUnlock(new ItemStack(ItemRegistry.wsb), wsb, new ItemStack(ItemRegistry.crootStick), new ItemStack(ItemRegistry.wsb), new ItemStack(Blocks.tallgrass, 1, 1));
		
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.crootBeetle), nestCrootBeeltel);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.wsb), nestWSB);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.crootClaw), nestCrootClaw);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.kraKenBug), nestKraKen);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.kittehBeetle), nestKittehBeetle);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.fruitSpider), nestFruitSpider);
		
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
