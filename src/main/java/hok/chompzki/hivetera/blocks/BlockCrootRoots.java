package hok.chompzki.hivetera.blocks;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileCroot;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneMember;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrootRoots extends BlockCroot {
	
    private static IIcon[] icons = null;
    public static final String NAME = "crootRoots";
    
    public BlockCrootRoots()
    {
    	super();
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
        this.setCreativeTab(HiveteraMod.creativeTab);
        this.setHardness(0.8f);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, BlockCrootSapling.subtypes.length)];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < BlockCrootSapling.subtypes.length; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    public int damageDropped(int meta)
    {
    	return MathHelper.clamp_int(meta, 0, BlockCrootSapling.subtypes.length);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	icons = new IIcon[BlockCrootSapling.subtypes.length];
        for (int i = 0; i < icons.length; ++i)
        {
        	icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockCrootSapling.subtypes[i]);
        }
    }
    
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Items.stick;
    }
	
	public int quantityDropped(Random p_149745_1_)
    {
        return 4;
    }
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(2);
	}
    
}
