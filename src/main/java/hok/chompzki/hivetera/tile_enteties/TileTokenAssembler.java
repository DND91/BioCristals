package hok.chompzki.hivetera.tile_enteties;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.api.IInsect;
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

public class TileTokenAssembler extends TileEntity  implements ISidedInventory{
	
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1, 2, 3};
    private static final int[] slotsSides = new int[] {};
    
	public int selected = 0;
	public int color = 0;
	public Integer[] values = new Integer[20];
	
	private String customName = null;
	
	
	public TileTokenAssembler(){
		super();
		for(int i = 0; i < values.length;i++)
			values[i] = 0;
	}
	
	@Override
	public void updateEntity() {
		
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
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.token.assembler";
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
		return false;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("CustomName", 8))
        {
            this.customName = nbt.getString("CustomName");
        }
        
        selected = nbt.getInteger("SELECTED");
        color = nbt.getInteger("COLOR");
        for(int i = 0; i < values.length; i++){
        	values[i] = nbt.getInteger("VALUE_" + i);
        }
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (this.hasCustomInventoryName())
        {
            nbt.setString("CustomName", this.customName);
        }
        
        nbt.setInteger("SELECTED", selected);
        nbt.setInteger("COLOR", color);
        for(int i = 0; i < values.length; i++){
        	nbt.setInteger("VALUE_" + i, values[i]);
        }
    }

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return false;
	}
	
	@Override
	public boolean canExtractItem(int s, ItemStack p_102008_2_,
			int p_102008_3_) {
		return false;
	}
	
}
