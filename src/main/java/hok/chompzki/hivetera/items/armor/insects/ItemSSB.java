package hok.chompzki.hivetera.items.armor.insects;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.entity.EntitySSB;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.SocketType;
import hok.chompzki.hivetera.items.insects.ItemInsect;
import hok.chompzki.hivetera.registrys.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemSSB extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemStoneShieldedBeetle";
	
	public ItemSSB(){
		super(EnumResource.WASTE, 1.0D, 20.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(!par2World.isRemote)
			this.feed(player, stack, false);
		
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!world.isRemote)
			this.feed(player, stack, false);
		
		return true;
    }

	@Override
	public int addMaxDamage(int currentDmg, BioArmor armor, int slot) {
		return 0;
	}
	
	
	
	@Override
	public void onArmorTick(World world, EntityPlayer player,
		BioArmor[] armors, int type, int slot) {
		
		if(world.isRemote)
			return;
		
		ItemStack stack = armors[type].getStackInSlot(slot);
		IInsect insect = (IInsect)stack.getItem();
		
		double currentFood = insect.getFood(stack);
		
		if(currentFood < insect.getCost(stack)){
			double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
			
			currentFood += value[insect.getFoodType(stack).toInt()];
			insect.setFood(stack, currentFood);
		}
		
		AxisAlignedBB bb = player.boundingBox;
		List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, bb.expand(10, 10, 10));
		
		for(EntityMob mob : list){
			if(mob.isDead)
				continue;
			if(mob.getHealth() <= 0.0F)
				continue;
			
			if(insect.getCost(stack) <= currentFood && rand.nextInt() % 10 == 0){
				currentFood -= insect.getCost(stack);
				insect.setFood(stack, currentFood);
				
				double x = player.posX;
				double y = player.posY;
				double z = player.posZ;
				x += mob.posX < x ? -0.55D : x < mob.posX ? 0.55D : 0.0D; 
				y += mob.posY < y ? -0.55D : y < mob.posY ? 0.55D : 0.0D; 
				z += mob.posZ < z ? -0.55D : z < mob.posZ ? 0.55D : 0.0D; 
				
				if(!canMobBeSeen(mob.worldObj, mob, x + 0.5D, y  + 0.5D, z + 0.5D))
					continue;
				
				attackEntityWithRangedAttack(mob, 1.0F, x, y, z);
			}
		}
		
	}
	
	public boolean canMobBeSeen(World worldObj, Entity p_70685_1_, double x, double y, double z)
    {
        return worldObj.rayTraceBlocks(Vec3.createVectorHelper(x, y, z), Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double)p_70685_1_.getEyeHeight(), p_70685_1_.posZ)) == null;
    }
	
	public void attackEntityWithRangedAttack(EntityLivingBase mob, float p_82196_2_, double x, double y, double z)
    {
		EntitySSB entitysnowball = new EntitySSB(mob.worldObj, x + 0.5D, y  + 0.5D, z + 0.5D);
        double d0 = mob.posX - x - 0.5D;
        double d1 = mob.posY + (double)mob.getEyeHeight() - 1.100000023841858D - entitysnowball.posY;
        double d2 = mob.posZ - z - 0.5D;
        float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
        entitysnowball.setThrowableHeading(d0, d1 + (double)f1, d2, 1.0F, 0.0F);
        mob.playSound("random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        mob.worldObj.spawnEntityInWorld(entitysnowball);
    }
	
	@Override
	public void addProperties(ArmorProperties prop, BioArmor[] armors,
			EntityPlayer player, DamageSource source, double damage,
			int type, int slot) {
		
	}

	@Override
	public boolean shouldWork(World world, EntityPlayer player,
			BioArmor[] armors, int type, int slot) {
		BioArmor armor = armors[type];
		if(armor == null)
			return false;
		ItemStack stack = armor.stack;
		
		AxisAlignedBB bb = player.boundingBox;
		List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, bb.expand(10, 10, 10));
		
		boolean b1 = stack.getItemDamage() < stack.getMaxDamage();
		boolean b2 = player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().isItemTool(player.getCurrentEquippedItem());
		boolean b3 = 0 < list.size();
		
		return b1 && b2 && b3;
	}
	
	@Override
	public void damageArmor(World worldObj, EntityPlayer player,
			BioArmor[] armors, int armorType, int i, DamageSource source,
			int damage) {
		
	}
	
	@Override
	public SocketType getType() {
		return SocketType.OFFENSIVE;
	}
	
	@Override
	public boolean shouldMod(World worldObj, EntityPlayer player) {
		return player.isInWater();
	}

	@Override
	public void applyModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
	}

	@Override
	public void removeModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
	}

	@Override
	public double getBaseModValue() {
		return 0;
	}

	@Override
	public int getDamageReduction(int dmg, BioArmor armor, int i) {
		return 0;
	}
	
}
