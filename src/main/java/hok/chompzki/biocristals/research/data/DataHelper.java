package hok.chompzki.biocristals.research.data;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class DataHelper {
	
	public static boolean hasOwner(ItemStack stack){
		if(!stack.hasTagCompound())
			return false;
		NBTTagCompound data = stack.getTagCompound();
		return data.hasKey("OWNER");
	}
	
	public static boolean belongsTo(EntityPlayer player, ItemStack stack){
		UUID userId = EntityPlayer.func_146094_a(player.getGameProfile());
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound data = stack.getTagCompound();
		if(data.hasKey("OWNER")){
			return data.getString("OWNER").equals(userId.toString());
		}else{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			if(side == Side.SERVER){
				data.setString("OWNER", userId.toString());
				return true;
			}else
				return false;
		}
	}
	
	public static String getOwner(ItemStack stack){
		return stack.getTagCompound().getString("OWNER");
	}
	
	public static String getOwnerName(ItemStack stack, World world){
		UUID id = UUID.fromString(stack.getTagCompound().getString("OWNER"));
		return getOwnerName(id, world);
	}
	
	public static String getOwnerName(UUID id, World world){
		return PlayerStorage.instance().get(id).getUsername(world);
	}
	
	public static MovingObjectPosition rayTrace(EntityLivingBase entity, double p_70614_1_, float p_70614_3_){
		Vec3 vec3 = getPosition(entity, p_70614_3_);
        vec3.yCoord += entity.getEyeHeight();
        Vec3 vec31 = entity.getLook(p_70614_3_);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * p_70614_1_, vec31.yCoord * p_70614_1_, vec31.zCoord * p_70614_1_);
        
        return entity.worldObj.func_147447_a(vec3, vec32, false, false, true);
    }
	
	public static Vec3 getPosition(EntityLivingBase entity, float p_70666_1_)
    {
        if (p_70666_1_ == 1.0F)
        {
            return Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
        }
        else
        {
            double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)p_70666_1_;
            double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)p_70666_1_;
            double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)p_70666_1_;
            return Vec3.createVectorHelper(d0, d1, d2);
        }
    }
	
	public static MovingObjectPosition getEntityRayTrace(EntityLivingBase viewer, float p_78473_1_)
    {
		World world = viewer.worldObj;
		Entity pointedEntity = null;
        double d0 = 6.0D;
        MovingObjectPosition objectMouseOver = rayTrace(viewer, d0, p_78473_1_);
        double d1 = d0;
        Vec3 vec3 = getPosition(viewer, p_78473_1_);
        vec3.yCoord += viewer.getEyeHeight();
        
        if (objectMouseOver != null)
        {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
        }

        Vec3 vec31 = viewer.getLook(p_78473_1_);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
        
        Vec3 vec33 = null;
        float f1 = 1.0F;
        List list = world.getEntitiesWithinAABBExcludingEntity(viewer, viewer.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = (Entity)list.get(i);

            if (entity.canBeCollidedWith())
            {
                float f2 = entity.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3))
                {
                    if (0.0D < d2 || d2 == 0.0D)
                    {
                        pointedEntity = entity;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                }
                else if (movingobjectposition != null)
                {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D)
                    {
                        if (entity == viewer.ridingEntity && !entity.canRiderInteract())
                        {
                            if (d2 == 0.0D)
                            {
                                pointedEntity = entity;
                                vec33 = movingobjectposition.hitVec;
                            }
                        }
                        else
                        {
                            pointedEntity = entity;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
        }

        if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
        {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
        }
        return objectMouseOver;
    }
}
