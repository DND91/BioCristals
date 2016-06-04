package hok.chompzki.hivetera.potion;

import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.PotionRegistry;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class PotionHungersWrath extends PotionBase {
	
	/** 
	 * 2 hours debuff....
		duration < 2 min
			- 50% hunger 5, 1 min
			- 50% starve damage, 1 pt
			- 1 in 50 spawn blood at the feet of entity.
			- 1 in 25 to create explosion around entity.
			- 1 in 5 to play ghast moan
		duration < 15 min
			- 1 in 25 hunger 4, 1 min
			- 1 in 25 starve damage, 1 pt
			- 1 in 100 spawn blood at the feet of entity.
			- 1 ib 50 spawn web at the feet of entity.
			- 1 in 100 to create explosion around entity.
			- 1 in 10 to play ghast moan
		duration < 30 min
			- 1 in 50 hunger 3, 1 min
			- 1 in 100 starve damage, 1 pt
			- 1 ib 100 spawn web at the feet of entity.
			- 1 in 10 to play enderman scream
		duration < 1h
			- 1 in 100 hunger 2, 1 min
			- 1 in 250 starve damage, 1 pt
			- 1 in 10 to play enderman scream
		duration < 1.5h
			- 1 in 100 hunger 1, 1 min
			- 1 in 250 confusion 1, 30 sec
			- 1 in 10 to play creeper prime
			- 1 in 10 to play zombie remedy
		duration < 2h
			- 1 in 250 hunger 0, 1 min
			- 1 in 500 confusion 1, 10 sec
			- 1 in 10 to play tnt prime
	 */

	public final static Random rand = new Random();
	public final static int startTime = 20 * 60 * 60 * 2;
	
	public PotionHungersWrath() {
		super(true, 0x0000FF);
		this.setIconIndex(0, 0);
		this.setPotionName("potion.hunger.wrath");
	}
	
	public boolean hasStatusIcon(){
		return false;
	}
	
	public void performEffect(EntityLivingBase entity, int amp)
    {
		
    }
	
	public boolean isReady(int duration, int amplifier)
    {
		int k = 25 >> amplifier;
        return k > 0 ? duration % k == 0 : true;
    }
	
}















