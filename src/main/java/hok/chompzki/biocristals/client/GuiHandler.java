package hok.chompzki.biocristals.client;

import net.minecraft.entity.player.EntityPlayer;
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
			NBTTagCompound compound = (NBTTagCompound) player.getEntityData().getTag(player.getGameProfile().getName()+".BioBook");
			if(compound == null)
				return null;
			return null; //new GuiKnowledges(compound, player);
		}
		
		return null;
	}

}
