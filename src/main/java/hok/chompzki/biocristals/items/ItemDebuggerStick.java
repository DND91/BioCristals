package hok.chompzki.biocristals.items;

import java.util.Map.Entry;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.CrootHelper;
import hok.chompzki.biocristals.api.ICroot;
import hok.chompzki.biocristals.api.ICrootCore;
import hok.chompzki.biocristals.client.gui.KnowledgeDescriptions;
import hok.chompzki.biocristals.entity.EntityFruitSpider;
import hok.chompzki.biocristals.entity.EntityWSB;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;
import hok.chompzki.biocristals.tile_enteties.TileReplacer;
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
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(world.isRemote)
			return stack;
		
		activate(stack, player, world);
		
        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x2, int y2, int z2, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.isRemote)
			return true;
			
		activate(stack, player, world);
		
        return true;
    }
	
	public void activate(ItemStack stack, EntityPlayer player, World world){
		
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		
		for(int i = -5; i <= 5; i++)
		for(int j = 0; j <= 5; j++)
		for(int k = -5; k <= 5; k++){
			Block block = world.getBlock(x+i, y+j, z+k);
			int meta = world.getBlockMetadata(x+i, y+j, z+k);
			int distance = i * i + j * j + k * k;
			distance *= ConfigRegistry.displaceMultiplier;
			
			if(block == Blocks.air || block instanceof BlockContainer || block instanceof ITileEntityProvider
					|| block.hasTileEntity(meta) || block == BlockRegistry.replacer || block.getLightValue() != 0
					|| block.getBlockHardness(world, x+i, y+j, z+k) < 0.0F){
				if((block == Blocks.air || block == null) && world.isAirBlock(x+i, y+j+1, z+k) && world.isSideSolid(x+i, y+j-1, z+k, ForgeDirection.UP)){
					world.setBlock(x+i, y+j, z+k, BlockRegistry.replacerOpen, 0, 2);
					world.setTileEntity(x+i, y+j, z+k, new TileReplacer(block, meta, ConfigRegistry.displaceTime - distance));
				}
				continue;
			}
			if(KnowledgeDescriptions.getName(new ItemStack(block)).contains("ore"))
				continue;
			
			world.setBlock(x+i, y+j, z+k, BlockRegistry.replacer, 0, 2);
			world.setTileEntity(x+i, y+j, z+k, new TileReplacer(block, meta, ConfigRegistry.displaceTime - distance));
		}
		
	}
}












