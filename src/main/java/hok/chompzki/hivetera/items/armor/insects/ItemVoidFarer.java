package hok.chompzki.hivetera.items.armor.insects;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.items.armor.SocketType;
import hok.chompzki.hivetera.items.insects.ItemInsect;
import hok.chompzki.hivetera.research.events.GameEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class ItemVoidFarer extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemVoidFarer";
	
	public ItemVoidFarer(){
		super(EnumResource.PSY_ENG, 50.0D, 50.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}

	@Override
	public int addMaxDamage(int currentDmg, BioArmor armor, int slot) {
		return 0;
	}
	
	
	
	@Override
	public void onArmorTick(World world, EntityPlayer player,
		BioArmor[] armors, int type, int slot) {
		
	}
	
	@Override
	public void addProperties(ArmorProperties prop, BioArmor[] armors,
			EntityPlayer player, DamageSource source, double damage,
			int type, int slot) {
		
		if(!player.worldObj.isRemote){
			
			ItemStack stack = armors[type].getStackInSlot(slot);
			IInsect insect = (IInsect)stack.getItem();
			
			double currentFood = insect.getFood(stack);
			
			if(currentFood < insect.getCost(stack)){
				double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
				
				currentFood += value[insect.getFoodType(stack).toInt()];
				insect.setFood(stack, currentFood);
			}
			
			if(insect.getCost(stack) <= currentFood && player.getHealth() <= damage){
				prop.AbsorbMax = Integer.MAX_VALUE;
				prop.AbsorbRatio = 1.0D;
				currentFood -= insect.getCost(stack);
				insect.setFood(stack, currentFood);
				
				double sX = player.posX;
				double sY = player.posY;
				double sZ = player.posZ;
		        for(int i = 0; i < 10; i++){
		        	double d0 = player.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
			        double d1 = player.posY + (double)(this.rand.nextInt(64) - 32);
			        double d2 = player.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
			       
		        	if(this.teleportTo(player, d0, d1, d2)){
		        		double distance = Math.sqrt((player.posX-sX)*(player.posX-sX) + (player.posY-sY)*(player.posY-sY) + (player.posZ-sZ)*(player.posZ-sZ));
		        		if(distance <= 5.0D)
		        			continue;
		        		return;
		        	}
		        }
			}
		}
	}
	
	 public static boolean teleportTo(EntityPlayer player, double p_70825_1_, double p_70825_3_, double p_70825_5_)
	    {
	        EnderTeleportEvent event = new EnderTeleportEvent(player, p_70825_1_, p_70825_3_, p_70825_5_, 0);
	        if (MinecraftForge.EVENT_BUS.post(event)){
	            return false;
	        }
	        double d3 = player.posX;
	        double d4 = player.posY;
	        double d5 = player.posZ;
	        player.posX = event.targetX;
	        player.posY = event.targetY;
	        player.posZ = event.targetZ;
	        boolean flag = false;
	        int i = MathHelper.floor_double(player.posX);
	        int j = MathHelper.floor_double(player.posY);
	        int k = MathHelper.floor_double(player.posZ);

	        if (player.worldObj.blockExists(i, j, k))
	        {
	            boolean flag1 = false;

	            while (!flag1 && j > 0)
	            {
	                Block block = player.worldObj.getBlock(i, j - 1, k);

	                if (block.getMaterial().blocksMovement())
	                {
	                    flag1 = true;
	                }
	                else
	                {
	                    --player.posY;
	                    --j;
	                }
	            }

	            if (flag1)
	            {
	            	player.setPositionAndUpdate(player.posX, player.posY, player.posZ);
	            	
	                if (player.worldObj.getCollidingBoundingBoxes(player, player.boundingBox).isEmpty() && !player.worldObj.isAnyLiquid(player.boundingBox))
	                {
	                    flag = true;
	                }
	            }
	        }

	        if (!flag)
	        {
	        	player.setPositionAndUpdate(d3, d4, d5);
	            return false;
	        }
	        else
	        {
	            short short1 = 128;

	            for (int l = 0; l < short1; ++l)
	            {
	                double d6 = (double)l / ((double)short1 - 1.0D);
	                float f = (rand.nextFloat() - 0.5F) * 0.2F;
	                float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
	                float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
	                double d7 = d3 + (player.posX - d3) * d6 + (rand.nextDouble() - 0.5D) * (double)player.width * 2.0D;
	                double d8 = d4 + (player.posY - d4) * d6 + rand.nextDouble() * (double)player.height;
	                double d9 = d5 + (player.posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * (double)player.width * 2.0D;
	                player.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
	            }

	            player.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
	            player.playSound("mob.endermen.portal", 1.0F, 1.0F);
	            return true;
	        }
	    }
	
	@Override
	public boolean shouldWork(World world, EntityPlayer player,
			BioArmor[] armors, int type, int slot) {
		BioArmor armor = armors[type];
		if(armor == null)
			return false;
		ItemStack stack = armor.stack;
		
		return stack.getItemDamage() < stack.getMaxDamage();
	}
	
	@Override
	public void damageArmor(World worldObj, EntityPlayer player,
			BioArmor[] armors, int armorType, int i, DamageSource source,
			int damage) {
		
	}
	
	@Override
	public SocketType getType() {
		return SocketType.DEFENSIVE;
	}

	@Override
	public boolean shouldMod(World worldObj, EntityPlayer player) {
		return false;
	}

	@Override
	public void applyModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBaseModValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDamageReduction(int dmg, BioArmor armor, int i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
