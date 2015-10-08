package hok.chompzki.biocristals.blocks;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.croot.dna.GrowthSystem;
import hok.chompzki.biocristals.croot.dna.data.DNACoord;
import hok.chompzki.biocristals.croot.dna.data.TreeMemory;
import hok.chompzki.biocristals.croot.dna.logic.GrowthAction;
import hok.chompzki.biocristals.tile_enteties.TileExperiment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockExperiment extends BlockContainer {

	public static final String NAME = "blockExperiment";
	
	public BlockExperiment() {
		super(Material.grass);
		setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + BlockBiomass.NAME);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileExperiment();
	}
}
