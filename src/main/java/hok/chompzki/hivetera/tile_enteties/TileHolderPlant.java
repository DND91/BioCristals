package hok.chompzki.hivetera.tile_enteties;

import hok.chompzki.hivetera.registrys.RecipeRegistry;

import java.util.UUID;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class TileHolderPlant extends TileEntity {
	
	IPlantable plantable = null;
	private Block plant = null;
	
	public TileHolderPlant(){
		
	}
	
	public boolean canUpdate()
    {
        return false;
    }
	
	public static boolean canGorw(IPlantable plantable){
		return true;
	}
	
	public void setPlant(IPlantable plantable, Block plant) {
		this.plantable = plantable;
		this.plant = plant;
	}

	public Block getBlock() {
		return plant;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        String name = nbt.getString("PLANT_NAME");
        String modId = nbt.getString("PLANT_MOD");
        
        plant = GameRegistry.findBlock(modId, name);
       	
        if(worldObj != null && worldObj.isRemote){
        	worldObj.markBlockRangeForRenderUpdate(xCoord-1, yCoord-1, zCoord-1, xCoord+1, yCoord+1, zCoord+1);
        }
    }
	
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        UniqueIdentifier ui = GameRegistry.findUniqueIdentifierFor(plant);
        nbt.setString("PLANT_NAME", ui.name);
        nbt.setString("PLANT_MOD", ui.modId);
        
        
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
