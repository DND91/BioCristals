package hok.chompzki.biocristals.registrys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import hok.chompzki.biocristals.research.data.ArticleStorage;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.content.BabySteps;
import hok.chompzki.biocristals.research.logic.content.CrootSapling;
import hok.chompzki.biocristals.research.logic.content.CubeMass;
import hok.chompzki.biocristals.research.logic.content.Reaction;
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

public class ReserchRegistry {
	
	public static final String babySteps = "babySteps"; //Attuner
	public static final String crootSapling = "crootSapling";
	
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
		PlayerStorage.instance();
		ArticleStorage.instance();
		
		//LORE
		ReserchDataNetwork.register(new Research(rabarberpaj, -3, 0, Items.book, new Rabarberpaj(), babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(cara_rot, -3, -2, Items.book, new CaraRot(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(berry, -3, -1, Items.book, new Dberry(), reaction));
		ReserchDataNetwork.register(new Research(carla_fleur, -3, 1, Items.book, new CarlaFleur(), cubeMass));
		
		//RESEARCH
		ReserchDataNetwork.register(new Research(babySteps, 0, 0, ItemRegistry.researchBook, new BabySteps()).setSpecial());
		ReserchDataNetwork.register(new Research(crootSapling, -1, 0, BlockRegistry.crootSapling, new CrootSapling()));
		
		ReserchDataNetwork.register(new Research(cubeMass, -1, 1, BlockRegistry.biomass, new CubeMass(), babySteps, crootSapling));
		ReserchDataNetwork.register(new Research(reaction, 1, 1, ItemRegistry.bioReagent, new Reaction(), babySteps, crootSapling));
		
		ReserchDataNetwork.register(new Research(tuft, 2, 1, BlockRegistry.sulphurTuft, new SulphurTuft(), reaction));
		
		ReserchDataNetwork.register(new Research(sheepSkin, 3, 1, Blocks.wool, new SheepSkin(), tuft));
		
		ReserchDataNetwork.register(new Research(featherFriend, 3, 0, Items.feather, new FeatherFriend(), sheepSkin));
		ReserchDataNetwork.register(new Research(widowMaker, 3, 2, Items.spider_eye, new WidowMaker(), sheepSkin));
		ReserchDataNetwork.register(new Research(pinkBlouse, 4, 0, Items.carrot, new PinkBlouse(), sheepSkin));
		ReserchDataNetwork.register(new Research(puddingSplit, 4, 2, Items.slime_ball, new PuddingSplit(), sheepSkin));
		
		ReserchDataNetwork.register(new Research(fleshRapture, 4, 1, Items.rotten_flesh, new FleshRapture(), puddingSplit));
		ReserchDataNetwork.register(new Research(boneWreck, 5, 0, Items.bone, new BoneWreck(), puddingSplit));
		ReserchDataNetwork.register(new Research(leatherBeast, 5, 2, Items.leather, new LeatherBeast(), puddingSplit));
		ReserchDataNetwork.register(new Research(leatherHound, 5, 1, Items.leather, new LeatherHound(), puddingSplit));
		
		ReserchDataNetwork.register(new Research(darkWarp, 6, 0, Items.ender_pearl, new DarkWarp(), fleshRapture));
		ReserchDataNetwork.register(new Research(payingTaxes, 6, 2, Items.gold_nugget, new PayingTaxes(), leatherBeast));
		
		ReserchDataNetwork.register(new Research(wheatCristalisation, 0, 1, BlockRegistry.wheatCristal, new WheatCristalisation(), cubeMass, reaction));
		
		ReserchDataNetwork.register(new Research(carrotCristalisation, -1, 2, BlockRegistry.carrotCristal, new CarrotCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(reedsCristalisation, 0, 2, BlockRegistry.reedsCristal, new ReedsCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(potatoCristalisation, 1, 2, BlockRegistry.potatoCristal, new PotatoCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(melonCristalisation, -1, 3, BlockRegistry.melonCristal, new MelonCristalisation(), wheatCristalisation));
		ReserchDataNetwork.register(new Research(pumpkinCristalisation, 0, 3, BlockRegistry.pumpkinCristal, new PumpkinCristalisation(), wheatCristalisation));
		
		
		
	}

    
	public void init(FMLInitializationEvent event) {
		ReserchDataNetwork.build();
		
	}

    
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
}
