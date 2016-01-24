package hok.chompzki.hivetera.tile_enteties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IGrowthCristal;
import hok.chompzki.hivetera.recipes.RecipePurifier;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;

public class TileExtractor extends TileCroot {
	
	private ForgeDirection outputSide = ForgeDirection.UP;
    private ForgeDirection[] inputSides = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	
	public final static int tickMod = 40;
	private long tick = 0;
	
	private ItemStack stored_result = null;
	private UUID owner = null;
	
	public TileExtractor(){
		super(-5);
		
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.stored_result = null;
        
        if(0 < nbttaglist.tagCount()){
        	NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
            int j = nbttagcompound1.getByte("Slot") & 255;
            this.stored_result = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }
        
       if(nbt.hasKey("OUTPUT_SIDE")){
        	int side = nbt.getInteger("OUTPUT_SIDE");
        	this.setOutputSide(ForgeDirection.getOrientation(side));
        }
       
       if(nbt.hasKey("OWNER")){
    	   owner = UUID.fromString(nbt.getString("OWNER"));
       }
       
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();
        
        if (this.stored_result != null)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)0);
            this.stored_result.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
        }
        
        nbt.setTag("Items", nbttaglist);
        
        nbt.setInteger("OUTPUT_SIDE", this.outputSide.ordinal());
        if(owner != null){
        	nbt.setString("OWNER", owner.toString());
        }
        
    }
    
    @Override
	public boolean canUpdate(){
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.worldObj != null && !this.worldObj.isRemote && this.treeForm != null && this.treeForm.getStabel())
        {
			tick++;
			if(canFunction() && tick % tickMod == 0){
				extraction();
			}
        }
	}
	
    public void setOutputSide(ForgeDirection side) {
        ArrayList<ForgeDirection> newIn = new ArrayList<ForgeDirection>();
        
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        	newIn.add(dir);
        
        newIn.remove(side);
        newIn.remove(side.getOpposite());
        
        inputSides = newIn.toArray(new ForgeDirection[4]);
        outputSide = side;
        if(this.worldObj != null)
        	this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
    
    public ForgeDirection getOutputSide() {
        return outputSide;
    }
    public ForgeDirection[] getInputSides() {
        return this.inputSides;
    }
    public ForgeDirection getHarvestSide(){
    	return outputSide.getOpposite();
    }
    

    public boolean canFunction(){
    	if(owner == null)
    		return false;
		TileEntity tile = BioHelper.getTileEntityOnSide(this, outputSide);
		if(tile == null || !(tile instanceof IInventory))
			return false;
		Block block = BioHelper.getBlockOnSide(this, getHarvestSide());
		if(block == null || !(block instanceof IGrowthCristal))
			return false;
		return true;
	}
	
	public void extraction(){
		if(this.stored_result == null){
			IInventory output = (IInventory) BioHelper.getTileEntityOnSide(this, getOutputSide());
			ForgeDirection hs = this.getHarvestSide();
			int radius = 1;
			List<ItemStack> list = new ArrayList<ItemStack>();
			int xc = xCoord + (1 + radius) * hs.offsetX;
			int yc = yCoord + (1 + radius) * hs.offsetY;
			int zc = zCoord + (1 + radius) * hs.offsetZ;
			for(int xo = -radius; xo <= radius; xo++)
			for(int yo = -radius; yo <= radius; yo++)
			for(int zo = -radius; zo <= radius; zo++){
				int x = xc + xo;
				int y = yc + yo;
				int z = zc + zo;
				if(!worldObj.blockExists(x, y, z) || worldObj.isAirBlock(x, y, z))
					continue;
				Block block = worldObj.getBlock(x, y, z);
				if(block == null || !(block instanceof IGrowthCristal))
					continue;
				IGrowthCristal cristal = (IGrowthCristal)block;
				EntityPlayer player = worldObj.func_152378_a(owner);
				if(cristal.isMature(worldObj, player, null, x, y, z)){
					cristal.harvest(worldObj, player, null, x, y, z, list);
				}
			}
			
			for(ItemStack stack : list){
				ItemStack result = stack.copy();
				if(!BioHelper.addItemStackToInventory(result, output) || 0 < result.stackSize){
					stored_result = result;
					break;
				}
			}
			
		}else{
			IInventory output = (IInventory) BioHelper.getTileEntityOnSide(this, outputSide);
			if(BioHelper.addItemStackToInventory(stored_result, output) && stored_result.stackSize <= 0){
				stored_result = null;
			}
		}
		
	}
	
	/**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
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

	public void setOwner(UUID id) {
		owner = id;
	}
	
}
