package hok.chompzki.hivetera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BioHelper {
	
	/**
	 * Healing block for animals that eats somekind of resource; Beacon?
	 * Block that generates biomass & biological reagent
	 * Add creative tab for the mod
	 * Add cristalisation of mobs :P
	 * Add somekind of in game wikipedia :3
	 * 
	 * 
	 */
	
	private static Random random = new Random();
	
	public static void dropItems(World world, List<ItemStack> list, int x, int y, int z){
		for (int i1 = 0; i1 < list.size(); ++i1)
        {
            ItemStack itemstack = list.get(i1);
            
            if (itemstack != null && 0 < itemstack.stackSize)
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
	
	public static int getFirstEmptyStack(IInventory inventory)
    {
        return getFirstEmptyStack(inventory, 0, inventory.getSizeInventory());
    }
	
	public static int getFirstEmptyStack(IInventory inventory, int min, int max)
    {
        for (int i = min; i < max; ++i)
        {
            if (inventory.getStackInSlot(i) == null)
            {
                return i;
            }
        }

        return -1;
    }
	
	public static boolean addItemStackToInventory(final ItemStack stack, IInventory inventory){
		return addItemStackToInventory(stack, inventory, 0, inventory.getSizeInventory());
	}
	
	public static boolean addItemStackToInventory(final ItemStack stack, IInventory inventory, int min, int max){
		if (stack != null && stack.stackSize != 0 && stack.getItem() != null)
        {
            try
            {
                int i;

                if (stack.isItemDamaged())
                {
                    i = getFirstEmptyStack(inventory, min, max);

                    if (i >= 0)
                    {
                    	inventory.setInventorySlotContents(i, ItemStack.copyItemStack(stack));
                    	stack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = stack.stackSize;
                        stack.stackSize = storePartialItemStack(stack, inventory, min, max);
                    }
                    while (stack.stackSize > 0 && stack.stackSize < i);

                    if (stack.stackSize == 0)
                    {
                        return true;
                    }
                    else
                    {
                        return stack.stackSize < i;
                    }
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(stack.getItem())));
                crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getItemDamage()));
                crashreportcategory.addCrashSectionCallable("Item name", new Callable()
                {
                    private static final String __OBFID = "CL_00001710";
                    public String call()
                    {
                        return stack.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return false;
        }
	}
	
	private static int storePartialItemStack(ItemStack stack, IInventory inventory){
		return storePartialItemStack(stack, inventory, 0, inventory.getSizeInventory());
	}
	
	private static int storePartialItemStack(ItemStack stack, IInventory inventory, int min, int max){
        Item item = stack.getItem();
        int i = stack.stackSize;
        int j;

        if (stack.getMaxStackSize() == 1)
        {
            j = getFirstEmptyStack(inventory, min, max);

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (inventory.getStackInSlot(j) == null)
                {
                	inventory.setInventorySlotContents(j, ItemStack.copyItemStack(stack));
                }

                return 0;
            }
        }
        else
        {
            j = storeItemStack(stack, inventory, min, max);

            if (j < 0)
            {
                j = getFirstEmptyStack(inventory, min, max);
            }

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (inventory.getStackInSlot(j) == null)
                {
                	inventory.setInventorySlotContents(j, new ItemStack(item, 1, stack.getItemDamage()));
                	stack.stackSize--;
                	i--;
                    if (stack.hasTagCompound())
                    {
                    	NBTTagCompound tag = (NBTTagCompound)stack.getTagCompound().copy();
                    	inventory.getStackInSlot(j).setTagCompound(tag);
                    }
                }

                int k = i;

                if (i > inventory.getStackInSlot(j).getMaxStackSize() - inventory.getStackInSlot(j).stackSize)
                {
                    k = inventory.getStackInSlot(j).getMaxStackSize() - inventory.getStackInSlot(j).stackSize;
                }

                if (k > inventory.getInventoryStackLimit() - inventory.getStackInSlot(j).stackSize)
                {
                    k = inventory.getInventoryStackLimit() - inventory.getStackInSlot(j).stackSize;
                }

                if (k == 0)
                {
                    return i;
                }
                else
                {
                    i -= k;
                    inventory.getStackInSlot(j).stackSize += k;
                    return i;
                }
            }
        }
    }
	
	private static int storeItemStack(ItemStack stack, IInventory inventory)
    {
		return storeItemStack(stack, inventory, 0, inventory.getSizeInventory());
    }
	
	private static int storeItemStack(ItemStack stack, IInventory inventory, int min, int max)
    {
        for (int i = min; i < max; ++i)
        {
            if (inventory.getStackInSlot(i) != null && inventory.getStackInSlot(i).getItem() == stack.getItem() && inventory.getStackInSlot(i).isStackable() && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize() && inventory.getStackInSlot(i).stackSize < inventory.getInventoryStackLimit() && (!inventory.getStackInSlot(i).getHasSubtypes() || inventory.getStackInSlot(i).getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(inventory.getStackInSlot(i), stack))
            {
                return i;
            }
        }

        return -1;
    }
	
	public static Block getBlockOnSide(TileEntity tile, ForgeDirection direction){
		return tile.getWorldObj().getBlock(tile.xCoord+direction.offsetX, tile.yCoord+direction.offsetY, tile.zCoord+direction.offsetZ);
	}
	
	public static TileEntity getTileEntityOnSide(TileEntity tile, ForgeDirection direction){
		return tile.getWorldObj().getTileEntity(tile.xCoord+direction.offsetX, tile.yCoord+direction.offsetY, tile.zCoord+direction.offsetZ);
	}
	
	public static IInventory[] getInventories(TileEntity tile, ForgeDirection... sides){
		ArrayList<IInventory> list = new ArrayList<IInventory>();
		
		for(ForgeDirection side : sides){
			TileEntity t = getTileEntityOnSide(tile, side);
			if(t != null && t instanceof IInventory)
				list.add((IInventory)t);
		}
		
		return list.toArray(new IInventory[list.size()]);
	}
}
