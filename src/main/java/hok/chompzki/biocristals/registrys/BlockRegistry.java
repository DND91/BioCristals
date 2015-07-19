package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.blocks.BlockBiomass;
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
	public static Block sugerCaneCristal = null;
	public static Block potatoCristal = null;
	public static Block melonCristal = null;
	public static Block pumpikCristal = null;
	public static Block sulphurTuft = null;
	public static Block reagentPurifier = null;
	public static Block primogenitus = null;
	
	
	public void registerBlocks(){
		biomass = new BlockBiomass();
		wheatCristal = new BlockWeakCristal("blockWheatCristal", 3, new ItemStack(Items.wheat, 2));
		carrotCristal = new BlockWeakCristal("blockCarrotCristal", 3, new ItemStack(Items.carrot, 2));
		sugerCaneCristal = new BlockWeakCristal("blockSugerCaneCristal", 3, new ItemStack(Items.reeds, 2));
		potatoCristal = new BlockWeakCristal("blockPotatoCristal", 3, new ItemStack(Items.potato, 2));
		melonCristal = new BlockWeakCristal("blockMelonCristal", 3, new ItemStack(Items.melon, 2));
		pumpikCristal = new BlockWeakCristal("blockPumpkinCristal", 3, new ItemStack(Blocks.pumpkin, 1));
		sulphurTuft = new BlockSulphurTuft();
		reagentPurifier = new BlockReagentPurifier();
		primogenitus = new BlockPrimogenitus();
		
		GameRegistry.registerBlock(biomass, BlockBiomass.NAME);
		GameRegistry.registerBlock(wheatCristal, "blockWheatCristal");
		GameRegistry.registerBlock(carrotCristal, "blockCarrotCristal");
		GameRegistry.registerBlock(sugerCaneCristal, "blockSugerCaneCristal");
		GameRegistry.registerBlock(potatoCristal, "blockPotatoCristal");
		GameRegistry.registerBlock(melonCristal, "blockMelonCristal");
		GameRegistry.registerBlock(pumpikCristal, "blockPumpkinCristal");
		GameRegistry.registerBlock(sulphurTuft, BlockSulphurTuft.NAME);
		GameRegistry.registerBlock(reagentPurifier, BlockReagentPurifier.NAME);
		GameRegistry.registerBlock(primogenitus, BlockPrimogenitus.NAME);
	}
	
	
}
