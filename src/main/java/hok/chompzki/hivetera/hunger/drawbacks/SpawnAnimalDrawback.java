package hok.chompzki.hivetera.hunger.drawbacks;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SpawnAnimalDrawback extends Drawback {
	
	private static Random rand = new Random();
	
	private final EnumResource res;
	private final double value;
	private final Class<? extends EntityAnimal> c;
	private final int min;
	private final int max;
	private final int radius;
	
	public SpawnAnimalDrawback(EnumResource res, double value, Class<? extends EntityAnimal> c, int min, int max, int radius){
		this.res = res;
		this.value = value;
		this.c = c;
		this.min = min;
		this.max = max;
		this.radius = radius;
	}
	

	@Override
	public boolean canAct(ResourcePackage pack, World world, int x, int y, int z) {
		return value <= pack.get(res);
	}

	@Override
	public void act(ResourcePackage pack, World world, int x, int y, int z) {
		int number = rand.nextInt(max-min) + min;
		for(int i = 0; i < number; i++){
			int x2 = x + rand.nextInt(radius) - rand.nextInt(radius);
			int y2 = y + rand.nextInt(radius) - rand.nextInt(radius);
			int z2 = z + rand.nextInt(radius) - rand.nextInt(radius);
			try {
				EntityAnimal mob = c.getDeclaredConstructor(c).newInstance(world);
				mob.setLocationAndAngles(x2+0.5D, y2+0.5D, z2+0.5D, rand.nextFloat(), rand.nextFloat());
				mob.setPositionAndUpdate(x2+0.5D, y2+0.5D, z2+0.5D);
				world.spawnEntityInWorld(mob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}











