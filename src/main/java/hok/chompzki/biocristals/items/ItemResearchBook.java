package hok.chompzki.biocristals.items;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.DataPlayer;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemResearchBook extends Item {
	
	public final static String NAME = "itemResearchBook";
	
	public ItemResearchBook() {
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		
		if(DataHelper.hasOwner(itemstack)){
			list.add("Owner: " + DataHelper.getOwnerName(itemstack, par2EntityPlayer.worldObj));
			
		}else{
			list.add("Owner: None");
		}
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		DataHelper.belongsTo(player, itemstack);
		
		if(!DataHelper.hasOwner(itemstack))
			return itemstack;
		
		player.openGui(BioCristalsMod.instance, 100, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		
		return itemstack;
	}
	
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
    {
		if(DataHelper.hasOwner(stack))
			return DataHelper.getOwnerName(stack, Minecraft.getMinecraft().theWorld) + "'s " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		return super.getItemStackDisplayName(stack);
    }
}
