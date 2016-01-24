package hok.chompzki.hivetera.entity;

import hok.chompzki.hivetera.entity.ai.EntityAIBrakeMaturePlant;
import hok.chompzki.hivetera.entity.ai.EntityAIFindMaturePlant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityFruitSpider extends EntityLiving {
	
	public EntityFruitSpider(World p_i1595_1_) {
		super(p_i1595_1_);
		
		this.tasks.addTask(0, new EntityAIBrakeMaturePlant(this, 1.0D));
		this.tasks.addTask(5, new EntityAIFindMaturePlant(this, 1.0D));
		
		this.setAIMoveSpeed(0.25F);
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000000417232513D);
    }

	
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float dmg)
    {
		if(source == DamageSource.magic){
			float f2 = this.getHealth();
            this.setHealth(f2 - dmg);
            if(this.getHealth() <= 0.0F)
            	this.setDead();
		}
		return false;
    }
	
	public boolean attackEntityAsMob(Entity p_70652_1_)
    {
		return false;
    }
	
	protected boolean canDespawn()
    {
        return false;
    }
	
	public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }
        
    }
	
	protected void fall(float p_70069_1_) {}
	
	
}
