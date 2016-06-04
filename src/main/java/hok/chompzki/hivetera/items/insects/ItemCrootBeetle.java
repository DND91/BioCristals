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
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemCrootBeetle extends ItemInsect implements INestInsect {
	
	public final static String NAME = "itemCrootBeetle";
	private Random rand = new Random();
	
	public ItemCrootBeetle(){
		super(EnumResource.RAW_FOOD, 1.0D, 10.0D, false);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(64);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add("I live in grass...");
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I'm currently helping your plants grow!";
	}
	
	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		int y = entity.yCoord;
		World world = entity.getWorldObj();
		
		for(int x = entity.xCoord - 4; x <= entity.xCoord + 4; x++)
		for(int z = entity.zCoord - 4; z <= entity.zCoord + 4; z++){
			if(world.getBlock(x, y, z) instanceof IGrowable && rand.nextInt(6) == 0){
				IGrowable plant = (IGrowable)world.getBlock(x, y, z);
				Block block = world.getBlock(x, y, z);
				FakePlayer player = FakePlayerFactory.get((WorldServer) world, new GameProfile(UUID.randomUUID(), "BioCristals"));
		        BonemealEvent event = new BonemealEvent(player, world, block, x, y, z);
		        if (MinecraftForge.EVENT_BUS.post(event))
		        {
		            continue;
		        }

		        if (event.getResult() == Result.ALLOW)
		        {
		        	continue;
		        }

		        if (block instanceof IGrowable)
		        {
		            IGrowable igrowable = (IGrowable)block;

		            if (igrowable.func_149851_a(world, x, y, z, world.isRemote))
		            {
		                if (!world.isRemote)
		                {
		                    if (igrowable.func_149852_a(world, world.rand, x, y, z))
		                    {
		                        igrowable.func_149853_b(world, world.rand, x, y, z);
		                    }
		                }

		                continue;
		            }
		        }

		        continue;
			}
		}
	}
	
	private boolean hasPlanteable(TileEntity entity){
		int y = entity.yCoord;
		World world = entity.getWorldObj();
		
		for(int x = entity.xCoord - 4; x <= entity.xCoord + 4; x++)
		for(int z = entity.zCoord - 4; z <= entity.zCoord + 4; z++){
			if(world.getBlock(x, y, z) instanceof IGrowable){
				IGrowable grow = (IGrowable)world.getBlock(x, y, z);
				if(grow.func_149851_a(world, x, y, z, true))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		return hasPlanteable(entity);
	}

	@Override
	public int workSpan(ItemStack stack) {
		return 64 / stack.stackSize;
	}
}
