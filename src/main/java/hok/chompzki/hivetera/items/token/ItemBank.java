package hok.chompzki.hivetera.items.token;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IToken;
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

public class ItemBank extends ItemToken implements IToken {

	public final static String NAME = "itemBank";
	
	public ItemBank(){
		super(NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		String channel = this.getChannel(itemstack);
		list.add("Channel: " + I18n.format("container."+channel, new Object[0]));
		
		DecimalFormat df = new DecimalFormat("0.00"); 
		for(EnumResource res : EnumResource.values()){
			if(!itemstack.stackTagCompound.hasKey(res.name()))
				continue;
			double value = itemstack.stackTagCompound.getDouble(res.name());
			list.add(I18n.format("container."+res, new Object[0]) + ": " + df.format(value));
		}
		
		list.add("Max Size: " + NBTHelper.get(itemstack, "SIZE", 0.0D));
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		NBTHelper.init(stack, "CHANNEL", "NONE");
		NBTHelper.init(stack, "SIZE", 100.0D);
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if(world.isRemote)
			return stack;
		
		if(!player.capabilities.isCreativeMode)
			return stack;
		
		double size = NBTHelper.get(stack, "SIZE", 100.0D);
		NBTTagCompound nbt = stack.getTagCompound();
		
		for(EnumResource res : EnumResource.values()){
			if(!nbt.hasKey(res.name()))
				continue;
			
			double saved = nbt.getDouble(res.name()) ;
			double value = Math.min(size, saved + 100);
			nbt.setDouble(res.name(), value);
		}
		
		return stack;
	}
	
	@Override
	public boolean stop(ItemStack stack) {
		return true;
	}
	
	@Override
	public EnumToken getType(ItemStack stack) {
		return EnumToken.BANK;
	}
	
	@Override
	public void feed(ItemStack stack, ResourcePackage amount) {
		double size = NBTHelper.get(stack, "SIZE", 100.0D);
		NBTTagCompound nbt = stack.getTagCompound();
		
		for(EnumResource res : EnumResource.values()){
			if(!nbt.hasKey(res.name()))
				continue;
			
			double saved = nbt.getDouble(res.name()) ;
			double value = Math.min(size, saved + amount.get(res));
			double diff = value - saved;
			
			nbt.setDouble(res.name(), value);
			amount.sub(res, diff);
		}
	}
	
	@Override
	public void drain(ItemStack stack, ResourcePackage p, double amount) {
		NBTTagCompound nbt = stack.getTagCompound();
		
		for(EnumResource res : EnumResource.values()){
			if(!nbt.hasKey(res.name()))
				continue;
			
			double value = nbt.getDouble(res.name());
			double drain = Math.min(value, amount);
			value -= drain;
			nbt.setDouble(res.name(), value);
			p.add(res, drain);
		}
	}

	@Override
	public boolean canFeed(ItemStack input, ResourcePackage pack) {
		double size = NBTHelper.get(input, "SIZE", 100.0D);
		NBTTagCompound nbt = input.getTagCompound();
		
		for(EnumResource res : EnumResource.values()){
			if(!nbt.hasKey(res.name()))
				continue;
			double saved = nbt.getDouble(res.name());
			if(size < (saved+pack.get(res)))
				return false;
		}
		return true;
	}

}
