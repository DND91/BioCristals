package hok.chompzki.biocristals.croot.dna.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BranchMemory {
	
	public class Branching{
		public int x = 0;
		public int z = 0;
		public int distance = 0;
		public int height = 0;
		
		public ArrayList<Branching> branches = new ArrayList<Branching>();
		
		public Branching(int x, int y, int distance, int height){
			this.x = x;
			this.z = y;
			this.distance = distance;
			this.height = height;
		}
		
		public int getLevel(int range, double w, double s){
			return (int) Math.ceil(Math.sqrt(range)*(32.0D/64.0D-w)/(s));
		}
		
		public void act(World world, int x2, int y2, int z2, TreeMemory memory, int md, double w, double s) {
			int m = md;
			
			if(1 <= branches.size()){
				m = branches.get(0).distance;
			}
			
			
			for(int i = 0; i <= m - distance; i++){
				int y = y2 + getLevel((i+distance)*height, w, s);
				int x = x2 + i * this.x;
				int z = z2 + i * this.z;
				
				world.setBlock(x, y, z, Blocks.gold_block);
				
			}
			
			//Think of branching!
			for(Branching branch : branches){
				int y = y2;// + getLevel(branch.distance*height, w, s);
				int x = x2 + (branch.distance - distance) * this.x;
				int z = z2 + (branch.distance - distance) * this.z;
				
				branch.act(world, x, y, z, memory, md, w, s);
			}
		}
	}
	
	public final int currentHeight;
	
	//8 sides... -1,-1 -> 1,1
	public ArrayList<Branching> branch_bases = new ArrayList<Branching>();
	
	public BranchMemory(int currentHeight, int trunkRadius, int branches, int distance, Random random) {
		this.currentHeight = currentHeight;
		
		for(int i = 0; i < branches; i++){
			int x = 1 - random.nextInt(3);
			int y = 1 - random.nextInt(3);
			if(x == 0 && y == 0)
				continue;
			if(contains(x, y))
				continue;
			
			Branching branch = new Branching(x, y, 1, currentHeight);
			branch_bases.add(branch);
			//Add branches to branch!!!
			int min = Math.min(20, trunkRadius);
			this.branching(branch, 3, min + random.nextInt(3), distance, random);
		}
		
		
	}
	
	public void branching(Branching branch, int n, int d, int md, Random random){
		if(md <= d)
			return;
		
		for(int i = 0; i < n; i++){
			int x = 1 - random.nextInt(3);
			int z = 1 - random.nextInt(3);
			if(x == 0 && z == 0)
				continue;
			
			if(contains(x, z))
				continue;
			
			if((x != 0 && x == branch.x) || (z != 0 && z == branch.z)){
				Branching bra = new Branching(x, z, d, branch.height);
				branch.branches.add(bra);
				//Add branches to branch!!!
				int min = 2;
				int r = 10; //Math.abs((md-min-d)/4);
				r = r <= 0 ? 1 : r;
				this.branching(bra, n, d + min + random.nextInt(r), md, random);
			}
		}
	}
	
	private boolean contains(int x, int y){
		for(Branching pair : branch_bases){
			if(x == pair.x && y == pair.z)
				return true;
		}
		return false;
	}

}
