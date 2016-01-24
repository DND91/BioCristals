package hok.chompzki.hivetera.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IBaseCristal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBiomass extends Block implements IBaseCristal{

	public static final String NAME = "blockBiomass";

	public BlockBiomass() {
		super(Material.grass);
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
}
