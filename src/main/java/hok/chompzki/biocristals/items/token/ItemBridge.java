package hok.chompzki.biocristals.items.token;

import java.util.List;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.NBTHelper;
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

public class ItemBridge extends ItemToken implements IToken {
	
	public final static String NAME = "itemBridge";
	
	public ItemBridge(){
		super(NAME);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		String channel = this.getChannel(itemstack);
		list.add("Channel: " + I18n.format("container."+channel, new Object[0]));
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
		return EnumToken.BRIDGE;
	}
	
	@Override
	public void feed(ItemStack stack, ResourcePackage amount) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void drain(ItemStack stack, ResourcePackage p, double amount) {
		// TODO Auto-generated method stub
		
	}

}
