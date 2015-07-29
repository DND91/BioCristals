package hok.chompzki.biocristals.transformation;

import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.ITransformation;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WeakCristalTransformation implements ITransformation {
	
	private Item input;
	private Block output;
	private String code;
	
	public WeakCristalTransformation(Item input, Block output, String code){
		this.input = input;
		this.output = output;
		this.code = code;
	}

	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canPlaceAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z) {// AxisAlignedBB
		
		return BioHelper.getFirstEntityItemWithinAABB(world, player, input, 10, 10, 10) != null;
	}
	
	@Override
	public void pay(ItemStack stack, EntityPlayer player) {
		
	}
	
	@Override
	public void construct(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z) {
		EntityItem item = BioHelper.getFirstEntityItemWithinAABB(world, player, input, 10, 10, 10);
		if(item != null && item.getEntityItem().getItem() == input){
			world.setBlock(x, y, z, output);
			
			item.getEntityItem().stackSize--;
			if(item.getEntityItem().stackSize <= 0)
				item.setDead();
			
			if(code != null){
				UUID id = player.getGameProfile().getId();
				PlayerResearch research = PlayerStorage.instance(false).get(id);
				if(!research.hasCompleted(code)){
					research.addCompleted(code);
				} 
			}
		}
	}

}
