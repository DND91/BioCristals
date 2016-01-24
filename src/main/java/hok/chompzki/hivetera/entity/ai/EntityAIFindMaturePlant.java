package hok.chompzki.hivetera.entity.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;

public class EntityAIFindMaturePlant extends EntityAIBase {
	
	private EntityLiving theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    
    private static Random rand = new Random();
    
    public EntityAIFindMaturePlant(EntityLiving p_i2347_1_, double p_i2347_2_)
    {
        this.theEntity = p_i2347_1_;
        this.movementSpeed = p_i2347_2_;
        this.setMutexBits(1);
    }
	
	@Override
	public boolean shouldExecute() {
		if(theEntity.getEquipmentInSlot(0) == null){
			World world = theEntity.worldObj;
			int dx = (int) theEntity.posX;
			int dy = (int) theEntity.posY;
			int dz = (int) theEntity.posZ;
			
			
			List<WorldCoord> list = new ArrayList<WorldCoord>();
			
			for(int x = dx - 5; x < (dx+5); x++)
			for(int y = dy - 5; y < (dy+5); y++)
			for(int z = dz - 5; z < (dz+5); z++){
				Block block = world.getBlock(x, y, z);
				if(block instanceof IGrowable){
					IGrowable grow = (IGrowable)block;
					if(!grow.func_149851_a(world, x, y, z, world.isRemote)){
						list.add(new WorldCoord(x, y, z, 0));
					}
				}
			}
			
			return 0 < list.size();
		}
		return false;
	}
	
	public void startExecuting()
    {
		World world = theEntity.worldObj;
		int dx = (int) theEntity.posX;
		int dy = (int) theEntity.posY;
		int dz = (int) theEntity.posZ;
		
		
		List<WorldCoord> list = new ArrayList<WorldCoord>();
		
		for(int x = dx - 5; x < (dx+5); x++)
		for(int y = dy - 5; y < (dy+5); y++)
		for(int z = dz - 5; z < (dz+5); z++){
			Block block = world.getBlock(x, y, z);
			if(block instanceof IGrowable){
				IGrowable grow = (IGrowable)block;
				if(!grow.func_149851_a(world, x, y, z, world.isRemote)){
					list.add(new WorldCoord(x, y, z, 0));
				}
			}
		}
		if(list.size() <= 0)
			return;
		
		WorldCoord coord = list.get(rand.nextInt(list.size()));
		
        this.theEntity.getNavigator().tryMoveToXYZ(coord.x + 0.5D, coord.y + 0.1D, coord.z + 0.5D, this.movementSpeed);
    }
	
	public boolean continueExecuting()
    {
        return !this.theEntity.getNavigator().noPath();
    }
}
