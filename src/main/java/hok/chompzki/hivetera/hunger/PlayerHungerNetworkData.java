package hok.chompzki.hivetera.hunger;

import hok.chompzki.hivetera.research.data.PlayerResearchStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerHungerNetworkData implements Serializable{
	
	protected String name = null;
	
	protected ArrayList<String> contents = new ArrayList<String>();
	
	public PlayerHungerNetwork toForm(){
		PlayerHungerNetwork form = new PlayerHungerNetwork(name);
		int i = 0;
		for(String str : contents){
			if(str.equals("NULL")){
				form.contents[i] = null;
			} else {
				try {
					form.contents[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound) JsonToNBT.func_150315_a(str));
				} catch (NBTException e) {
					e.printStackTrace();
				}
			}
			i++;
		}
		
		
		return form;
	}
	
	
}
