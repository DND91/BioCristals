package hok.chompzki.biocristals.blocks.croot;

import hok.chompzki.biocristals.api.BioHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileCrootConsumer extends TileEntity implements ICrootPowerCon {
	
	private int power = 0;
	
	private boolean hasCore = false;
	
	private int coreX = 0;
	private int coreY = 0;
	private int coreZ = 0;
	
	public TileCrootConsumer(){
		
	}
	
	public TileCrootConsumer(int power){
		this.power = power;
	}
	
	@Override
	public boolean canUpdate(){
		return false;
	}
	
	@Override
	public void setCore(int xCoord, int yCoord, int zCoord) {
		if(!hasCore){
			coreX = xCoord;
			coreY = yCoord;
			coreZ = zCoord;
		}
		hasCore = true;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        coreX = nbt.getInteger("CORE_X");
        coreY = nbt.getInteger("CORE_Y");
        coreZ = nbt.getInteger("CORE_Z");
        power = nbt.getInteger("POWER");
        hasCore = nbt.getBoolean("HAS_CORE");
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("CORE_X", coreX);
        nbt.setInteger("CORE_Y", coreY);
        nbt.setInteger("coreZ", coreZ);
        nbt.setInteger("POWER", power);
        nbt.setBoolean("HAS_CORE", hasCore);
    }

	@Override
	public int getConsumption() {
		return power;
	}
	
	@Override
	public ICrootPowerGen genCore() {
		if(!hasCore)
			return null;
		
		TileEntity ent = this.worldObj.getTileEntity(coreX, coreY, coreZ);
		if(ent != null && ent instanceof ICrootPowerGen){
			ICrootPowerGen gen = (ICrootPowerGen)ent;
			return gen;
		}
		
		return null;
	}

	public void addTile() {
		this.updateConntection();
	}
	
	@Override
	public void updateConntection(){
		if(worldObj.isRemote)
			return;
		
		if(hasCore){
			ICrootPowerGen core = this.genCore();
			if(core == null){
				hasCore = false;
			}else
				return;
		}
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = BioHelper.getTileEntityOnSide(this, dir);
			if(entity == null || !(entity instanceof ICrootPowerGen || entity instanceof ICrootPowerMem || entity instanceof ICrootPowerCon))
				continue;
			if(entity instanceof ICrootPowerGen){ //Found Core!
				ICrootPowerGen gen = (ICrootPowerGen)entity;
				gen.register(this);
				return;
			}else if(entity instanceof ICrootPowerMem){ //Found a member!
				ICrootPowerMem mem = (ICrootPowerMem)entity;
				ICrootPowerGen core = mem.genCore();
				if(core != null){
					core.register(this);
					return;
				}
			}else if(entity instanceof ICrootPowerCon){ //Found a consumer!
				ICrootPowerCon mem = (ICrootPowerCon)entity;
				ICrootPowerGen core = mem.genCore();
				if(core != null){
					core.register(this);
					return;
				}
			}
		}
	}
	
	public void breakTile(){
		TileEntity ent = this.worldObj.getTileEntity(coreX, coreY, coreZ);
		if(ent != null && ent instanceof ICrootPowerGen){
			ICrootPowerGen gen = (ICrootPowerGen)ent;
			gen.unregister(this);
			hasCore = false;
		}
	}
	
	@Override
	public void unsetCore() {
		hasCore = false;
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
	public boolean hasPower() {
		ICrootPowerGen gen = genCore();
		if(gen == null)
			return false;
		return gen.stable();
	}
}
