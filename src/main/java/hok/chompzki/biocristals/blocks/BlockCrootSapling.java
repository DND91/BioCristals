package hok.chompzki.biocristals.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IGrowthCristal;
import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCrootSapling extends Block implements IGrowthCristal {
	
	public static final String[] subtypes = new String[] {"normal"};
    private static final IIcon[] icons = new IIcon[subtypes.length];
    public static final String NAME = "croot_sapling";
    
    public BlockCrootSapling()
    {
    	super(Material.wood);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setTickRandomly(true);
        setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return 1;
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
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return  !world.isAirBlock(x, y-1, z);
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }
    
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        this.checkAndDropBlock(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }
    
    protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
        }
    }
    
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
        {
        	this.checkAndDropBlock(world, x, y, z);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(world, x, y, z);
            }
        }
    }
    
    @Override
    public void grow(World world, int x, int y, int z){
    	Block block = world.getBlock(x, y-1, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	if(block == null || !(block instanceof BlockCrootRoots)){
    		world.setBlock(x, y-1, z, BlockRegistry.crootRoots, meta, 2);
    		return;
    	}
    	
    	y -= 1;
    	
    	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
    		if(dir == ForgeDirection.UP)
    			continue;
    		
    		block = world.getBlock(x + dir.offsetX, y  + dir.offsetY, z + dir.offsetZ);
        	if(block == null || !(block instanceof BlockCrootRoots)){
        		world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, BlockRegistry.crootRoots, meta, 2);
        		return;
        	}
    	}
    	
    	y += 1;
    	
    	CrootHelper.spawnCircle(world, x, y-1, z, 2, BlockRegistry.crootRoots, BlockRegistry.crootRoots);
    	CrootHelper.spawnCircle(world, x, y-2, z, 1, BlockRegistry.crootRoots, BlockRegistry.crootRoots);
    	world.setBlock(x, y-3, z, BlockRegistry.crootRoots, meta, 2);
    	
    	world.setBlock(x, y, z, BlockRegistry.crootCore, meta, 2);
    	
    	world.setBlock(x, y+1, z, BlockRegistry.crootLeaves, meta, 2);
    	
    	TileCrootCore tile = (TileCrootCore) world.getTileEntity(x, y, z);
    	tile.setBlockCount(100);
    	
    }

	@Override
	public boolean isMature(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z) {
		return false;
	}

	@Override
	public void harvest(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z) {
	}

	@Override
	public void harvest(World world, EntityPlayer player, ItemStack stack,
			int x, int y, int z, List<ItemStack> list) {
	}
	
}
