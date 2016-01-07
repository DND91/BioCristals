package hok.chompzki.biocristals.research.data.network;

import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.GuiInventoryOverlay;
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

public class PlayerStoragePullHandler implements
		IMessageHandler<PlayerStoragePullMessage, IMessage> {
	
	public static List<String> totallyNew = new ArrayList<String>();

	@Override
	public IMessage onMessage(PlayerStoragePullMessage message, MessageContext ctx) {
		UUID id = message.id;
		if(id == null){
			return null;
		}
		
		PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
		if(research == null){
			System.out.println("PULL RESEARCH STORAGE FOR PLAYER... PLAYER NOT FOUND ON ID: " + ctx.getServerHandler().playerEntity.getDisplayName());
			return null;
		}
		
		return new PlayerStorageSyncMessage(research);
	}

}
