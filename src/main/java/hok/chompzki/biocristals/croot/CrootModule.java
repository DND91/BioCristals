package hok.chompzki.biocristals.croot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CrootModule {
	
	public class CrootComparator implements Comparator<CrootBlock> {
		
		public CrootComparator(){
			
		}
		
	    @Override
	    public int compare(CrootBlock a, CrootBlock b) {
	    	if(a == b)
	    		return 0;
	    	if(a.priority < b.priority)
	    		return -1;
	    	if(b.priority < a.priority)
	    		return 1;
	    	
	    	int ad = a.x * a.x + a.y * a.y + a.z * a.z;
	    	int bd = b.x * b.x + b.y * b.y + b.z * b.z;
	    	
	    	if(ad < bd)
	    		return -1;
	    	if(bd < ad)
	    		return 1;
	    	return 0;
	    }
	}
	
	List<CrootBlock> blocks = new ArrayList<CrootBlock>();
	ForgeDirection[] sides = {};
	
	public void sort(){
		for(int i = 0; i < blocks.size();){
			if(blocks.get(i) == null || blocks.get(i).block == null)
				blocks.remove(i);
			else
				i++;
		}
		blocks.sort(new CrootComparator());
	}
	
	public boolean contains(int x, int y, int z){
		for(int i = 0; i < blocks.size(); i++){
			CrootBlock block = blocks.get(i);
			if(block.x == x && block.y == y && block.z == z){
				return true;
			}
		}
		return false;
	}
	
	public int getPos(int x, int y, int z){
		for(int i = 0; i < blocks.size(); i++){
			CrootBlock block = blocks.get(i);
			if(block.x == x && block.y == y && block.z == z){
				blocks.remove(i);
				return i;
			}
		}
		return -1;
	}
	
	public CrootBlock get(int i){
		return blocks.get(i);
	}
	
	
	
	public void add(CrootBlock b){
		if(this.contains(b.x, b.y, b.z)){
			CrootBlock a = this.get(this.getPos(b.x, b.y, b.z));
			a.priority = b.priority;
			a.block = b.block;
			a.meta = b.meta;
		}else{
			blocks.add(b);
		}
		sort();
	}
	
	public void remove(int x, int y, int z){
		for(int i = 0; i < blocks.size(); i++){
			CrootBlock block = blocks.get(i);
			if(block.x == x && block.y == y && block.z == z){
				blocks.remove(i);
				break;
			}
		}
		sort();
	}
	
	public ForgeDirection[] buildSides(){
		return sides;
	}
	
	public boolean controll(World world, int bx, int by, int bz){
		for(CrootBlock block : blocks){
			int x = bx + block.x;
			int y = by + block.y;
			int z = bz + block.z;
			
			Block b = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			if(block.block != b)
				return false;
			if(block.meta != meta)
				return false;
		}
		return true;
	}
	
	public CrootBlock getNext(World world, int bx, int by, int bz){
		for(CrootBlock block : blocks){
			int x = bx + block.x;
			int y = by + block.y;
			int z = bz + block.z;
			
			Block b = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			if(block.block != b)
				return block;
			if(block.meta != meta)
				return block;
		}
		return null;
	}
}
