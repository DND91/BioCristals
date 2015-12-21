package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemCrootHoe extends Item {
	
	/*
	<Piro_pc> rightclick on water to tile around it, more times you click further it can go, up to 9x9, but at same time it eats durability much faster, untill 9x9 when it breaks tool
	<Piro_pc> so you can get couple small fields
	<Piro_pc> or one big
	<Piro_pc> and dont allow it to till dirt directly;P
	*/
	
	public final static String NAME = "itemCrootHoe";
	private Random random = new Random();
	
	public ItemCrootHoe(int hp){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		this.setMaxDamage(hp);
	}
	
	private int getCost(Block block){
		if(block == Blocks.grass)
			return 1;
		if(block == Blocks.dirt)
			return 1;
		
		return 0;
	}
	
	private int getCost(ItemStack stack, World world, int radius, int dx, int dy, int dz){
		int cost = 0;
		
		int minX = dx - radius;
		int minZ = dz - radius;
		int maxX = dx + radius;
		int maxZ = dz + radius;
		for(int x = minX; x <= maxX; x++){
			cost += getCost(world.getBlock(x, dy, minZ));
			cost += getCost(world.getBlock(x, dy, maxZ));
		}
		for(int z = minZ; z <= maxZ; z++){
			cost += getCost(world.getBlock(minX, dy, z));
			cost += getCost(world.getBlock(maxX, dy, z));
		}
		
		return cost;
	}
	
	private void till(EntityPlayer player, ItemStack stack, World world, int dx, int dy, int dz){
		for(int i = 1; i <= 4; i++){
			if(stack.getMaxDamage() < stack.getItemDamage())
				return;
			
			int cost = getCost(stack, world, i, dx, dy, dz);
			if(0 < cost){
				int minX = dx - i;
				int minZ = dz - i;
				int maxX = dx + i;
				int maxZ = dz + i;
				for(int x = minX; x <= maxX; x++){
					if(0 < getCost(world.getBlock(x, dy, maxZ))){
						world.setBlock(x, dy, maxZ, Blocks.farmland);
						stack.damageItem(1, player);
						if(stack.getMaxDamage() < stack.getItemDamage())
							return;
					}
					if(0 < getCost(world.getBlock(x, dy, minZ))){
						world.setBlock(x, dy, minZ, Blocks.farmland);
						stack.damageItem(1, player);
						if(stack.getMaxDamage() < stack.getItemDamage())
							return;
					}
				}
				for(int z = minZ; z <= maxZ; z++){
					if(0 < getCost(world.getBlock(minX, dy, z))){
						world.setBlock(minX, dy, z, Blocks.farmland);
						stack.damageItem(1, player);
						if(stack.getMaxDamage() < stack.getItemDamage())
							return;
					}
					if(0 < getCost(world.getBlock(maxX, dy, z))){
						world.setBlock(maxX, dy, z, Blocks.farmland);
						stack.damageItem(1, player);
						if(stack.getMaxDamage() < stack.getItemDamage())
							return;
					}
				}
				return;
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		
		
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
		if(world.isRemote || movingobjectposition == null)
			return stack;
		
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (!world.canMineBlock(player, i, j, k))
            {
                return stack;
            }
            
            Material material = world.getBlock(i, j, k).getMaterial();
            int l = world.getBlockMetadata(i, j, k);
            
            if (material == Material.water && l == 0)
            {
            	till(player, stack, world, i, j, k);
                return stack;
            }
        }
        
        return stack;
    }
	
}
