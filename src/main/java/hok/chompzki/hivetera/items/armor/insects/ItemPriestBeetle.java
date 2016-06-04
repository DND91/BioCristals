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
import hok.chompzki.hivetera.registrys.PotionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemPriestBeetle extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemPriestBeetle";
	
	public ItemPriestBeetle(){
		super(EnumResource.PSY_ENG, 500.0D, 500.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		PotionEffect effect = player.getActivePotionEffect(PotionRegistry.hungersWrath);
		if(effect != null){
			int tot = effect.getDuration() / 20;
			int hours = tot / 3600;
			int minutes = (tot % 3600) / 60;
			int seconds = tot % 60;
			String timeString = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
			
			player.addChatComponentMessage(new ChatComponentText("Time left untill death: " + timeString + "(Stage " + effect.getAmplifier() + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("The hunger have not blessed you..."));
		}
		return stack;
    }
    
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		PotionEffect effect = player.getActivePotionEffect(PotionRegistry.hungersWrath);
		if(effect != null){
			int tot = effect.getDuration() / 20;
			int hours = tot / 3600;
			int minutes = (tot % 3600) / 60;
			int seconds = tot % 60;
			String timeString = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
			
			player.addChatComponentMessage(new ChatComponentText("Time left untill death: " + timeString + "(Stage " + effect.getAmplifier() + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("The hunger have not blessed you..."));
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
		return SocketType.OFFENSIVE;
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
	
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
		World world = player.worldObj;
		
		if(world.isRemote)
			return true;
		PotionEffect effect = entity.getActivePotionEffect(PotionRegistry.hungersWrath);
		if(effect != null){
			int tot = effect.getDuration() / 20;
			int hours = tot / 3600;
			int minutes = (tot % 3600) / 60;
			int seconds = tot % 60;
			String timeString = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
			
			player.addChatComponentMessage(new ChatComponentText("Time left untill death for " + entity.getCommandSenderName() + ": " + timeString + "(Stage " + effect.getAmplifier() + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("The hunger have not blessed the biomass collection..."));
		}
		return true;
    }
	
	
	
}
