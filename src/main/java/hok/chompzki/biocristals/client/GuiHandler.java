package hok.chompzki.biocristals.client;

import java.util.UUID;

import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.gui.GuiResearchBook;
import hok.chompzki.biocristals.tile_enteties.TileCrootHollow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			EntityPlayer p = world.getPlayerEntityByName(player.getCommandSenderName());
			UUID observer = p.getGameProfile().getId();
			UUID subject = UUID.fromString(p.inventory.getCurrentItem().getTagCompound().getString("OWNER"));
			PlayerStorage.instance().registerLissner(observer, subject);
			return null;
		}else if(ID == 101){ //CrootHollow
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			return new GuiResearchBook(player);
		}else if(ID == 101){ //ResearchBook
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}
		
		return null;
	}

}
