package hok.chompzki.hivetera.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.CrootHelper;
import hok.chompzki.hivetera.api.ICrootCore;
import hok.chompzki.hivetera.api.IGrowthCristal;
import hok.chompzki.hivetera.croot.building.CrootBlock;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.tile_enteties.TileCroot;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrootCore extends BlockCore {
	
    private static IIcon[] icons = null;
    public static final String NAME = "crootCore";
	private Random random = new Random();
    
    
    
	public BlockCrootCore() {
		super(Material.wood);
		this.setTickRandomly(true);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setHardness(0.8f);
		this.setLightLevel(1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootCore();
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, BlockCrootSapling.subtypes.length)];
    }
	
	public int damageDropped(int meta)
    {
    	return MathHelper.clamp_int(meta, 0, BlockCrootSapling.subtypes.length);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < BlockCrootSapling.subtypes.length; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	icons = new IIcon[BlockCrootSapling.subtypes.length];
        for (int i = 0; i < icons.length; ++i)
        {
        	icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockCrootSapling.subtypes[i]);
        }
    }
	
    @Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if (par1World.isRemote)
	    {
			return true;
	    }
		
		TileCrootCore ent = (TileCrootCore) par1World.getTileEntity(par2, par3, par4);
		if(ent == null)
			return true;
		
		par5EntityPlayer.openGui(HiveteraMod.instance, 102, par1World, ent.xCoord, ent.yCoord, ent.zCoord);
		
        return true;
    }
    
    @Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
    	TileCrootCore tileentitychest = (TileCrootCore)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

        if (tileentitychest != null)
        {
            for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1)
            {
                ItemStack itemstack = tileentitychest.getStackInSlot(i1);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; p_149749_1_.spawnEntityInWorld(entityitem))
                    {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        p_149749_1_.spawnEntityInWorld(entityitem);
                    }
                }
            }

            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
}
