package hok.chompzki.biocristals.blocks;

import java.util.ArrayList;
import java.util.Random;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.tile_enteties.TileReagentPurifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockReagentPurifier extends BlockContainer {
	
	public static final String NAME = "blockReagentPurifier";
	
	private final Random random = new Random();

	public BlockReagentPurifier() {
		super(Material.wood);
		setBlockName(BioCristalsMod.MODID + "_" + NAME);
		this.setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileReagentPurifier();
	}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hit_x, float hit_y, float hit_z){
        TileReagentPurifier te = (TileReagentPurifier)world.getTileEntity(x,y,z);
        if (ForgeDirection.getOrientation(side) !=te.getOutputSide()){

            ForgeDirection newOut = ForgeDirection.getOrientation(side);
            ForgeDirection[]tempIn=te.getInputSides();
            ForgeDirection tempOut=te.getOutputSide();
            ArrayList<ForgeDirection> tempArray=new ArrayList<ForgeDirection>();

            for (int temp_x=0;temp_x<=tempIn.length;temp_x++) {
                tempArray.add(tempIn[temp_x]);
            }
            tempArray.add(tempOut);
            tempArray.remove(newOut);
            tempIn=(ForgeDirection[])tempArray.toArray();

            te.setInputSides(tempIn);
            te.setOutputSide(newOut);

            return true;
        }
        return false;
    }
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        /*
		TileEntityChest tileentitychest = (TileEntityChest)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
        */
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
}
