package hok.chompzki.hivetera.potion;

import hok.chompzki.hivetera.registrys.PotionRegistry;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class PotionLeech extends PotionBase {

	public PotionLeech() {
		super(true, 0x551A8B);
		this.setIconIndex(0, 0);
		this.setPotionName("potion.leech");
		
	}
	
	public void performEffect(EntityLivingBase entity, int amp)
    {
		if (entity.getHealth() > 1.0F)
        {
			entity.attackEntityFrom(DamageSource.starve, 1.0F);
			AxisAlignedBB bb = entity.boundingBox;
			List<EntityPlayer> list = entity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb.expand(10, 10, 10));
			for(EntityPlayer player : list){
				if(entity == player || player.getActivePotionEffect(PotionRegistry.leech) != null)
					continue;
				if(!player.capabilities.isCreativeMode)
					player.heal(1.0F);
			}
        }
    }
	
	public boolean isReady(int duration, int amplifier)
    {
		int k = 25 >> amplifier;
        return k > 0 ? duration % k == 0 : true;
    }
	
}
