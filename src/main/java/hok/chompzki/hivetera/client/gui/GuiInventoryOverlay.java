package hok.chompzki.hivetera.client.gui;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ICrootCore;
import hok.chompzki.hivetera.api.IGrowthCristal;
import hok.chompzki.hivetera.client.CraftingHelper;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.items.ItemCrootStick;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.tile_enteties.TileCore;
import hok.chompzki.hivetera.tile_enteties.TileReagentPurifier;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class GuiInventoryOverlay extends Gui{
	
	public static CraftingHelper craftingHelper = new CraftingHelper();
	public static GuiUnlockedResearch unlockedGui = new GuiUnlockedResearch(Minecraft.getMinecraft());
	private static final ResourceLocation overlay = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/Overlay.png");
	
	private static  int tick = 0;
	private static  int tickMod = 20;
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEventGui(InitGuiEvent.Post event)
    {
        
    }
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEventAction(ActionPerformedEvent.Post event)
    {
		
    }
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEventDraw(RenderGameOverlayEvent.Pre event){
		tick++;
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		ItemStack currentStack = player.getCurrentEquippedItem();
		
		if(!player.capabilities.isCreativeMode && event.type == ElementType.FOOD){
			mc.renderEngine.bindTexture(overlay);
			double saturation = player.getFoodStats().getSaturationLevel();
			
			GL11.glEnable(GL11.GL_BLEND);
	        int left = event.resolution.getScaledWidth() / 2 + 91;
	        int top = event.resolution.getScaledHeight() - GuiIngameForge.right_height - 1;
			
	        for(int i = 0; i < saturation; i++){
	        	int x = left - i * 4 - 5;
	        	this.drawTexturedModalRect(x, top, i % 2 == 0 ? 5 : 0, 0, 5, 11);
	        }
			
			mc.renderEngine.bindTexture(Gui.icons);
		}
		
		if(currentStack != null && currentStack.getItem() == ItemRegistry.crootStick){
			
			int bx = Minecraft.getMinecraft().objectMouseOver.blockX;
			int by = Minecraft.getMinecraft().objectMouseOver.blockY;
			int bz = Minecraft.getMinecraft().objectMouseOver.blockZ;
			Entity entity  = Minecraft.getMinecraft().objectMouseOver.entityHit;
			if(entity != null)
				entity = world.getEntityByID(entity.getEntityId());
			
			
			int x = 0;
			int y = 20;
			Block block = world.getBlock(bx, by, bz);
			
			GL11.glPushMatrix();
			ArrayList<String> list = new ArrayList<String>();
			ItemStack stack = block.getPickBlock(Minecraft.getMinecraft().objectMouseOver, world, bx, by, bz, null);
			String txt = "Unknown";
			if(stack != null)
				txt = stack.getDisplayName();
			else
				txt = "Unknown";
			
			if(ItemCrootStick.canHarvest(block)){
				txt = ((char)167) + "a" + txt + ((char)167) + "r";
				list.add(txt);
				if((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
						|| Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
						|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
						|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))){
					list.add("- Samples -");
					BiomeGenBase biome = world.getBiomeGenForCoords(bx, bz);
					Type[] l = BiomeDictionary.getTypesForBiome(biome);
					for(Type type : l)
					if(BiomeRegistry.biomeToId.containsKey(type)){
						list.add(type.name());
					}
				}
			} else {
				txt = ((char)167) + "c" + txt + ((char)167) + "r";
				list.add(txt);
			}
			
			
			
			drawHoveringText(list, x, y, mc.fontRenderer);
			GL11.glPopMatrix();
			
		} else if(currentStack != null && currentStack.getItem() == ItemRegistry.attuner){
			int bx = Minecraft.getMinecraft().objectMouseOver.blockX;
			int by = Minecraft.getMinecraft().objectMouseOver.blockY;
			int bz = Minecraft.getMinecraft().objectMouseOver.blockZ;
			Entity entity  = Minecraft.getMinecraft().objectMouseOver.entityHit;
			if(entity != null)
				entity = world.getEntityByID(entity.getEntityId());
			
			if(entity != null && entity instanceof EntityLiving){
				int x = 0;
				int y = 20;
				
				EntityLiving target = (EntityLiving)entity;
				if(target.isPotionActive(Potion.moveSlowdown)){
					GL11.glPushMatrix();
					ArrayList<String> list = new ArrayList<String>();
					list.add(StatCollector.translateToLocal(target.getCommandSenderName()));
					list.add("Is paralysed");
					drawHoveringText(list, x, y, mc.fontRenderer);
					GL11.glPopMatrix();
				}
				
			}else if(!world.isAirBlock(bx, by, bz) && world.getTileEntity(bx, by, bz) != null && world.getTileEntity(bx, by, bz) instanceof TileCore){
				int x = 0;
				int y = 20;
				Block block = world.getBlock(bx, by, bz);
				TileCore tile = (TileCore) world.getTileEntity(bx, by, bz);
				
				
				GL11.glPushMatrix();
				ArrayList<String> list = new ArrayList<String>();
				
				list.add(StatCollector.translateToLocal(block.getLocalizedName()));
				list.add("~ Power ~");
				list.add("Total: " + tile.totalPower);
				list.add("Usage: " + tile.powerUsage);
				
				drawHoveringText(list, x, y, mc.fontRenderer);
				GL11.glPopMatrix();
				
			}else if(!world.isAirBlock(bx, by, bz) && world.getBlock(bx, by, bz) instanceof IGrowthCristal){
				int x = 0;
				int y = 20;
				Block block = world.getBlock(bx, by, bz);
				IGrowthCristal cristal = (IGrowthCristal)block;
				GL11.glPushMatrix();
				ArrayList<String> list = new ArrayList<String>();
				
				list.add(StatCollector.translateToLocal(block.getLocalizedName()));
				list.add(cristal.isMature(world, player, currentStack, bx, by, bz) ? "Mature: True" : "Mature: False");
				
				drawHoveringText(list, x, y, mc.fontRenderer);
				GL11.glPopMatrix();
				
			}else if(!world.isAirBlock(bx, by, bz) && world.getTileEntity(bx, by, bz) != null && world.getTileEntity(bx, by, bz) instanceof TileReagentPurifier){
				int x = 0;
				int y = 20;
				Block block = world.getBlock(bx, by, bz);
				TileReagentPurifier tile = (TileReagentPurifier) world.getTileEntity(bx, by, bz);
				
				GL11.glPushMatrix();
				ArrayList<String> list = new ArrayList<String>();
				
				list.add((block.getLocalizedName()));
				list.add(tile.getTimeLeft() <= 0 ? "Not working..." : "Working on " + tile.getWork());
				if(0 < tile.getTimeLeft())
					list.add("Time Left: " + tile.getTimeLeftGui());
				
				list.add(tile.getStored() == null ? "Not choking" : "Choking on " + tile.getStored().getDisplayName());
				
				drawHoveringText(list, x, y, mc.fontRenderer);
				GL11.glPopMatrix();
				
			}
		}
		this.unlockedGui.updateResearchWindow();
		mc.renderEngine.bindTexture(Gui.icons);
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEventDraw(DrawScreenEvent.Post event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;
		GuiScreen currentScreen = mc.currentScreen;
		
		if(player == null)
			return;
		ItemStack currentStack = player.getCurrentEquippedItem();
		
		if(world != null && currentScreen != null && currentScreen instanceof GuiContainer && currentStack != null && currentStack.getItem() == ItemRegistry.researchBook){
			if(DataHelper.belongsTo(player, currentStack))
				this.craftingHelper.drawCurrentSelected(currentScreen, mc, world, player, currentStack, event.mouseX, event.mouseY, event.renderPartialTicks);
		}
		
		this.unlockedGui.updateResearchWindow();
	}

	
	
	protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
        if (!p_146283_1_.isEmpty())
        {
            //GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            //RenderHelper.disableStandardItemLighting();
            //GL11.glDisable(GL11.GL_LIGHTING);
            //GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }
            
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }
            
            //GL11.glEnable(GL11.GL_LIGHTING);
            //GL11.glEnable(GL11.GL_DEPTH_TEST);
            //RenderHelper.enableStandardItemLighting();
            //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
