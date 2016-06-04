package hok.chompzki.hivetera.items.armor.insects;

import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.items.armor.SocketType;
import hok.chompzki.hivetera.items.insects.ItemInsect;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLightbringer extends ItemInsect implements IArmorInsect {
	
	public static final String NAME = "itemLightbringer";
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemLightbringer(){
		super(EnumResource.BIOMASS, 10.0D/64.0D, 10.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
	}
	
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {	
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		if(!world.isAirBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ))
			return false;
		if(!world.getBlock(x, y, z).isSideSolid(world, x, y, z, dir))
			return false;
		
		feed(player, stack, false);
		world.setBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, BlockRegistry.lightGoo);
		
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
		
		if(!world.isRemote){
			
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
			
			int light = world.getBlockLightValue(x, y, z);
			if(light <= 4 && world.isAirBlock(x, y, z) && world.isSideSolid(x, y-1, z, ForgeDirection.UP)){
				double currentFood = insect.getFood(stack);
				
				if(currentFood < insect.getCost(stack)){
					double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
					
					currentFood += value[insect.getFoodType(stack).toInt()];
					insect.setFood(stack, currentFood);
				}
				
				if(insect.getCost(stack) <= currentFood){
					currentFood -= insect.getCost(stack);
					insect.setFood(stack, currentFood);
					world.setBlock(x, y, z, BlockRegistry.lightGoo, 0, 3);
				}
			}
		}
	}

	@Override
	public void addProperties(ArmorProperties prop, BioArmor[] armors,
			EntityPlayer player, DamageSource source, double damage, int type,
			int slot) {
		
	}

	@Override
	public boolean shouldWork(World world, EntityPlayer player,
			BioArmor[] armors, int type, int i) {
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
	public void applyModifier(IAttributeInstance attribute,
			AttributeModifier value, EntityPlayer player) {
		
	}	

	@Override
	public void removeModifier(IAttributeInstance attribute,
			AttributeModifier value, EntityPlayer player) {
		
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