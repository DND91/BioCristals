package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.blocks.BlockBiomass;
import hok.chompzki.biocristals.blocks.BlockWheatCristal;
import hok.chompzki.biocristals.items.ItemAttuner;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static Block biomass = null;
	public static Block wheatCristal = null;
	
	public void registerBlocks(){
		biomass = new BlockBiomass();
		wheatCristal = new BlockWheatCristal();
		
		GameRegistry.registerBlock(biomass, BlockBiomass.NAME);
		GameRegistry.registerBlock(wheatCristal, BlockWheatCristal.NAME);
		
	}
	
	
}
