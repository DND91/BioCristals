package hok.chompzki.hivetera.tile_enteties;

import java.util.ArrayList;
import java.util.UUID;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.recipes.RecipePurifier;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileReagentPurifier extends TileCroot implements IInventory{
	
	private static final int timeLeftID = 1;
	private static final int startTimeID = 2;

    private ForgeDirection outputSide = ForgeDirection.UP;
    private ForgeDirection[] inputSides = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	
	public final static int tickMod = 40;
	private long tick = 0;
	
	private ItemStack stored_result = null;
	private UUID owner = null;
	private RecipePurifier recipe = null;
	private int timeLeft = 0;
	private int startTime = 0;
	
	public TileReagentPurifier(){
		super(-5);
		
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}
	
	public String getTimeLeftGui(){
		int partTime = this.startTime / 10;
		StringBuilder s = new StringBuilder("            ");
		
		if(partTime == 0)
			partTime = -1;
		
		for(int i = 0; i <= this.timeLeft / partTime; i++){
			s.setCharAt(i, '\u2622');
		}
		return s.toString();
	}
	
	public ItemStack getStored(){
		return stored_result;
	}
	
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
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
       if(nbt.hasKey("RECIPE")){
    	   this.recipe = RecipeRegistry.getRecipePurifier(nbt.getString("RECIPE"));
       }
       
       this.timeLeft = nbt.getInteger("TIME_LEFT");
       this.startTime = nbt.getInteger("TIME_START");
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
        if(this.recipe != null){
        	nbt.setString("RECIPE", recipe.name());
        }
        
        nbt.setInteger("TIME_LEFT", this.timeLeft);
        nbt.setInteger("TIME_START", this.startTime);
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
			if(canFunction()){
				transformation();
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
    public ForgeDirection getFilterSide(){
    	return outputSide.getOpposite();
    }
    

    public boolean canFunction(){
		TileEntity tile = BioHelper.getTileEntityOnSide(this, outputSide);
		if(tile == null || !(tile instanceof IInventory))
			return false;
		tile = BioHelper.getTileEntityOnSide(this, getFilterSide());
		if(tile == null || !(tile instanceof IInventory))
			return false;
		for(ForgeDirection side : inputSides){
			TileEntity ent = BioHelper.getTileEntityOnSide(this, side);
			if(ent != null && ent instanceof IInventory)
				return true;
		}
		return false;
	}
	
	public void transformation(){
		if(stored_result == null && recipe == null){
			IInventory[] inputs = BioHelper.getInventories(this, inputSides);
			IInventory filter = (IInventory) BioHelper.getTileEntityOnSide(this, getFilterSide());
			ArrayList<ItemStack> filters = new ArrayList<ItemStack>();
			for(int i = 0; i < filter.getSizeInventory(); i++){
				ItemStack stack = filter.getStackInSlot(i);
				if(stack == null || filters.contains(stack))
					continue;
				filters.add(stack);
			}
			recipe = RecipeRegistry.getRecipePurifier(filters, inputs);
			if(recipe == null)
				return;
			
			if(!recipe.code.equals("NONE")){
				if(this.owner == null)
					return;
				PlayerResearch research = PlayerResearchStorage.instance(false).get(owner);
				if(research == null){
					System.out.println("UNKOWN PLAYER TRIED WEAK FLESH WITHOUT REGISTATION: " + owner);
					return;
				}
				if(!ResearchLogicNetwork.instance().available(research, recipe.code())){
					recipe = null;
					return;
				}
			}
			
			recipe.pay(inputs);
			timeLeft = recipe.time();
			startTime = timeLeft;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), startTimeID, startTime);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty(); 
		}else if(stored_result == null && recipe != null){
			this.timeLeft--;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), timeLeftID, timeLeft);
			if(0 < timeLeft)
				return;
			IInventory output = (IInventory) BioHelper.getTileEntityOnSide(this, outputSide);
			
			ItemStack[] res = recipe.result();
			
			for(ItemStack stack : res){
				ItemStack result = stack.copy();
				if(!BioHelper.addItemStackToInventory(result, output) || 0 < result.stackSize){
					stored_result = result;
					break;
				}
			}
			
			timeLeft = 0;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty(); 
			
			if(owner == null || recipe.code().equals("NONE")){
				recipe = null;
				return;
			}
			PlayerResearch research = PlayerResearchStorage.instance(false).get(owner);
			if(research == null){
				System.out.println("UNKOWN PLAYER TRIED WEAK FLESH WITHOUT REGISTATION: " + owner);
				return;
			}
			ResearchLogicNetwork.instance().compelte(research, recipe.code());
			recipe = null;
		} else{
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

	public String getWork() {
		return this.recipe.name();
	}
	
	public boolean receiveClientEvent(int action, int parmeter)
    {
		if(action == timeLeftID){
			this.timeLeft = parmeter;
			return true;
		}else if(action == startTimeID){
			this.startTime = parmeter;
		}
		
        return false;
    }
}
