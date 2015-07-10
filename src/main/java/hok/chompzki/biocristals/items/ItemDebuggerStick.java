package hok.chompzki.biocristals.items;

import java.util.Map.Entry;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.tutorials.data.DataPlayerProgression;
import hok.chompzki.biocristals.tutorials.data.StorageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebuggerStick extends Item {

	public final static String NAME = "itemDebuggerStick";
	
	public ItemDebuggerStick(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(!world.isRemote)
			return stack;
		
		System.out.println("============================================================");
		System.out.println("StorageHandler<DataPlayerProgression> size " + StorageHandler.data.size());
		System.out.println("============================================================");
		for(Entry<UUID, DataPlayerProgression> entry : StorageHandler.data.entrySet()){
			System.out.println("USERNAME " + entry.getValue().getUser());
		}
		System.out.println("============================================================");
		
        return stack;
    }
}
