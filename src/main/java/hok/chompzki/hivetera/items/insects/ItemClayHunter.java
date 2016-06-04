package hok.chompzki.hivetera.items.insects;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.hunger.logic.EnumResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemClayHunter extends ItemInsect implements INestInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemClayHunter";
	
	public ItemClayHunter(){
		super(EnumResource.LIFE_FLUIDS, 5.0D, 5.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will collect clay.";
	}
	
	private boolean canInsert(IInventory ent, ItemStack stack, int min, int max)
    {
		if(stack == null)
			return false;
        
		for(int i = min; i < max; i++){
            if (ent.getStackInSlot(i) == null) return true;
            if (!ent.getStackInSlot(i).isItemEqual(stack)) continue;
            int result = ent.getStackInSlot(i).stackSize + stack.stackSize;
            if(result <= ent.getInventoryStackLimit() && result <= ent.getStackInSlot(i).getMaxStackSize())
            	return true;
		}
		return false;
    }

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int y = entity.yCoord;
		
		if(!canInsert((IInventory)entity, new ItemStack(Items.clay_ball), 2, 4))
			return false;
		
		for(int x = entity.xCoord - 4; x <= entity.xCoord + 4; x++)
		for(int z = entity.zCoord - 4; z <= entity.zCoord + 4; z++){
			if(!world.blockExists(x, y, z) || world.isAirBlock(x, y, z) || world.getBlock(x, y, z) != Blocks.clay)
				continue;
			else
				return true;
		}
		
		return false;
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int y = entity.yCoord;
		
		for(int x = entity.xCoord - 4; x <= entity.xCoord + 4; x++)
		for(int z = entity.zCoord - 4; z <= entity.zCoord + 4; z++){
			if(!world.blockExists(x, y, z) || world.isAirBlock(x, y, z) || world.getBlock(x, y, z) != Blocks.clay)
				continue;
			
			double chance = 0.10D;
			if(world.isAirBlock(x, y+1, z) && world.getBlock(x, y+1, z).getMaterial() == Material.water)
				chance += 0.15D;
			if(world.isAirBlock(x, y+2, z) && world.getBlock(x, y+2, z) == Blocks.waterlily)
				chance += 0.15D;
			if(world.isAirBlock(x, y-1, z) && world.getBlock(x, y-1, z) == Blocks.sand)
				chance += 0.15D;
			
			if(rand.nextGaussian() <= chance){
				BioHelper.addItemStackToInventory(new ItemStack(Items.clay_ball), (IInventory) entity, 2, 4);
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(!par2World.isRemote)
			this.feed(player, stack, false);
		ItemStack clay = new ItemStack(Items.clay_ball);
		if(!BioHelper.addItemStackToInventory(clay, player.inventory)){
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(clay);
			BioHelper.dropItems(player.worldObj, list, (int)player.posX, (int)player.posY, (int)player.posZ);
		}else
			player.inventory.markDirty();
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!world.isRemote)
			this.feed(player, stack, false);
		ItemStack clay = new ItemStack(Items.clay_ball);
		if(!BioHelper.addItemStackToInventory(clay, player.inventory)){
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(clay);
			BioHelper.dropItems(player.worldObj, list, (int)player.posX, (int)player.posY, (int)player.posZ);
		}else
			player.inventory.markDirty();
        return true;
    }

	@Override
	public int workSpan(ItemStack stack) {
		return 6400 / stack.stackSize;
	}
	
}
