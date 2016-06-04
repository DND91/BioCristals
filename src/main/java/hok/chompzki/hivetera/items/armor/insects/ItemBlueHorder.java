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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemBlueHorder extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemBlueHorder";
	
	public ItemBlueHorder(){
		super(EnumResource.BIOMASS, 0.01D, 0.01D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		
		AxisAlignedBB bb = player.boundingBox;
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(5, 5, 5));
		
		for(EntityItem ei : list){
			if(1.0F < player.getDistanceToEntity(ei)){
				this.feed(player, stack, false);
				
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			} else if(player.getDistanceToEntity(ei) <= 1.0F){
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			}
			
			if(!ei.isDead)
				ei.setPosition(player.posX, player.posY, player.posZ);
		}
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		
		AxisAlignedBB bb = player.boundingBox;
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(5, 5, 5));
		
		for(EntityItem ei : list){
			if(1.0F < player.getDistanceToEntity(ei)){
				this.feed(player, stack, false);
				
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			} else if(player.getDistanceToEntity(ei) <= 1.0F){
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			}
			
			if(!ei.isDead)
				ei.setPosition(player.posX, player.posY, player.posZ);
		}
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
		
		
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		
		AxisAlignedBB bb = player.boundingBox;
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(4, 4, 4));
		
		for(EntityItem ei : list){
			if(0 < ei.delayBeforeCanPickup)
				continue;
			
			double currentFood = insect.getFood(stack);
			
			if(currentFood < insect.getCost(stack)){
				double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
				
				currentFood += value[insect.getFoodType(stack).toInt()];
				insect.setFood(stack, currentFood);
			}
			
			if(1.0F < player.getDistanceToEntity(ei) && insect.getCost(stack) <= currentFood){
				currentFood -= insect.getCost(stack);
				insect.setFood(stack, currentFood);
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			} else if(player.getDistanceToEntity(ei) <= 1.0F){
				ItemStack item = ei.getEntityItem();
				if(BioHelper.addItemStackToInventory(item, (IInventory) player.inventory, 0, player.inventory.getSizeInventory())){
					if(item.stackSize <= 0){
						ei.setDead();
					}
				}
			}
			
			if(!ei.isDead)
				ei.setPosition(player.posX, player.posY, player.posZ);
		}
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
		return stack.getItemDamage() < stack.getMaxDamage();
	}

	@Override
	public void damageArmor(World worldObj, EntityPlayer player,
			BioArmor[] armors, int armorType, int i, DamageSource source,
			int damage) {
		
	}
	
	@Override
	public SocketType getType() {
		return SocketType.FUNC;
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
