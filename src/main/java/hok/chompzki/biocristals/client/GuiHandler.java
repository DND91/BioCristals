package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.tutorials.data.DataBook;
import hok.chompzki.biocristals.tutorials.data.description.BabySteps;
import hok.chompzki.biocristals.tutorials.gui.GuiBioBook;
import hok.chompzki.biocristals.tutorials.gui.GuiDescription;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //BioBook
			return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //BioBook
			//return new GuiDescription(new BabySteps());
			return new GuiBioBook(player);
		}
		
		return null;
	}

}
