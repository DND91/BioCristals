package hok.chompzki.biocristals.research.data.network;

import java.util.UUID;

import hok.chompzki.biocristals.research.data.PlayerStorage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerStorageDelissenHandler implements IMessageHandler<PlayerStorageDelissenMessage, IMessage> {
	
	public PlayerStorageDelissenHandler(){
		
	}
	
	@Override
	public IMessage onMessage(PlayerStorageDelissenMessage message,
			MessageContext ctx) {
		PlayerStorage.instance(false).deregisterLissner(UUID.fromString(message.getContainer().observer), UUID.fromString(message.getContainer().subject));
		return null;
	}
	
}
