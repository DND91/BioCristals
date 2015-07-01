package hok.chompzki.biocristals.blocks;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IBaseCristal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBiomass extends Block implements IBaseCristal{

	public static final String NAME = "blockBiomass";

	public BlockBiomass() {
		super(Material.grass);
		setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(CreativeTabs.tabMisc);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
	}

}
