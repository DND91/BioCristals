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
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemHillSpider extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemHillSpider";
	
	public static final UUID steepAssistUUID = UUID.fromString("8e9a5459-6f5b-4ca7-997a-7e35f0385daf");
	public static final AttributeModifier steepAssistModifier = (new AttributeModifier(steepAssistUUID, "Movement Speed Increase", 1.0D, 1));
	public static final IAttribute steepAssist = (new RangedAttribute("generic.steepAssist", 0.5F, Float.MIN_VALUE, Float.MAX_VALUE)).setDescription("Steep Assist").setShouldWatch(true);
	
	public ItemHillSpider(){
		super(EnumResource.NURITMENT, 10.0D, 10.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		
		this.registerModifier(this, steepAssist, steepAssistModifier, true);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(!par2World.isRemote)
			this.feed(player, stack, false);
		
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 600, 2));
		
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!world.isRemote)
			this.feed(player, stack, false);
		
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 600, 2));
		
		return true;
    }

	@Override
	public int addMaxDamage(int currentDmg, BioArmor armor, int slot) {
		return 0;
	}
	
	
	
	@Override
	public void onArmorTick(World world, EntityPlayer player,
		BioArmor[] armors, int type, int slot) {
		if(!player.worldObj.isRemote)
			return;
		IAttributeInstance attribute = player.getEntityAttribute(this.steepAssist);
		if(attribute == null)
			return;
		AttributeModifier value = attribute.getModifier(steepAssistUUID);
		if(value == null)
			return;
		float v = (float) attribute.getAttributeValue();
		if(player.stepHeight < v)
		player.stepHeight = v;
		player.stepHeight = MathHelper.clamp_float(player.stepHeight, 0.5F, Float.MAX_VALUE);
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
		return SocketType.PASSIVE;
	}
	
	@Override
	public boolean shouldMod(World worldObj, EntityPlayer player) {
		return true;
	}

	@Override
	public void applyModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
		if(!player.worldObj.isRemote)
			return;
		player.stepHeight -= 0.5D;
		player.stepHeight += attribute.getAttributeValue();
		player.stepHeight = MathHelper.clamp_float(player.stepHeight, 0.5F, Float.MAX_VALUE);
	}
	
	@Override
	public void removeModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player) {
		if(!player.worldObj.isRemote)
			return;
		player.stepHeight -= attribute.getAttributeValue();
		player.stepHeight = MathHelper.clamp_float(player.stepHeight, 0.5F, Float.MAX_VALUE);
	}

	@Override
	public double getBaseModValue() {
		return 0.5D;
	}

	@Override
	public int getDamageReduction(int dmg, BioArmor armor, int i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
