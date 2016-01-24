package hok.chompzki.hivetera.client.renderer;

import hok.chompzki.hivetera.blocks.BlockGhost;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class SpeciallRenenderGhost extends TileEntitySpecialRenderer {

	private static final double renderMinX = 0;
	private static final double renderMaxX = 1.0D;
	private static final double renderMinZ = 0;
	private static final double renderMaxZ = 1.0D;
	private static final double renderMinY = 0;
	private static final float transparancy = 0.75f;
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x,
			double y, double z, float partialTick) {
		if(tile.getBlockType() != BlockRegistry.ghost)
			return;
		
		BlockGhost holder = (BlockGhost)tile.getBlockType();
		World world = tile.getWorldObj();
		
		Block target = holder.getBlock(world, tile.xCoord, tile.yCoord, tile.zCoord);
		
		if(target == null){
			tile.markDirty();
			tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
			return ;
		}
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ItemRegistry.researchBook){
			RenderBlocks renderer = new RenderBlocks(world);
			renderer.clearOverrideBlockTexture();
			renderer.setRenderBounds(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
			
			
			renderer.setRenderFromInside(true);
			renderer.enableAO = false;
			
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			
			Tessellator tessellator = Tessellator.instance;
			
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			//GL11.glRotatef(180F, 0, 0, 0);
			GL11.glTranslated(x, y, z);
			GL11.glDisable(GL11.GL_LIGHTING);
			
			
			
			//GL11.glColor4f(1.0f, 1.0f, 1.0f, transparancy);
			tessellator.startDrawingQuads();
		    
			int l = target.colorMultiplier(world, tile.xCoord, tile.yCoord, tile.zCoord);
	        float f = (float)(l >> 16 & 255) / 255.0F;
	        float f1 = (float)(l >> 8 & 255) / 255.0F;
	        float f2 = (float)(l & 255) / 255.0F;
	        
			renderStandardBlockWithColorMultiplier(renderer, holder, target, tile.xCoord, tile.yCoord, tile.zCoord, f, f1, f2);
			
			
			tessellator.draw();
			//GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			GL11.glPopAttrib();
			return;
		}
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
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_, 0))
        {
	        tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f10, f13, f16, transparancy);
	        renderer.renderFaceYNeg(target, (double)0, (double)0, (double)0, renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 0));
	    	flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_, 1))
        {
        	tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f7, f8, f9, transparancy);
	        renderer.renderFaceYPos(target, (double)0, (double)0, (double)0, renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 1));
	        flag = true;
        }
        
        IIcon iicon;
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1, 2))
        {
        	tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f11, f14, f17, transparancy);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
	        renderer.renderFaceZNeg(target, (double)0, (double)0, (double)0, iicon);
	        flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1, 3))
        {
        	tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f11, f14, f17, transparancy);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
	        renderer.renderFaceZPos(target, (double)0, (double)0, (double)0, iicon);
	        flag = true;
        }
       
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_, 4))
        {
        	tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f12, f15, f18, transparancy);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
	        renderer.renderFaceXNeg(target, (double)0, (double)0, (double)0, iicon);
	        flag = true;
        }
        
        if (ghost.shouldSideBeRendered(renderer.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_, 5))
        {
        	tessellator.setBrightness(255);
	        tessellator.setColorRGBA_F(f12, f15, f18, transparancy);
	        iicon = renderer.getBlockIcon(target, renderer.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
	        renderer.renderFaceXPos(target, (double)0, (double)0, (double)0, iicon);
	        flag = true;
        }
        
        return flag;
    }
	
	public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_)
    {
        Tessellator tessellator = Tessellator.instance;

        double d3 = (double)p_147768_8_.getInterpolatedU(this.renderMinX * 16.0D);
        double d4 = (double)p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0D);
        double d5 = (double)p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0D);
        double d6 = (double)p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
        

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        
        double d11 = p_147768_2_ + this.renderMinX;
        double d12 = p_147768_2_ + this.renderMaxX;
        double d13 = p_147768_4_ + this.renderMinY;
        double d14 = p_147768_6_ + this.renderMinZ;
        double d15 = p_147768_6_ + this.renderMaxZ;

     
        tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
        tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
        tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        
    }
}
