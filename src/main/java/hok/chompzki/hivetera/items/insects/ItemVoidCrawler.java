package hok.chompzki.hivetera.items.insects;

import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.tile_enteties.TileReplacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemVoidCrawler extends ItemInsect implements INestInsect {
	
	public final static String NAME = "itemVoidCrawler";
	
	public ItemVoidCrawler(){
		super(EnumResource.PSY_ENG, 25.0D, 100.0D, true);
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		feed(player, stack, player.isSneaking());
		if(0.0F < player.getHealth())
			activate(stack, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		
        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x2, int y2, int z2, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
		
		feed(player, stack, player.isSneaking());
		if(0.0F < player.getHealth())
			activate(stack, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		
        return true;
    }
	
	public void activate(ItemStack stack, World world, int x, int y, int z){
		
		for(int i = -5; i <= 5; i++)
		for(int j = 0; j <= 5; j++)
		for(int k = -5; k <= 5; k++){
			Block block = world.getBlock(x+i, y+j, z+k);
			int meta = world.getBlockMetadata(x+i, y+j, z+k);
			int distance = i * i + j * j + k * k;
			distance *= ConfigRegistry.displaceMultiplier;
			
			if(block == Blocks.air || block instanceof BlockContainer || block instanceof ITileEntityProvider
					|| block.hasTileEntity(meta) || block == BlockRegistry.replacer || block == BlockRegistry.replacerOpen 
					|| block.getLightValue() != 0 || block.getBlockHardness(world, x+i, y+j, z+k) < 0.0F){
				if((block == Blocks.air || block == null) && world.isAirBlock(x+i, y+j+1, z+k) && world.isSideSolid(x+i, y+j-1, z+k, ForgeDirection.UP)){
					world.setBlock(x+i, y+j, z+k, BlockRegistry.replacerOpen, 0, 2);
					world.setTileEntity(x+i, y+j, z+k, new TileReplacer(block, meta, ConfigRegistry.displaceTime - distance));
				}
				if(block == BlockRegistry.replacer || block == BlockRegistry.replacerOpen){
					TileReplacer tile = (TileReplacer) world.getTileEntity(x+i, y+j, z+k);
					tile.trigger = ConfigRegistry.displaceTime - distance;
				}
				continue;
			}
			if(KnowledgeDescriptions.getName(new ItemStack(block)).contains("ore"))
				continue;
			
			world.setBlock(x+i, y+j, z+k, BlockRegistry.replacer, 0, 2);
			world.setTileEntity(x+i, y+j, z+k, new TileReplacer(block, meta, ConfigRegistry.displaceTime - distance));
		}
	}

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will phase what is not of value.";
	}
	
	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 6, entity.zCoord + 1);
		List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, bb.expand(6, 0, 6));
		
		return 0 < list.size();
	}
	
	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		activate(stack, entity.getWorldObj(), entity.xCoord, entity.yCoord, entity.zCoord);
	}
	
	@Override
	public int workSpan(ItemStack stack) {
		return ConfigRegistry.displaceTime / stack.stackSize;
	}
	
	
}
