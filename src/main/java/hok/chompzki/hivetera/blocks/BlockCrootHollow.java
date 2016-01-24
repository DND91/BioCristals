package hok.chompzki.hivetera.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;

public class BlockCrootHollow extends BlockCroot {

	public static final String NAME = "blockCrootHollow";
	private Random random = new Random();

	public BlockCrootHollow() {
		super();
		setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if (par1World.isRemote)
	    {
			return true;
	    }
		
		TileCrootHollow ent = (TileCrootHollow) par1World.getTileEntity(par2, par3, par4);
		if(ent == null)
			return true;
		
		if(ent.stable()){
			par5EntityPlayer.openGui(HiveteraMod.instance, 101, par1World, ent.xCoord, ent.yCoord, ent.zCoord);
		}else{
			par5EntityPlayer.addChatMessage(new ChatComponentText("Missing life force..."));
		}
        return true;
    }
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
		TileCrootHollow tileentitychest = (TileCrootHollow)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

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
	
	
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootHollow();
	}

}
