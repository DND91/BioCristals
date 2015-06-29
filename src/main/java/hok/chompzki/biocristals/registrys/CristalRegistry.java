package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.api.ITransformation;
import hok.chompzki.biocristals.transformation.WeakCristalTransformation;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CristalRegistry {
	
	private static ArrayList<ITransformation> structures = new ArrayList<ITransformation>();
	
	public static void register(ITransformation struct){
		structures.add(struct);
	}
	
	public static ITransformation get(ItemStack stack, EntityPlayer player, World world, int x, int y, int z){
		for(ITransformation struct : structures){
			if(struct.hasResources(stack, player) && struct.canPlaceAt(stack, player, world, x, y, z)){
				return struct;
			}
		}
		return null;
	}
	
	public static void registerAll(){
		CristalRegistry.register(new WeakCristalTransformation(Items.wheat, BlockRegistry.wheatCristal));
		CristalRegistry.register(new WeakCristalTransformation(Items.carrot, BlockRegistry.carrotCristal));
		CristalRegistry.register(new WeakCristalTransformation(Items.reeds, BlockRegistry.sugerCaneCristal));
		CristalRegistry.register(new WeakCristalTransformation(Items.potato, BlockRegistry.potatoCristal));
		CristalRegistry.register(new WeakCristalTransformation(Items.melon, BlockRegistry.melonCristal));
		CristalRegistry.register(new WeakCristalTransformation((new ItemStack(Blocks.pumpkin)).getItem(), BlockRegistry.wheatCristal));
		
	}
}
