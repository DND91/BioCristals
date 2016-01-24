package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.client.gui.GuiBreeding;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.GuiCrootStickHelper;
import hok.chompzki.hivetera.client.gui.GuiInsectHelper;
import hok.chompzki.hivetera.client.gui.GuiToken;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;

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
				if(research == null){
					System.out.println("UNKOWN PLAYER TRIED UNLOCK RESEARCH WITHOUT REGISTATION: " + player.getDisplayName());
					return;
				}
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
		
		return new GuiCraft(Minecraft.getMinecraft(), data.stack, data.code);
	}
}
