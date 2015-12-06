package hok.chompzki.biocristals.items;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.BioHelper;
import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemCrootClaw extends Item implements IInsect{
	
	public final static String NAME = "itemCrootClaw";
	
	public ItemCrootClaw(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		
	}
	
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {	
		if(world.isAirBlock(x, y, z))
			return false;
		
		Block block = world.getBlock(x, y, z);
		
		if(block.getCollisionBoundingBoxFromPool(world, x, y, z) == null)
				return false;
		
		if(world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z)){
			if(!world.isRemote){
				player.setPositionAndUpdate(x+0.5D, y+1.5D, z+0.5D);
				PotionEffect pot = player.getActivePotionEffect(Potion.hunger);
				if(pot == null)
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, ConfigRegistry.hungerDurationTP, ConfigRegistry.hungerAmplifierTP));
				else
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, ConfigRegistry.hungerDurationTP + pot.getDuration(), pot.getAmplifier()+ConfigRegistry.hungerAmplifierTP));
				return true;
			}
			return false;
		}
		
        return false;
    }

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will collect items close to me.";
	}

	@Override
	public ItemStack[] getResult(ItemStack stack) {
		return new ItemStack[]{};
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(3, 3, 3));
		
		return 0 < list.size();
	}
	
	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		if(world.isRemote)
			return;
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(3, 3, 3));
		
		for(EntityItem ei : list){
			ItemStack item = ei.getEntityItem();
			if(BioHelper.addItemStackToInventory(item, (IInventory) entity, 1, 4)){
				if(item.stackSize <= 0){
					ei.setDead();
				}
			}
			
		}
	}

	@Override
	public int lifeSpan(ItemStack stack) {
		return 1000;
	}

	@Override
	public int workSpan(ItemStack stack) {
		return 50;
	}
	
	
}
