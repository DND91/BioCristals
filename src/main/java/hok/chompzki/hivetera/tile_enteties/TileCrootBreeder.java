package hok.chompzki.hivetera.tile_enteties;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.items.insects.ItemInsect;
import hok.chompzki.hivetera.items.token.ItemToken;
import hok.chompzki.hivetera.recipes.BreedingRecipe;
import hok.chompzki.hivetera.registrys.BreedingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileCrootBreeder extends TileEntity  implements ISidedInventory{
	
	private static final int[] slotsTop = new int[] {0,1};
    private static final int[] slotsBottom = new int[] {4};
    private static final int[] slotsSides = new int[] {2, 3};
	
    public ItemStack workStack = null;
	private ItemStack[] contents = new ItemStack[5];
	private String customName = null;
	public int startTime = -1;
	public int lifeTime = -1;
	public double currentFood = 0;
	
	public TileCrootBreeder(){
		super();
	}
	
	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return this.contents[p_70301_1_];
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if (this.contents[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.contents[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.contents[p_70298_1_];
                this.contents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.contents[p_70298_1_].splitStack(p_70298_2_);

                if (this.contents[p_70298_1_].stackSize == 0)
                {
                    this.contents[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (this.contents[p_70304_1_] != null)
        {
            ItemStack itemstack = this.contents[p_70304_1_];
            this.contents[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		this.contents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.croot.breeder";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if((slot == 0 || slot == 1) && stack.getItem() instanceof IInsect){
			if(this.getStackInSlot(slot) != null && 
					(this.getStackInSlot(0) == null || this.getStackInSlot(1) == null))
				return false;
			if(this.getStackInSlot(0) == null || this.getStackInSlot(1) == null)
				return true;
			int o = slot == 0 ? 1 : 0;
			
			ItemStack first = this.getStackInSlot(slot);
			ItemStack other = this.getStackInSlot(o);
			
			return first.getItem() == other.getItem() ? first.stackSize <= other.stackSize : true;
		}
		if((slot == 2))
			return !((stack.getItem() instanceof ItemFood) || (stack.getItem() instanceof ItemToken));
		if((slot == 3))
			return ((stack.getItem() instanceof ItemFood) || (stack.getItem() instanceof ItemToken) && (
					((IToken)stack.getItem()).getType(stack) == EnumToken.EATER
					|| ((IToken)stack.getItem()).getType(stack) == EnumToken.BANK));
		if(slot == 4)
			return false;
		return false;
	}
	
	public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.contents = new ItemStack[this.getSizeInventory()];

        if (p_145839_1_.hasKey("CustomName", 8))
        {
            this.customName = p_145839_1_.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.contents.length)
            {
                this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        lifeTime = p_145839_1_.getInteger("LIFE_TIME");
        startTime = p_145839_1_.getInteger("START_TIME");
        currentFood = p_145839_1_.getDouble("FOOD_LEVEL");
        if(p_145839_1_.hasKey("WORK_ITEM")){
        	NBTTagCompound item = p_145839_1_.getCompoundTag("WORK_ITEM");
        	workStack = ItemStack.loadItemStackFromNBT(item);
        }else{
        	workStack = null;
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.contents.length; ++i)
        {
            if (this.contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.customName);
        }
        
        p_145841_1_.setInteger("LIFE_TIME", lifeTime);
        p_145841_1_.setInteger("START_TIME", startTime);
        p_145841_1_.setDouble("FOOD_LEVEL", currentFood);
        
        if(this.workStack != null){
        	NBTTagCompound item = new NBTTagCompound();
        	workStack.writeToNBT(item);
        	p_145841_1_.setTag("WORK_ITEM", item);
        }
    }
	
    @Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		
		if(this.workStack != null && 0 < lifeTime){
        	if(0.0F < this.currentFood)
        		this.currentFood--;
        	else if(this.getStackInSlot(3) != null){
        		ItemStack input = this.getStackInSlot(3);
        		IInsect insect = ((IInsect)workStack.getItem());
        		double drain = insect.getDrain(workStack);
        		EnumResource foodType = insect.getFoodType(workStack);
        		
        		if((input.getItem() instanceof ItemFood && foodType != EnumResource.RAW_FOOD))
        			return;
        		
    			double[] v = ItemInsect.drain(this, false, input, drain, foodType);
    			if(input.stackSize <= 0)
    				this.setInventorySlotContents(3, null);
    			currentFood += v[foodType.toInt()];
    			this.markDirty();
        	}
			
			if(0.0F < this.currentFood)
				lifeTime--;
        	
        	if(lifeTime == 0){
        		
				if(this.getStackInSlot(4) == null){
					this.setInventorySlotContents(4, workStack);
				}else{
					ItemStack s = this.getStackInSlot(4);
					s.stackSize = Math.min(s.stackSize + workStack.stackSize, s.getMaxStackSize());
					this.setInventorySlotContents(4, s);
				}
        		
        		startTime = lifeTime = 1;
        		workStack = null;
        		currentFood = 0;
        		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        		this.markDirty();
        	}
        }else if (this.getStackInSlot(0) != null && this.getStackInSlot(1) != null) {
        	
        	ItemStack p = this.getStackInSlot(0);
        	ItemStack m = this.getStackInSlot(1);
        	IInsect pappa = (IInsect)(p.getItem());
        	IInsect mamma = (IInsect)(m.getItem());
        	BreedingRecipe rep = BreedingRegistry.get(pappa, mamma, this.getStackInSlot(2));
        	
        	if(rep == null)
        		return;
        	
        	ItemStack result = this.getStackInSlot(4);
        	
        	if(result != null && !result.isItemEqual(rep.result))
        		return;
        	
        	if(result != null && result.getMaxStackSize() < (result.stackSize + rep.result.stackSize))
        		return;
    		
    		startTime = lifeTime = rep.foodNeed;
    		
    		this.workStack = rep.result.copy();
    		workStack.getItem().onCreated(workStack, worldObj, null);
    		
    		this.decrStackSize(2, 1);
    		
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		this.markDirty();
        }
		
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaledWidth(int p_145953_1_)
    {
    	float hv = (float)startTime / 2F;
    	float ht = hv - (float)lifeTime;
    	if((float)this.lifeTime <= hv){
    		int v = (int) Math.min(ht * p_145953_1_ / (hv == 0.0F ? 1.0F : hv), p_145953_1_);
        	return Math.min(p_145953_1_ - v, p_145953_1_ - 5);
    	} else {
    		return p_145953_1_ - 5;
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaledHeigth(int p_145953_1_)
    {
    	float hv = (float)startTime / 2F;
    	float ht = (float)lifeTime / 2F;
    	
    		int v = (int) Math.min(ht * p_145953_1_ / (hv == 0.0F ? 1.0F : hv), p_145953_1_);
        	return v;
    	
    }

    @Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slotsBottom : (p_94128_1_ == 1 ? slotsTop : slotsSides);
	}
    
    @Override
	public boolean canInsertItem(int s, ItemStack stack,
			int side) {
		int[] slots = getAccessibleSlotsFromSide(side);
		for(int s2 : slots)
			if(s == s2)
				return this.isItemValidForSlot(s, stack);
		return false;
	}

    @Override
	public boolean canExtractItem(int s, ItemStack stack,
			int side) {
		int[] slots = getAccessibleSlotsFromSide(side);
		
		for(int s2 : slots)
			if(s == s2)
				return true;
		
		return false;
	}
}
