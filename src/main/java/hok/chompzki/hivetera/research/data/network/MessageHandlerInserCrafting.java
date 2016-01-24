package hok.chompzki.hivetera.research.data.network;

import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerInserCrafting implements IMessageHandler<MessageInsertCrafting, IMessage> {
	
	
	
	@Override
	public IMessage onMessage(MessageInsertCrafting message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		player = ctx.getServerHandler().playerEntity.worldObj.func_152378_a(player.getGameProfile().getId());
		
		Container container = player.openContainer;
		if(container == null)
			container = player.inventoryContainer;
		
		if(container != null){
			List<SlotCrafting> craftingSlots = getCraftingSlots(container);
			RecipeContainer recipe = RecipeRegistry.getCrootRecipreFor(message.getResult());
			if(recipe == null)
				recipe = RecipeRegistry.getRecipreFor(message.getResult());
			if(recipe == null)
				return null;
			for(SlotCrafting slot : craftingSlots){
				IInventory grid = getCraftMatrix(slot);
				if(grid == null)
					continue;
				Slot[] slots = getSlots(container, grid);
				
				int gridLength = (int) Math.sqrt(grid.getSizeInventory());
				int diff = gridLength - (recipe.length);
				
				if(slots.length == grid.getSizeInventory() && recipe.length*recipe.length <= grid.getSizeInventory()){
					for(int x = 0; x < recipe.length; x++)
					for(int y = 0; y < recipe.length; y++){
						
						List<ItemStack> stack = recipe.getItemStack(x+y*recipe.length);
						
						insertStack(player, container, stack == null ? null : stack.get(0), slots[x+y*gridLength]);
					}
					grid.markDirty();
					container.detectAndSendChanges();
				}
			}
		}
		return null;
	}
	
	private void insertStack(EntityPlayer player, Container container,
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
		
		int[] targetIds = OreDictionary.getOreIDs(itemStack);
		
		if(0 < targetIds.length && hasItem(player.inventory, targetIds) && !player.capabilities.isCreativeMode){
			container.putStackInSlot(slot.slotNumber, getOneOre(player, targetIds));
		} else if(itemStack != null && (player.inventory.hasItem(itemStack.getItem()) || player.capabilities.isCreativeMode)){
			container.putStackInSlot(slot.slotNumber, getOneOf(player, itemStack));
		}
	}
	
	
	
	private boolean hasItem(InventoryPlayer inventory, int[] targetIds) {
		for(ItemStack stack : inventory.mainInventory){
			int[] subjectIds = OreDictionary.getOreIDs(stack);
			for(int tid : targetIds)
			for(int sid : subjectIds)
				if(tid == sid)
					return true;
		}
		return false;
	}
	
	private ItemStack getOneOre(EntityPlayer player, int[] targetIds){
		
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			ItemStack current = player.inventory.mainInventory[i];
			if(current != null){
				int[] subjectIds = OreDictionary.getOreIDs(current);
				for(int tid : targetIds)
				for(int sid : subjectIds)
					if(tid == sid){
						ItemStack copy = current.copy();
						copy.stackSize = 1;
						current.stackSize--;
						if(current.stackSize <= 0){
							player.inventory.mainInventory[i] = null;
						}
						return copy;
					}
			}
		}
		return null;
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
			for(Field field : SlotCrafting.class.getDeclaredFields()){
				if(field.getType().isAssignableFrom(IInventory.class)){
					Field matrix = field;
					matrix.setAccessible(true);
					IInventory t = (IInventory) matrix.get(slot);
					if(grid == null)
						grid = t;
					else if(grid.getSizeInventory() < t.getSizeInventory())
						grid = t;
				}
			}
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return grid;
	}

}
