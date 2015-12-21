package hok.chompzki.biocristals.research.events;

import java.util.UUID;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import hok.chompzki.biocristals.BioHelper;
import hok.chompzki.biocristals.recipes.CrootRecipeContainer;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.registrys.RecipeRegistry;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.data.EnumUnlock;
import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.ResearchUnlocks;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class GameEvents {
	
	@SubscribeEvent
	public void somethingPickedup(EntityItemPickupEvent event){
		if(event.entityPlayer == null)
			return;
		
		if(event.entityPlayer.worldObj.isRemote)
			return;
		
		ResearchUnlocks.unlock(event.entityPlayer, EnumUnlock.PICKUP, event.item.getEntityItem());
		
		ItemStack current = event.entityPlayer.inventory.getCurrentItem();
		if(current != null && current.getItem() == ItemRegistry.nomadSack){
			ItemStack floor = event.item.getEntityItem();
			if(ItemRegistry.nomadSack.canEat(current, floor)){
				ItemRegistry.nomadSack.eat(current, floor);
				if(floor.stackSize <= 0){
					
				}
				event.setResult(Result.ALLOW);
			}
			
			
		}
	}
	
	@SubscribeEvent
	public void somethingCrafted(ItemCraftedEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		
		ResearchUnlocks.unlock(event.player, EnumUnlock.CRAFT, event.crafting);
		
		
	}
	
	//PlayerDestroyItemEvent
	@SubscribeEvent
	public void playerDestroyItemEvent(PlayerDestroyItemEvent event)
	{
		if(event.entityPlayer == null || event.entityPlayer.worldObj.isRemote)
			return;
		
		if(event.original.getItem() == ItemRegistry.crootIronPickaxe){
			BioHelper.addItemStackToInventory(new ItemStack(ItemRegistry.crootStick), event.entityPlayer.inventory);
		} else if(event.original.getItem() == ItemRegistry.crootWoodHoe){
			BioHelper.addItemStackToInventory(new ItemStack(ItemRegistry.crootStick), event.entityPlayer.inventory);
		}
		
		
	}
	
}
