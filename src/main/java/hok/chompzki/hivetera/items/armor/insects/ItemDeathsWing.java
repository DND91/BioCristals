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
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemDeathsWing extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemDeathsWing";
	
	public ItemDeathsWing(){
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
			
			double chance = Math.max(0.1D, 1.0D - ((double)(player.getHealth()) / player.getMaxHealth()));	
			double roll = rand.nextDouble();
			
			if(insect.getCost(stack) <= currentFood && roll < chance){
				prop.AbsorbMax = Integer.MAX_VALUE;
				prop.AbsorbRatio = 1.0D;
				currentFood -= insect.getCost(stack);
				insect.setFood(stack, currentFood);
			}
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
