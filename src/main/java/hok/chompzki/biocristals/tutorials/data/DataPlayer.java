package hok.chompzki.biocristals.tutorials.data;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DataPlayer {
	
	EntityPlayer player = null;
	DataPlayerProgression data = null;
	
	public DataPlayer(EntityPlayer player){
		this.player = player;
		data = StorageHandler.getPlayerBioData(this.getId());
	}
	
	public UUID getId(){
		return player.getGameProfile().getId();
	}
	
	public DataPlayerProgression getProgression(){
		return data;
	}
}
