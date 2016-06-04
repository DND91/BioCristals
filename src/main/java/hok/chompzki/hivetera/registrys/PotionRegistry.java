package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.potion.PotionHungersWrath;
import hok.chompzki.hivetera.potion.PotionLeech;
import hok.chompzki.hivetera.potion.PotionSwarm;
import net.minecraft.potion.Potion;

public class PotionRegistry {
	
	public static Potion leech = null;
	public static Potion swarm = null;
	public static Potion hungersWrath = null;
	
	public void register() {
		
		leech = new PotionLeech();
		swarm = new PotionSwarm();
		hungersWrath = new PotionHungersWrath();
		
	}

}
