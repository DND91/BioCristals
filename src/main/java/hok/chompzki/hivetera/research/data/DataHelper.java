package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.research.data.network.PlayerStoragePullMessage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class DataHelper {
	
	public static boolean hasOwner(ItemStack stack){
		if(!stack.hasTagCompound())
			return false;
		NBTTagCompound data = stack.getTagCompound();
		return data.hasKey("OWNER");
	}
	
	public static boolean hasNetwork(ItemStack stack){
		if(!stack.hasTagCompound())
			return false;
		NBTTagCompound data = stack.getTagCompound();
		return data.hasKey("NETWORK");
	}
	
	public static boolean belongsTo(EntityPlayer player, ItemStack stack){
		if(stack == null)
			return false;
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
	
	public static String getNetworkName(ItemStack stack, World world){
		String id = stack.getTagCompound().getString("NETWORK");
		return getNetworkName(id, world);
	}
	
	public static String getNetworkName(String id, World world){
		if(PlayerHungerStorage.instance(true).get(id) == null)
			return id;
		else
			return PlayerHungerStorage.instance(true).get(id).getName();
	}
	
	public static String getOwnerName(UUID id, World world){
		if(PlayerResearchStorage.instance(true).get(id) == null){
			HiveteraMod.network.sendToServer(new PlayerStoragePullMessage(id));
			return "UNKOWN";
		}else
			return PlayerResearchStorage.instance(true).get(id).getUsername();
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
	
	public static List<ItemStack> getDrops(EntityLivingBase entity, Random rand){
		List<ItemStack> list = new ArrayList<ItemStack>();
		entity.captureDrops = true;
		entity.capturedDrops.clear();
		simulateLootDrop(entity, rand);
		entity.captureDrops = false;
		for(EntityItem entitm : entity.capturedDrops){
			list.add(entitm.getEntityItem());
		}
		entity.capturedDrops.clear();
		return list;
	}
	
	public static void simulateLootDrop(EntityLivingBase entitiy, Random rand){
		try {
			try{
				Method dropFewItems = EntityLivingBase.class.getDeclaredMethod("dropFewItems", boolean.class, int.class);
				dropFewItems.setAccessible(true);
				dropFewItems.invoke(entitiy, true, 0);
			} catch (Exception e) {
			}
			try{
				Method dropEquipment = EntityLivingBase.class.getDeclaredMethod("dropEquipment", boolean.class, int.class);
				dropEquipment.setAccessible(true);
				dropEquipment.invoke(entitiy, true, 0);
			} catch (Exception e) {
			}
			
			int j = rand.nextInt(200) - 0;
            
            if (j < 5)
            {
            	try{
					Method dropRareDrop = EntityLivingBase.class.getDeclaredMethod("dropRareDrop", boolean.class);
					dropRareDrop.setAccessible(true);
					dropRareDrop.invoke(entitiy, true);
            	} catch (Exception e) {
        		}
            }
			
            ForgeHooks.onLivingDrops(entitiy, DamageSource.generic, entitiy.capturedDrops, 0, true, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static String getNetwork(ItemStack stack) {
		return stack.getTagCompound().getString("NETWORK");
	}
}
