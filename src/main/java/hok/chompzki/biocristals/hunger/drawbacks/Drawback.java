package hok.chompzki.biocristals.hunger.drawbacks;

import net.minecraft.world.World;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;

public abstract class Drawback {
	
	public abstract boolean canAct(ResourcePackage pack, World world, int x, int y, int z);
	
	public abstract void act(ResourcePackage pack, World world, int x, int y, int z);
	
}
