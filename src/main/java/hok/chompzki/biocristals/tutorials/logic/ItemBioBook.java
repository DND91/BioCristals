package hok.chompzki.biocristals.tutorials.logic;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.tutorials.data.DataBook;
import hok.chompzki.biocristals.tutorials.data.DataPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBioBook extends Item {
	
	public final static String NAME = "itemBiobook";
	
	public ItemBioBook() {
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		DataBook book = new DataBook(itemstack);
		
		if(book.hasOwner()){
			list.add("Owner: " + book.getOwner());
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
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer ply){
		DataBook book = new DataBook(itemstack);
		DataPlayer player = new DataPlayer(ply);
		
		book.belongsTo(player);
		
		ply.openGui(BioCristalsMod.instance, 100, world, (int)ply.posX, (int)ply.posY, (int)ply.posZ);
		return itemstack;
	}
	
	public String getItemStackDisplayName(ItemStack stack)
    {
		DataBook book = new DataBook(stack);
		if(book.hasOwner())
			return book.getOwner() + "'s " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		return super.getItemStackDisplayName(stack);
    }
}
