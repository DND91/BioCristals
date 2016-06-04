package hok.chompzki.hivetera.api;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;

public interface IToken {
	
	public boolean stop(ItemStack stack);
	
	public EnumToken getType(ItemStack stack);

	public String getChannel(ItemStack stack);

	public String getOwner(ItemStack stack);
	
	public void feed(ItemStack stack, ResourcePackage amount);
	
	public void drain(ItemStack stack, ResourcePackage p, double amount);

	public boolean canFeed(ItemStack input, ResourcePackage pack);
	
	
}
