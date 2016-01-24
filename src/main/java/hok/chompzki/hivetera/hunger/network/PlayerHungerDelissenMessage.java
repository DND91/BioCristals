package hok.chompzki.hivetera.hunger.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.network.DesContainer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PlayerHungerDelissenMessage implements IMessage {
	
	DesContainer container = new DesContainer();
	
	public PlayerHungerDelissenMessage(UUID observer, String subject){
		container.observer = observer.toString();
		container.subject = subject.toString();
	}
	
	public PlayerHungerDelissenMessage(){
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bytesIn);
			container = (DesContainer) ois.readObject();
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
		    oos.writeObject(container);
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
	
	public DesContainer getContainer(){
		return container;
	}

}
