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
import hok.chompzki.hivetera.tile_enteties.TileReplacer;
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

public class BlockReplacerOpen extends BlockContainer{
	
	public static final String NAME = "blockReplacerOpen";
	
	public static int renderPass = 0;

	private Random rand = new Random();
    
    public BlockReplacerOpen()
    {
    	super(BlockGhost.plasma);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setLightOpacity(0);
		this.setResistance(10000.0f);
        float f = 0.5F;
        //this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
        this.setBlockUnbreakable();
        setCreativeTab(HiveteraMod.creativeTab);
        this.setTickRandomly(true);
        this.setLightLevel(0.5F);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.03125F, 1.0F);
    }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
    	if(rand.nextInt(10) == 0){
	    	for (int k = 0; k < 2; ++k)
	        {
	            world.spawnParticle("portal", x + (this.rand .nextDouble() + 0.25D), y + 0.25D + this.rand.nextDouble(), z+ (this.rand.nextDouble() + 0.25D) , (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
	        }
    	}
    }
    
    public int tickRate(World p_149738_1_)
    {
        return 30;
    }
    
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileReplacer();
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
    public int getRenderBlockPass()
    {
        return 1;
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
    
    @Override
    public boolean canRenderInPass(int pass)
    {
	    renderPass = pass;
	    return pass == getRenderBlockPass();
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
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return world.isAirBlock(x, y+1, z) && world.isSideSolid(x, y-1, z, ForgeDirection.UP);
    }
}

