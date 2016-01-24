package hok.chompzki.hivetera.client.gui;

import java.util.UUID;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerHiveBrain;
import hok.chompzki.hivetera.containers.ContainerNest;
import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.hunger.network.PlayerHungerDelissenMessage;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncHandler;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiHiveBrain extends GuiContainer {
	
	private static final ResourceLocation hollowBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/HiveBrain.png");

	private EntityPlayer player;
	private PlayerHungerNetwork hunger;
    private ItemStack stack;

	public GuiHiveBrain(EntityPlayer player, PlayerHungerNetwork hunger) {
		super(new ContainerHiveBrain(player, hunger));
		this.player = player;
		this.hunger = hunger;
		this.stack = player.getCurrentEquippedItem();
		this.xSize = 256;
		this.ySize = 256;
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = I18n.format(this.hunger.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, 175, 175, 0xFFFFFF);
        
    }
	
	int tick = 0;
	
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	tick++;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(hollowBG);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
    }
    
    @Override
	public void onGuiClosed(){
		UUID observer = player.getGameProfile().getId();
		String subject = DataHelper.getNetwork(stack);
		player.getCurrentEquippedItem().stackTagCompound.setBoolean("OPEN", false);
		if(!player.getCommandSenderName().equals(subject)){
			HiveteraMod.network.sendToServer(new PlayerHungerDelissenMessage(observer, subject));
		}
    }
    
    @Override
	public boolean doesGuiPauseGame()
    {
        return true;
    }
}
