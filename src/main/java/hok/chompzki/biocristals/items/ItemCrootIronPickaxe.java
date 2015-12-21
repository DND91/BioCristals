package hok.chompzki.biocristals.items;

import java.util.Set;

import com.google.common.collect.Sets;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCrootIronPickaxe extends ItemPickaxe {
	
	public static final String NAME = "itemCrootIronPickaxe";
	
	public ItemCrootIronPickaxe(){
		super(ItemRegistry.crootIron);
		this.setMaxStackSize(1);
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		
	}
    
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
    	super.onBlockDestroyed(stack, world, block, x, y, z, living);
    	
    	
    	
    	return ItemRegistry.crootStick.onBlockDestroyed(stack, world, block, x, y, z, living);
    }
}
