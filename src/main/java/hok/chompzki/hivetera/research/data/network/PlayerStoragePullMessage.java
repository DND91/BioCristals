package hok.chompzki.hivetera.research.data.network;

import hok.chompzki.hivetera.research.data.PlayerResearch;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PlayerStoragePullMessage implements IMessage {
	
	public UUID id;
	
	public PlayerStoragePullMessage(UUID id){
		this.id = id;
	}
	
	public PlayerStoragePullMessage(){
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bytesIn);
	        id = (UUID) ois.readObject();
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
		    oos.writeObject(id);
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
}