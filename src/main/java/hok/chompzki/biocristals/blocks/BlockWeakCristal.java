package hok.chompzki.biocristals.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.ICristal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockWeakCristal extends Block implements ICristal{
	
	//Plants: Wheat, Carrots, Suger Cane, Potato, Melon, Pumpkin
	
	
		@SideOnly(Side.CLIENT)
	    private IIcon[] field_149867_a;
		
		public final String name;
		private final int maxMeta;
		private final ItemStack[] drops;
		
		public BlockWeakCristal(String name, int maxMeta, ItemStack... drops) {
			super(Material.iron);
			this.name = name;
			this.maxMeta = maxMeta;
			
			setBlockName(BioCristalsMod.MODID + "_" + name);
			setCreativeTab(CreativeTabs.tabMisc);
			setBlockTextureName(BioCristalsMod.MODID + ":" + name);
			
			this.setTickRandomly(true);
			this.setStepSound(soundTypeGrass);
			this.drops = drops;
		}
		
		public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	    {
	        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

	        
	        int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

	        if (l < maxMeta)
	        {
	            if (p_149674_5_.nextInt(5) == 0)
	            {
	                ++l;
	                p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 2);
	            }
	        }
	        
	    }
		
		public void func_149863_m(World p_149863_1_, int p_149863_2_, int p_149863_3_, int p_149863_4_)
	    {
	        int l = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + MathHelper.getRandomIntegerInRange(p_149863_1_.rand, 2, 5);

	        if (l > maxMeta)
	        {
	            l = maxMeta;
	        }

	        p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, l, 2);
	    }
		
		
		@SideOnly(Side.CLIENT)
	    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	    {
	        if (p_149691_2_ < 0 || p_149691_2_ > maxMeta)
	        {
	            p_149691_2_ = maxMeta;
	        }

	        return this.field_149867_a[p_149691_2_];
	    }
		
		@SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister p_149651_1_)
	    {
	        this.field_149867_a = new IIcon[maxMeta+1];
	        
	        for (int i = 0; i < this.field_149867_a.length; ++i)
	        {
	            this.field_149867_a[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + i);
	            
	        }
	    }

		@Override
		public boolean isMature(World world, EntityPlayer player, ItemStack stack,
				int x, int y, int z) {
			return world.getBlockMetadata(x, y, z) == maxMeta;
		}
		
		@Override
		public void harvest(World world, EntityPlayer player, ItemStack stack,
				int x, int y, int z) {
	    	List<ItemStack> list = new ArrayList<ItemStack>();
	    	this.harvest(world, player, stack, x, y, z, list);
	    	BioHelper.dropItems(world, list, x, y+1, z);
		}
		
		@Override
		public void harvest(World world, EntityPlayer player, ItemStack stack,
				int x, int y, int z, List<ItemStack> list) {
			for(ItemStack d : drops)
				list.add(d);
	    	int meta = 0;
	    	world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
}
