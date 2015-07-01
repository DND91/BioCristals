package hok.chompzki.biocristals.tile_enteties;

import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.recipes.RecipePurifier;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileReagentPurifier extends TileEntity implements IInventory{
	
	/**
	 * WILL CONTINUE TO CONSUME RESOURCES EVEN IF OUTPUT INVENTORY IS FULL! NEEDS FIX!
	 */
	
	public final static ForgeDirection outputSide = ForgeDirection.UP;
	public final static ForgeDirection[] inputSides = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	
	public final static int tickMod = 40;
	private long tick = 0;
	
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
		return 0;
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
	
	@Override
	public void updateEntity() {
		if (this.worldObj != null && !this.worldObj.isRemote)
        {
			tick++;
			if(tick % tickMod == 0 && canFunction()){
				transformation();
			}
        }
	}
	
	public boolean canFunction(){
		TileEntity tile = BioHelper.getTileEntityOnSide(this, outputSide);
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
		IInventory[] inputs = BioHelper.getInventories(this, inputSides);
		IInventory output = (IInventory) BioHelper.getTileEntityOnSide(this, outputSide);
		RecipePurifier recp = RecipeRegistry.getRecipePurifier(inputs);
		if(recp == null)
			return;
		ItemStack result = recp.result();
		
		recp.pay(inputs);
		BioHelper.addItemStackToInventory(result, output);
		
	}
}
