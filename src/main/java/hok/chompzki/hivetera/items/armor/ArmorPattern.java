package hok.chompzki.hivetera.items.armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorPattern {
	
	public final String name;
	private HashMap<Integer, List<ArmorSocket>> sockets = new HashMap<Integer, List<ArmorSocket>>();
	
	public ArmorPattern(String name){
		this.name = name;
	}
	
	public void add(int armorType, ArmorSocket socket){
		if(!sockets.containsKey(armorType))
			sockets.put(armorType, new ArrayList<ArmorSocket>());
		List<ArmorSocket> list = sockets.get(armorType);
		list.add(socket);
	}
	
	public List<ArmorSocket> get(int armorType){
		if(!sockets.containsKey(armorType))
			sockets.put(armorType, new ArrayList<ArmorSocket>());
		return sockets.get(armorType);
	}
}
