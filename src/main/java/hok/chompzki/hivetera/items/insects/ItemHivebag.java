package hok.chompzki.hivetera.items.insects;

import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.containers.Hivebag;
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

public class ItemHivebag extends ItemInsect implements INestInsect {
	
	public static final String NAME = "itemHivebag";
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemHivebag(){
		super(EnumResource.RAW_FOOD, 8.0D, 10.0D, true);
		this.setMaxDamage(ConfigRegistry.hungerDistance);
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
		
		player.openGui(HiveteraMod.instance, GuiHandler.hivebagId, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
		NBTHelper.init(stack, "OPEN", false);
		NBTTagList nbttaglist = new NBTTagList();
		stack.stackTagCompound.setTag("INVENTORY", nbttaglist);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
		this.icons = new IIcon[2];
		this.itemIcon = iconReg.registerIcon(this.getIconString());
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
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack)
    {
        return true;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
	
	public double getDurabilityForDisplay(ItemStack stack)
    {
		int damage = NBTHelper.get(stack, "DISTANCE", 0);
        return 1.0d - ((double)damage / (double)stack.getMaxDamage());
    }
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean par5) {
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			float currentDistance = player.distanceWalkedOnStepModified;
			float lastDistance = NBTHelper.get(stack, "HIVECRAFT_PLAYER_LAST_DIST", player.distanceWalkedOnStepModified);
			float sub_distance = NBTHelper.get(stack, "F_DISTANCE", 0.0F);
			int distance = NBTHelper.get(stack, "DISTANCE", 0);
			
			sub_distance += (currentDistance-lastDistance);
			
			if(1.0f <= sub_distance){
				sub_distance--;
				distance++;
				if(!world.isRemote && stack.hasTagCompound()){
					Hivebag bag = new Hivebag(player, slot);
					bag.tickAll();
				}
			}
			if(sub_distance < 0.0f)
				sub_distance = 0.0f;
			
			if(stack.getMaxDamage() <= distance){
				distance = 0;
				if(!world.isRemote){
					feed(player, stack, false);
				}
			}
			
			player.getEntityData().setFloat("HIVECRAFT_PLAYER_LAST_DIST", currentDistance);
			stack.getTagCompound().setFloat("F_DISTANCE", sub_distance);
			stack.getTagCompound().setInteger("DISTANCE", distance);
		}
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "";
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(3, 3, 3));
		
		for(EntityItem ei : list){
			ItemStack item = ei.getEntityItem();
			
			if(this.canSmelt((IInventory) entity, item, 2, 4))
				return true;
		}
		
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
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bb.expand(3, 3, 3));
		
		int i = 0;
		
		for(EntityItem ei : list){
			ItemStack item = ei.getEntityItem();
			if(FurnaceRecipes.smelting().getSmeltingResult(stack) != null)
				ei.age %= ei.lifespan / 3;
			
			while(0 < item.stackSize && this.canSmelt((IInventory) entity, item, 2, 4)){
				ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(item);
				i++;
				item.stackSize--;
				if(BioHelper.addItemStackToInventory(itemstack.copy(), (IInventory) entity, 2, 4)){
					if(item.stackSize <= 0){
						ei.setDead();
						break;
					}
				}
				if(32 <= i)
					return;
			}
			
		}
	}
	
	private boolean canSmelt(IInventory ent, ItemStack stack, int min, int max)
    {
		if(stack == null)
			return false;
		
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(stack);
        if (itemstack == null) return false;
        
		for(int i = min; i < max; i++){
            if (ent.getStackInSlot(i) == null) return true;
            if (!ent.getStackInSlot(i).isItemEqual(itemstack)) continue;
            int result = ent.getStackInSlot(i).stackSize + itemstack.stackSize;
            if(result <= ent.getInventoryStackLimit() && result <= ent.getStackInSlot(i).getMaxStackSize())
            	return true;
		}
		return false;
    }

	@Override
	public int workSpan(ItemStack stack) {
		return 200;
	}
	
	
}