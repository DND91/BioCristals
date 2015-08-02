package hok.chompzki.biocristals.croot;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.croot_old.CrootBlock;
import hok.chompzki.biocristals.croot_old.CrootModule;
import hok.chompzki.biocristals.croot_old.CrootRegistry;
import hok.chompzki.biocristals.croot_old.RegisteredCoord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class TileCore extends TileEntity implements
		ICrootCore {
	
	public static final int tickMod = 60;
	public int tick = 0;
	
	private int maxBlocks = 0;
	private int blocks = 0;
	
	private WorldCoord coreCoords = null;
	protected TreeForm treeForm = null;
	
	public int totalPower = 0;
	public int powerUsage = 0;
	
	public TileCore(){
		
	}
	
	public void setBlockCount(int i) {
		maxBlocks = 0;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        maxBlocks = nbt.getInteger("MAX_BLOCKS");
        blocks = nbt.getInteger("BLOCKS");
        this.powerUsage = nbt.getInteger("POWER_USAGE");
        this.totalPower = nbt.getInteger("POWER_TOTAL");
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("MAX_BLOCKS", maxBlocks);
        nbt.setInteger("BLOCKS", blocks);
        if(treeForm == null){
        	nbt.setInteger("POWER_USAGE", 0);
            nbt.setInteger("POWER_TOTAL", 0);
        }else{
        	nbt.setInteger("POWER_USAGE", treeForm.getPowerUsage());
            nbt.setInteger("POWER_TOTAL", treeForm.getPowerTotal());
        }
        
    }
    
    
    
    @Override
    public void updateEntity(){
    	if(worldObj.isRemote)
    		return;
    	if(treeForm == null)
    		treeForm = TreeStorage.instance().getForm(getCoords());
    	
    	tick++;
    	if(tick % tickMod == 0){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty(); 
    	}
    }

	public void grow() {
		CrootModule module = CrootRegistry.get("core_1");
		int y = yCoord;
		/*if(module.controll(worldObj, xCoord, y, zCoord)){
			module = CrootRegistry.get("trunk_1");
			y += 6;
		}
		/*if(module.controll(worldObj, xCoord, y, zCoord)){
			module = CrootRegistry.get("crown_1");
			y += 7;
		}*/
		
		if(!module.controll(worldObj, xCoord, y, zCoord)){
			CrootBlock block = module.getNext(worldObj, xCoord, y, zCoord);
			CrootHelper.spawnBlock(worldObj, block, xCoord, y, zCoord);
			
		}
	}
	
	public TreeForm getForm(){
		if(treeForm == null)
			treeForm = TreeStorage.instance().getForm(getCoords());
		if(treeForm == null){
			TreeStorage.instance().setForm(getCoords());
			treeForm = TreeStorage.instance().getForm(getCoords());
		}
		return treeForm;
	}

	@Override
	public int getX() {
		return xCoord;
	}

	@Override
	public int getY() {
		return yCoord;
	}
	
	@Override
	public int getZ() {
		return zCoord;
	}
	
	@Override
	public int getDim() {
		return worldObj.provider.dimensionId;
	}
	
	@Override
	public WorldCoord getCoords(){
		if(coreCoords == null){
			coreCoords = new WorldCoord(getX(), getY(), getZ(), getDim());
		}
		return coreCoords;
	}
	
	protected void updateAfterRemove(){
		treeForm.updateAfterRemove(worldObj);
	}
	
	protected void updateAfterAdd(WorldCoord added){
		treeForm.updateAfterAdd(worldObj, added);
	}
	
	@Override
	public void breakTile() {
		if(treeForm == null)
			return;
		treeForm.clean(worldObj);
		TreeStorage.instance().removeForm(treeForm.coreCoord);
	}
	
	@Override
	public void onNeighborChange(){
		treeForm.updateOnNeighbor(worldObj);
	}
	
	@Override
	public void addTile(){
		TreeStorage.instance().setForm(getCoords());
		treeForm = TreeStorage.instance().getForm(getCoords());
		treeForm.updateAfterAdd(worldObj, getCoords());
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj != null)
			return false;
		if(!(obj instanceof TileCore))
			return false;
		TileCore b = (TileCore)obj;
		
		return b.getX() == getX() && b.getY() == getY() && b.getZ() == getZ();
	}
	
	@Override
	public boolean isValid(World world) {
		if(!world.blockExists(xCoord, yCoord, zCoord))
			return true;
		if(world.isAirBlock(xCoord, yCoord, zCoord))
			return false;
		
		TileEntity te = world.getTileEntity(xCoord, yCoord, zCoord);
		
		return te instanceof TileCore;
	}
	
	@Override
	public void print() {
		this.coreCoords.print();
		this.treeForm.print();
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
}