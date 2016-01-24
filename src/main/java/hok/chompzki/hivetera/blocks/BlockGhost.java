package hok.chompzki.hivetera.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.ClientProxy;
import hok.chompzki.hivetera.MaterialGhost;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.tile_enteties.TileGhost;
import hok.chompzki.hivetera.tile_enteties.TileHolderPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGhost extends BlockContainer{
	
	public static final String NAME = "blockGhost";
	public static final Material plasma = new MaterialGhost();

	
	public static int renderPass = 0;
    
    public BlockGhost()
    {
    	super(plasma);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setLightOpacity(0);
		this.setResistance(10000.0f);
        float f = 0.5F;
        //this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
        this.setTickRandomly(true);
        this.setBlockUnbreakable();
    }
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileGhost();
	}
	
	/**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
    }


    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
    	
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
    	super.onNeighborBlockChange(world, x, y, z, neighbor);
    }
    
    public void breakBlock(World world, int x, int y, int z, Block brocke, int u)
    {
    	
    }
   
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
    	return null;
    }
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
    	return new ArrayList<ItemStack>();
    }
    
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
    	return false;
    }
    
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
    {
    	return false;
    }
    
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }
    /*
    public int getRenderType()
    {
        return ClientProxy.idGhost;
    }
	*/
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int u)
    {
    	return null;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int a, int b)
    {
        return null;
    }
    
    public Block getBlock(World world, int x, int y, int z){
    	TileGhost plant = (TileGhost) world.getTileEntity(x, y, z);
    	if(plant == null)
    		return null;
    	return plant.getBlock();
    }
    
    public Block getBlock(IBlockAccess world, int x, int y, int z) {
    	TileGhost plant = (TileGhost) world.getTileEntity(x, y, z);
    	if(plant == null)
    		return null;
    	return plant.getBlock();
	}
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return -1;
    }
    
    @Override
    public boolean canRenderInPass(int pass)
    {
	    //Set the static var in the client proxy
	    renderPass = pass;
	    //the block can render in both passes, so return true always
	    return pass == getRenderBlockPass();
	}
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        Block block = world.getBlock(x, y, z);
        Block block2 = world.getBlock(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);

        if (this == BlockRegistry.ghost)
        {
            if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
            {
                return true;
            }
            
            if(block == BlockRegistry.ghost && block2 == block){
            	Block a = this.getBlock(world, x, y, z);
            	Block b = this.getBlock(world, x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]);
            	return a != b;
            }
            
        }else{
        	return true;
        }
        
        

        return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
    }
    
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
    {
        return false;
    }
    
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }
    
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
}

