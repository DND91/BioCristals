package hok.chompzki.hivetera.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.CrootHelper;
import hok.chompzki.hivetera.api.IGrowthCristal;
import hok.chompzki.hivetera.croot.building.CrootBlock;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCrootSapling extends Block implements IGrowthCristal {
	
	public static String[] subtypes = new String[] {"normal"};
    private static IIcon[] icons = null;
    public static final String NAME = "crootSapling";
    
    public BlockCrootSapling()
    {
    	super(Material.wood);
    	
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setTickRandomly(true);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		
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
    	return MathHelper.clamp_int(meta, 0, subtypes.length);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	icons = new IIcon[subtypes.length];
        for (int i = 0; i < icons.length; ++i)
        {
        	icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + subtypes[i]);
        }
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
    	if(world.isAirBlock(x, y-1, z))
    		return false;
    	Block block = world.getBlock(x, y-1, z);
    	if(!CrootHelper.startsOn(block))
    		return false;
    	
    	if(CrootHelper.hasZoneOwner(world, x, y, z, 16))
    		return false;	
    	
        return  true;
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

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(3) == 0)
            {
                this.grow(world, x, y, z);
            }
        }
    }
    
    @Override
    public void grow(World world, int x, int y, int z){
    	int meta = world.getBlockMetadata(x, y, z);
    	String name = this.subtypes[meta];
    	/*
    	CrootModule module = null;
		
		if(!module.controll(world, x, y, z)){
			CrootBlock block = module.getNext(world, x, y, z);
			block.meta = meta;
			CrootHelper.spawnBlock(world, block, x, y, z);
			
		}else{
			module = treeContainer.spurt;
			while(!module.controll(world, x, y, z)){
				CrootBlock block = module.getNext(world, x, y, z);
				block.meta = meta;
				CrootHelper.spawnBlock(world, block, x, y, z);
			}
		}
    	*/
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
