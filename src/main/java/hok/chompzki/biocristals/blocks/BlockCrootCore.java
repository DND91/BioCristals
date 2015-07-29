package hok.chompzki.biocristals.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IGrowthCristal;
import hok.chompzki.biocristals.croot.BlockCore;
import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.croot.ICrootCore;
import hok.chompzki.biocristals.croot.TileCroot;
import hok.chompzki.biocristals.croot_old.CrootBlock;
import hok.chompzki.biocristals.croot_old.CrootModule;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrootCore extends BlockCore {
	
	public static final String[] subtypes = new String[] {"normal"};
    private static final IIcon[] icons = new IIcon[subtypes.length];
    public static final String NAME = "crootCore";
    
    
    
	public BlockCrootCore() {
		super(Material.wood);
		this.setTickRandomly(true);
        setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootCore();
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	meta &= 7;
        return icons[MathHelper.clamp_int(meta, 0, subtypes.length)];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < subtypes.length; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    public int damageDropped(int meta)
    {
        return MathHelper.clamp_int(meta & 7, 0, 5);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        for (int i = 0; i < icons.length; ++i)
        {
        	icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + subtypes[i]);
        }
    }
	
	
	
}
