package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.blocks.BlockAttunedEarth;
import hok.chompzki.biocristals.blocks.BlockBiomass;
import hok.chompzki.biocristals.blocks.BlockCrootCore;
import hok.chompzki.biocristals.blocks.BlockCrootHollow;
import hok.chompzki.biocristals.blocks.BlockCrootLeaves;
import hok.chompzki.biocristals.blocks.BlockCrootRoots;
import hok.chompzki.biocristals.blocks.BlockCrootSapling;
import hok.chompzki.biocristals.blocks.BlockCrootStreamStem;
import hok.chompzki.biocristals.blocks.BlockCrootTrunk;
import hok.chompzki.biocristals.blocks.BlockExtractor;
import hok.chompzki.biocristals.blocks.BlockPrimogenitus;
import hok.chompzki.biocristals.blocks.BlockReagentPurifier;
import hok.chompzki.biocristals.blocks.BlockSulphurTuft;
import hok.chompzki.biocristals.blocks.BlockWeakCristal;
import hok.chompzki.biocristals.items.ItemAttuner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
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
	public static Block attunedEarth = null;
	public static Block holderPlant = null;
	
	public void registerBlocks(){
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
		attunedEarth = new BlockAttunedEarth();
		holderPlant = new BlockHolderPlant();
		
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
		GameRegistry.registerBlock(attunedEarth, BlockAttunedEarth.NAME);
		GameRegistry.registerBlock(holderPlant, BlockHolderPlant.NAME);
	}
	
	
}
