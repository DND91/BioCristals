package hok.chompzki.biocristals.registrys;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.tile_enteties.TileHolderPlant;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHolderPlant extends BlockContainer implements IGrowable{
	
public static final String NAME = "blockPlaceHolder";
    
    public BlockHolderPlant()
    {
    	super(Material.grass);
        setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(null);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setLightOpacity(1);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
        this.setTickRandomly(true);
    }
    
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == BlockRegistry.attunedEarth;
    }
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileHolderPlant();
	}
	
	/**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null){
    		IGrowable grow = (IGrowable)block;
    		if(grow.func_149851_a(world, x, y, z, true)){
    			if (world.getBlockLightValue(x, y + 1, z) >= 9)
    	        {
    				float f = this.func_149864_n(world, x, y, z);
    				
    				if (rand.nextInt((int)(25.0F / f) + 1) == 0)
                    {
    					grow.func_149853_b(world, rand, x, y, z);
                    }
    	        }
    		}
    	}
    }
    
    private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_)
    {
        float f = 1.0F;
        Block block = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        Block block1 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        Block block2 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        Block block3 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        Block block4 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        Block block5 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        Block block6 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        Block block7 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;
        
        Block b = this.getBlock(p_149864_1_, p_149864_2_, p_149864_3_, p_149864_4_);
        IPlantable plantable = (IPlantable)b;
        
        
        for (int l = p_149864_2_ - 1; l <= p_149864_2_ + 1; ++l)
        {
            for (int i1 = p_149864_4_ - 1; i1 <= p_149864_4_ + 1; ++i1)
            {
                float f1 = 0.0F;

                if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).canSustainPlant(p_149864_1_, l, p_149864_3_ - 1, i1, ForgeDirection.UP, plantable))
                {
                    f1 = 1.0F;

                    if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).isFertile(p_149864_1_, l, p_149864_3_ - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != p_149864_2_ || i1 != p_149864_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }


    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).randomDisplayTick(world, x, y, z, rand);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null){
    		
    		
    		
    		this.getBlock(world, x, y, z).onBlockDestroyedByPlayer(world, x, y, z, meta);
    	}
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
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null){
    		
	    	
	    	this.getBlock(world, x, y, z).breakBlock(world, x, y, z, block, u);
    	}
    	super.breakBlock(world, x, y, z, block, u);
    }
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hit_x, float hit_y, float hit_z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null){
    		if(this.getBlock(world, x, y, z).onBlockActivated(world, x, y, z, player, side, hit_x, hit_y, hit_z)){
    			world.markBlockForUpdate(x, y, z);
    			return true;
    		}
    		
	    	IGrowable grow = (IGrowable)block;
	    	if(!grow.func_149851_a(world, x, y, z, true)){
	    		
    			int meta = world.getBlockMetadata(x, y, z);
    	    	
    			if(!world.isRemote){
	    	    	List<ItemStack> list = block.getDrops(world, x, y, z, meta, 0);
	    	    	
	    	    	BioHelper.dropItems(world, list, x, y, z);
    			}
    	    	world.setBlockMetadataWithNotify(x, y, z, 0, 3);
    	    	TileEntity tile = world.getTileEntity(x, y, z);
    			tile.markDirty();
    			world.markBlockForUpdate(x, y, z);
    			world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
	    		
	    		
	    		return true;
	    	}
    	}
    	return false;
    }
    
    public void onBlockPreDestroy(World world, int x, int y, int z, int u) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null){
    		int meta = world.getBlockMetadata(x, y, z);
	    	
	    	List<ItemStack> list = block.getDrops(world, x, y, z, meta, 0);
	    	
	    	BioHelper.dropItems(world, list, x, y, z);
	    	
    		this.getBlock(world, x, y, z).onBlockPreDestroy(world, x, y, z, u);
    	}
    }
    
    
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).onEntityWalking(world, x, y, z, entity);
    }
    
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).onBlockClicked(world, x, y, z, player);
    }
    
    public void velocityToAddToEntity(World world, int x, int y, int z, Entity entity, Vec3 vec) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).velocityToAddToEntity(world, x, y, z, entity, vec);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).onEntityCollidedWithBlock(world, x, y, z, entity);
    }
    
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float vel) {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		this.getBlock(world, x, y, z).onFallenUpon(world, x, y, z, entity, vel);
    }
    
    public int getDamageValue(World world, int x, int y, int z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).getDamageValue(world, x, y, z);
    	else
    		return 0;
    }
    
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
    	MovingObjectPosition mop = DataHelper.getEntityRayTrace(player, 1.0f);
		
		if(mop == null)
			return false;
		
		int x = mop.blockX;
		int y = mop.blockY;
		int z = mop.blockZ;
    	
    	
		Block block = this.getBlock(player.worldObj, x, y, z);
    	if(block != null)
    		return this.getBlock(player.worldObj, x, y, z).canHarvestBlock(player, meta);
    	else
    		return super.canHarvestBlock(player, meta);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).getItem(world, x, y, z);
    	else
    		return null;
    }
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).getDrops(world, x, y, z, metadata, fortune);
    	else
    		return super.getDrops(world, x, y, z, metadata, fortune);
    }
    
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).canSilkHarvest(world, player, x, y, z, metadata);
    	else
    		return super.canSilkHarvest(world, player, x, y, z, metadata);
    }
    
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).canCreatureSpawn(type, world, x, y, z);
    	else
    		return super.canCreatureSpawn(type, world, x, y, z);
    }
    
    

	public boolean isFoliage(IBlockAccess world, int x, int y, int z)
    {
		Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).isFoliage(world, x, y, z);
    	else
    		return true;
    }
    
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }
    
    public int getRenderType()
    {
        return ClientProxy.idPlantHolder;
    }

    
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
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).colorMultiplier(world, x, y, z);
    	else
    		return super.colorMultiplier(world, x, y, z);
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
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	Block block = this.getBlock(world, x, y, z);
    	if(block != null)
    		return this.getBlock(world, x, y, z).getSelectedBoundingBoxFromPool(world, x, y, z);
    	else
    		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }
    
    public Block getBlock(World world, int x, int y, int z){
    	TileHolderPlant plant = (TileHolderPlant) world.getTileEntity(x, y, z);
    	if(plant == null)
    		return null;
    	return plant.getBlock();
    }
    
    public Block getBlock(IBlockAccess world, int x, int y, int z) {
    	TileHolderPlant plant = (TileHolderPlant) world.getTileEntity(x, y, z);
    	if(plant == null)
    		return null;
    	return plant.getBlock();
	}

	@Override
	public boolean func_149851_a(World world, int x,
			int y, int z, boolean p_149851_5_) {
		Block block = this.getBlock(world, x, y, z);
		if(block instanceof IGrowable){
			IGrowable growable = (IGrowable)block;
			return growable.func_149851_a(world, x, y, z, p_149851_5_);
		}
		return false;
	}

	@Override
	public boolean func_149852_a(World world, Random rand,
			int x, int y, int z) {
		Block block = this.getBlock(world, x, y, z);
		if(block instanceof IGrowable){
			IGrowable growable = (IGrowable)block;
			return growable.func_149852_a(world, rand, x, y, z);
		}
		return false;
	}

	@Override
	public void func_149853_b(World world, Random rand,
			int x, int y, int z) {
		Block block = this.getBlock(world, x, y, z);
		if(block instanceof IGrowable){
			IGrowable growable = (IGrowable)block;
			growable.func_149853_b(world, rand, x, y, z);
			TileEntity tile = world.getTileEntity(x, y, z);
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
			world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			
		}
	}
}
