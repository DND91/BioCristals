package hok.chompzki.biocristals.items.armor;

import java.util.Random;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.registrys.GuiHandler;
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

public class ItemArmorAttuner extends Item {
	
	public final static String NAME = "itemArmorAttuner";
	private Random random = new Random();
	
	public ItemArmorAttuner(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		
		
		player.openGui(BioCristalsMod.instance, 110, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		return stack;
    }
	
}
