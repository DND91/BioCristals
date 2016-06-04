package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.items.armor.ArmorPattern;
import hok.chompzki.hivetera.items.armor.ArmorSocket;
import hok.chompzki.hivetera.items.armor.SocketType;

import java.util.HashMap;

import net.minecraft.util.IIcon;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorPatternRegistry {
	
	
	public static String[] pattern_names = new String[] {"gray", "devonian"};
	public static int[] pattern_colors = new int[] {0xd3d3d3, 0xCD853F};
	public static final EnumResource[] pattern_food = new EnumResource[] {EnumResource.RAW_FOOD, EnumResource.BIOMASS};
	
	public static HashMap<String, ArmorPattern> patterns = new HashMap<String, ArmorPattern>();
	
	public void init(FMLInitializationEvent event) {
		
		ArmorPattern gray = new ArmorPattern(pattern_names[0]);
		patterns.put(gray.name, gray);
		
		ArmorPattern devonian = new ArmorPattern(pattern_names[1]);
		devonian.add(0, new ArmorSocket(-1, -1, SocketType.FUNC));
		devonian.add(0, new ArmorSocket(1, -1, SocketType.FUNC));
		
		devonian.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		devonian.add(1, new ArmorSocket(0, -1, SocketType.DEFENSIVE));
		
		devonian.add(2, new ArmorSocket(-1, -1, SocketType.OFFENSIVE));
		devonian.add(2, new ArmorSocket(0,-1, SocketType.DEFENSIVE));
		devonian.add(2, new ArmorSocket(1, -1, SocketType.OFFENSIVE));
		
		devonian.add(3, new ArmorSocket(-1, 0, SocketType.PASSIVE));
		devonian.add(3, new ArmorSocket(1, 0, SocketType.PASSIVE));
		patterns.put(devonian.name, devonian);
		
	}
	
	
	
}
