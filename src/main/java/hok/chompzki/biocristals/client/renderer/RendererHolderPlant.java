package hok.chompzki.biocristals.client.renderer;

import hok.chompzki.biocristals.registrys.BlockHolderPlant;
import hok.chompzki.biocristals.registrys.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererHolderPlant implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		//SHOULD NEVER HAPPEN!!!
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		BlockHolderPlant holder = (BlockHolderPlant)block;
		
		Block target = holder.getBlock(world, x, y, z);
		
		if(target == null){
			TileEntity tile = world.getTileEntity(x, y, z);
			tile.markDirty();
			tile.getWorldObj().markBlockForUpdate(x, y, z);
			return true;
		}
		
		renderer.renderBlockAllFaces(target, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxy.idPlantHolder;
	}

}
