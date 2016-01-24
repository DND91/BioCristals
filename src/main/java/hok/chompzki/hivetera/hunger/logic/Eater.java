package hok.chompzki.hivetera.hunger.logic;

import java.util.ArrayList;
import java.util.List;

public class Eater {
	
	public int x;
	public int y;
	public String channel;
	public String owner;

	public List<Path> path = new ArrayList<Path>();
	
	public void add(Path mod) {
		path.add(mod);
	}
}
