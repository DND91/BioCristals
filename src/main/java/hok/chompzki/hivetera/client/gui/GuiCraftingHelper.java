package hok.chompzki.hivetera.client.gui;

import org.lwjgl.opengl.GL11;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.research.data.Research;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class GuiCraftingHelper extends Gui {
	
	protected  final ArticleFontRenderer articleFontRenderer;
	
	protected static final ResourceLocation bgTextures = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/book.png");
	/** Button width in pixels */
    public int width = 254;
    /** Button height in pixels */
    public int height = 179;
    /** The x position of this control. */
    public int xPosition = 0;
    /** The y position of this control. */
    public int yPosition = 0;
    
    public float xScale = 0.5f;
    public float yScale = 1.0f;
    
    protected Research research = null;
    protected RecipeContainer input;
    protected ItemStack output;
    
    public RecipeContainer getInput(){
    	return input;
    }
    
    public ItemStack getOutput(){
    	return output;
    }
    
    public GuiCraftingHelper(Minecraft minecraft, Research research){
    	this.articleFontRenderer = new ArticleFontRenderer(minecraft, minecraft.gameSettings, HiveteraMod.MODID + ":textures/font/ascii.png", minecraft.renderEngine, false);
    	this.research = research;
    }
    
    public int getWidth(){
    	return (int) (xScale * width);
    }
    
    public int getHeight(){
    	return (int) (yScale * height);
    }
    
    public void rescale(Minecraft mc, GuiScreen currentScreen){
    	GL11.glPushMatrix();
    	
    	float extraScale = 1.0f;
    	if(mc.gameSettings.guiScale == 1){
    		extraScale = 0.5f;
    	}
    	
    	ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	
    	float freeX = (res.getScaledWidth()) / 2;
    	float freeY = (res.getScaledHeight()) / 2;
    	
    	xScale = freeX / (width) / 2 * extraScale;
    	yScale = freeY / height * extraScale;
    	
		GL11.glScalef(xScale, yScale, 1.0f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
	public void drawBackground(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		//RenderHelper.disableStandardItemLighting();
		//GL11.glDisable(GL11.GL_BLEND);
		mc.renderEngine.bindTexture(bgTextures);
		this.drawTexturedModalRect(xPosition, yPosition, 0, 0, width, height);
		normalScale();
	}
	
	public void drawGui(GuiScreen currentScreen, Minecraft mc, World world, EntityPlayer player, ItemStack currentStack, int mouseX, int mouseY, float renderPartialTicks) {
		
		normalScale();
	}
	
	public void normalScale(){
		//System.out.println(Minecraft.getMinecraft().gameSettings.guiScale);
		
		GL11.glScalef(1.0f / xScale, 1.0f / yScale, 1.0f);
		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	
	public boolean collision(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {
        return p_146116_2_ >= this.xPosition && p_146116_3_ >= this.yPosition && p_146116_2_ < this.xPosition + this.getWidth() && p_146116_3_ < this.yPosition + this.getHeight();
    }

	public Research getResearch() {
		return research;
	}
	
	
}
