package hok.chompzki.biocristals.research.data;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DataHelper {
	
	public static boolean hasOwner(ItemStack stack){
		if(!stack.hasTagCompound())
			return false;
		NBTTagCompound data = stack.getTagCompound();
		return data.hasKey("OWNER");
	}
	
	public static boolean belongsTo(EntityPlayer player, ItemStack stack){
		UUID userId = player.getGameProfile().getId();
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound data = stack.getTagCompound();
		if(data.hasKey("OWNER")){
			return data.getString("OWNER").equals(userId.toString());
		}else{
			data.setString("OWNER", userId.toString());
			return true;
		}
	}
	
	public static String getOwner(ItemStack stack){
		return stack.getTagCompound().getString("OWNER");
	}
	
	public static String getOwnerName(ItemStack stack, World world){
		UUID id = UUID.fromString(stack.getTagCompound().getString("OWNER"));
		return getOwnerName(id, world);
	}
	
	public static String getOwnerName(UUID id, World world){
		return PlayerStorage.instance().get(id).getUsername(world);
	}
}
