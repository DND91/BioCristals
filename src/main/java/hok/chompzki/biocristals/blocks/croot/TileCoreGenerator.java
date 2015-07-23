package hok.chompzki.biocristals.blocks.croot;

import hok.chompzki.biocristals.croot.CrootBlock;
import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.croot.CrootModule;
import hok.chompzki.biocristals.croot.CrootRegistry;
import hok.chompzki.biocristals.croot.RegisteredCoord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileCoreGenerator extends TileEntity implements
		ICrootPowerGen {
	
	
	
	public static final int tickMod = 60;
	public int tick = 0;
	
	private int maxBlocks = 0;
	private int blocks = 0;
	
	private int totalPower = 0;
	private int powerUsage = 0;
	
	Set<RegisteredCoord> coords = new HashSet<RegisteredCoord>();
	
	
	
	public TileCoreGenerator(){
		
	}

	public void setBlockCount(int i) {
		maxBlocks = 0;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        maxBlocks = nbt.getInteger("MAX_BLOCKS");
        blocks = nbt.getInteger("BLOCKS");
        totalPower = nbt.getInteger("TOTAL_POWER");
        powerUsage = nbt.getInteger("POWER_USAGE");
       
        NBTTagList nbttaglist = nbt.getTagList("COORDS", 10);
        this.coords.clear();
        
        if(0 < nbttaglist.tagCount()){
        	NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
        	RegisteredCoord coord = new RegisteredCoord(worldObj, 0,0,0);
            coord.readFromNBT(nbttagcompound1);
            coords.add(coord);
        }
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("MAX_BLOCKS", maxBlocks);
        nbt.setInteger("BLOCKS", blocks);
        nbt.setInteger("TOTAL_POWER", totalPower);
        nbt.setInteger("POWER_USAGE", powerUsage);
        NBTTagList nbttaglist = new NBTTagList();
        RegisteredCoord[] list = this.coords.toArray(new RegisteredCoord[coords.size()]);
		for(RegisteredCoord coord : list){
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            coord.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
		}
		nbt.setTag("COORDS", nbttaglist);
    }
    
    @Override
    public void setWorldObj(World world)
    {
        this.worldObj = world;
        for(RegisteredCoord coord : coords){
        	coord.setWorld(world);
        }
    }
    
    @Override
    public void updateEntity(){
    	if(worldObj.isRemote)
    		return;
    	tick++;
    	if(tick % tickMod == 0){
    		//this.grow();
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

	@Override
	public int getFreePower() {
		return this.totalPower - this.powerUsage;
	}
	
	@Override
	public int getTotalPower() {
		return this.totalPower;
	}
	
	@Override
	public void unregister(ICrootPowerCon reciver) {
		// TODO Auto-generated method stub
		RegisteredCoord coord = new RegisteredCoord(worldObj, reciver.getX(), reciver.getY(), reciver.getZ());
		if(coords.contains(coord)){
			this.powerUsage -= reciver.getConsumption();
			coords.remove(coord);
			updateAfterRemove();
		}
	}

	@Override
	public void register(ICrootPowerCon reciver) {
		// TODO Auto-generated method stub
		RegisteredCoord coord = new RegisteredCoord(worldObj, reciver.getX(), reciver.getY(), reciver.getZ());
		if(!coords.contains(coord)){
			updateAfterAdd(coord);
		}
	}

	@Override
	public boolean stable() {
		// TODO Auto-generated method stub
		return powerUsage <= totalPower;
	}

	@Override
	public void unregister(ICrootPowerMem member) {
		RegisteredCoord coord = new RegisteredCoord(worldObj, member.getX(), member.getY(), member.getZ());
		if(coords.contains(coord)){
			this.totalPower -= member.getProduction();
			coords.remove(coord);
			updateAfterRemove();
		}
	}

	@Override
	public void register(ICrootPowerMem member) {
		RegisteredCoord coord = new RegisteredCoord(worldObj, member.getX(), member.getY(), member.getZ());
		if(!coords.contains(coord)){
			updateAfterAdd(coord);
		}
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
	
	protected void updateAfterRemove(){
		
		RegisteredCoord start = new RegisteredCoord(worldObj, xCoord, yCoord, zCoord);
		
		TileEntity te = start.getTile();
		if(te != null){
			if(te instanceof ICrootPowerMem){
				ICrootPowerMem mem = (ICrootPowerMem)te;
				this.totalPower -= mem.getProduction();
				mem.unsetCore();
			}else if(te instanceof ICrootPowerCon){
				ICrootPowerCon mem = (ICrootPowerCon)te;
				this.powerUsage -= mem.getConsumption();
				mem.unsetCore();
			}
		}
		
		Set<RegisteredCoord> unsafe = CrootHelper.unsafe(coords, start);
		for(RegisteredCoord coord : unsafe){
			if(coord.equals(start))
				continue;
			
			te = coord.getTile();
			if(te != null){
				if(te instanceof ICrootPowerMem){
					ICrootPowerMem mem = (ICrootPowerMem)te;
					this.totalPower -= mem.getProduction();
					mem.unsetCore();
				}else if(te instanceof ICrootPowerCon){
					ICrootPowerCon mem = (ICrootPowerCon)te;
					this.powerUsage -= mem.getConsumption();
					mem.unsetCore();
				}
			}
			coords.remove(coord);
		}
	}
	
	protected void updateAfterAdd(RegisteredCoord added){
		Set<RegisteredCoord> unregistered = CrootHelper.unregistered(worldObj, added);
		
		for(RegisteredCoord coord : unregistered){
			if(coords.contains(coord))
				continue;
			
			TileEntity te = coord.getTile();
			if(te instanceof ICrootPowerMem){
				ICrootPowerMem mem = (ICrootPowerMem)te;
				this.totalPower += mem.getProduction();
				mem.setCore(xCoord, yCoord, zCoord);
			}else if(te instanceof ICrootPowerCon){
				ICrootPowerCon mem = (ICrootPowerCon)te;
				this.powerUsage += mem.getConsumption();
				mem.setCore(xCoord, yCoord, zCoord);
			}
			coords.add(coord);
		}
	}
	
	@Override
	public void breakTile() {
		RegisteredCoord[] list = this.coords.toArray(new RegisteredCoord[coords.size()]);
		for(RegisteredCoord coord : list){
			TileEntity te = coord.getTile();
			if(te != null){
				if(te instanceof ICrootPowerMem){
					ICrootPowerMem mem = (ICrootPowerMem)te;
					mem.unsetCore();
				}else if(te instanceof ICrootPowerCon){
					ICrootPowerCon mem = (ICrootPowerCon)te;
					mem.unsetCore();
				}
			}
			coords.remove(coord);
		}
	}

}
