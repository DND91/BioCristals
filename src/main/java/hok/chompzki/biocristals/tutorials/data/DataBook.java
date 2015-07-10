package hok.chompzki.biocristals.tutorials.data;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DataBook {
	
	ItemStack book = null;
	NBTTagCompound data = null;
	
	public DataBook(ItemStack book){
		this.book = book;
		if(!book.hasTagCompound())
			book.setTagCompound(new NBTTagCompound());
		data = book.getTagCompound();
	}
	
	public boolean belongsTo(DataPlayer player){
		UUID username = player.getId();
		if(data.hasKey("OWNER")){
			return data.getString("OWNER").equals(username.toString());
		}else{
			data.setString("OWNER", username.toString());
			return true;
		}
	}
	
	public boolean hasOwner(){
		return data.hasKey("OWNER");
	}
	
	public String getOwner(){
		return data.getString("OWNER");
	}
}
