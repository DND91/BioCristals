package hok.chompzki.biocristals.research.events;

import java.util.UUID;

import net.minecraft.item.Item;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
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
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals("NONE"))
				continue;
			if(con.output.isItemEqual(event.crafting)){
				UUID id = event.player.getGameProfile().getId();
				PlayerResearch research = PlayerStorage.instance().get(id);
				if(!research.hasCompleted(con.code)){
					research.addCompleted(con.code);
				} 
			}
		}
	}
	
}
