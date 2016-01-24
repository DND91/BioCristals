package hok.chompzki.hivetera.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileExtractor;
import hok.chompzki.hivetera.tile_enteties.TileReagentPurifier;

public class BlockExtractor extends BlockCroot {

	public static final String NAME = "blockExtractor";
	
	private final Random random = new Random();
	
	//@SideOnly(Side.CLIENT)
    private IIcon[] iconArray = new IIcon[3];
	
	public BlockExtractor() {
		super();
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		this.setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
        iconArray[0] = this.blockIcon;
        iconArray[1] = p_149651_1_.registerIcon(this.getTextureName() + "_output");
        iconArray[2] = p_149651_1_.registerIcon(this.getTextureName() + "_input");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockacc, int x, int y, int z, int side)
    {
		TileExtractor ent = (TileExtractor)blockacc.getTileEntity(x, y, z);
        ForgeDirection drawSide = ForgeDirection.getOrientation(side);
        ForgeDirection outputSide = ent.getOutputSide();
        ForgeDirection filterSide = ent.getHarvestSide();
        
        if(drawSide == outputSide)
        	return iconArray[1];
        if(drawSide == filterSide)
        	return iconArray[2];
        return iconArray[0];
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileExtractor();
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hit_x, float hit_y, float hit_z){
    	if(!player.isSneaking())
    		return true;
    	TileExtractor te = (TileExtractor)world.getTileEntity(x,y,z);
        
        ForgeDirection newOut = ForgeDirection.getOrientation(side);
        
        if (newOut !=te.getOutputSide()){
        	te.setOwner(player.getGameProfile().getId());
            te.setOutputSide(newOut);
            world.markBlockForUpdate(x, y, z);
            te.markDirty();
            return true;
        }
        return true;
    }

}
