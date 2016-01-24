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
	
	
	public static String[] pattern_names = new String[] {"gray", "red", "green", "blue", "yellow"};//, "gold", "silver", "crystal", "ruby", "sapphire", "emerald"};
	public static int[] pattern_colors = new int[] {0xd3d3d3, 11743532, 3887386, 2437522, 14602026};//, "gold", "silver", "crystal", "ruby", "sapphire", "emerald"};
	public static final EnumResource[] pattern_food = new EnumResource[] {EnumResource.RAW_FOOD, EnumResource.BIOMASS, EnumResource.NURITMENT, EnumResource.LIFE_FLUIDS, EnumResource.WASTE};
	
	public static HashMap<String, ArmorPattern> patterns = new HashMap<String, ArmorPattern>();
	
	public void init(FMLInitializationEvent event) {
		
		ArmorPattern gray = new ArmorPattern(pattern_names[0]);
		patterns.put(gray.name, gray);
		
		ArmorPattern red = new ArmorPattern(pattern_names[1]);
		red.add(0, new ArmorSocket(0, -1, SocketType.OFFENSIVE));
		
		red.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		red.add(1, new ArmorSocket(0, -1, SocketType.OFFENSIVE));
		
		red.add(2, new ArmorSocket(0, -1, SocketType.OFFENSIVE));
		red.add(2, new ArmorSocket(-1,-1, SocketType.OFFENSIVE));
		red.add(2, new ArmorSocket(1, -1, SocketType.OFFENSIVE));
		
		red.add(3, new ArmorSocket(0, 0, SocketType.OFFENSIVE));
		patterns.put(red.name, red);
		
		ArmorPattern green = new ArmorPattern(pattern_names[2]);
		green.add(0, new ArmorSocket(0, -1, SocketType.PASSIVE));
		
		green.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		green.add(1, new ArmorSocket(0, -1, SocketType.PASSIVE));
		
		green.add(2, new ArmorSocket(0, -1, SocketType.PASSIVE));
		green.add(2, new ArmorSocket(-1,-1, SocketType.PASSIVE));
		green.add(2, new ArmorSocket(1, -1, SocketType.PASSIVE));
		
		green.add(3, new ArmorSocket(0, 0, SocketType.PASSIVE));
		patterns.put(green.name, green);
		
		ArmorPattern blue = new ArmorPattern(pattern_names[3]);
		blue.add(0, new ArmorSocket(0, -1, SocketType.DEFENSIVE));
		
		blue.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		blue.add(1, new ArmorSocket(0, -1, SocketType.DEFENSIVE));
		
		blue.add(2, new ArmorSocket(0, -1, SocketType.DEFENSIVE));
		blue.add(2, new ArmorSocket(-1,-1, SocketType.DEFENSIVE));
		blue.add(2, new ArmorSocket(1, -1, SocketType.DEFENSIVE));
		
		blue.add(3, new ArmorSocket(0, 0, SocketType.DEFENSIVE));
		patterns.put(blue.name, blue);
		
		ArmorPattern yellow = new ArmorPattern(pattern_names[4]);
		yellow.add(0, new ArmorSocket(0, -1, SocketType.FUNC));
		
		yellow.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		yellow.add(1, new ArmorSocket(0, -1, SocketType.FUNC));
		
		yellow.add(2, new ArmorSocket(0, -1, SocketType.FUNC));
		yellow.add(2, new ArmorSocket(-1,-1, SocketType.FUNC));
		yellow.add(2, new ArmorSocket(1, -1, SocketType.FUNC));
		
		yellow.add(3, new ArmorSocket(0, 0, SocketType.FUNC));
		patterns.put(yellow.name, yellow);
	}
	
	
	
}
