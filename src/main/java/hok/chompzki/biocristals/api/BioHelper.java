package hok.chompzki.biocristals.api;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BioHelper {
	
	/**
	 * Healing block for animals that eats somekind of resource
	 * Block that generates biomass & biological reagent
	 * 
	 */
	
	private static Random random = new Random();
	
	public static void dropItems(World world, List<ItemStack> list, int x, int y, int z){
		for (int i1 = 0; i1 < list.size(); ++i1)
        {
            ItemStack itemstack = list.get(i1);
            
            if (itemstack != null)
            {
                float f = random.nextFloat() * 0.8F + 0.1F;
                float f1 = random.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem;

                for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                {
                    int j1 = random.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize)
                    {
                        j1 = itemstack.stackSize;
                    }
                    
                    itemstack.stackSize -= j1;
                    entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (double)((float)random.nextGaussian() * f3);
                    entityitem.motionY = (double)((float)random.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double)((float)random.nextGaussian() * f3);

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }
        }
	}
	
	public static EntityItem getFirstEntityItemWithinAABB(World world, EntityPlayer player, Item item, int x, int y, int z){
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(x, y, z));
		for(EntityItem en : list){
			if(en.getEntityItem().getItem() == item)
				return en;
		}
		return null;
	}
	
	
}
