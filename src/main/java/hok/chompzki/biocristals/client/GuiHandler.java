package hok.chompzki.biocristals.client;

import java.util.UUID;

import hok.chompzki.biocristals.research.data.DataBook;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.gui.GuiResearchBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			UUID observer = player.getGameProfile().getId();
			UUID subject = UUID.fromString(player.inventory.getCurrentItem().getTagCompound().getString("OWNER"));
			PlayerStorage.instance().registerLissner(observer, subject);
			return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			return new GuiResearchBook(player);
		}
		
		return null;
	}

}
