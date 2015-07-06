package hok.chompzki.biocristals.tutorials;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
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
		if(itemstack.hasTagCompound()){
			String name = itemstack.getTagCompound().getString("Owner");
			list.add("Owner: " + name);
		}
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		String username = player.getGameProfile().getName();
		if(!itemstack.hasTagCompound()){
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("Owner", username);
			itemstack.setTagCompound(compound);
		}else{
			NBTTagCompound compound = itemstack.getTagCompound();
			String owner = compound.getString("Owner");
			if(!owner.equals(username)){
				player.addChatMessage(new ChatComponentText("You do not own this book..."));
				return itemstack;
			}
		}
		
		NBTTagCompound comp = player.getEntityData();
		if(!comp.hasKey(username+".BioBook")){
			NBTTagCompound hivebook = new NBTTagCompound(); //TODO: KnowledgeAppedix.makeHiveBook();
			hivebook.setString("Owner", username);
			comp.setTag(username+".BioBook", hivebook);
			//TODO: KnowledgeAppedix.unlockKnowledge(player, KnowledgeAppedix.old_tome);
		}
		
		player.openGui(BioCristalsMod.instance, 100, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return itemstack;
	}
}
