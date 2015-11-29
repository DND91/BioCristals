package hok.chompzki.biocristals.items;

import java.util.Random;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCrootStick extends Item {
	
	public final static String NAME = "itemCrootStick";
	private Random random = new Random();
	
	public ItemCrootStick(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	private boolean hasInInventory(Item item, EntityPlayer player){
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack == null)
				continue;
			if(stack.getItem() == item){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
		
		if(!(living instanceof EntityPlayer))
			return false;
		
		EntityPlayer player = (EntityPlayer)living;
		InventoryPlayer inv = player.inventory;
		
		if(!world.isRemote && block.getMaterial() == Material.leaves && random.nextInt(6) % 6 == 0){ //Add chance kraken bug
			float f = this.random.nextFloat() * 0.8F + 0.1F;
            float f1 = this.random.nextFloat() * 0.8F + 0.1F;
            float f2 = this.random.nextFloat() * 0.8F + 0.1F;
            
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(ItemRegistry.kraKenBug, 1));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
			return true;
		}else if(!world.isRemote && block.getMaterial() == Material.vine && random.nextInt(6) % 6 == 0){ //Add chance WSB
			float f = this.random.nextFloat() * 0.8F + 0.1F;
            float f1 = this.random.nextFloat() * 0.8F + 0.1F;
            float f2 = this.random.nextFloat() * 0.8F + 0.1F;
            
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(ItemRegistry.wsb, 1));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
			return true;
		}else if(!world.isRemote && block == Blocks.reeds  && random.nextInt(4) % 4 == 0 && !inv.hasItem(ItemRegistry.hivebag)){ //Add chance and can only pick up one
			float f = this.random.nextFloat() * 0.8F + 0.1F;
            float f1 = this.random.nextFloat() * 0.8F + 0.1F;
            float f2 = this.random.nextFloat() * 0.8F + 0.1F;
            
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(ItemRegistry.hivebag, 1));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
			return true;
		}else if(!world.isRemote && block == Blocks.cactus && !inv.hasItem(ItemRegistry.crootClaw)){ //Add chance and can only pick up one
			float f = this.random.nextFloat() * 0.8F + 0.1F;
            float f1 = this.random.nextFloat() * 0.8F + 0.1F;
            float f2 = this.random.nextFloat() * 0.8F + 0.1F;
            
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(ItemRegistry.crootClaw, 1));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
			return true;
		}
		return false;
    }
}
