package hok.chompzki.hivetera.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTokenAssembler extends BlockContainer {
	
	public static final String NAME = "blockTokenAssembler";
	private final Random field_149955_b = new Random();
	
	private static boolean field_149934_M;
    @SideOnly(Side.CLIENT)
    private IIcon side_icon;
    @SideOnly(Side.CLIENT)
    private IIcon top_icon;

	public BlockTokenAssembler() {
		super(Material.rock);
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileTokenAssembler();
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 0 ? Blocks.cobblestone.getIcon(side, meta) : side == 1 ? this.top_icon : this.side_icon;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(HiveteraMod.MODID + ":" + NAME+"_side");
        this.side_icon = p_149651_1_.registerIcon(HiveteraMod.MODID + ":" + NAME+"_side");
        this.top_icon = p_149651_1_.registerIcon(HiveteraMod.MODID + ":" + NAME+"_top");
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	if (world.isRemote)
	    {
			return true;
	    }
		
    	TileTokenAssembler ent = (TileTokenAssembler) world.getTileEntity(x, y, z);
		player.openGui(HiveteraMod.instance, 107, world, ent.xCoord, ent.yCoord, ent.zCoord);
		
        return true;
    }
    
}
