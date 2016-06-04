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

import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemClayFoamer extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemClayFoamer";
	
	public ItemClayFoamer(){
		super(EnumResource.RAW_FOOD, 1.0D, 1.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		for(ItemStack armor : player.inventory.armorInventory){
			if(armor == null || !(armor.getItem().isRepairable()))
				continue; 
			
			if(!armor.isItemDamaged())
				continue;
			
			if(!par2World.isRemote)
				this.feed(player, stack, false);
			
			armor.setItemDamage(Math.max(0, armor.getItemDamage()-5));
			
			player.inventory.markDirty();
			
		}
		
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		for(ItemStack armor : player.inventory.armorInventory){
			if(armor == null || !(armor.getItem().isRepairable()))
				continue; 
			
			if(!armor.isItemDamaged())
				continue;
			
			if(!world.isRemote)
				this.feed(player, stack, false);
			
			armor.setItemDamage(Math.max(0, armor.getItemDamage()-5));
			
			player.inventory.markDirty();
			
		}
		
		return true;
    }

	@Override
	public int addMaxDamage(int currentDmg, BioArmor armor, int slot) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public void onArmorTick(World world, EntityPlayer player,
		BioArmor[] armors, int type, int slot) {
		ItemStack stack = armors[type].getStackInSlot(slot);
		IInsect insect = (IInsect)stack.getItem();
		
		double currentFood = insect.getFood(stack);
		
		if(currentFood < insect.getCost(stack)){
			double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
			
			currentFood += value[insect.getFoodType(stack).toInt()];
			insect.setFood(stack, currentFood);
		}
		
		for(BioArmor armor : armors){
			if(armor == null)
				continue;
			
			int dmg = armor.stack.getItemDamage();
			if(0 < dmg && rand.nextInt(100) == 0){
				
				if(insect.getCost(stack) <= currentFood){
					currentFood -= insect.getCost(stack);
					insect.setFood(stack, currentFood);
					dmg = Math.max(0, dmg-5);
					armor.stack.setItemDamage(dmg);
				}
			}
		}
	}
	
	@Override
	public void addProperties(ArmorProperties prop, BioArmor[] armors,
			EntityPlayer player, DamageSource source, double damage,
			int type, int slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldWork(World world, EntityPlayer player,
			BioArmor[] armors, int type, int slot) {
			ItemStack stack = armors[type].getStackInSlot(slot);
			
			if(stack == null || stack.getMaxDamage() <= stack.getItemDamage())
				return false;
			
			for(BioArmor armor : armors){
				if(armor == null)
					continue;
				int dmg = armor.stack.getItemDamage();
				if(0 < dmg)
					return true;
			}
			return false;
	}

	@Override
	public void damageArmor(World worldObj, EntityPlayer player,
			BioArmor[] armors, int armorType, int i, DamageSource source,
			int damage) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public SocketType getType() {
		return SocketType.FUNC;
	}

	@Override
	public boolean shouldMod(World worldObj, EntityPlayer player) {
		// TODO Auto-generated method stub
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
