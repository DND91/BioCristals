package hok.chompzki.hivetera.items.insects;

import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLightbringer extends ItemInsect implements INestInsect {
	
	public static final String NAME = "itemLightbringer";
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemLightbringer(){
		super(EnumResource.NURITMENT, 10.0D/64.0D, 10.0D, true);
		this.setMaxDamage(ConfigRegistry.hungerDistance);
		this.setNoRepair();
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
		
	}
	
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean par5) {
		if(entity instanceof EntityPlayer && !world.isRemote){
			EntityPlayer player = (EntityPlayer)entity;
			
			int x = (int) entity.posX;
			int y = (int) entity.posY;
			int z = (int) entity.posZ;
			
			int light = world.getBlockLightValue(x, y, z);
			if(light <= 4 && world.isAirBlock(x, y, z) && world.isSideSolid(x, y-1, z, ForgeDirection.UP)){
				feed(player, stack, false);
				world.setBlock(x, y, z, BlockRegistry.lightGoo);
			}
		}
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
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I shall bring light!";
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		
		
		return false;
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		if(world.isRemote)
			return;
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		
		
	}

	@Override
	public int workSpan(ItemStack stack) {
		return 100;
	}
	
}