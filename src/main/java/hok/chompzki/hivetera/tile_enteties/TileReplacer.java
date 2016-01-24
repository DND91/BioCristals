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

public class TileReplacer extends TileEntity {
	
	protected Block block = Blocks.stone;
	protected int meta = 0;
	public int trigger = 500;
	
	public TileReplacer() {
		
	}
	
	public TileReplacer(Block block, int meta, int time) {
		this.block = block;
		this.meta = meta;
		this.trigger = time;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        String ghost = nbt.getString("BLOCK");
        if(ghost.equals("air")){
        	block = Blocks.air;
        }else{
	        try{
		        List<ItemStack> list = RecipeTransformer.dataToItemStack(ghost, false);
		        ItemStack stack = list.get(0);
		        block = Block.getBlockFromItem(stack.getItem());
	        }catch (Exception ex){
	        	block = Blocks.air;
	        }
        }
        this.trigger = nbt.getInteger("TRIGGER");
        this.meta = nbt.getInteger("META");
    }
	
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(block == null || block == Blocks.air){
        	nbt.setString("BLOCK", "air");
        }else{
	        ItemStack stack = new ItemStack(block);
	        nbt.setString("BLOCK", KnowledgeDescriptions.transformName(stack));
        }
        nbt.setInteger("TRIGGER", this.trigger);
        nbt.setInteger("META", this.meta);
    }
	
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		trigger--;
		if(trigger <= 0){
			worldObj.setBlock(xCoord, yCoord, zCoord, block, meta, 3);
		}
	}
}
