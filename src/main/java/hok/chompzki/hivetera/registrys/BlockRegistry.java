package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.CrootHelper;
import hok.chompzki.hivetera.blocks.BlockBiomass;
import hok.chompzki.hivetera.blocks.BlockCorePlasma;
import hok.chompzki.hivetera.blocks.BlockCrootBreeder;
import hok.chompzki.hivetera.blocks.BlockCrootCore;
import hok.chompzki.hivetera.blocks.BlockCrootHollow;
import hok.chompzki.hivetera.blocks.BlockCrootLeaves;
import hok.chompzki.hivetera.blocks.BlockCrootNest;
import hok.chompzki.hivetera.blocks.BlockCrootRoots;
import hok.chompzki.hivetera.blocks.BlockCrootSapling;
import hok.chompzki.hivetera.blocks.BlockCrootStreamStem;
import hok.chompzki.hivetera.blocks.BlockCrootTrunk;
import hok.chompzki.hivetera.blocks.BlockExtractor;
import hok.chompzki.hivetera.blocks.BlockGhost;
import hok.chompzki.hivetera.blocks.BlockLightGoo;
import hok.chompzki.hivetera.blocks.BlockMembrane;
import hok.chompzki.hivetera.blocks.BlockNest;
import hok.chompzki.hivetera.blocks.BlockPlatformer;
import hok.chompzki.hivetera.blocks.BlockPrimogenitus;
import hok.chompzki.hivetera.blocks.BlockReagentPurifier;
import hok.chompzki.hivetera.blocks.BlockReplacer;
import hok.chompzki.hivetera.blocks.BlockReplacerOpen;
import hok.chompzki.hivetera.blocks.BlockSacrificePit;
import hok.chompzki.hivetera.blocks.BlockShell;
import hok.chompzki.hivetera.blocks.BlockStructer;
import hok.chompzki.hivetera.blocks.BlockSulphurTuft;
import hok.chompzki.hivetera.blocks.BlockTokenAssembler;
import hok.chompzki.hivetera.blocks.BlockWeakCristal;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.fluids.FluidBlood;
import hok.chompzki.hivetera.items.ItemAttuner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistry {
	
	public static Fluid fluidBlood = new Fluid("blood");
	
	public static Block biomass = null;
	public static Block wheatCristal = null;
	public static Block carrotCristal = null;
	public static Block reedsCristal = null;
	public static Block potatoCristal = null;
	public static Block melonCristal = null;
	public static Block pumpkinCristal = null;
	public static Block sulphurTuft = null;
	public static Block reagentPurifier = null;
	public static Block primogenitus = null;
	public static Block crootSapling = null;
	public static Block crootRoots = null;
	public static Block crootCore = null;
	public static Block crootLeaves = null;
	public static Block crootTrunk = null;
	public static Block crootStem = null;
	public static Block crootHollow = null;
	public static Block extractor = null;
	public static Block ghost = null;
	public static Block replacer = null;
	public static Block replacerOpen = null;
	public static Block lightGoo = null;
	
	public static Block structer = null;
	
	
	public static Block platformer = null;
	public static Block bioplatform = null;
	
	
	public static Block corePlasma = null;
	public static Block membrane = null;
	public static Block shell = null;
	public static Block blood = null;
	public static Block crootNest = null;
	public static Block nest = null;
	public static Block crootBreeder = null;
	public static Block tokenAssembler = null;
	public static Block sacrificePit = null;
	
	public void registerBlocks(){
		
		FluidRegistry.registerFluid(fluidBlood);
		fluidBlood.setLuminosity(5);
		fluidBlood.setDensity(8000);
		fluidBlood.setTemperature(600);
		fluidBlood.setViscosity(8000);
		
		
		biomass = new BlockBiomass();
		int maxMeta = 3;
		wheatCristal = new BlockWeakCristal("blockWheatCristal", maxMeta, 0xF0E68C, new ItemStack(Items.wheat, 2));
		carrotCristal = new BlockWeakCristal("blockCarrotCristal", maxMeta, 0xFFA500, new ItemStack(Items.carrot, 2));
		reedsCristal = new BlockWeakCristal("blockSugerCaneCristal", maxMeta, 0x006400, new ItemStack(Items.reeds, 2));
		potatoCristal = new BlockWeakCristal("blockPotatoCristal", maxMeta, 0xF4A460, new ItemStack(Items.potato, 2));
		melonCristal = new BlockWeakCristal("blockMelonCristal", maxMeta, 0x458B00, new ItemStack(Items.melon, 2));
		pumpkinCristal = new BlockWeakCristal("blockPumpkinCristal", maxMeta, 0xFF7619, new ItemStack(Blocks.pumpkin, 1));
		sulphurTuft = new BlockSulphurTuft();
		reagentPurifier = new BlockReagentPurifier();
		primogenitus = new BlockPrimogenitus();
		crootSapling = new BlockCrootSapling();
		crootRoots = new BlockCrootRoots();
		crootCore = new BlockCrootCore();
		crootLeaves = new BlockCrootLeaves();
		crootTrunk = new BlockCrootTrunk();
		crootStem = new BlockCrootStreamStem();
		crootHollow = new BlockCrootHollow();
		extractor = new BlockExtractor();
		ghost = new BlockGhost();
		corePlasma = new BlockCorePlasma();
		membrane = new BlockMembrane();
		shell = new BlockShell();
		blood = new FluidBlood(fluidBlood, Material.water);
		crootNest = new BlockCrootNest();
		nest = new BlockNest();
		crootBreeder = new BlockCrootBreeder();
		replacer = new BlockReplacer();
		replacerOpen = new BlockReplacerOpen();
		tokenAssembler = new BlockTokenAssembler();
		sacrificePit = new BlockSacrificePit();
		lightGoo = new BlockLightGoo();
		
		
		CrootModule module = new CrootModule();
		
		CrootHelper.addLine(module, 3, 1000, 0, 0, 0, BlockRegistry.corePlasma, 0);
		
		CrootHelper.addLine(module, 3, 1000, -1, 0, -1, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000, -1, 0,  0, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000, -1, 0,  1, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  0, 0, -1, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  0, 0,  0, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  0, 0,  1, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  1, 0, -1, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  1, 0,  0, BlockRegistry.membrane, 0);
		CrootHelper.addLine(module, 3, 1000,  1, 0,  1, BlockRegistry.membrane, 0);
		
		CrootHelper.addLine(module, 3, 1000,  -2, 0, -1, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,  -2, 0,  0, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,  -2, 0,  1, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   2, 0, -1, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   2, 0,  0, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   2, 0,  1, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,  -1, 0, -2, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   0, 0, -2, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   1, 0, -2, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,  -1, 0,  2, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   0, 0,  2, BlockRegistry.shell, 0);
		CrootHelper.addLine(module, 3, 1000,   1, 0,  2, BlockRegistry.shell, 0);
		
		CrootHelper.addCircle(module, 0, 3, 0, 1, BlockRegistry.membrane, 0, 1000, BlockRegistry.membrane, 0, 1000);
		CrootHelper.addCircle(module, 0, 3, 0, 2, BlockRegistry.shell, 0, 1000, BlockRegistry.shell, 0, 1000);
		
		CrootHelper.addCircle(module, 0, 4, 0, 1, BlockRegistry.shell, 0, 1000, BlockRegistry.shell, 0, 1000);
		
		structer = new BlockStructer(module);
		
		module = new CrootModule();
		CrootHelper.addCircle(module, 0, 0, 0, 4, BlockRegistry.shell, 0, 1000, BlockRegistry.shell, 0, 1000);
		
		platformer = new BlockPlatformer("", module, 4, 1, 4);
		
		module = new CrootModule();
		CrootHelper.addCircle(module, 0, 0, 0, 4, BlockRegistry.shell, 0, 1000, BlockRegistry.biomass, 0, 1000);
		
		bioplatform = new BlockPlatformer("_bio", module, 4, 1, 4);
		
		GameRegistry.registerBlock(biomass, BlockBiomass.NAME);
		GameRegistry.registerBlock(wheatCristal, "blockWheatCristal");
		GameRegistry.registerBlock(carrotCristal, "blockCarrotCristal");
		GameRegistry.registerBlock(reedsCristal, "blockSugerCaneCristal");
		GameRegistry.registerBlock(potatoCristal, "blockPotatoCristal");
		GameRegistry.registerBlock(melonCristal, "blockMelonCristal");
		GameRegistry.registerBlock(pumpkinCristal, "blockPumpkinCristal");
		GameRegistry.registerBlock(sulphurTuft, BlockSulphurTuft.NAME);
		GameRegistry.registerBlock(reagentPurifier, BlockReagentPurifier.NAME);
		GameRegistry.registerBlock(primogenitus, BlockPrimogenitus.NAME);
		GameRegistry.registerBlock(crootSapling, BlockCrootSapling.NAME);
		GameRegistry.registerBlock(crootRoots, BlockCrootRoots.NAME);
		GameRegistry.registerBlock(crootCore, BlockCrootCore.NAME);
		GameRegistry.registerBlock(crootLeaves, BlockCrootLeaves.NAME);
		GameRegistry.registerBlock(crootTrunk, BlockCrootTrunk.NAME);
		GameRegistry.registerBlock(crootStem, BlockCrootStreamStem.NAME);
		GameRegistry.registerBlock(crootHollow, BlockCrootHollow.NAME);
		GameRegistry.registerBlock(extractor, BlockExtractor.NAME);
		
		GameRegistry.registerBlock(ghost, BlockGhost.NAME);
		GameRegistry.registerBlock(structer, BlockStructer.NAME);
		GameRegistry.registerBlock(corePlasma, BlockCorePlasma.NAME);
		GameRegistry.registerBlock(membrane, BlockMembrane.NAME);
		GameRegistry.registerBlock(shell, BlockShell.NAME);
		GameRegistry.registerBlock(blood, FluidBlood.NAME);
		GameRegistry.registerBlock(platformer, BlockPlatformer.NAME);
		GameRegistry.registerBlock(bioplatform, BlockPlatformer.NAME + "_bio");
		GameRegistry.registerBlock(crootNest, BlockCrootNest.NAME);
		GameRegistry.registerBlock(nest, BlockNest.NAME);
		GameRegistry.registerBlock(crootBreeder, BlockCrootBreeder.NAME);
		GameRegistry.registerBlock(replacer, BlockReplacer.NAME);
		GameRegistry.registerBlock(replacerOpen, BlockReplacerOpen.NAME);
		GameRegistry.registerBlock(tokenAssembler, BlockTokenAssembler.NAME);
		GameRegistry.registerBlock(sacrificePit, BlockSacrificePit.NAME);
		GameRegistry.registerBlock(lightGoo, BlockLightGoo.NAME);
	}
	
	
}
