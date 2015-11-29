package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCrootClaw extends Item {
	
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
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 0));
				else
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100 + pot.getDuration(), pot.getAmplifier()+0));
				return true;
			}
			return false;
		}
		
        return false;
    }
}
