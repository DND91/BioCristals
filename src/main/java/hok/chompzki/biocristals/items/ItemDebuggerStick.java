package hok.chompzki.biocristals.items;

import java.util.Map.Entry;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.croot.ICroot;
import hok.chompzki.biocristals.croot.ICrootCore;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
		
		
		
		
        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		
		
		
        return true;
    }
}












