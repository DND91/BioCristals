package hok.chompzki.hivetera.client.renderer;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.ClientProxy;
import hok.chompzki.hivetera.blocks.BlockGhost;
import hok.chompzki.hivetera.containers.Hivebag;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererGhost implements ISimpleBlockRenderingHandler {

	
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		//SHOULD NEVER HAPPEN!!!
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		if (BlockGhost.renderPass != 1)
			return false;
		
		BlockGhost holder = (BlockGhost)block;
		
		Block target = holder.getBlock(world, x, y, z);
		
		if(target == null){
			TileEntity tile = world.getTileEntity(x, y, z);
			tile.markDirty();
			tile.getWorldObj().markBlockForUpdate(x, y, z);
			return true;
		}
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		
		if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ItemRegistry.researchBook){
			Tessellator tessellator = Tessellator.instance;
			
			GL11.glPushMatrix();
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			//GL11.glDisable(GL11.GL_CULL_FACE);
			
			//GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			
			int l = target.colorMultiplier(world, x, y, z);
	        float f = (float)(l >> 16 & 255) / 255.0F;
	        float f1 = (float)(l >> 8 & 255) / 255.0F;
	        float f2 = (float)(l & 255) / 255.0F;
	        
			renderStandardBlockWithColorMultiplier(renderer, holder, target, x, y, z, f, f1, f2);
			
			//GL11.glEnable(GL11.GL_CULL_FACE);
			//GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			return true;
		}
		
		
		return false;
	}
	
    public boolean renderStandardBlockWithColorMultiplier(RenderBlocks renderer, BlockGhost ghost, Block target, int p_147736_2_, int p_147736_3_, int p_147736_4_, float p_147736_5_, float p_147736_6_, float p_147736_7_)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * p_147736_5_;
        float f8 = f4 * p_147736_6_;
        float f9 = f4 * p_147736_7_;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;

        int l = target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_, 0))
        {
	        tessellator.setBrightness(renderer.renderMinY > 0.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_));
	        tessellator.setColorRGBA_F(f10, f13, f16, 0.5f);
	        renderer.renderFaceYNeg(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 0));
	    	flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_, 1))
        {
	        tessellator.setBrightness(renderer.renderMaxY < 1.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_));
	        tessellator.setColorRGBA_F(f7, f8, f9, 0.5f);
	        renderer.renderFaceYPos(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 1));
	        flag = true;
        }
        
        IIcon iicon;
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1, 2))
        {
	        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1));
	        tessellator.setColorRGBA_F(f11, f14, f17, 0.5f);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
	        renderer.renderFaceZNeg(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, iicon);
	        flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1, 3))
        {
	        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1));
	        tessellator.setColorRGBA_F(f11, f14, f17, 0.5f);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
	        renderer.renderFaceZPos(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, iicon);
	        flag = true;
        }
       
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_, 4))
        {
	        tessellator.setBrightness(renderer.renderMinX > 0.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_));
	        tessellator.setColorRGBA_F(f12, f15, f18, 0.5f);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
	        renderer.renderFaceXNeg(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, iicon);
	        flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_, 5))
        {
	        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? l : target.getMixedBrightnessForBlock(renderer.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_));
	        tessellator.setColorRGBA_F(f12, f15, f18, 0.5f);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
	        renderer.renderFaceXPos(target, (double)p_147736_2_, (double)p_147736_3_, (double)p_147736_4_, iicon);
	        flag = true;
        }
        
        return flag;
    }

    
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return ClientProxy.idGhost;
	}

}
