package hok.chompzki.hivetera.items;

import java.util.Map.Entry;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.CrootHelper;
import hok.chompzki.hivetera.api.ICroot;
import hok.chompzki.hivetera.api.ICrootCore;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.entity.EntityFruitSpider;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;
import hok.chompzki.hivetera.tile_enteties.TileReplacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDebuggerStick extends Item {

	public final static String NAME = "itemDebuggerStick";
	
	public ItemDebuggerStick(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		
		
        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x2, int y2, int z2, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		
		System.out.println("FEED AND DRAIN!!!!");
		
		String userId = player.getCommandSenderName();
		
		if(!player.isSneaking()){
			ResourcePackage pack = new ResourcePackage();
			pack.add(EnumResource.RAW_FOOD, 40.0D);
			PlayerHungerStorage.instance(false).feed(userId, "NONE", pack);
			System.out.println("--- TRAVELED FEED PACKAGE!");
			pack.print();
		} else {
			ResourcePackage pack = PlayerHungerStorage.instance(false).drain(userId, "NONE", 15.0D);
			System.out.println("--- TRAVELED DRAIN PACKAGE!");
			pack.print();
		}
        return true;
    }
}












