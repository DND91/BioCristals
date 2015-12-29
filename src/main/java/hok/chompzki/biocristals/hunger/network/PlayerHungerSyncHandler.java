package hok.chompzki.biocristals.hunger.network;

import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.GuiInventoryOverlay;
import hok.chompzki.biocristals.hunger.PlayerHungerNetwork;
import hok.chompzki.biocristals.hunger.PlayerHungerStorage;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerResearchStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ResearchUnlocks;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerHungerSyncHandler implements
		IMessageHandler<PlayerHungerSyncMessage, IMessage> {

	@Override
	public IMessage onMessage(PlayerHungerSyncMessage message, MessageContext ctx) {
		PlayerHungerNetwork hunger = message.getHunger();
		
		if(hunger == null){
			return null;
		}
		
		PlayerHungerStorage storage = PlayerHungerStorage.instance(true);
		
		String id = hunger.getName();
		
		PlayerHungerNetwork oldHunger = storage.get(id);
		if(oldHunger == null){
			storage.put(id, hunger);
		}else{
			oldHunger.load(hunger);
		}
		return null;
	}

}
