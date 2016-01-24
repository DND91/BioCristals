package hok.chompzki.hivetera.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
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

public class BlockCrootBreeder extends BlockContainer {
	
	public static final String NAME = "blockCrootBreeder";
	private final Random field_149955_b = new Random();
	
	private static boolean field_149934_M;
    @SideOnly(Side.CLIENT)
    private IIcon side_icon;
    @SideOnly(Side.CLIENT)
    private IIcon top_icon;

	public BlockCrootBreeder() {
		super(Material.rock);
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootBreeder();
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
        this.top_icon = p_149651_1_.registerIcon(HiveteraMod.MODID + ":" + BlockNest.NAME +"_top");
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
		
		TileCrootBreeder ent = (TileCrootBreeder) world.getTileEntity(x, y, z);
		player.openGui(HiveteraMod.instance, 105, world, ent.xCoord, ent.yCoord, ent.zCoord);
		
        return true;
    }

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
    	TileCrootBreeder tileentitychest = (TileCrootBreeder)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    	

        if (tileentitychest != null)
        {
            for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1)
            {
                ItemStack itemstack = tileentitychest.getStackInSlot(i1);

                if (itemstack != null)
                {
                    float f = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.field_149955_b.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; p_149749_1_.spawnEntityInWorld(entityitem))
                    {
                        int j1 = this.field_149955_b.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.field_149955_b.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.field_149955_b.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.field_149955_b.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
}
