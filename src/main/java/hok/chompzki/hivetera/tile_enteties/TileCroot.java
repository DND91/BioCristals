package hok.chompzki.hivetera.tile_enteties;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.ICroot;
import hok.chompzki.hivetera.api.ICrootCore;
import hok.chompzki.hivetera.croot.power.TreeForm;
import hok.chompzki.hivetera.croot.power.TreeStorage;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileCroot extends TileEntity implements ICroot {
	
	private WorldCoord coreCoord = null;
	private WorldCoord myCoord = null;
	protected TreeForm treeForm = null;
	
	private int power = 0;
	
	public TileCroot(){
		
	}
	
	public TileCroot(int power){
		this.power = power;
	}
	
	@Override
	public boolean canUpdate(){
		return false;
	}
	
	@Override
	public void setCore(WorldCoord coord) {
		if(this.coreCoord == null){
			coreCoord = coord;
			treeForm = TreeStorage.instance().getForm(coreCoord);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("CORE_DIM")){
        	int coreDim = nbt.getInteger("CORE_DIM");
	        int coreX = nbt.getInteger("CORE_X");
	        int coreY = nbt.getInteger("CORE_Y");
	        int coreZ = nbt.getInteger("CORE_Z");
	        this.coreCoord = new WorldCoord(coreX, coreY, coreZ, coreDim);
	        treeForm = TreeStorage.instance().getForm(coreCoord);
        }
        power = nbt.getInteger("POWER");
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(this.coreCoord != null){
        	nbt.setInteger("CORE_DIM", coreCoord.dimId);
	        nbt.setInteger("CORE_X", coreCoord.x);
	        nbt.setInteger("CORE_Y", coreCoord.y);
	        nbt.setInteger("CORE_Z", coreCoord.z);
        }
        nbt.setInteger("POWER", power);
        
    }

	@Override
	public int getValue() {
		return power;
	}
	
	@Override
	public ICrootCore genCore() {
		if(coreCoord == null)
			return null;
		
		TileEntity ent = coreCoord.getTile(worldObj);
		if(ent != null && ent instanceof ICrootCore){
			ICrootCore gen = (ICrootCore)ent;
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
		
		if(coreCoord != null){
			ICrootCore core = this.genCore();
			if(core != null && !core.isValid(worldObj)){
				this.unsetCore();
			}else
				return;
		}
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = BioHelper.getTileEntityOnSide(this, dir);
			if(entity == null)
				continue;
			if(entity instanceof ICrootCore){ //Found Core!
				ICrootCore gen = (ICrootCore)entity;
				gen.getForm().register(worldObj, this.getCoords());
				return;
			}else if(entity instanceof ICroot){ //Found a member!
				ICroot mem = (ICroot)entity;
				TreeForm form = mem.getForm();
				if(form != null){
					form.register(worldObj, this.getCoords());
					return;
				}
			}
		}
	}
	
	public void breakTile(){
		if(coreCoord != null){
			TileEntity ent = coreCoord.getTile(worldObj);
			if(ent != null && ent instanceof ICrootCore){
				ICrootCore gen = (ICrootCore)ent;
				treeForm.unregister(worldObj, this.getCoords(), this.getValue());
				unsetCore();
			}
		}
	}
	
	@Override
	public void unsetCore() {
		coreCoord = null;
		treeForm = null;
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
		if(myCoord == null){
			myCoord = new WorldCoord(getX(), getY(), getZ(), getDim());
		}
		return myCoord;
	}
	
	@Override
	public boolean stable(){
		return treeForm != null && treeForm.getStabel();
	}
	
	@Override
	public void print() {
		this.getCoords().print();
		if(this.coreCoord != null){
			System.out.println("- CORE -");
			coreCoord.print();
		}else{
			System.out.println("HAS NO CORE!!!!");
		}
	}
	
	public TreeForm getForm(){
		return treeForm;
	}
}
