package hok.chompzki.hivetera.transformation;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.ITransformation;
import hok.chompzki.hivetera.recipes.TransformerContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;

import java.util.ArrayList;
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
	
	private TransformerContainer container = null;

	public WeakCristalTransformation(TransformerContainer con) {
		container = con;
	}

	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player) {
		UUID id = player.getGameProfile().getId();
		PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
		if(research == null){
			System.out.println("UNKOWN PLAYER TRIED TRANSFORMATION WITHOUT REGISTATION: " + player.getDisplayName());
			return false;
		}
		return container.code.equals("NONE") || ResearchLogicNetwork.instance().available(research, container.code);
	}

	@Override
	public boolean canPlaceAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z) {// AxisAlignedBB
		for(ItemStack input : container.getTrueInput())
			if(BioHelper.getFirstEntityItemWithinAABB(world, player, input.getItem(), 10, 10, 10) != null)
				return true;
		
		return false;
	}
	
	@Override
	public void pay(ItemStack stack, EntityPlayer player) {
		
	}
	
	@Override
	public void construct(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z) {
		EntityItem item = null;
		
		for(ItemStack input : container.getTrueInput()){
			item = BioHelper.getFirstEntityItemWithinAABB(world, player, input.getItem(), 10, 10, 10);
			if(item != null)
				break;
		}
		
		if(item != null){
			world.setBlock(x, y, z, Block.getBlockFromItem(container.output.getItem()));
			
			item.getEntityItem().stackSize--;
			if(item.getEntityItem().stackSize <= 0)
				item.setDead();
			
			if(container.code != null && !container.code.equals("NONE")){
				UUID id = player.getGameProfile().getId();
				PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
				if(research == null){
					System.out.println("Ehm... someone tried to complete the uncompletable! " + player.getDisplayName());
				}else
					ResearchLogicNetwork.instance().compelte(research, container.code);
			}
		}
	}

}
