package hok.chompzki.biocristals.items;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.research.data.DataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBioBlob extends Item {
	
	public final static String NAME = "itemBioBlob";
	protected Random rand = new Random();
	
	public ItemBioBlob(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
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
		stack = player.getCurrentEquippedItem();
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(!entity.writeToNBTOptional(nbt))
			return true;
		
		stack.setTagCompound(nbt);
		
		entity.captureDrops = true;
		entity.setDead();
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("Stack has NBT: " + stack.hasTagCompound());
	}
	
}
