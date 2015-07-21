package hok.chompzki.biocristals.croot;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hok.chompzki.biocristals.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CrootHelper {
	
	public static void spawnCircle(World world, int x, int y, int z, int radius, Block outer, Block inner){
		int radiusOutsideSquared = radius * radius;
		int radiusInsideSquared = (radius-1) * (radius-1);
		
        for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	int distance = ((i * i) + (j * j));
            	
        		if(radiusInsideSquared <= distance && distance <= radiusOutsideSquared){
        			world.setBlock(x + i, y, z + j, outer);
        		}else if(distance <= radiusInsideSquared)
                {
                	world.setBlock(x + i, y, z + j, inner);
                }
            }
        }
	}
	
	public static void spawnSphere(World world, int x, int y, int z, int radius, Block outer, Block inner){
		int radiusOutsideSquared = radius * radius;
		int radiusInsideSquared = (radius-1) * (radius-1);
		
        for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	for(int k = radius * -1; k <= radius; ++k){
	            	int distance = ((i * i) + (j * j) + (k * k));
	            	
	        		if(radiusInsideSquared <= distance && distance <= radiusOutsideSquared){
	        			world.setBlock(x + i, y + k, z + j, outer);
	        		}else if(distance <= radiusInsideSquared)
	                {
	                	world.setBlock(x + i, y + k, z + j, inner);
	                }
            	}
            }
        }
	}
	
	public static void spawnCylinder(World world, int x, int y, int z, int radius, int heigth, Block outer, Block inner){
		
        for(int i = 0; i <= heigth; ++i)
        {
        	spawnCircle(world, x, y + i, z, radius, outer, inner);
        }
	}
	
	public static void spawnBlock(World world, CrootBlock block, int x, int y, int z){
		world.setBlock(block.x + x, block.y + y, block.z + z, block.block, block.meta, 3);
	}
	
	public static TileEntity getTileEntity(World world, CrootBlock block, int x, int y, int z){
		return world.getTileEntity(block.x + x, block.y + y, block.z + z);
	}
	
	public static void addCircle(CrootModule module, int x, int y, int z, int radius, Block outer, int meta_outer, int prio_outer, Block inner, int meta_inner, int prio_inner){
		int radiusOutsideSquared = radius * radius;
		int radiusInsideSquared = (radius-1) * (radius-1);
		
        for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	int distance = ((i * i) + (j * j));
            	
        		if(radiusInsideSquared <= distance && distance <= radiusOutsideSquared){
        			CrootBlock b = new CrootBlock();
        			b.priority = prio_outer;
        			b.x = x + i;
        			b.y = y;
        			b.z = z + j;
        			b.block = outer;
        			b.meta = meta_outer;
        			module.add(b);
        		}else if(distance <= radiusInsideSquared)
                {
        			CrootBlock b = new CrootBlock();
        			b.priority = prio_inner;
        			b.x = x + i;
        			b.y = y;
        			b.z = z + j;
        			b.block = inner;
        			b.meta = meta_inner;
        			module.add(b);
                }
            }
        }
	}
	
	public static void addBlock(CrootModule module, int prio, int x, int y, int z, Block block, int meta){
		CrootBlock b = new CrootBlock();
		b.priority = prio;
		b.x = x;
		b.y = y;
		b.z = z;
		b.block = block;
		b.meta = meta;
		module.add(b);
	}
	
	public static void addLine(CrootModule module, int height, int prio, int x, int y, int z, Block block, int meta){
		for(int i = y; i < (y+height); i++){
			addBlock(module, prio, x, i, z, block, meta);
		}
	}
	
	public static void addCylinder(CrootModule module, int heigth, int x, int y, int z, int radius, Block outer, int meta_outer, int prio_outer, Block inner, int meta_inner, int prio_inner){
		
        for(int i = 0; i <= heigth; ++i)
        {
        	addCircle(module, x, y + i, z, radius, outer, meta_outer, prio_outer, inner, meta_inner, prio_inner);
        }
	}
	
	public static void addSphere(CrootModule module, int x, int y, int z, int radius, Block outer, int meta_outer, int prio_outer, Block inner, int meta_inner, int prio_inner){
		int radiusOutsideSquared = radius * radius;
		int radiusInsideSquared = (radius-1) * (radius-1);
		
        for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	for(int k = radius * -1; k <= radius; ++k){
	            	int distance = ((i * i) + (j * j) + (k * k));
	            	
	            	if(radiusInsideSquared <= distance && distance <= radiusOutsideSquared){
	        			CrootBlock b = new CrootBlock();
	        			b.priority = prio_outer;
	        			b.x = x + i;
	        			b.y = y + k;
	        			b.z = z + j;
	        			b.block = outer;
	        			b.meta = meta_outer;
	        			module.add(b);
	        		}else if(distance <= radiusInsideSquared)
	                {
	        			CrootBlock b = new CrootBlock();
	        			b.priority = prio_inner;
	        			b.x = x + i;
	        			b.y = y + k;
	        			b.z = z + j;
	        			b.block = inner;
	        			b.meta = meta_inner;
	        			module.add(b);
	                }
            	}
            }
        }
	}
	
	
	public static boolean connection(TileEntity a, TileEntity b){
		if(a == b)
			return true;
		if(a == null || b == null)
			return false;
		if(a.getWorldObj().provider.dimensionId != b.getWorldObj().provider.dimensionId)
			return false;
		World world = a.getWorldObj();
		AStar star = new AStar(world);
		
		return 0 < star.astar(a, b).size();
	}
	
	public static Set<RegisteredCoord> getEdges(RegisteredCoord node){
		Set<RegisteredCoord> edges = new HashSet<RegisteredCoord>();
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir == ForgeDirection.UNKNOWN)
				continue;
			RegisteredCoord coord = new RegisteredCoord(node.getWorld(), node.x + dir.offsetX, node.y + dir.offsetY, node.z + dir.offsetZ);
			edges.add(coord);
		}
		return edges;
	}
	
	public static Set<RegisteredCoord> safe(Set<RegisteredCoord> unkowns, RegisteredCoord startPoint){
		Set<RegisteredCoord> safe = new HashSet<RegisteredCoord>(); //Identiferade unkowns + kringliggande punkter
		Queue<RegisteredCoord> openQueue = new ArrayDeque<RegisteredCoord>(); //Identiferade Unkowns
		
		openQueue.add(startPoint);
		
		while(!openQueue.isEmpty()){
			RegisteredCoord current = openQueue.poll();
			Set<RegisteredCoord> edges = getEdges(current);
			for(RegisteredCoord edge : edges){
				if(unkowns.contains(edge) && !safe.contains(edge)){
					openQueue.add(edge);
					safe.add(edge);
				}else if(!safe.contains(edge)){
					safe.add(edge);
				}
			}
		}
		
		return safe;
	}
	
	public static Set<RegisteredCoord> unsafe(Set<RegisteredCoord> unkowns, RegisteredCoord startPoint){
		System.out.println("UNKOWNS: " + unkowns.size());
		Set<RegisteredCoord> safe = safe(unkowns, startPoint);
		System.out.println("SAFE: " + safe.size());
		Set<RegisteredCoord> unsafe = new HashSet<RegisteredCoord>();
		for(RegisteredCoord coord : unkowns){
			if(!safe.contains(coord))
				unsafe.add(coord);
		}
		System.out.println("UNSAFE: " + unsafe.size());
		return unsafe;
	}
	
	public static Set<RegisteredCoord> unregistered(World world, RegisteredCoord startPoint){
		Set<RegisteredCoord> unregistered = new HashSet<RegisteredCoord>(); //Identiferade unowned croots
		Queue<RegisteredCoord> openQueue = new ArrayDeque<RegisteredCoord>(); //Identiferade potentiella croots
		
		openQueue.add(startPoint);
		unregistered.add(startPoint);
		
		while(!openQueue.isEmpty()){
			RegisteredCoord current = openQueue.poll();
			Set<RegisteredCoord> edges = getEdges(current);
			for(RegisteredCoord edge : edges){
				if(!edge.isValid())
					continue;
				if(edge.hasCore())
					continue;
				
				if(!unregistered.contains(edge)){
					openQueue.add(edge);
					unregistered.add(edge);
				}
			}
		}
		
		return unregistered;
	}
}
