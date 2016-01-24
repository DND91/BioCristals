package hok.chompzki.hivetera.hunger.network;

import java.util.UUID;

import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerHungerDelissenHandler implements IMessageHandler<PlayerHungerDelissenMessage, IMessage> {
	
	public PlayerHungerDelissenHandler(){
		
	}
	
	@Override
	public IMessage onMessage(PlayerHungerDelissenMessage message,
			MessageContext ctx) {
		PlayerHungerStorage.instance(false).deregisterLissner(UUID.fromString(message.getContainer().observer), message.getContainer().subject);
		return null;
	}
	
}
