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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class EntityAIBrakeMaturePlant extends EntityAIBase {
	
	private EntityLiving theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    
    private static Random rand = new Random();
    
    public EntityAIBrakeMaturePlant(EntityLiving p_i2347_1_, double p_i2347_2_)
    {
        this.theEntity = p_i2347_1_;
        this.movementSpeed = p_i2347_2_;
        this.setMutexBits(2);
    }
	
	@Override
	public boolean shouldExecute() {
		if(theEntity.getEquipmentInSlot(0) == null){
			World world = theEntity.worldObj;
			int dx = (int) theEntity.posX;
			int dy = (int) theEntity.posY;
			int dz = (int) theEntity.posZ;
			
			
			List<WorldCoord> list = new ArrayList<WorldCoord>();
			
			for(int x = dx - 1; x < (dx+1); x++)
			for(int y = dy - 1; y < (dy+1); y++)
			for(int z = dz - 1; z < (dz+1); z++){
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
		
		for(int x = dx - 1; x < (dx+1); x++)
		for(int y = dy - 1; y < (dy+1); y++)
		for(int z = dz - 1; z < (dz+1); z++){
			Block block = world.getBlock(x, y, z);
			if(block instanceof IGrowable){
				IGrowable grow = (IGrowable)block;
				if(!grow.func_149851_a(world, x, y, z, world.isRemote)){
					list.add(new WorldCoord(x, y, z, 0));
				}
			}
		}
		
		WorldCoord coord = list.get(rand.nextInt(list.size()));
		
        this.theEntity.getNavigator().tryMoveToXYZ(coord.x + 0.5D, coord.y + 0.1D, coord.z + 0.5D, this.movementSpeed);
        
        
        Block block = world.getBlock(coord.x, coord.y, coord.z);
        List<ItemStack> stacks = block.getDrops(world, coord.x, coord.y, coord.z, world.getBlockMetadata(coord.x, coord.y, coord.z), 0);
        
        for(ItemStack stack : stacks){
        	Item item = stack.getItem();
        	if(item instanceof IPlantable){
        		IPlantable seeds = (IPlantable)item;
        		world.setBlockToAir(coord.x, coord.y, coord.z);
        		Block plant = seeds.getPlant(world, coord.x, coord.y, coord.z);
        		int meta = seeds.getPlantMetadata(world, coord.x, coord.y, coord.z);
        		world.setBlock(coord.x, coord.y, coord.z, block, meta, 3);
        		stack.stackSize--;
    			this.theEntity.attackEntityFrom(DamageSource.magic, 1.0F);
        		break;
        	}
        }
        
        BioHelper.dropItems(world, stacks, coord.x, coord.y, coord.z);
    }
	
	public boolean continueExecuting()
    {
        return !this.theEntity.getNavigator().noPath() && this.theEntity.getEquipmentInSlot(0) == null;
    }
}
