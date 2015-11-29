package hok.chompzki.biocristals.registrys;

import java.util.UUID;

import hok.chompzki.biocristals.client.gui.GuiCrootCore;
import hok.chompzki.biocristals.client.gui.GuiCrootHollow;
import hok.chompzki.biocristals.client.gui.GuiHivebag;
import hok.chompzki.biocristals.client.gui.GuiResearchBook;
import hok.chompzki.biocristals.containers.ContainerCrootHollow;
import hok.chompzki.biocristals.containers.ContainerCrootCore;
import hok.chompzki.biocristals.containers.ContainerHivebag;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.tile_enteties.TileCrootHollow;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int hivebagId = 103;
	
	public static GuiResearchBook book = null;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			EntityPlayer p = world.getPlayerEntityByName(player.getCommandSenderName());
			UUID observer = p.getGameProfile().getId();
			UUID subject = UUID.fromString(DataHelper.getOwner(player.inventory.getCurrentItem()));
			PlayerStorage.instance(false).registerLissner(observer, subject);
			return null;
		}else if(ID == 101){ //CrootHollow
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}else if(ID == 102){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootCore(player.inventory, (TileCrootCore) tileEntity, false);
		}else if(ID == this.hivebagId){
			return new ContainerHivebag(player, player.inventory.currentItem);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if(ID == 100){ //ResearchBook
			ItemStack stack = player.getCurrentEquippedItem();
			
			if(DataHelper.belongsTo(player, stack) && book == null)
				book = new GuiResearchBook(player);
			return DataHelper.belongsTo(player, stack) ? book : new GuiResearchBook(player);
		}else if(ID == 101){ //ResearchBook
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}else if(ID == 102){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootCore(player.inventory, (TileCrootCore) tileEntity);
		}else if(ID == this.hivebagId){
			return new GuiHivebag(player, player.inventory.currentItem);
		}
		
		return null;
	}

}
