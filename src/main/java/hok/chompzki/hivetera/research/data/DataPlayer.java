package hok.chompzki.hivetera.research.data;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DataPlayer {
	
	EntityPlayer player = null;
	
	public DataPlayer(EntityPlayer player){
		this.player = player;
	}
	
	public UUID getId(){
		return player.getGameProfile().getId();
	}
	
}
