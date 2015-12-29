package hok.chompzki.biocristals.registrys;

import java.util.UUID;

import hok.chompzki.biocristals.client.gui.GuiCrootBreeder;
import hok.chompzki.biocristals.client.gui.GuiCrootCore;
import hok.chompzki.biocristals.client.gui.GuiCrootHollow;
import hok.chompzki.biocristals.client.gui.GuiHiveBrain;
import hok.chompzki.biocristals.client.gui.GuiHivebag;
import hok.chompzki.biocristals.client.gui.GuiNest;
import hok.chompzki.biocristals.client.gui.GuiResearchBook;
import hok.chompzki.biocristals.client.gui.GuiSacrificePit;
import hok.chompzki.biocristals.client.gui.GuiTokenAssembler;
import hok.chompzki.biocristals.containers.ContainerCrootBreeder;
import hok.chompzki.biocristals.containers.ContainerCrootHollow;
import hok.chompzki.biocristals.containers.ContainerCrootCore;
import hok.chompzki.biocristals.containers.ContainerHiveBrain;
import hok.chompzki.biocristals.containers.ContainerHivebag;
import hok.chompzki.biocristals.containers.ContainerNest;
import hok.chompzki.biocristals.containers.ContainerSacrificePit;
import hok.chompzki.biocristals.containers.ContainerTokenAssembler;
import hok.chompzki.biocristals.hunger.PlayerHungerNetwork;
import hok.chompzki.biocristals.hunger.PlayerHungerStorage;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.PlayerResearchStorage;
import hok.chompzki.biocristals.tile_enteties.TileCrootBreeder;
import hok.chompzki.biocristals.tile_enteties.TileCrootHollow;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import hok.chompzki.biocristals.tile_enteties.TileNest;
import hok.chompzki.biocristals.tile_enteties.TileSacrificePit;
import hok.chompzki.biocristals.tile_enteties.TileTokenAssembler;
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
			PlayerResearchStorage.instance(false).registerLissner(observer, subject);
			return null;
		}else if(ID == 101){ //CrootHollow
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}else if(ID == 102){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootCore(player.inventory, (TileCrootCore) tileEntity, false);
		}else if(ID == this.hivebagId){
			return new ContainerHivebag(player, player.inventory.currentItem);
		}else if(ID == 104){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerNest(player.inventory, (TileNest)tileEntity);
		}else if(ID == 105){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerCrootBreeder(player.inventory, (TileCrootBreeder)tileEntity);
		}else if(ID == 106){ //HiveBrain
			EntityPlayer p = world.getPlayerEntityByName(player.getCommandSenderName());
			UUID observer = p.getGameProfile().getId();
			String subject = DataHelper.getNetwork(player.inventory.getCurrentItem());
			PlayerHungerStorage.instance(false).registerLissner(observer, subject);
			PlayerHungerNetwork hunger = PlayerHungerStorage.instance(false).get(subject);
			return new ContainerHiveBrain(player, hunger);
		}else if(ID == 107){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerTokenAssembler(player, (TileTokenAssembler)tileEntity);
		}else if(ID == 108){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new ContainerSacrificePit(player, (TileSacrificePit)tileEntity);
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
			return DataHelper.belongsTo(player, stack) ? book.load() : new GuiResearchBook(player);
		}else if(ID == 101){ //ResearchBook
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootHollow(player.inventory, (TileCrootHollow) tileEntity);
		}else if(ID == 102){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootCore(player.inventory, (TileCrootCore) tileEntity);
		}else if(ID == this.hivebagId){
			return new GuiHivebag(player, player.inventory.currentItem);
		}else if(ID == 104){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiNest(player.inventory, (TileNest)tileEntity);
		}else if(ID == 105){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiCrootBreeder(player.inventory, (TileCrootBreeder)tileEntity);
		}else if(ID == 106){ //HiveBrain
			EntityPlayer p = world.getPlayerEntityByName(player.getCommandSenderName());
			ItemStack stack = p.getCurrentEquippedItem();
			
			String sid = DataHelper.getNetwork(stack);
			PlayerHungerNetwork hunger = PlayerHungerStorage.instance(true).get(sid);
			return new GuiHiveBrain(player, hunger);
		}else if(ID == 107){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiTokenAssembler(player, (TileTokenAssembler)tileEntity);
		}else if(ID == 108){
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return new GuiSacrificePit(player, (TileSacrificePit)tileEntity);
		}
		
		return null;
	}

}
