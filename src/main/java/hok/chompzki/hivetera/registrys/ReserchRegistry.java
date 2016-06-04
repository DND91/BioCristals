package hok.chompzki.hivetera.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.client.book.BookParseTree;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.research.data.ArticleStorage;
import hok.chompzki.hivetera.research.data.Category;
import hok.chompzki.hivetera.research.data.Chapeter;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.content.BookTutorial;
import hok.chompzki.hivetera.research.logic.content.ServerContent;
import hok.chompzki.hivetera.research.logic.content.chapeter.AgeOfConflict;
import hok.chompzki.hivetera.research.logic.content.chapeter.FirstEra;
import hok.chompzki.hivetera.research.logic.content.chapeter.Lore;
import hok.chompzki.hivetera.research.logic.content.chapeter.Settlement;
import hok.chompzki.hivetera.research.logic.content.chapeter.Underworld;
import hok.chompzki.hivetera.research.logic.content.lore.CaraRot;
import hok.chompzki.hivetera.research.logic.content.lore.CarlaFleur;
import hok.chompzki.hivetera.research.logic.content.lore.Dberry;
import hok.chompzki.hivetera.research.logic.content.lore.Rabarberpaj;
import hok.chompzki.hivetera.research.logic.first_era.BabySteps;
import hok.chompzki.hivetera.research.logic.first_era.ChitinBoots;
import hok.chompzki.hivetera.research.logic.first_era.ChitinChestplate;
import hok.chompzki.hivetera.research.logic.first_era.ChitinHelmet;
import hok.chompzki.hivetera.research.logic.first_era.ChitinLeggings;
import hok.chompzki.hivetera.research.logic.first_era.ChitinPlate;
import hok.chompzki.hivetera.research.logic.first_era.ClayHunter;
import hok.chompzki.hivetera.research.logic.first_era.CrootClaw;
import hok.chompzki.hivetera.research.logic.first_era.CrootStick;
import hok.chompzki.hivetera.research.logic.first_era.Hivebag;
import hok.chompzki.hivetera.research.logic.first_era.KittehBeetle;
import hok.chompzki.hivetera.research.logic.first_era.KraKen;
import hok.chompzki.hivetera.research.logic.first_era.NomadsSack;
import hok.chompzki.hivetera.research.logic.first_era.WSB;
import hok.chompzki.hivetera.research.logic.settlement.BreedingClayHunter;
import hok.chompzki.hivetera.research.logic.settlement.BreedingCrootBeetle;
import hok.chompzki.hivetera.research.logic.settlement.BreedingCrootClaw;
import hok.chompzki.hivetera.research.logic.settlement.BreedingFruitSpider;
import hok.chompzki.hivetera.research.logic.settlement.BreedingHivebag;
import hok.chompzki.hivetera.research.logic.settlement.BreedingKittehBeetle;
import hok.chompzki.hivetera.research.logic.settlement.BreedingKraKenBug;
import hok.chompzki.hivetera.research.logic.settlement.BreedingWSB;
import hok.chompzki.hivetera.research.logic.settlement.CrootBreeder;
import hok.chompzki.hivetera.research.logic.settlement.CrootHoe;
import hok.chompzki.hivetera.research.logic.settlement.HungerPortal;
import hok.chompzki.hivetera.research.logic.settlement.Nest;
import hok.chompzki.hivetera.research.logic.settlement.NestingClayHunter;
import hok.chompzki.hivetera.research.logic.settlement.NestingCrootBeetle;
import hok.chompzki.hivetera.research.logic.settlement.NestingCrootClaw;
import hok.chompzki.hivetera.research.logic.settlement.NestingFruitSpider;
import hok.chompzki.hivetera.research.logic.settlement.NestingHivebag;
import hok.chompzki.hivetera.research.logic.settlement.NestingKittehBettle;
import hok.chompzki.hivetera.research.logic.settlement.NestingKraKen;
import hok.chompzki.hivetera.research.logic.settlement.NestingVoidCrawler;
import hok.chompzki.hivetera.research.logic.settlement.NestingWSB;
import hok.chompzki.hivetera.research.logic.settlement.SacrificePit;
import hok.chompzki.hivetera.research.logic.settlement.TokenAssembler;
import hok.chompzki.hivetera.research.logic.settlement.TokenBank;
import hok.chompzki.hivetera.research.logic.settlement.TokenBridge;
import hok.chompzki.hivetera.research.logic.settlement.TokenEater;
import hok.chompzki.hivetera.research.logic.settlement.TokenFeeder;
import hok.chompzki.hivetera.research.logic.settlement.TokenFilter;
import hok.chompzki.hivetera.research.logic.settlement.TokenTransformer;
import hok.chompzki.hivetera.research.logic.settlement.TutorialHunger;
import hok.chompzki.hivetera.research.logic.underworld.IronCrootPickaxe;
import hok.chompzki.hivetera.research.logic.underworld.VoidCrawler;
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
	public static final Chapeter loreChapeter = new Chapeter("loreChapeter", "loreChapeter");
	public static final Chapeter firstEra = new Chapeter("firstEra", "firstEra");
	public static final Chapeter settlement = new Chapeter("settlement", "settlement");
	public static final Chapeter underworld = new Chapeter("underworld", "underworld");
	
	
	//public static final Chapeter ageOfConflict = new Chapeter("ageOfConflict", "ageOfConflict", isServer ? new ServerContent() : new AgeOfConflict());
	
	
	
	//Categories
	public static final Category tutorialCat = new Category("tutorial", "tutorial");
	public static final Category fundamental = new Category("fundamental", "fundamental");
	public static final Category crystallization = new Category("crystallization", "crystallization");
	public static final Category flesh = new Category("flesh", "flesh");
	public static final Category structure = new Category("structure", "structure");
	public static final Category purifing = new Category("purifing", "purifing");
	public static final Category hunger = new Category("hunger", "hunger");
	public static final Category lore = new Category("lore", "lore");
	public static final Category tools = new Category("tools", "tools");
	public static final Category breeding = new Category("breeding", "breeding");
	
	public static final Category offensive = new Category("offensive", "offensive");
	public static final Category defensive = new Category("defensive", "defensive");
	public static final Category passive = new Category("passive", "passive");
	public static final Category functional = new Category("functional", "functional");
	
	//Reseaches
	public static final String tutorial = "tutorial";
	public static final String tutorialHunger = "tutorialHunger";
	
	public static final String babySteps = "babySteps";
	public static final String crootStick = "crootStick";
	
	public static final String kraken = "kraken";
	public static final String wsb = "wsb";
	public static final String crootClaw = "crootClaw";
	public static final String hivebag = "hivebag";
	public static final String kittehBeetle = "kittehBeetle";
	public static final String clayHunter = "clayHunter";
	public static final String nomadsSack = "nomadsSack";
	public static final String voidCrawler = "voidCrawler";
	
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
	public static final String nestHivebag = "nestHivebag";
	public static final String nestVoidCrawler = "nestVoidCrawler";
	public static final String nestClayHunter = "nestClayHunter";
	
	public static final String crootBreeder = "crootBreeder";
	public static final String breedingCrootBeetle = "breedingCrootBeetle";
	public static final String breedingCrootClaw = "breedingCrootClaw";
	public static final String breedingKraKenBug = "breedingKraKenBug";
	public static final String breedingWSB = "breedingWSB";
	public static final String breedingKittehBeetle = "breedingKittehBeetle";
	public static final String breedingClayHunter = "breedingClayHunter";
	public static final String breedingHivebag = "breedingHivebag";
	public static final String breedingFruitSpider = "breedingFruitSpider";
	
	public static final String hungerPortal = "hungerPortal";
	public static final String tokenAssembler = "tokenAssembler";
	public static final String sacrificePit = "sacrificePit";
	
	public static final String tokenBridge = "tokenBridge";
	public static final String tokenFilter = "tokenFilter";
	public static final String tokenTransformer = "tokenTransformer";
	public static final String tokenFeeder = "tokenFeeder";
	public static final String tokenEater = "tokenEater";
	public static final String tokenBank = "tokenBank";
	
	public static final String crootHoe = "crootHoe";
	
	
	public static final String chitinHelmetDevonian = "chitinHelmetDevonian";
	public static final String chitinChestplateDevonian = "chitinChestplateDevonian";
	public static final String chitinLeggingsDevonian = "chitinLeggingsDevonian";
	public static final String chitinBootsDevonian = "chitinBootsDevonian";
	
	public static final String lightbringer = "lightbringer";
	public static final String honeyWidow = "honeyWidow";
	public static final String armorAttuner = "armorAttuner";
	public static final String clayFoamer = "clayFoamer";
	public static final String sprinter = "sprinter";
	public static final String darter = "darter";
	public static final String dragonShell = "dragonShell";
	public static final String waterHunter = "waterHunter";
	public static final String hillSpider = "hillSpider";
	public static final String fireSprinter = "fireSprinter";
	public static final String deathsWing = "deathsWing";
	public static final String carpaceSlug = "carpaceSlug";
	public static final String carpaceSnail = "carpaceSnail";
	public static final String lifeShader = "lifeShader";
	public static final String voidFarer = "voidFarer";
	public static final String sludgeGrim = "sludgeGrim";
	public static final String bullWorm = "bullWorm";
	public static final String ssb = "ssb";
	public static final String blackLeech = "blackLeech";
	public static final String hungerSwarm = "hungerSwarm";
	public static final String greenBlower = "greenBlower";
	public static final String priestBeetle = "priestBeetle";
	public static final String colonialSlug = "colonialSlug";
	public static final String blueHorder = "blueHorder";
	public static final String hungerLarva = "hungerLarva";
	public static final String buttScotch = "buttScotch";
	
	
	
	
	
	
	public static final String crootSapling = "crootSapling";
	
	
	public static final String rabarberpaj = "rabarberpaj";
	public static final String cara_rot = "cara_rot";
	public static final String berry = "dberry";
	public static final String carla_fleur = "carla_fleur";
	
	public void preInit(FMLPreInitializationEvent event) {
		ArticleStorage.instance();
		
		boolean side = event.getSide() == Side.SERVER;
		
		//LORE
		tutorialResearch = new Research(tutorial, 0, 0, Items.book, loreChapeter, tutorialCat);
		ReserchDataNetwork.register(tutorialResearch);
		
		ReserchDataNetwork.register(new Research(tutorialHunger, 0, 0, ItemRegistry.hiveBrain, loreChapeter, tutorialCat, hungerPortal));
		
		/** FIRST AGE **/
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.crootBeetle, firstEra, fundamental).setSpecial());
		ReserchDataNetwork.register(new Research(crootStick, 0, 1, ItemRegistry.crootStick, firstEra, tools, babySteps));
		
		ReserchDataNetwork.register(new Research(kraken, -1, 0, ItemRegistry.kraKenBug, firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(kittehBeetle, 0, 1, ItemRegistry.kittehBeetle, firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(wsb, 1, 0, ItemRegistry.wsb, firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(clayHunter, -1, -1, ItemRegistry.clayHunter, firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(crootClaw, -1, 1, ItemRegistry.crootClaw, firstEra, fundamental, crootStick));
		ReserchDataNetwork.register(new Research(hivebag, 1, 1, ItemRegistry.hivebag, firstEra, fundamental, crootStick));

		ReserchDataNetwork.register(new Research(chitinPlate, -1, 0, ItemRegistry.chitinPlate, firstEra, tools, clayHunter));
		ReserchDataNetwork.register(new Research(chitinHelmet, -1, -1, ItemRegistry.chitinHelmet, firstEra, tools, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinChestplate, -1, 0, ItemRegistry.chitinChestplate, firstEra, tools, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinLeggings, -1, 1, ItemRegistry.chitinLeggings, firstEra, tools, chitinPlate));
		ReserchDataNetwork.register(new Research(chitinBoots, -1, 2, ItemRegistry.chitinBoots, firstEra, tools, chitinPlate));
		
		ReserchDataNetwork.register(new Research(nomadsSack, 0, -1, ItemRegistry.nomadSack, firstEra, tools, babySteps));
		
		//FAMILY ERA
		ReserchDataNetwork.register(new Research(nest, 3, 0, BlockRegistry.nest, settlement, fundamental, crootStick).setSpecial());
		ReserchDataNetwork.register(new Research(nestCrootBeeltel, 0, 0, ItemRegistry.crootBeetle, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestWSB, 0, 0, ItemRegistry.wsb, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestCrootClaw, 0, 0, ItemRegistry.crootClaw, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestKraKen, 0, 0, ItemRegistry.kraKenBug, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestKittehBeetle, 0, 0, ItemRegistry.kittehBeetle, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestFruitSpider, 0, 0, ItemRegistry.fruitSpider, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestHivebag, 0, 0, ItemRegistry.hivebag, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestVoidCrawler, 0, 0, ItemRegistry.voidCrawler, settlement, fundamental, nest));
		ReserchDataNetwork.register(new Research(nestClayHunter, 0, 0, ItemRegistry.clayHunter, settlement, fundamental, nest));
		
		
		ReserchDataNetwork.register(new Research(hungerPortal, -4, 0, ItemRegistry.hiveBrain , settlement, hunger, nest).setSpecial());
		ReserchDataNetwork.register(new Research(tokenAssembler, -1, 0, BlockRegistry.tokenAssembler , settlement, hunger, hungerPortal));
		ReserchDataNetwork.register(new Research(sacrificePit, 1, 0, BlockRegistry.sacrificePit , settlement, hunger, hungerPortal));
		ReserchDataNetwork.register(new Research(tokenBridge, 0, 0, ItemRegistry.tokenBridge , settlement, hunger, tokenAssembler));
		ReserchDataNetwork.register(new Research(tokenFilter, 0, 0, ItemRegistry.tokenFilter , settlement, hunger, tokenAssembler));
		ReserchDataNetwork.register(new Research(tokenTransformer, 0, 0, ItemRegistry.tokenTransformer , settlement, hunger, tokenAssembler));
		ReserchDataNetwork.register(new Research(tokenFeeder, 0, 0, ItemRegistry.tokenFeeder , settlement, hunger, tokenAssembler));
		ReserchDataNetwork.register(new Research(tokenEater, 0, 0, ItemRegistry.tokenEater , settlement, hunger, tokenAssembler));
		ReserchDataNetwork.register(new Research(tokenBank, 0, 0, ItemRegistry.tokenBank , settlement, hunger, tokenAssembler));
		
		ReserchDataNetwork.register(new Research(crootHoe, 1, 3, ItemRegistry.crootWoodHoe, settlement, tools, crootStick).setSpecial());
		
		
		ReserchDataNetwork.register(new Research(crootBreeder, 1, -3, BlockRegistry.crootBreeder, settlement, breeding, crootStick).setSpecial());
		ReserchDataNetwork.register(new Research(breedingCrootBeetle, 0, 0, ItemRegistry.crootBeetle, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingCrootClaw, 0, 0, ItemRegistry.crootClaw, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingKraKenBug, 0, 0, ItemRegistry.kraKenBug, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingWSB, 0, 0, ItemRegistry.wsb, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingKittehBeetle, 0, 0, ItemRegistry.kittehBeetle, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingClayHunter, 0, 0, ItemRegistry.clayHunter, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingHivebag, 0, 0, ItemRegistry.hivebag, settlement, breeding, crootBreeder));
		ReserchDataNetwork.register(new Research(breedingFruitSpider, 0, 0, ItemRegistry.fruitSpider, settlement, breeding, crootBreeder));
		
		
		//UNDERWORLD ERA
		
		//ARMOR DEVONIAN
		int x = -2;
		int y = 0;
		ItemStack armor = new ItemStack(ItemRegistry.chitinHelmet);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ReserchDataNetwork.register(new Research(chitinHelmetDevonian, x, y-1, armor, underworld, breeding, crootBreeder, hungerPortal));
		armor = new ItemStack(ItemRegistry.chitinChestplate);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ReserchDataNetwork.register(new Research(chitinChestplateDevonian, x, y, armor, underworld, breeding, crootBreeder, hungerPortal));
		armor = new ItemStack(ItemRegistry.chitinLeggings);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ReserchDataNetwork.register(new Research(chitinLeggingsDevonian, x, y+1, armor, underworld, breeding, crootBreeder, hungerPortal));
		armor = new ItemStack(ItemRegistry.chitinBoots);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ReserchDataNetwork.register(new Research(chitinBootsDevonian, x, y+2, armor, underworld, breeding, crootBreeder, hungerPortal));
		
		//UNDERWORLD
		ReserchDataNetwork.register(new Research(armorAttuner, x-1, y, ItemRegistry.armorAttuner, underworld, fundamental, crootBreeder));
		ReserchDataNetwork.register(new Research(voidCrawler, 0, y-1, ItemRegistry.voidCrawler, underworld, breeding, armorAttuner));
		ReserchDataNetwork.register(new Research(honeyWidow, 0, y+1, ItemRegistry.honeyWidow, underworld, breeding, armorAttuner));
		
		x = 1;
		ReserchDataNetwork.register(new Research(lightbringer, x+1, y-1, ItemRegistry.lightBringer, underworld, functional, armorAttuner));
		ReserchDataNetwork.register(new Research(clayFoamer, x+2, y-1, ItemRegistry.clayFoamer, underworld, functional, armorAttuner));
		ReserchDataNetwork.register(new Research(colonialSlug, x+3, y-1, ItemRegistry.colonialSlug, underworld, functional, armorAttuner));
		ReserchDataNetwork.register(new Research(blueHorder, x+4, y-1, ItemRegistry.blueHorder, underworld, functional, armorAttuner));
		ReserchDataNetwork.register(new Research(hungerLarva, x+5, y-1, ItemRegistry.hungerLarva, underworld, functional, armorAttuner));
		ReserchDataNetwork.register(new Research(buttScotch, x+6, y-1, ItemRegistry.medusaEye, underworld, functional, armorAttuner));
		
		ReserchDataNetwork.register(new Research(deathsWing, x+1, y+0, ItemRegistry.deathsWing, underworld, defensive, armorAttuner));
		ReserchDataNetwork.register(new Research(carpaceSlug, x+2, y+0, ItemRegistry.carpaceSlug, underworld, defensive, armorAttuner));
		ReserchDataNetwork.register(new Research(carpaceSnail, x+3, y+0, ItemRegistry.carpaceSnail, underworld, defensive, armorAttuner));
		ReserchDataNetwork.register(new Research(lifeShader, x+4, y+0, ItemRegistry.lifeShader, underworld, defensive, armorAttuner));
		ReserchDataNetwork.register(new Research(voidFarer, x+5, y+0, ItemRegistry.voidFarer, underworld, defensive, armorAttuner));
		ReserchDataNetwork.register(new Research(sludgeGrim, x+6, y+0, ItemRegistry.sludgeGrim, underworld, defensive, armorAttuner));
		
		ReserchDataNetwork.register(new Research(bullWorm, x+1, y+1, ItemRegistry.bullWorm, underworld, offensive, armorAttuner));
		ReserchDataNetwork.register(new Research(ssb, x+2, y+1, ItemRegistry.ssb, underworld, offensive, armorAttuner));
		ReserchDataNetwork.register(new Research(blackLeech, x+3, y+1, ItemRegistry.blackLeech, underworld, offensive, armorAttuner));
		ReserchDataNetwork.register(new Research(hungerSwarm, x+4, y+1, ItemRegistry.hungerSwarm, underworld, offensive, armorAttuner));
		ReserchDataNetwork.register(new Research(greenBlower, x+5, y+1, ItemRegistry.greenBlower, underworld, offensive, armorAttuner));
		ReserchDataNetwork.register(new Research(priestBeetle, x+6, y+1, ItemRegistry.priestBeetle, underworld, offensive, armorAttuner));
		
		ReserchDataNetwork.register(new Research(sprinter, x+1, y+2, ItemRegistry.sprinter, underworld, passive, armorAttuner));
		ReserchDataNetwork.register(new Research(darter, x+2, y+2, ItemRegistry.darter, underworld, passive, armorAttuner));
		ReserchDataNetwork.register(new Research(dragonShell, x+3, y+2, ItemRegistry.dragonShell, underworld, passive, armorAttuner));
		ReserchDataNetwork.register(new Research(waterHunter, x+4, y+2, ItemRegistry.waterHunter, underworld, passive, armorAttuner));
		ReserchDataNetwork.register(new Research(hillSpider, x+5, y+2, ItemRegistry.hillSpider, underworld, passive, armorAttuner));
		ReserchDataNetwork.register(new Research(fireSprinter, x+6, y+2, ItemRegistry.fireSprinter, underworld, passive, armorAttuner));
		
		
		
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
		ResearchUnlocks.addCraftingUnlock(new ItemStack(BlockRegistry.crootBreeder), crootBreeder);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.crootWoodHoe), crootHoe);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(BlockRegistry.sacrificePit), sacrificePit);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(BlockRegistry.tokenAssembler), tokenAssembler);
		ResearchUnlocks.addCraftingUnlock(new ItemStack(ItemRegistry.hiveBrain), hungerPortal);
		
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
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.hivebag), nestHivebag);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.voidCrawler), nestVoidCrawler);
		ResearchUnlocks.addPutInNestUnlock(new ItemStack(ItemRegistry.clayHunter), nestClayHunter);
		
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.crootBeetle), breedingCrootBeetle);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.crootClaw), breedingCrootClaw);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.kraKenBug), breedingKraKenBug);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.wsb), breedingWSB);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.kittehBeetle), breedingKittehBeetle);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.clayHunter), breedingClayHunter);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.hivebag), breedingHivebag);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.fruitSpider), breedingFruitSpider);
		ResearchUnlocks.addBreedingUnlock(new ItemStack(ItemRegistry.voidCrawler), voidCrawler);
		
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenBridge), tokenBridge);
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenFilter), tokenFilter);
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenTransformer), tokenTransformer);
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenFeeder), tokenFeeder);
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenEater), tokenEater);
		ResearchUnlocks.addTokenAssemblyUnlock(new ItemStack(ItemRegistry.tokenBank), tokenBank);
		
		
		//ARMOR RED
		ItemStack armor = new ItemStack(ItemRegistry.chitinHelmet);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ResearchUnlocks.addBreedingUnlock(armor, chitinHelmetDevonian);
		armor = new ItemStack(ItemRegistry.chitinChestplate);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ResearchUnlocks.addBreedingUnlock(armor, chitinChestplateDevonian);
		armor = new ItemStack(ItemRegistry.chitinLeggings);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ResearchUnlocks.addBreedingUnlock(armor, chitinLeggingsDevonian);
		armor = new ItemStack(ItemRegistry.chitinBoots);
		armor.getItem().onCreated(armor, null, null);
		armor.stackTagCompound.setString("PATTERN", ArmorPatternRegistry.pattern_names[1]);
		ResearchUnlocks.addBreedingUnlock(armor, chitinBootsDevonian);
		
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
