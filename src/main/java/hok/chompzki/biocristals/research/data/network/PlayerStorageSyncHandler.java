package hok.chompzki.biocristals.research.data.network;

import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.research.data.ArticleContent;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerStorageSyncHandler implements
		IMessageHandler<PlayerStorageSyncMessage, IMessage> {
	
	public static List<String> totallyNew = new ArrayList<String>();

	@Override
	public IMessage onMessage(PlayerStorageSyncMessage message, MessageContext ctx) {
		PlayerResearch research = message.getResearch();
		if(research == null)
			return null;
		PlayerStorage storage = PlayerStorage.instance();
		
		UUID id = research.getOwnerId();
		PlayerResearch oldResearch = storage.get(id);
		
		
		
		storage.put(id, research);
		
		if(Minecraft.getMinecraft().thePlayer.getGameProfile().getId().equals(id)){
			int place = GuiInventoryOverlay.craftingHelper.place();
			GuiInventoryOverlay.craftingHelper.clear();
			for(String code : research.getFaved()){
				if(ReserchDataNetwork.instance().getResearch(code) == null)
					continue;
				ArticleContent content = ReserchDataNetwork.instance().getResearch(code).getContent();
				GuiInventoryOverlay.craftingHelper.add(content.getFaved());
			}
			GuiInventoryOverlay.craftingHelper.setPage(place);
			if(oldResearch != null){
				List<String> diff = research.notIn(oldResearch);
				for(String code : diff){
					Research r = ReserchDataNetwork.instance().getResearch(code);
					if(r != null){
						GuiInventoryOverlay.unlockedGui.queueTakenAchievement(r);
						for(String s : ReserchDataNetwork.instance().children.get(code)){
							totallyNew.add(s);
						}
					}
				}
				
			}
		}
		
		return null;
	}

}
