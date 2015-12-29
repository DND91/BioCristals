package hok.chompzki.biocristals.items.token;

import java.util.List;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;
import hok.chompzki.biocristals.research.data.DataHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFilter extends ItemToken implements IToken {
	
	public final static String NAME = "itemFilter";
	
	public ItemFilter(){
		super(NAME);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		String channel = itemstack.hasTagCompound() ? this.getChannel(itemstack) : "NONE";
		list.add("Channel: " + I18n.format("container."+channel, new Object[0]));
		
		if(!itemstack.hasTagCompound())
			return;
		
		list.add("- Filter -");
		int[] arr = itemstack.stackTagCompound.getIntArray("FILTER");
		int j = 0;
		for(int i : arr){
			if(i == 1)
				list.add(I18n.format("container."+EnumResource.values()[j], new Object[0]));
			j++;
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(stack.hasTagCompound())
			return;
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("CHANNEL", "NONE");
		stack.stackTagCompound.setIntArray("FILTER", new int[EnumResource.values().length]);
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote)
			return itemstack;
		
		return itemstack;
	}
	
	@Override
	public boolean stop(ItemStack stack) {
		return false;
	}
	
	@Override
	public EnumToken getType(ItemStack stack) {
		return EnumToken.FILTER;
	}
	
	@Override
	public void feed(ItemStack stack, ResourcePackage amount) {
		int[] arr = stack.stackTagCompound.getIntArray("FILTER");
		int j = 0;
		for(int i : arr){
			if(i == 1)
				amount.backlog(EnumResource.values()[j]);
			j++;
		}
	}
	
	@Override
	public void drain(ItemStack stack, ResourcePackage p, double amount) {
		int[] arr = stack.stackTagCompound.getIntArray("FILTER");
		int j = 0;
		for(int i : arr){
			if(i == 1)
				p.backlog(EnumResource.values()[j]);
			j++;
		}
	}

}
