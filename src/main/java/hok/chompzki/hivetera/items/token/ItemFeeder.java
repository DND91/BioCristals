package hok.chompzki.hivetera.items.token;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.research.data.DataHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFeeder extends ItemToken implements IToken {
	
	public final static String NAME = "itemFeeder";
	
	public ItemFeeder(){
		super(NAME);
	}
	

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		String channel = this.getChannel(itemstack);
		list.add("Channel: " + I18n.format("container."+channel, new Object[0]));
		
		if(DataHelper.hasNetwork(itemstack)){
			list.add("Network: " + DataHelper.getNetworkName(itemstack, par2EntityPlayer.worldObj));
			list.add("Right-click + sneak to clear network");
		}else{
			list.add("Network: None");
			list.add("Right-click to bind to you.");
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
			NBTHelper.init(stack, "CHANNEL", "NONE");
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
		if(!player.isSneaking()){
			
		}else
			itemstack.stackTagCompound.removeTag("NETWORK");
		
		return itemstack;
	}

	@Override
	public boolean stop(ItemStack stack) {
		return true;
	}

	@Override
	public EnumToken getType(ItemStack stack) {
		return EnumToken.FEEDER;
	}

	@Override
	public void feed(ItemStack stack, ResourcePackage amount) {
		if(DataHelper.hasNetwork(stack)){
			String network = DataHelper.getNetwork(stack);
			String channel = this.getChannel(stack);
			PlayerHungerStorage.instance(false).feed(network, channel, amount);
		}
	}
	
	
	@Override
	public void drain(ItemStack stack, ResourcePackage p, double amount) {
	}


	@Override
	public boolean canFeed(ItemStack input, ResourcePackage pack) {
		if(DataHelper.hasNetwork(input)){
			String network = DataHelper.getNetwork(input);
			String channel = this.getChannel(input);
			return PlayerHungerStorage.instance(false).canFeed(network, channel, pack);
		}
		return false;
	}

}
