package hok.chompzki.hivetera.research.data.network;

import hok.chompzki.hivetera.research.data.PlayerResearch;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PlayerStorageSyncMessage implements IMessage {
	
	public PlayerResearch research = null;
	
	public PlayerStorageSyncMessage(PlayerResearch research){
		this.research = research;
	}
	
	public PlayerStorageSyncMessage(){
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bytesIn);
	        research = (PlayerResearch) ois.readObject();
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
		    oos.writeObject(research);
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
	
	public PlayerResearch getResearch(){
		return research;
	}
	
}