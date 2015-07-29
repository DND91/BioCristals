package hok.chompzki.biocristals.research.events;

import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.recipes.RecipeContainer;
import hok.chompzki.biocristals.registrys.RecipeRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerInserCrafting implements IMessageHandler<MessageInsertCrafting, IMessage> {
	
	
	
	@Override
	public IMessage onMessage(MessageInsertCrafting message, MessageContext ctx) {
		System.out.println("MESSAGE RECIVED!!!!!!!!!!");
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		Container container = player.openContainer;
		if(container == null)
			container = player.inventoryContainer;
		
		if(container != null){
			System.out.println("CONTAINER FOUND!");
			List<SlotCrafting> craftingSlots = getCraftingSlots(container);
			if(craftingSlots.size() == 0)
				System.out.println("NO CRAFTING SLOTS FOUND!");
			RecipeContainer recipe = RecipeRegistry.getRecipreFor(message.getResult());
			if(recipe == null)
				return null;
			System.out.println("RECIPES FOUND!");
			for(SlotCrafting slot : craftingSlots){
				IInventory grid = getCraftMatrix(slot);
				if(grid == null)
					continue;
				Slot[] slots = getSlots(container, grid);
				
				if(slots.length == grid.getSizeInventory() && 2*recipe.length <= grid.getSizeInventory()){
					for(int i = 0; i < grid.getSizeInventory(); i++){
						inserStack(player, container, recipe.getItemStack(i), slots[i]);
						container.detectAndSendChanges();
					}
				}
			}
		}
		return null;
	}
	
	private void inserStack(EntityPlayer player, Container container,
			ItemStack itemStack, Slot slot) {
		ItemStack sloted = slot.getStack();
		
		if(itemStack != null && sloted != null && sloted.getItem() == itemStack.getItem())
			return;
		
		if(sloted != null){
			container.putStackInSlot(slot.slotNumber, null);
			BioHelper.addItemStackToInventory(sloted, player.inventory);
			if(0 < sloted.stackSize){
				List<ItemStack> list = new ArrayList<ItemStack>();
				list.add(sloted);
				BioHelper.dropItems(player.worldObj, list, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
		}
		
		if(itemStack == null)
			return;
		
		if(itemStack != null && (player.inventory.hasItem(itemStack.getItem()) || player.capabilities.isCreativeMode)){
			container.putStackInSlot(slot.slotNumber, getOneOf(player, itemStack));
		}
	}
	
	private ItemStack getOneOf(EntityPlayer player, ItemStack stack){
		if(player.capabilities.isCreativeMode){
			ItemStack copy = stack.copy();
			copy.stackSize = 1;
			return copy;
		}
		
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			ItemStack current = player.inventory.mainInventory[i];
			if(current != null && current.getItem() == stack.getItem()){
				ItemStack copy = current.copy();
				copy.stackSize = 1;
				current.stackSize--;
				if(current.stackSize <= 0){
					player.inventory.mainInventory[i] = null;
				}
				return copy;
			}
		}
		return null;
	}

	private Slot[] getSlots(Container container, IInventory grid) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		for(Object obj : container.inventorySlots){
			Slot slot = (Slot) obj;
			if(slot.inventory == grid)
				slots.add(slot);
		}
		return slots.toArray(new Slot[slots.size()]);
	}

	public List<SlotCrafting> getCraftingSlots(Container container){
		List<SlotCrafting> slots = new ArrayList<SlotCrafting>();
		for(Object obj : container.inventorySlots){
			if(obj instanceof SlotCrafting){
				slots.add((SlotCrafting) obj);
			}
		}
		return slots;
	}
	
	public IInventory getCraftMatrix(SlotCrafting slot){
		IInventory grid = null;
		try{
			Field matrix = SlotCrafting.class.getDeclaredField("craftMatrix");
			matrix.setAccessible(true);
			grid = (IInventory) matrix.get(slot);
		}catch (Exception ex){}
		return grid;
	}

}
