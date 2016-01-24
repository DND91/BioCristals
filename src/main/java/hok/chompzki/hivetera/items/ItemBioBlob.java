package hok.chompzki.hivetera.items;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.research.data.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class ItemBioBlob extends Item {
	
	public final static String NAME = "itemBioBlob";
	protected Random rand = new Random();
	
	public ItemBioBlob(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
		if(player.worldObj.isRemote)
			return true;
		EntityLiving target = (EntityLiving)entity;
		if(!target.isPotionActive(Potion.moveSlowdown)){
			player.addChatMessage(new ChatComponentText("Target not weakened for capture..."));
			return true;
		}
		
		stack = player.getCurrentEquippedItem();
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(!entity.writeToNBTOptional(nbt))
			return true;
		
		nbt.setString("BIO_NAME", entity.getCommandSenderName());
		//nbt.setString("BIO_NAME", (String) (EntityList.classToStringMapping.get(entity.getClass())));
		
		stack.setTagCompound(nbt);
		
		entity.captureDrops = true;
		entity.setDead();
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if(stack.hasTagCompound()){
			list.add("Prey: " + stack.getTagCompound().getString("BIO_NAME"));
		}else{
			list.add("Weaken a prey and then eat it!");
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
    {
		if(stack.hasTagCompound())
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim() + " (" + stack.getTagCompound().getString("BIO_NAME") + ")";
		return super.getItemStackDisplayName(stack);
    }
	
}
