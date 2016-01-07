package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.items.armor.ArmorPattern;
import hok.chompzki.biocristals.items.armor.ArmorSocket;
import hok.chompzki.biocristals.items.armor.SocketType;

import java.util.HashMap;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ArmorPatternRegistry {
	
	public static String[] pattern_names = new String[] {"red", "green", "blue", "yellow"};//, "gold", "silver", "crystal", "ruby", "sapphire", "emerald"};
	
	public static HashMap<String, ArmorPattern> patterns = new HashMap<String, ArmorPattern>();

	public void init(FMLInitializationEvent event) {
		ArmorPattern red = new ArmorPattern(pattern_names[0]);
		red.add(0, new ArmorSocket(0, 0, SocketType.OFFENSIVE));
		red.add(1, new ArmorSocket(0, 0, SocketType.EATER));
		red.add(1, new ArmorSocket(0, -1, SocketType.OFFENSIVE));
		red.add(2, new ArmorSocket(0, 0, SocketType.OFFENSIVE));
		red.add(3, new ArmorSocket(0, 0, SocketType.OFFENSIVE));
		patterns.put(red.name, red);
	}
	
	
	
}
