package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.entity.EntityWSB;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWSB extends Item {
	
	public final static String NAME = "itemWSB";
	
	public ItemWSB(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(64);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }
        
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityWSB(world, player));
        }
        
        return stack;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
    	list.add("Kitteh's fear them as they are");
    	list.add("bringers of mischief and bad luck.");
    }
	
}
