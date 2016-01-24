package hok.chompzki.hivetera.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneMember;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCorePlasma extends BlockCroot {
	
	public static final String NAME = "blockCorePlasma";
	//public static final Material corePlasma = (new MaterialLiquid(MapColor.magentaColor));
    
    public BlockCorePlasma()
    {
    	super(Material.water);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setLightOpacity(25);
		this.setResistance(2000.0f);
        this.setHardness(5.0F);
        this.setStepSound(soundTypeGravel);
        this.disableStats();
        this.setTickRandomly(true);
        this.setLightLevel(0.5f);
    }
	
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
    {
        return false;
    }
    
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return true;
    }
    
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
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
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(5);
	}
}
