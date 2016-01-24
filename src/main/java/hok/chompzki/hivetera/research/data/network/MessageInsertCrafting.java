package hok.chompzki.hivetera.research.data.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public class MessageInsertCrafting implements IMessage {
	
	ItemStack result = null;
	
	public MessageInsertCrafting(){
		
	}

	public MessageInsertCrafting(ItemStack result) {
		this.result = result;
	}
	
	public ItemStack getResult(){
		return result;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
		try {
			String s = "";
			ObjectInputStream ois = new ObjectInputStream(bytesIn);
			s = (String) ois.readObject();
	        ois.close();
	        NBTTagCompound nbt = (NBTTagCompound) JsonToNBT.func_150315_a(s);
	        result = ItemStack.loadItemStackFromNBT(nbt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			result.writeToNBT(nbt);
			String nbtJsonStr = nbt.toString();  
			
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
		    oos.writeObject(nbtJsonStr);
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
