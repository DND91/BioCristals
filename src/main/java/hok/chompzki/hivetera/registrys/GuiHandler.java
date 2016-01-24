package hok.chompzki.hivetera.registrys;

import java.util.UUID;

import hok.chompzki.hivetera.client.gui.GuiArmorAttuner;
import hok.chompzki.hivetera.client.gui.GuiCrootBreeder;
import hok.chompzki.hivetera.client.gui.GuiCrootCore;
import hok.chompzki.hivetera.client.gui.GuiCrootHollow;
import hok.chompzki.hivetera.client.gui.GuiHiveBrain;
import hok.chompzki.hivetera.client.gui.GuiHivebag;
import hok.chompzki.hivetera.client.gui.GuiHoneyWidow;
import hok.chompzki.hivetera.client.gui.GuiInsectHunt;
import hok.chompzki.hivetera.client.gui.GuiNest;
import hok.chompzki.hivetera.client.gui.GuiResearchBook;
import hok.chompzki.hivetera.client.gui.GuiSacrificePit;
import hok.chompzki.hivetera.client.gui.GuiTokenAssembler;
import hok.chompzki.hivetera.containers.ContainerArmorAttuner;
import hok.chompzki.hivetera.containers.ContainerCrootBreeder;
import hok.chompzki.hivetera.containers.ContainerCrootCore;
import hok.chompzki.hivetera.containers.ContainerCrootHollow;
import hok.chompzki.hivetera.containers.ContainerHiveBrain;
import hok.chompzki.hivetera.containers.ContainerHivebag;
import hok.chompzki.hivetera.containers.ContainerHoneyWidow;
import hok.chompzki.hivetera.containers.ContainerInsectHunt;
import hok.chompzki.hivetera.containers.ContainerNest;
import hok.chompzki.hivetera.containers.ContainerSacrificePit;
import hok.chompzki.hivetera.containers.ContainerTokenAssembler;
import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.research.data.DataHelper;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import hok.chompzki.hivetera.tile_enteties.TileSacrificePit;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
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
		} else if(ID == 109){
			return new ContainerHoneyWidow(player, player.inventory.currentItem);
		} else if(ID == 110){
			return new ContainerArmorAttuner(player);
		} else if(ID == 111){
			return new ContainerInsectHunt(player);
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
		}else if(ID == 109){
			return new GuiHoneyWidow(player, player.inventory.currentItem);
		}else if(ID == 110){
			return new GuiArmorAttuner(player);
		}else if(ID == 111){
			return new GuiInsectHunt(player);
		}
		
		return null;
	}

}
