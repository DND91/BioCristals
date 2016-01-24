package hok.chompzki.hivetera;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	
	public static void init(ItemStack stack, String key, boolean value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setBoolean(key, value);
	}
	
	public static void init(ItemStack stack, String key, byte value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setByte(key, value);
	}
	
	public static void init(ItemStack stack, String key, short value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setShort(key, value);
	}
	
	public static void init(ItemStack stack, String key, int value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setInteger(key, value);
	}
	
	public static void init(ItemStack stack, String key, float value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setFloat(key, value);
	}
	
	public static void init(ItemStack stack, String key, double value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setDouble(key, value);
	}
	
	public static void init(ItemStack stack, String key, String value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setString(key, value);
	}
	
	public static void init(ItemStack stack, String key, NBTBase value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setTag(key, value);
	}
	
	public static void init(ItemStack stack, String key, int[] value){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey(key))
			stack.getTagCompound().setIntArray(key, value);
	}
	
	/** GET WITH STANDRARD **/
	public static boolean get(ItemStack stack, String key, boolean value){
		init(stack, key, value);
		return stack.getTagCompound().getBoolean(key);
	}
	
	public static byte get(ItemStack stack, String key, byte value){
		init(stack, key, value);
		return stack.getTagCompound().getByte(key);
	}
	
	public static short get(ItemStack stack, String key, short value){
		init(stack, key, value);
		return stack.getTagCompound().getShort(key);
	}
	
	public static int get(ItemStack stack, String key, int value){
		init(stack, key, value);
		return stack.getTagCompound().getInteger(key);
	}
	
	public static float get(ItemStack stack, String key, float value){
		init(stack, key, value);
		return stack.getTagCompound().getFloat(key);
	}
	
	public static double get(ItemStack stack, String key, double value){
		init(stack, key, value);
		return stack.getTagCompound().getDouble(key);
	}
	
	public static String get(ItemStack stack, String key, String value){
		init(stack, key, value);
		return stack.getTagCompound().getString(key);
	}
	
	public static NBTBase get(ItemStack stack, String key, NBTBase value){
		init(stack, key, value);
		return stack.getTagCompound().getTag(key);
	}
	
	public static int[] get(ItemStack stack, String key, int[] value){
		init(stack, key, value);
		return stack.getTagCompound().getIntArray(key);
	}
}
