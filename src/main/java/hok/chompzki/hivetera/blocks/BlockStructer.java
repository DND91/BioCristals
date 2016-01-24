package hok.chompzki.hivetera.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.croot.building.CrootBlock;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.tile_enteties.TileReagentPurifier;
import hok.chompzki.hivetera.tile_enteties.TileStructer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStructer extends BlockContainer {

	public static final String NAME = "blockStructer";
	
	protected CrootModule structure = null;
	
	private IIcon[] iconArray = new IIcon[3];

	public BlockStructer(CrootModule structure) {
		super(Material.wood);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
        this.setStepSound(soundTypeGrass);
        this.setTickRandomly(true);
        this.structure = structure;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
        iconArray[0] = this.blockIcon;
        iconArray[1] = p_149651_1_.registerIcon(this.getTextureName() + "_output");
        iconArray[2] = p_149651_1_.registerIcon(this.getTextureName() + "_filter");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockacc, int x, int y, int z, int side)
    {
		int meta = blockacc.getBlockMetadata(x, y, z);
        ForgeDirection drawSide = ForgeDirection.getOrientation(side);
        ForgeDirection outputSide = ForgeDirection.getOrientation(meta);
        ForgeDirection filterSide = ForgeDirection.getOrientation(meta).getOpposite();
        
        if(drawSide == outputSide)
        	return iconArray[1];
        if(drawSide == filterSide)
        	return iconArray[2];
        return iconArray[0];
    }
	
	public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int meta, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
    {
        

        return meta;
    }
	
	
	public CrootModule getStructure(){
		return structure;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileStructer();
	}
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_)
    {
        
        if(world.isRemote){
        	super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
        	return;
        }
        TileStructer te = (TileStructer) world.getTileEntity(x, y, z);
        
        te.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
        super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	if(!player.isSneaking())
    		return false;
    	
    	int meta = world.getBlockMetadata(x, y, z);
        ForgeDirection dir = ForgeDirection.getOrientation(meta);
    	
    	world.setBlockToAir(x,y,z);
		
		for(CrootBlock block : structure.blocks){
			int tx = TileStructer.getX(dir, block.x, block.y, block.z);
			int ty = TileStructer.getY(dir, block.x, block.y, block.z);
			int tz = TileStructer.getZ(dir, block.x, block.y, block.z);
			//if(!world.isAirBlock(x+tx, y+ty, z+tz))
			//	continue;
			world.setBlock(x+tx, y+ty, z+tz, block.block, block.meta, 3);
			
		}
    	
        return true;
    }
}
