package hok.chompzki.hivetera.tile_enteties;

import java.util.List;

import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeTransformer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileGhost extends TileEntity {
	
	protected Block block = BlockRegistry.crootTrunk;
	
	public TileGhost() {
		
	}

	public Block getBlock() {
		return block;
	}
	
	public void setBlock(Block block){
		this.block = block;
	}
	
	public boolean canUpdate()
    {
        return false;
    }
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        String ghost = nbt.getString("GHOST_IMG");
        try{
	        List<ItemStack> list = RecipeTransformer.dataToItemStack(ghost, false);
	        ItemStack stack = list.get(0);
	        block = Block.getBlockFromItem(stack.getItem());
        }catch (Exception ex){
        	block = null;
        }
    }
	
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        ItemStack stack = new ItemStack(block);
        nbt.setString("GHOST_IMG", KnowledgeDescriptions.transformName(stack));
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		NBTTagCompound nbt = pkt.func_148857_g();
		this.readFromNBT(nbt);
    }
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, blockMetadata, nbt);
    }
}
