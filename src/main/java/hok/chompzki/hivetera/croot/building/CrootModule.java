package hok.chompzki.hivetera.croot.building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CrootModule {
	
	public List<CrootBlock> blocks = new ArrayList<CrootBlock>();
	ForgeDirection[] sides = {};
	
	public void sort(){
		for(int i = 0; i < blocks.size();){
			if(blocks.get(i) == null || blocks.get(i).block == null)
				blocks.remove(i);
			else
				i++;
		}
		
		//blocks.sort(new CrootComparator());
		Collections.sort(blocks, new CrootComparator());
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
			/*CrootBlock a = this.get(this.getPos(b.x, b.y, b.z));
			a.priority = b.priority;
			a.block = b.block;
			a.meta = b.meta;*/
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
			
			if(b == Blocks.bedrock)
				continue;
			
			
			
			
			if(block.block != b)
				return block;
			if(block.meta != meta)
				return block;
		}
		return null;
	}
}
