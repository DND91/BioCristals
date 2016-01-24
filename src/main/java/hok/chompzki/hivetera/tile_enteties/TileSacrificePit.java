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

public class TileSacrificePit extends TileEntity  implements ISidedInventory{
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {0, 1};
	
	private ItemStack[] contents = new ItemStack[2];
	private String customName = null;
	public int time = 0;
	public double rawFood = 0;
	
	public TileSacrificePit(){
		super();
	}
	
	@Override
	public int getSizeInventory() {
		return contents.length;
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
		return this.hasCustomInventoryName() ? this.customName : "container.sacrifice.pit";
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
		if(slot == 0)
			return (stack.getItem() instanceof ItemFood) || 
					((stack.getItem() instanceof ItemToken) && 
					((IToken)stack.getItem()).getType(stack) == EnumToken.EATER ||
					((IToken)stack.getItem()).getType(stack) == EnumToken.BANK);
		if(slot == 1)
			return (((stack.getItem() instanceof ItemToken) && ((IToken)stack.getItem()).getType(stack) == EnumToken.FEEDER) ||
					((IToken)stack.getItem()).getType(stack) == EnumToken.BANK);
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
        
        time = p_145839_1_.getInteger("TIME");
        rawFood = p_145839_1_.getDouble("RAW_FOOD");
        
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
        
        p_145841_1_.setInteger("TIME", time);
        p_145841_1_.setDouble("RAW_FOOD", rawFood);
        
    }
    
    @Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		
		if(time == 0 && 5.0D < rawFood && this.getStackInSlot(1) != null){
			ItemStack portal = this.getStackInSlot(1);
			if((portal.getItem() instanceof ItemToken) && ((IToken)portal.getItem()).getType(portal) == EnumToken.FEEDER){
				IToken feeder = (IToken)portal.getItem();
				ResourcePackage pack = new ResourcePackage();
				pack.add(EnumResource.RAW_FOOD, rawFood);
				feeder.feed(portal, pack);
				rawFood = pack.get(EnumResource.RAW_FOOD);
				pack.put(EnumResource.RAW_FOOD, 0.0D);
				ItemInsect.drawbacks(this, portal, pack);
			}else if((portal.getItem() instanceof ItemToken) && ((IToken)portal.getItem()).getType(portal) == EnumToken.BANK){
				IToken bank = (IToken)portal.getItem();
				ResourcePackage pack = new ResourcePackage();
				pack.add(EnumResource.RAW_FOOD, rawFood);
				bank.feed(portal, pack);
				rawFood = pack.get(EnumResource.RAW_FOOD);
				pack.put(EnumResource.RAW_FOOD, 0.0D);
				ItemInsect.drawbacks(this, portal, pack);
			}
			time = 200;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		this.markDirty();
		}else if(rawFood <= 5.0D && this.getStackInSlot(0) != null && this.getStackInSlot(1) != null && time == 0){
			ItemStack input = this.getStackInSlot(0);
			
			double[] v = ItemInsect.drain(this, true, input, 20.0D, EnumResource.RAW_FOOD);
			if(input.stackSize <= 0)
				this.setInventorySlotContents(0, null);
			rawFood += v[EnumResource.RAW_FOOD.toInt()];
			
			time = 200;
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		this.markDirty();
        }else if (0 < time && this.getStackInSlot(1) != null) {
        	time--;
        }
		
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_)
    {
    	return this.time * p_145953_1_ / 200;
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
