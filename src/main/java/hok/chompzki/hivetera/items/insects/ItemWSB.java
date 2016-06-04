package hok.chompzki.hivetera.items.insects;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.logic.EnumResource;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemWSB extends ItemInsect implements INestInsect{
	
	public final static String NAME = "itemWSB";
	private Random rand = new Random();
	
	public ItemWSB(){
		super(EnumResource.RAW_FOOD, 1.0D, 10.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!world.isRemote)
        {
        	feed(player, stack, false);
            world.spawnEntityInWorld(new EntityWSB(world, player));
        }
        
        return stack;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
    	list.add("Kitteh's fear them as they are");
    	list.add("bringers of mischief and bad luck.");
    }

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will attack any enemies nerby.";
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, bb.expand(10, 10, 10));
		
		return 0 < list.size();
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, bb.expand(10, 10, 10));
		
		for(EntityMob mob : list){
			double x = entity.xCoord;
			double y = entity.yCoord;
			double z = entity.zCoord;
			x += mob.posX < x ? -0.55D : x < mob.posX ? 0.55D : 0.0D; 
			y += mob.posY < y ? -0.55D : y < mob.posY ? 0.55D : 0.0D; 
			z += mob.posZ < z ? -0.55D : z < mob.posZ ? 0.55D : 0.0D; 
			
			if(!canMobBeSeen(mob.worldObj, mob, x + 0.5D, y  + 0.5D, z + 0.5D))
				continue;
			
			attackEntityWithRangedAttack(mob, 1.0F, x, y, z);
		}
	}
	
	public boolean canMobBeSeen(World worldObj, Entity p_70685_1_, double x, double y, double z)
    {
        return worldObj.rayTraceBlocks(Vec3.createVectorHelper(x, y, z), Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double)p_70685_1_.getEyeHeight(), p_70685_1_.posZ)) == null;
    }
	
	public void attackEntityWithRangedAttack(EntityLivingBase mob, float p_82196_2_, double x, double y, double z)
    {
		EntityWSB entitysnowball = new EntityWSB(mob.worldObj, x + 0.5D, y  + 0.5D, z + 0.5D);
        double d0 = mob.posX - x - 0.5D;
        double d1 = mob.posY + (double)mob.getEyeHeight() - 1.100000023841858D - entitysnowball.posY;
        double d2 = mob.posZ - z - 0.5D;
        float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
        entitysnowball.setThrowableHeading(d0, d1 + (double)f1, d2, 1.0F, 0.0F);
        mob.playSound("random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        mob.worldObj.spawnEntityInWorld(entitysnowball);
    }
	
	@Override
	public int workSpan(ItemStack stack) {
		return 64 / stack.stackSize;
	}
	
}
