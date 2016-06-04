package hok.chompzki.hivetera.items.insects;

import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.containers.HoneyWidow;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHoneyWidow extends ItemInsect implements INestInsect {
	
	public static final String NAME = "itemHoneyWidow";
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemHoneyWidow(){
		super(EnumResource.RAW_FOOD, 0.0D, 0.0D, false);
		this.setNoRepair();
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(par2World.isRemote)
			return stack;
		this.onCreated(stack, par2World, player);
		
		player.openGui(HiveteraMod.instance, 109, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
		NBTHelper.init(stack, "OPEN", false);
		if(!stack.stackTagCompound.hasKey("INVENTORY")){
			NBTTagList nbttaglist = new NBTTagList();
			stack.stackTagCompound.setTag("INVENTORY", nbttaglist);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
		this.icons = new IIcon[2];
		this.itemIcon = iconReg.registerIcon(this.getIconString() + "_closed");
		this.icons[0] = iconReg.registerIcon(this.getIconString() + "_closed");
		this.icons[1] = iconReg.registerIcon(this.getIconString() + "_open");
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		boolean isOpen = NBTHelper.get(stack, "OPEN", false);
        if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		boolean isOpen = NBTHelper.get(stack, "OPEN", false);
		if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		this.onCreated(stack, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer);
		boolean isOpen = NBTHelper.get(stack, "OPEN", false);
		if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean par5) {
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			HoneyWidow widow = new HoneyWidow(player, slot);
			if(widow.isOpen)
				return;
			
			for(int i = 0; i < 9; i++){
				ItemStack s = widow.getStackInSlot(i);
				if(s != null)
					s.getItem().onUpdate(s, world, entity, slot, par5);
			}
			
		}
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I'm the home giver to insects.";
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
		return 200;
	}
	
	
}