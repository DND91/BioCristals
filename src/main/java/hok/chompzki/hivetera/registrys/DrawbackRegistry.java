package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.hunger.drawbacks.Drawback;
import hok.chompzki.hivetera.hunger.drawbacks.PotionEffectDrawback;
import hok.chompzki.hivetera.hunger.drawbacks.SpawnAnimalDrawback;
import hok.chompzki.hivetera.hunger.drawbacks.SpawnMobsDrawback;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.recipes.BreedingRecipe;

import java.util.ArrayList;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class DrawbackRegistry {
	
	public static ArrayList<Drawback> list = new ArrayList<Drawback>();
	
	//Input 0,1: Insects
	//Input   2: Nesting material
	//Input   3: Food -> foodLevel * foodSaturationLevel* 2.0F increase in process
	//Output  4: Result
	
	
	
	public static void register(Drawback drawback){
		list.add(drawback);
	}
	public void init(FMLInitializationEvent event) {
		//PotionEffects
		register(new PotionEffectDrawback(EnumResource.PSY_ENG, 5.0D, Potion.confusion.id, 100, 1, 3));
		register(new PotionEffectDrawback(EnumResource.NURITMENT, 5.0D, Potion.regeneration.id, 100, 1, 3));
		register(new PotionEffectDrawback(EnumResource.LIFE_FLUIDS, 5.0D, Potion.fireResistance.id, 100, 1, 3));
		register(new PotionEffectDrawback(EnumResource.WASTE, 5.0D, Potion.wither.id, 100, 1, 3));
		register(new PotionEffectDrawback(EnumResource.BIOMASS, 5.0D, Potion.hunger.id, 100, 1, 3));
		register(new PotionEffectDrawback(EnumResource.RAW_FOOD, 5.0D, Potion.blindness.id, 100, 1, 3));
		
		//Creatures
		register(new SpawnAnimalDrawback(EnumResource.PSY_ENG, 25.0D, EntityMooshroom.class, 1, 5, 5));
		register(new SpawnAnimalDrawback(EnumResource.NURITMENT, 25.0D, EntityCow.class, 1, 5, 10));
		register(new SpawnAnimalDrawback(EnumResource.LIFE_FLUIDS, 25.0D, EntityHorse.class, 1, 5, 10));
		register(new SpawnAnimalDrawback(EnumResource.WASTE, 25.0D, EntityPig.class, 3, 5, 5));
		register(new SpawnAnimalDrawback(EnumResource.BIOMASS, 25.0D, EntityWolf.class, 1, 5, 10));
		register(new SpawnAnimalDrawback(EnumResource.RAW_FOOD, 25.0D, EntitySheep.class, 1, 5, 10));
		
		//Mobs
		register(new SpawnMobsDrawback(EnumResource.PSY_ENG, 50.0D, EntityWitch.class, 1, 5, 10));
		register(new SpawnMobsDrawback(EnumResource.NURITMENT, 50.0D, EntityEnderman.class, 1, 5, 10));
		register(new SpawnMobsDrawback(EnumResource.LIFE_FLUIDS, 50.0D, EntitySkeleton.class, 1, 5, 10));
		register(new SpawnMobsDrawback(EnumResource.WASTE, 50.0D, EntityCreeper.class, 1, 5, 5));
		register(new SpawnMobsDrawback(EnumResource.BIOMASS, 50.0D, EntityZombie.class, 1, 5, 10));
		register(new SpawnMobsDrawback(EnumResource.RAW_FOOD, 50.0D, EntitySilverfish.class, 3, 10, 3));
		
		
	}
	
}







