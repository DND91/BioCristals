package hok.chompzki.biocristals.blocks;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSulphurTuft extends Block {
	
	public static final String NAME = "blockSulphurTuft";
	
	public BlockSulphurTuft() {
		super(Material.plants);
		setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
		float f = 0.0625F;
		this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
		
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
	
	public boolean isOpaqueCube()
    {
        return false;
    }
	
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(world.isRemote)
			return;
		
		if(entity != null && entity instanceof EntityLiving){
			EntityLiving living = (EntityLiving)entity;
			
			living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1200, 100));
			living.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 1200, 100));
			living.addPotionEffect(new PotionEffect(Potion.jump.getId(), 1200, -8));
			
			
		}
		if(entity != null && entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1200, 100));
			player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 1200, 100));
			player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 1200, -8));
			
		}
	}

}
