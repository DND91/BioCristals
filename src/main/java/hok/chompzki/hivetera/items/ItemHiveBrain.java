package hok.chompzki.hivetera.items;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.DataPlayer;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemHiveBrain extends Item {
	
	public final static String NAME = "itemHiveBrain";
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemHiveBrain() {
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.tokenTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		GameRegistry.registerItem(this, NAME, HiveteraMod.MODID);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if(DataHelper.hasNetwork(itemstack)){
			list.add("Network: " + DataHelper.getNetworkName(itemstack, par2EntityPlayer.worldObj));
			list.add("Right-click + sneak to clear network");
		}else{
			list.add("Network: None");
			list.add("Right-click to bind to you.");
		}
		
	}
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
	
	public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (!hasContainerItem(itemStack))
        {
            return null;
        }
        return itemStack.copy();
    }
	
    public boolean hasContainerItem(ItemStack stack)
    {
        return DataHelper.hasNetwork(stack);
    }
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(!DataHelper.hasNetwork(itemstack)){
			String userId = player.getCommandSenderName();
			if(!itemstack.hasTagCompound())
				itemstack.setTagCompound(new NBTTagCompound());
			NBTTagCompound data = itemstack.getTagCompound();
			if(!data.hasKey("NETWORK")){
				Side side = FMLCommonHandler.instance().getEffectiveSide();
				if(side == Side.SERVER){
					data.setString("NETWORK", userId.toString());
				}
			}
			return itemstack;
		}
		
		if(world.isRemote)
			return itemstack;
		if(!player.isSneaking())
			player.openGui(HiveteraMod.instance, 106, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		else
			itemstack.stackTagCompound.removeTag("NETWORK");
		
		return itemstack;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(stack.hasTagCompound())
			return;
		
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("OPEN", false);
	}
	
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
    {
		if(DataHelper.hasOwner(stack))
			return DataHelper.getOwnerName(stack, Minecraft.getMinecraft().theWorld) + "'s " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		return super.getItemStackDisplayName(stack);
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
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
        if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
		if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		if(stack.stackTagCompound == null)
			this.onCreated(stack, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer);
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
		if(isOpen)
        	return this.icons[1];
		return this.icons[0];
    }
	
	
}
