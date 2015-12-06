package hok.chompzki.biocristals.tile_enteties;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.api.IInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileNest extends TileEntity  implements ISidedInventory{
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1, 2, 3};
    private static final int[] slotsSides = new int[] {};
	
    public ItemStack workStack = null;
	private ItemStack[] contents = new ItemStack[10];
	private String customName = null;
	public int startTime = -1;
	public int lifeTime = -1;
	
	public TileNest(){
		super();
	}
	
	@Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		
		if(this.workStack != null && 0 < lifeTime){
        	ItemStack stack = this.workStack;
        	IInsect insect = (IInsect)(stack.getItem());
        	
        	if(!insect.canUpdate(this, stack)){
        		return;
        	}
        	
        	lifeTime--;
        	
        	if(lifeTime % insect.workSpan(stack) == 0)
        		insect.tileUpdate(this, stack);
        	
        	if(lifeTime == 0){
        		ItemStack[] result = insect.getResult(stack);
        		for(ItemStack res : result)
        		for(int i = 1; i < 4 && (i-1) < result.length; i++){
        			if(this.isItemValidForSlot(i, res)){
        				if(this.getStackInSlot(i) == null){
        					this.setInventorySlotContents(i, res);
        					continue;
        				}else{
        					ItemStack s = this.getStackInSlot(i);
        					s.stackSize = Math.min(s.stackSize + res.stackSize, s.getMaxStackSize());
        					res.stackSize = s.stackSize + res.stackSize - s.getMaxStackSize();
        					this.setInventorySlotContents(i, s);
        					if(res.stackSize == 0)
        						continue;
        				}
        			}
        		}
        		
        		startTime = lifeTime = 1;
        		workStack = null;
        		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        		this.markDirty();
        	}
        }else if (this.getStackInSlot(0) != null) {
        	
        	ItemStack stack = this.getStackInSlot(0);
        	IInsect insect = (IInsect)(stack.getItem());
        	ItemStack[] result = insect.getResult(stack);
    		for(int i = 1; i < 4 && result.length < (i-1); i++){
    			if(!this.isItemValidForSlot(i, result[i-1])){
    				return;
    			}
    		}
    		startTime = lifeTime = insect.lifeSpan(stack);
    		
    		this.workStack = stack.copy();
    		this.workStack.stackSize = 1;
    		this.decrStackSize(0, 1);
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		this.markDirty();
        }
		
	}
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		NBTTagCompound nbt = pkt.func_148857_g();
		this.readFromNBT(nbt);
    }
	
	/**
     * Overriden in a sign to provide the text.
     */
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, blockMetadata, nbt);
    }
	
	@Override
	public int getSizeInventory() {
		return 10;
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
		return this.hasCustomInventoryName() ? this.customName : "container.nest";
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
		return this.contents[slot] == null ? true : this.contents[slot].isItemEqual(stack);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.contents = new ItemStack[this.getSizeInventory()];

        if (nbt.hasKey("CustomName", 8))
        {
            this.customName = nbt.getString("CustomName");
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
        
        lifeTime = nbt.getInteger("LIFE_TIME");
        startTime = nbt.getInteger("START_TIME");
        if(nbt.hasKey("WORK_ITEM")){
        	NBTTagCompound item = nbt.getCompoundTag("WORK_ITEM");
        	workStack = ItemStack.loadItemStackFromNBT(item);
        }else{
        	workStack = null;
        }
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
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

        nbt.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            nbt.setString("CustomName", this.customName);
        }
        
        nbt.setInteger("LIFE_TIME", lifeTime);
        nbt.setInteger("START_TIME", startTime);
        
        if(this.workStack != null){
        	NBTTagCompound item = new NBTTagCompound();
        	workStack.writeToNBT(item);
        	nbt.setTag("WORK_ITEM", item);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_)
    {
        return this.lifeTime * p_145953_1_ / (startTime == 0 ? 1 : startTime);
    }

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slotsBottom : (p_94128_1_ == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return p_102007_1_ == 0 && this.isItemValidForSlot(p_102007_1_, p_102007_2_);
	}

	@Override
	public boolean canExtractItem(int s, ItemStack p_102008_2_,
			int p_102008_3_) {
		return 1 <= s && s <= 3;
	}
	
}
