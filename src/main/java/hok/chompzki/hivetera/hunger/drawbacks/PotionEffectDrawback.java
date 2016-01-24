package hok.chompzki.hivetera.hunger.drawbacks;

import java.util.List;

import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class PotionEffectDrawback extends Drawback {
	
	private final EnumResource res;
	private final double value;
	private final int potionEffect;
	private final int duration;
	private final int amplifier;
	private final int radius;
	
	public PotionEffectDrawback(EnumResource res, double value, int potionEffect, int duration, int amplifier, int radius){
		this.res = res;
		this.value = value;
		this.potionEffect = potionEffect;
		this.duration = duration;
		this.amplifier = amplifier;
		this.radius = radius;
	}
	

	@Override
	public boolean canAct(ResourcePackage pack, World world, int x, int y, int z) {
		return value <= pack.get(res);
	}

	@Override
	public void act(ResourcePackage pack, World world, int x, int y, int z) {
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, bb.expand(radius, radius, radius));
		for(EntityPlayer player : list){
			player.addPotionEffect(new PotionEffect(potionEffect, duration, amplifier));
		}
	}

}











