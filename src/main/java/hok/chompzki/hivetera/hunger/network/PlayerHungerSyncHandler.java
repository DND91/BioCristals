package hok.chompzki.hivetera.hunger.network;

import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.GuiInventoryOverlay;
import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;

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
