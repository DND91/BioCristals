package hok.chompzki.hivetera.hunger.network;

import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.hunger.PlayerHungerNetworkData;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PlayerHungerSyncMessage implements IMessage {
	
	public PlayerHungerNetwork research = null;
	
	public PlayerHungerSyncMessage(PlayerHungerNetwork sub){
		this.research = sub;
	}
	
	public PlayerHungerSyncMessage(){
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bytesIn);
	        research = ((PlayerHungerNetworkData) ois.readObject()).toForm();
	        ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
		    oos.writeObject(research.toData());
		    oos.flush();
		    byte[] bytes = bytesOut.toByteArray();
		    bytesOut.close();
		    oos.close();
		    buf.capacity(bytes.length);
		    buf.writeBytes(bytes);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlayerHungerNetwork getHunger(){
		return research;
	}
	
}