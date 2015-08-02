package hok.chompzki.biocristals.research.data.network;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import hok.chompzki.biocristals.research.data.DataHelper;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class PlayerStorageFaveHandler implements IMessageHandler<PlayerStorageFaveMessage, IMessage> {
	
	public PlayerStorageFaveHandler(){
		
	}
	
	@Override
	public IMessage onMessage(PlayerStorageFaveMessage message,
			MessageContext ctx) {
		UUID id = UUID.fromString(message.getContainer().observer);
		if(PlayerStorage.instance(false).get(id) == null){
			System.err.println("ERROR: PLAYER RESEARCH NOT FOUND IN FAVE HANDELING!");
			return null;
		}
		if(PlayerStorage.instance(false).get(id).hasFaved(message.getContainer().subject)){
			PlayerStorage.instance(false).get(id).removeFaved(message.getContainer().subject);
		}else{
			PlayerStorage.instance(false).get(id).addFaved(message.getContainer().subject);
		}
		return null;
	}
	
}