package hok.chompzki.biocristals.structure;

import hok.chompzki.biocristals.structure.farming.TestFarmingStructure;
import hok.chompzki.biocristals.structure.farming.WheatCristalStructure;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FarmingStructures {
	
	private static ArrayList<IStructure> structures = new ArrayList<IStructure>();
	
	public static void register(IStructure struct){
		structures.add(struct);
	}
	
	public static IStructure get(ItemStack stack, EntityPlayer player, World world, int x, int y, int z){
		for(IStructure struct : structures){
			if(struct.hasResources(stack, player) && struct.canPlaceAt(stack, player, world, x, y, z)){
				return struct;
			}
		}
		return null;
	}
	
	public static void registerAll(){
		FarmingStructures.register(new TestFarmingStructure());
		FarmingStructures.register(new WheatCristalStructure());
		
		
	}
}
