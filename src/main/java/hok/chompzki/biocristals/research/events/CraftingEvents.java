package hok.chompzki.biocristals.research.events;

import java.util.UUID;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import hok.chompzki.biocristals.recipes.CrootRecipeContainer;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingEvents {
	
	@SubscribeEvent
	public void somethingPickedup(EntityItemPickupEvent event){
		if(event.entityPlayer == null)
			return;
		
		if(event.entityPlayer.worldObj.isRemote)
			return;
		
		if(event.item.getEntityItem().getItem() == ItemRegistry.crootBeetle){
			UUID id = event.entityPlayer.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance(false).get(id);
			ResearchLogicNetwork.instance().compelte(research, ReserchRegistry.babySteps);
		} else if(event.item.getEntityItem().getItem() == ItemRegistry.kraKenBug){
			UUID id = event.entityPlayer.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance(false).get(id);
			ResearchLogicNetwork.instance().compelte(research, ReserchRegistry.kraken);
		} else if(event.item.getEntityItem().getItem() == ItemRegistry.wsb){
			UUID id = event.entityPlayer.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance(false).get(id);
			ResearchLogicNetwork.instance().compelte(research, ReserchRegistry.wsb);
		} else if(event.item.getEntityItem().getItem() == ItemRegistry.crootClaw){
			UUID id = event.entityPlayer.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance(false).get(id);
			ResearchLogicNetwork.instance().compelte(research, ReserchRegistry.crootClaw);
		} else if(event.item.getEntityItem().getItem() == ItemRegistry.hivebag){
			UUID id = event.entityPlayer.getGameProfile().getId();
			PlayerResearch research = PlayerStorage.instance(false).get(id);
			ResearchLogicNetwork.instance().compelte(research, ReserchRegistry.hivebag);
		}
	}
	
	@SubscribeEvent
	public void somethingCrafted(ItemCraftedEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		
		for(CrootRecipeContainer con : RecipeRegistry.crootRecipes){
			if(con.code.equals("NONE"))
				continue;
			if(con.output.isItemEqual(event.crafting)){
				UUID id = event.player.getGameProfile().getId();
				PlayerResearch research = PlayerStorage.instance(false).get(id);
				ResearchLogicNetwork.instance().compelte(research, con.code);
				return;
			}
		}
		
		for(RecipeContainer con : RecipeRegistry.recipes){
			if(con.code.equals("NONE"))
				continue;
			if(con.output.isItemEqual(event.crafting)){
				UUID id = event.player.getGameProfile().getId();
				PlayerResearch research = PlayerStorage.instance(false).get(id);
				ResearchLogicNetwork.instance().compelte(research, con.code);
				return;
			}
		}
	}
	
}
