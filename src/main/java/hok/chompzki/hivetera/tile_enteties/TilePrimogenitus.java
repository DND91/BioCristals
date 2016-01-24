package hok.chompzki.hivetera.tile_enteties;

import java.util.List;
import java.util.Random;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import hok.chompzki.hivetera.research.data.DataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePrimogenitus extends TileCroot {
	
	private Random rand = new Random();
	
	NBTTagCompound entnbt = null;
	
	public final static int tickMod = 1000;
	private long tick = 0;
	
	public TilePrimogenitus(){
		super(-20);
		
	}
	
	@Override
	public boolean canUpdate(){
		return true;
	}

	public void setStack(ItemStack stack) {
		entnbt = stack.getTagCompound();
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.entnbt = null;
       if(nbt.hasKey("ENTITY_NBT")){
        	entnbt = (NBTTagCompound) nbt.getTag("ENTITY_NBT");
        }
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        if(entnbt != null)
        	nbt.setTag("ENTITY_NBT", entnbt);
    }
	
	@Override
	public void updateEntity() {
		if (this.worldObj != null && !this.worldObj.isRemote && treeForm != null && treeForm.getStabel())
        {
			tick++;
			if(tick % tickMod == 0){
				pump();
			}
        }
	}
	
	
	
	public void pump(){
		if(this.entnbt == null)
			return;
		
		ForgeDirection up = ForgeDirection.UP;
		TileEntity upte = BioHelper.getTileEntityOnSide(this, up);
		if(upte != null && upte instanceof IInventory){
			Entity entity = EntityList.createEntityFromNBT(entnbt, this.worldObj);
			entity.readFromNBT(entnbt);
			List<ItemStack> stacks = DataHelper.getDrops((EntityLivingBase) entity, rand);
			for(ItemStack drop : stacks){
				BioHelper.addItemStackToInventory(drop, (IInventory) upte);
			}
		}
	}
}
