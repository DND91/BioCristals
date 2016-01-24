package hok.chompzki.hivetera.research.data.network;

import java.util.UUID;

import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerStorageDelissenHandler implements IMessageHandler<PlayerStorageDelissenMessage, IMessage> {
	
	public PlayerStorageDelissenHandler(){
		
	}
	
	@Override
	public IMessage onMessage(PlayerStorageDelissenMessage message,
			MessageContext ctx) {
		PlayerResearchStorage.instance(false).deregisterLissner(UUID.fromString(message.getContainer().observer), UUID.fromString(message.getContainer().subject));
		return null;
	}
	
}
