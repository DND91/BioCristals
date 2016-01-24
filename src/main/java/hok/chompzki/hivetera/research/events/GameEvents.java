package hok.chompzki.hivetera.research.events;

import java.text.DecimalFormat;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.containers.InsectHunt;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.recipes.CrootRecipeContainer;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.data.EnumUnlock;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;
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
			
			
		}else if(current != null && current.getItem() == ItemRegistry.crootStick 
				&& event.item.getEntityItem().getItem() == ItemRegistry.crootBeetle){
			ItemStack floor = event.item.getEntityItem();
			if(floor.stackSize <= 0)
				return;
			
			ItemRegistry.crootBeetle.onCreated(floor, event.entityPlayer.worldObj, event.entityPlayer);
			InsectHunt hunt = new InsectHunt(event.entityPlayer, event.entityPlayer.inventory.currentItem);
			BioHelper.addItemStackToInventory(floor, hunt, 0, 1);
			hunt.markDirty();
			
			event.setResult(Result.ALLOW);
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
	
	@SubscribeEvent
	public void itemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.itemStack;
		if(!(stack.getItem() instanceof ItemFood))
			return;
		if(!event.showAdvancedItemTooltips && !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)))
			return;
		
		ItemFood input = (ItemFood)stack.getItem();
		DecimalFormat df = new DecimalFormat("0.00"); 
		double value = input.func_150905_g(stack) * input.func_150906_h(stack) * 2.0D * 0.125D;
		event.toolTip.add("Raw Food(Normal): " + df.format(value));
		value = input.func_150905_g(stack) * input.func_150906_h(stack) * 2.0D;
		event.toolTip.add("Raw Food(" + BlockRegistry.sacrificePit.getLocalizedName() + "): " + df.format(value));
	}
}
