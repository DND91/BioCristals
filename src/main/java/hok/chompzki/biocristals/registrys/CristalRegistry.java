package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.api.IEntityTransformation;
import hok.chompzki.biocristals.api.ITransformation;
import hok.chompzki.biocristals.transformation.WeakCristalTransformation;
import hok.chompzki.biocristals.transformation.WeakFleshTransformation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
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
		CristalRegistry.register(new WeakCristalTransformation(Items.wheat, BlockRegistry.wheatCristal, ReserchRegistry.wheatCristalisation));
		CristalRegistry.register(new WeakCristalTransformation(Items.carrot, BlockRegistry.carrotCristal, ReserchRegistry.carrotCristalisation));
		CristalRegistry.register(new WeakCristalTransformation(Items.reeds, BlockRegistry.reedsCristal, ReserchRegistry.reedsCristalisation));
		CristalRegistry.register(new WeakCristalTransformation(Items.potato, BlockRegistry.potatoCristal, ReserchRegistry.potatoCristalisation));
		CristalRegistry.register(new WeakCristalTransformation(Items.melon, BlockRegistry.melonCristal, ReserchRegistry.melonCristalisation));
		CristalRegistry.register(new WeakCristalTransformation((new ItemStack(Blocks.pumpkin)).getItem(), BlockRegistry.wheatCristal, ReserchRegistry.pumpkinCristalisation));
		
		CristalRegistry.register(new WeakFleshTransformation(EntitySheep.class, ReserchRegistry.sheepSkin, new ItemStack(Blocks.wool)));
		CristalRegistry.register(new WeakFleshTransformation(EntityCow.class, ReserchRegistry.leatherHound, new ItemStack(Items.leather)));
		CristalRegistry.register(new WeakFleshTransformation(EntityPig.class, ReserchRegistry.pinkBlouse, new ItemStack(Items.carrot)));
		CristalRegistry.register(new WeakFleshTransformation(EntityChicken.class, ReserchRegistry.featherFriend, new ItemStack(Items.egg), new ItemStack(Items.feather)));
		CristalRegistry.register(new WeakFleshTransformation(EntityHorse.class, ReserchRegistry.leatherBeast, new ItemStack(Items.leather)));
		CristalRegistry.register(new WeakFleshTransformation(EntityVillager.class, ReserchRegistry.payingTaxes, new ItemStack(Items.gold_nugget)));
		CristalRegistry.register(new WeakFleshTransformation(EntityZombie.class, ReserchRegistry.fleshRapture, new ItemStack(Items.rotten_flesh)));
		CristalRegistry.register(new WeakFleshTransformation(EntitySkeleton.class, ReserchRegistry.boneWreck, new ItemStack(Items.bone)));
		CristalRegistry.register(new WeakFleshTransformation(EntitySpider.class, ReserchRegistry.widowMaker, new ItemStack(Items.spider_eye), new ItemStack(Items.string)));
		CristalRegistry.register(new WeakFleshTransformation(EntitySlime.class, ReserchRegistry.puddingSplit, new ItemStack(Items.slime_ball)));
		CristalRegistry.register(new WeakFleshTransformation(EntityEnderman.class, ReserchRegistry.darkWarp, new ItemStack(Items.ender_pearl)));
	}
}
