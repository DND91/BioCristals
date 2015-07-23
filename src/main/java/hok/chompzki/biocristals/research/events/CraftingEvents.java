package hok.chompzki.biocristals.research.events;

import java.util.UUID;

import net.minecraft.item.Item;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingEvents {
	
	@SubscribeEvent
	public void SomethingPickedup(ItemCraftedEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		
		if (event.crafting.getItem() == ItemRegistry.attuner){
			UUID id = event.player.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance().get(id);
			
			if(!research.hasCompleted(ReserchRegistry.babySteps)){
				research.addCompleted(ReserchRegistry.babySteps);
			} 
			
		}else if (event.crafting.getItem() == ItemRegistry.bioReagent){
			UUID id = event.player.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance().get(id);
			
			if(!research.hasCompleted(ReserchRegistry.reaction)){
				research.addCompleted(ReserchRegistry.reaction);
			} 
		}else if (event.crafting.getItem() == Item.getItemFromBlock(BlockRegistry.biomass)){
			UUID id = event.player.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance().get(id);
			
			if(!research.hasCompleted(ReserchRegistry.cubeMass)){
				research.addCompleted(ReserchRegistry.cubeMass);
			} 
		}else if (event.crafting.getItem() == Item.getItemFromBlock(BlockRegistry.crootSapling)){
			UUID id = event.player.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance().get(id);
			
			if(!research.hasCompleted(ReserchRegistry.crootSapling)){
				research.addCompleted(ReserchRegistry.crootSapling);
			} 
		}else if (event.crafting.getItem() == Item.getItemFromBlock(BlockRegistry.sulphurTuft)){
			UUID id = event.player.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance().get(id);
			
			if(!research.hasCompleted(ReserchRegistry.tuft)){
				research.addCompleted(ReserchRegistry.tuft);
			} 
		}
		
	}
	
}
