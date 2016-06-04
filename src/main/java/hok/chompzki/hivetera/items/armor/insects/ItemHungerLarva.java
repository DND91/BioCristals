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
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.IGrowable;
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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemHungerLarva extends ItemInsect implements IArmorInsect {
	
	private final static Random rand = new Random();
	public final static String NAME = "itemHungerLarva";
	
	public ItemHungerLarva(){
		super(EnumResource.BIOMASS, 2.0D, 2.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		this.feed(player, stack, false);
		for(int x = (int) (player.posX - 4); x <= player.posX + 4; x++)
		for(int y = (int) (player.posY); y <= player.posY + 8; y++)
		for(int z = (int) (player.posZ - 4); z <= player.posZ + 4; z++){
			if(world.getBlock(x, y, z) != null && !(world.getBlock(x, y, z) instanceof BlockGrass)
					&& (world.getBlock(x, y, z).getMaterial() == Material.leaves
					|| world.getBlock(x, y, z).getMaterial() == Material.grass
					|| world.getBlock(x, y, z).getMaterial() == Material.plants
					|| world.getBlock(x, y, z).getMaterial() == Material.vine)){
				world.setBlockToAir(x, y, z);
				player.getFoodStats().addStats(1, 1.0F);
			}
		}
		return stack;
    }
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		this.feed(player, stack, false);
		for(int x = (int) (player.posX - 4); x <= player.posX + 4; x++)
		for(int y = (int) (player.posY); y <= player.posY + 8; y++)
		for(int z = (int) (player.posZ - 4); z <= player.posZ + 4; z++){
			if(world.getBlock(x, y, z) != null && !(world.getBlock(x, y, z) instanceof BlockGrass)
					&& (world.getBlock(x, y, z).getMaterial() == Material.leaves
					|| world.getBlock(x, y, z).getMaterial() == Material.grass
					|| world.getBlock(x, y, z).getMaterial() == Material.plants
					|| world.getBlock(x, y, z).getMaterial() == Material.vine)){
				world.setBlockToAir(x, y, z);
				player.getFoodStats().addStats(1, 1.0F);
			}
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
		ItemStack stack = armors[type].getStackInSlot(slot);
		IInsect insect = (IInsect)stack.getItem();
		
		if(world.isRemote)
			return;
		
		int r = 0;
		if(player.isSneaking())
			r = rand.nextInt(25);
		else
			r = rand.nextInt(100);
		
		if(0 < r)
			return;
		
		for(int x = (int) (player.posX + 4); player.posX - 4 <= x; x--)
		for(int y = (int) (player.posY + 8); player.posY - 1 <= y; y--)
		for(int z = (int) (player.posZ + 4); player.posZ - 4 <= z; z--){
			Block block = world.getBlock(x, y, z);
			if(block != null && !(block instanceof BlockGrass
					&& !(block instanceof BlockMycelium))
					&& (world.getBlock(x, y, z).getMaterial() == Material.leaves
					|| world.getBlock(x, y, z).getMaterial() == Material.grass
					|| world.getBlock(x, y, z).getMaterial() == Material.plants
					|| world.getBlock(x, y, z).getMaterial() == Material.vine)){
				double currentFood = insect.getFood(stack);
				
				if(currentFood < insect.getCost(stack)){
					double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
					
					currentFood += value[insect.getFoodType(stack).toInt()];
					insect.setFood(stack, currentFood);
				}
				
				if(insect.getCost(stack) <= currentFood && this.canFeed(player, armors, EnumResource.BIOMASS, 4.0D)){
					if(block == Blocks.reeds && world.getBlock(x, y-1, z) != Blocks.reeds)
						continue;
					
					world.setBlockToAir(x, y, z);
					currentFood -= insect.getCost(stack);
					insect.setFood(stack, currentFood);
					this.feed(player, armors, EnumResource.BIOMASS, 4.0D);
				}
			}
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
