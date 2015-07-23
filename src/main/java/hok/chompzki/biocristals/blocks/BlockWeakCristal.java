package hok.chompzki.biocristals.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.IGrowthCristal;
import hok.chompzki.biocristals.blocks.croot.BlockConsumer;
import hok.chompzki.biocristals.blocks.croot.ICrootPowerCon;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneConsumer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockWeakCristal extends BlockConsumer implements IGrowthCristal{
	
	//Plants: Wheat, Carrots, Suger Cane, Potato, Melon, Pumpkin
	
	
		@SideOnly(Side.CLIENT)
	    private IIcon[] field_149867_a;
		
		public final String name;
		private final int maxMeta;
		private final ItemStack[] drops;
		
		public BlockWeakCristal(String name, int maxMeta, ItemStack... drops) {
			this.name = name;
			this.maxMeta = maxMeta;
			
			setBlockName(BioCristalsMod.MODID + "_" + name);
			setCreativeTab(BioCristalsMod.creativeTab);
			setBlockTextureName(BioCristalsMod.MODID + ":" + name);
			
			this.setTickRandomly(true);
			this.setStepSound(soundTypeGrass);
			this.drops = drops;
		}
		
		public void updateTick(World world, int x, int y, int z, Random rand)
	    {
	        super.updateTick(world, x, y, z, rand);
	        ICrootPowerCon con = (ICrootPowerCon) world.getTileEntity(x, y, z);
			if(!con.hasPower())
				return;
            if (rand.nextInt(ConfigRegistry.weakCristalGrowthChance) == 0)
            {
                this.grow(world, x, y, z);
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
				list.add(d.copy());
	    	int meta = 0;
	    	world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
		
		@Override
		public void grow(World world, int x, int y, int z) {
			int l = world.getBlockMetadata(x, y, z);

	        if (l < maxMeta)
	        {
	        	++l;
                world.setBlockMetadataWithNotify(x, y, z, l, 2);
	        }
		}
		
		@Override
		public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
			return new TileCrootOneConsumer(1);
		}

}
