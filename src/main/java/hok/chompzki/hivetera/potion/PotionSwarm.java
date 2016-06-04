package hok.chompzki.hivetera.potion;

import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.PotionRegistry;

import java.util.List;
import java.util.Random;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;


public class PotionSwarm extends PotionBase {

	private final static Random rand = new Random();
	
	public PotionSwarm() {
		super(true, 0x000000);
		this.setIconIndex(0, 0);
		this.setPotionName("potion.swarm");
		
	}
	
	public void performEffect(EntityLivingBase entity, int amp)
    {
		entity.attackEntityFrom(DamageSource.starve, 1.0F + amp);
		
		
		AxisAlignedBB bb = entity.boundingBox;
		List<EntityLivingBase> list = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb.expand(5, 5, 5));
		for(EntityLivingBase living : list){
			if(living.getActivePotionEffect(PotionRegistry.swarm) == null){
				if(living instanceof EntityPlayer){
					EntityPlayer player = (EntityPlayer)living;
					if(player.capabilities.isCreativeMode || 
							ItemBioModArmor.contains(player, (IArmorInsect) ItemRegistry.hungerSwarm))
						continue;
				}
				if(0 < amp)
					living.addPotionEffect(new PotionEffect(PotionRegistry.swarm.id, 600, amp-1));
			}
		}
		
		
    }
	
	public boolean isReady(int duration, int amplifier)
    {
		int k = 50 >> amplifier;
        return k > 0 ? duration % k == 0 : true;
    }
	
}
