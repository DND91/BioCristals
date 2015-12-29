package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.client.gui.GuiBreeding;
import hok.chompzki.biocristals.client.gui.GuiCraft;
import hok.chompzki.biocristals.client.gui.GuiCraftingHelper;
import hok.chompzki.biocristals.client.gui.GuiCrootStickHelper;
import hok.chompzki.biocristals.client.gui.GuiInsectHelper;
import hok.chompzki.biocristals.client.gui.GuiToken;
import hok.chompzki.biocristals.registrys.ReserchRegistry;
import hok.chompzki.biocristals.research.logic.ResearchLogicNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ResearchUnlocks {
	
	public static HashMap<String, UnlockData> codeToMethod = new HashMap<String, UnlockData>(); 
	
	private static HashMap<EnumUnlock, ArrayList<UnlockData>> unlocks = new HashMap<EnumUnlock, ArrayList<UnlockData>>(); 
	
	private static void addToMain(String code, UnlockData data){
		
	}
	
	private static void addUnlockData(UnlockData data){
		codeToMethod.put(data.code, data);
		if(!unlocks.containsKey(data.type))
			unlocks.put(data.type, new ArrayList<UnlockData>());
		ArrayList<UnlockData> list = unlocks.get(data.type);
		list.add(data);
	}
	
	public static void addCraftingUnlock(ItemStack stack, String code){
		UnlockData data = new UnlockData();
		data.code = code;
		data.stack = stack;
		data.type = EnumUnlock.CRAFT;
		addUnlockData(data);
	}
	
	public static void addPickUpUnlock(ItemStack stack, String code, ItemStack tool, ItemStack insect, ItemStack... places){
		UnlockPickUpData data = new UnlockPickUpData();
		data.code = code;
		data.stack = stack;
		data.tool = tool;
		data.insect = insect;
		data.places = places;
		data.type = EnumUnlock.PICKUP;
		addUnlockData(data);
	}
	
	public static void addPutInNestUnlock(ItemStack stack, String code){
		UnlockData data = new UnlockData();
		data.code = code;
		data.stack = stack;
		data.type = EnumUnlock.PUT_NEST;
		addUnlockData(data);
	}
	
	public static void addBreedingUnlock(ItemStack stack, String code){
		UnlockData data = new UnlockData();
		data.code = code;
		data.stack = stack;
		data.type = EnumUnlock.BREEDING;
		addUnlockData(data);
	}
	
	public static void addTokenAssemblyUnlock(ItemStack stack, String code){
		UnlockData data = new UnlockData();
		data.code = code;
		data.stack = stack;
		data.type = EnumUnlock.TOKEN_ASSEMBLE;
		addUnlockData(data);
	}
	
	public static void unlock(EntityPlayer player, EnumUnlock type, ItemStack stack){
		if(!unlocks.containsKey(type))
			unlocks.put(type, new ArrayList<UnlockData>());
		ArrayList<UnlockData> list = unlocks.get(type);
		for(UnlockData data : list){
			ItemStack result = data.stack;
			if(stack.getItem() == result.getItem() && (result.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == result.getItemDamage())){
				UUID id = player.getGameProfile().getId();
				PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
				ResearchLogicNetwork.instance().compelte(research, data.code);
				return;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static GuiCraftingHelper getGui(String code){
		if(!codeToMethod.containsKey(code))
			return null;
		UnlockData data = codeToMethod.get(code);
		
		switch(data.type){
		case CRAFT:
			return new GuiCraft(Minecraft.getMinecraft(), data.stack, data.code);
		case PICKUP:
			UnlockPickUpData pickup = (UnlockPickUpData)data;
			return new GuiCrootStickHelper(Minecraft.getMinecraft(), data.code, pickup.tool, pickup.insect, pickup.places);
		case PUT_NEST:
			return new GuiInsectHelper(Minecraft.getMinecraft(), data.code, data.stack);
		case BREEDING:
			return new GuiBreeding(Minecraft.getMinecraft(), data.stack, data.code);
		case TOKEN_ASSEMBLE:
			return new GuiToken(Minecraft.getMinecraft(), data.stack, data.code);
		default:
			break;
		}
		
		return null;
	}
}
