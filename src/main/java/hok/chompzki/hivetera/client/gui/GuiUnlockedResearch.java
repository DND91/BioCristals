package hok.chompzki.hivetera.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.TextureHandler;
import hok.chompzki.hivetera.research.data.Research;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiUnlockedResearch extends Gui {
	public static final ResourceLocation bioBookBG = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/GuiBioBookBG.png");
	
	private Minecraft theGame;
	
	private int ResearchWindowWidth;
	private int ResearchWindowHeight;
	
	private String ResearchGetLocalText;
	private String ResearchStatName;
	
	private Research theResearch;
	private long ResearchTime;
	
	private RenderItem itemRender;
	private boolean haveResearch;
	
	public GuiUnlockedResearch(Minecraft par1Minecraft)
    {
        this.theGame = par1Minecraft;
        this.itemRender = new RenderItem();
    }
	
	public void queueTakenAchievement(Research know)
    {
        this.ResearchGetLocalText = StatCollector.translateToLocal("Research.get");
        this.ResearchStatName = know.getTitle();
        this.ResearchTime = Minecraft.getSystemTime();
        this.theResearch = know;
        this.haveResearch = false;
    }
	
	public void queueAchievementInformation(Research know)
    {
        this.ResearchGetLocalText = know.getTitle();
        this.ResearchStatName = know.getDesc();
        this.ResearchTime = Minecraft.getSystemTime() - 2500L;
        this.theResearch = know;
        this.haveResearch = true;
    }
	
	private void updateResearchWindowScale()
    {
        GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        this.ResearchWindowWidth = this.theGame.displayWidth;
        this.ResearchWindowHeight = this.theGame.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(this.theGame, this.theGame.displayWidth, this.theGame.displayHeight);
        this.ResearchWindowWidth = scaledresolution.getScaledWidth();
        this.ResearchWindowHeight = scaledresolution.getScaledHeight();
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double)this.ResearchWindowWidth, (double)this.ResearchWindowHeight, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }
	
	public void updateResearchWindow()
    {
        if (this.theResearch != null && this.ResearchTime != 0L)
        {
            double d0 = (double)(Minecraft.getSystemTime() - this.ResearchTime) / 3000.0D;

            if (!this.haveResearch && (d0 < 0.0D || d0 > 1.0D))
            {
                this.ResearchTime = 0L;
            }
            else
            {
                this.updateResearchWindowScale();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                double d1 = d0 * 2.0D;

                if (d1 > 1.0D)
                {
                    d1 = 2.0D - d1;
                }

                d1 *= 4.0D;
                d1 = 1.0D - d1;

                if (d1 < 0.0D)
                {
                    d1 = 0.0D;
                }

                d1 *= d1;
                d1 *= d1;
                int i = this.ResearchWindowWidth - 160;
                int j = 0 - (int)(d1 * 36.0D);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                this.theGame.renderEngine.bindTexture(bioBookBG);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.drawTexturedModalRect(i, j, 96, 202, 160, 32);

                if (this.haveResearch)
                {
                    this.theGame.fontRenderer.drawSplitString(this.ResearchStatName, i + 30, j + 7, 120, -1);
                }
                else
                {
                    this.theGame.fontRenderer.drawString(this.ResearchGetLocalText, i + 30, j + 7, -256);
                    this.theGame.fontRenderer.drawString(this.ResearchStatName, i + 30, j + 18, -1);
                }

                RenderHelper.enableGUIStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glEnable(GL11.GL_LIGHTING);
                this.itemRender.renderItemAndEffectIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.theResearch.getIconStack(), i + 8, j + 8);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                
                this.theGame.renderEngine.bindTexture(Gui.icons);
            }
        }
    }
	
}
