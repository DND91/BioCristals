package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.api.IEntityTransformation;
import hok.chompzki.biocristals.api.ITransformation;
import hok.chompzki.biocristals.transformation.WeakCristalTransformation;
import hok.chompzki.biocristals.transformation.WeakFleshTransformation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CristalRegistry {
	
	
private static ArrayList<IEntityTransformation> entityTransformations = new ArrayList<IEntityTransformation>();
	
	public static void register(IEntityTransformation trans){
		entityTransformations.add(trans);
	}
	
	public static IEntityTransformation get(ItemStack stack, EntityPlayer player, World world, Entity target){
		for(IEntityTransformation trans : entityTransformations){
			if(trans.hasResources(stack, player, target) && trans.canTransform(stack, player, world, target)){
				return trans;
			}
		}
		return null;
	}
	
	private static ArrayList<ITransformation> transformations = new ArrayList<ITransformation>();
	
	public static void register(ITransformation struct){
		transformations.add(struct);
	}
	
	public static ITransformation get(ItemStack stack, EntityPlayer player, World world, int x, int y, int z){
		for(ITransformation struct : transformations){
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
		
		CristalRegistry.register(new WeakFleshTransformation(EntitySheep.class, new ItemStack(Blocks.wool)));
		
	}
}
