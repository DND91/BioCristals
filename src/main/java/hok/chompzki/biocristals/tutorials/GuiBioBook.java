package hok.chompzki.biocristals.tutorials;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiBioBook extends GuiScreen {
	
	protected int knowledgePaneWidth = 256;
	protected int knowledgePaneHeight = 202;
	
	NBTTagCompound comp = null;
	EntityPlayer player = null;
	
	public GuiBioBook(NBTTagCompound compound, EntityPlayer player) {
		this.comp = compound;
		this.player = player;
	}
	
	
}
