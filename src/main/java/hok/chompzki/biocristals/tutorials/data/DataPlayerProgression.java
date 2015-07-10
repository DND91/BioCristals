package hok.chompzki.biocristals.tutorials.data;

import java.io.Serializable;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;

public class DataPlayerProgression implements Serializable {
	
	private UUID id = null;
	private NBTTagCompoundSerializable data = null;
	
	public DataPlayerProgression(UUID id){
		this.id = id;
		data = new NBTTagCompoundSerializable();
	}
	
	public UUID getUser(){
		return id;
	}
	
	public NBTTagCompound getData(){
		return data;
	}
}
