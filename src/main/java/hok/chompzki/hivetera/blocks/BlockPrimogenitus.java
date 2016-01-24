package hok.chompzki.hivetera.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.items.ItemBioBlob;
import hok.chompzki.hivetera.tile_enteties.TilePrimogenitus;
import hok.chompzki.hivetera.tile_enteties.TileReagentPurifier;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPrimogenitus extends BlockCroot {
	
	public static final String NAME = "blockPromogenitus";
	
	private IIcon[] iconArray = new IIcon[2];
	
	public BlockPrimogenitus() {
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		this.setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
        iconArray[0] = p_149651_1_.registerIcon(this.getTextureName() + "_output");
        iconArray[1] = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockacc, int x, int y, int z, int side)
    {
        ForgeDirection drawSide = ForgeDirection.getOrientation(side);
        
        if(drawSide == ForgeDirection.UP)
        	return iconArray[0];
        
        return iconArray[1];
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TilePrimogenitus();
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hit_x, float hit_y, float hit_z){
		if(world.isRemote)
			return true;
		
		TilePrimogenitus te = (TilePrimogenitus)world.getTileEntity(x,y,z);
        ItemStack stack = player.inventory.getCurrentItem();
        
        if(stack != null && stack.getItem() instanceof ItemBioBlob && stack.hasTagCompound()){
        	te.setStack(stack);
        	stack.setTagCompound(null);
        	return true;
        }
        
        return true;
    }
}
