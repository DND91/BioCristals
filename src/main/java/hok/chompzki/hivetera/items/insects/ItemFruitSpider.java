package hok.chompzki.hivetera.items.insects;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.entity.EntityFruitSpider;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemFruitSpider extends ItemInsect implements INestInsect {
	
	public final static String NAME = "itemFruitSpider";
	private Random rand = new Random();
	
	public ItemFruitSpider(){
		super(EnumResource.BIOMASS, 5.0D, 10.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		super.addInformation(stack, player, list, advancedTooltip);
		list.add("I... serve...");
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will harvest mature plants.";
	}
	
	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			
			if(world.isAirBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
				int newY = y + dir.offsetY;
				for(;world.isAirBlock(x + dir.offsetX, newY, z + dir.offsetZ);newY--) {}
				if(!(world.getBlock(x + dir.offsetX, newY, z + dir.offsetZ) instanceof IGrowable))
					newY++;
				EntityFruitSpider spider = new EntityFruitSpider(world);
		        spider.setPosition(x + dir.offsetX + 0.5D, newY + 0.5D, z + dir.offsetZ + 0.5D);
		        world.spawnEntityInWorld(spider);
			}
		}
	}
	
	private boolean hasMature(TileEntity entity){
		World world = entity.getWorldObj();
		
		for(int x = entity.xCoord - 4; x <= entity.xCoord + 4; x++)
		for(int y = entity.yCoord - 4; y <= entity.yCoord + 4; y++)
		for(int z = entity.zCoord - 4; z <= entity.zCoord + 4; z++){
			if(world.getBlock(x, y, z) instanceof IGrowable){
				IGrowable grow = (IGrowable)world.getBlock(x, y, z);
				if(!grow.func_149851_a(world, x, y, z, true))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityFruitSpider> list = world.getEntitiesWithinAABB(EntityFruitSpider.class, bb.expand(4, 4, 4));
		
		return hasMature(entity) && list.size() <= 0;
	}

	@Override
	public int workSpan(ItemStack stack) {
		return 64 / stack.stackSize;
	}
}
