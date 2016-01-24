package hok.chompzki.hivetera;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hok.chompzki.hivetera.blocks.BlockCore;
import hok.chompzki.hivetera.blocks.BlockCroot;
import hok.chompzki.hivetera.croot.building.CrootBlock;
import hok.chompzki.hivetera.croot.building.CrootModule;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.tile_enteties.TileCore;
import hok.chompzki.hivetera.tile_enteties.TileCroot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CrootHelper {
	
	public static final Block[] startOn = {Blocks.dirt, Blocks.grass, BlockRegistry.crootRoots};
	
	public static boolean startsOn(Block block){
		for(Block b : startOn)
			if(b == block)
				return true;
		return false;
	}
	
	public static boolean hasZoneOwner(World world, int x, int y, int z, int radius){
		
		int radiusSquared = radius * radius;
		
		for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	for(int k = radius * -1; k <= radius; ++k){
            		if(x == x + i && y == y + j && z == z + k)
        				continue;
            		
	            	int distance = ((i * i) + (j * j) + (k * k));
	        		if(distance <= radiusSquared && world.blockExists(x + i, y+ j, z + k)){
	        			Block block = world.getBlock(x + i, y + j, z + k);
	        			if(block == BlockRegistry.crootSapling || block instanceof BlockCore)
	        				return true;
	        			
	        			if(block instanceof BlockCroot){
	        				TileCroot tile = (TileCroot) world.getTileEntity(x + i, y + j, z + k);
	        				if(tile.genCore() != null)
	        					return true;
	        			}
	        		}
            	}
            }
        }
        return false;
	}
	
	public static boolean hasZoneOwner(TileCore core, World world, int x, int y, int z, int radius){
		int radiusSquared = radius * radius;
		
		for(int i = radius * -1; i <= radius; ++i)
        {
            for(int j = radius * -1; j <= radius; ++j)
            {
            	for(int k = radius * -1; k <= radius; ++k){
	            	int distance = ((i * i) + (j * j) + (k * k));
	        		if(distance <= radiusSquared && world.blockExists(x + i, y+ j, z + k)){
	        			if(x == x + i && y == y + j && z == z + k)
	        				continue;
	        			
	        			Block block = world.getBlock(x + i, y + j, z + k);
	        			if(block == BlockRegistry.crootSapling || block instanceof BlockCore)
	        				return true;
	        			
	        			if(core != null && block instanceof BlockCroot){
	        				TileCroot tile = (TileCroot) world.getTileEntity(x + i, y + j, z + k);
	        				if(tile != null && tile.getForm() != null && tile.getForm().coreCoord != null&& !tile.getForm().coreCoord.equals(core.getCoords()))
	        					return core.getForm().getPowerTotal() < tile.getForm().getPowerTotal(); //TODO: FIX CONFLICT BETWEEN CORES!
	        			}
	        		}
            	}
            }
        }
        return false;
	}
	
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
            	
        		if(outer != Blocks.air && radiusInsideSquared <= distance && distance <= radiusOutsideSquared){
        			CrootBlock b = new CrootBlock();
        			b.priority = prio_outer;
        			b.x = x + i;
        			b.y = y;
        			b.z = z + j;
        			b.block = outer;
        			b.meta = meta_outer;
        			module.add(b);
        		}else if(inner != Blocks.air && distance <= radiusInsideSquared)
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
	
	public static Set<WorldCoord> getEdges(World world, WorldCoord node){
		Set<WorldCoord> edges = new HashSet<WorldCoord>();
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir == ForgeDirection.UNKNOWN)
				continue;
			WorldCoord coord = new WorldCoord(node.x + dir.offsetX, node.y + dir.offsetY, node.z + dir.offsetZ, world.provider.dimensionId);
			edges.add(coord);
		}
		return edges;
	}
	
	public static Set<WorldCoord> safe(World world, Set<WorldCoord> unkowns, WorldCoord startPoint){
		Set<WorldCoord> safe = new HashSet<WorldCoord>(); //Identiferade unkowns + kringliggande punkter
		Queue<WorldCoord> openQueue = new ArrayDeque<WorldCoord>(); //Identiferade Unkowns
		
		openQueue.add(startPoint);
		
		while(!openQueue.isEmpty()){
			WorldCoord current = openQueue.poll();
			if(!current.isValid(world))
				continue;
			Set<WorldCoord> edges = getEdges(world, current);
			for(WorldCoord edge : edges){
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
	
	public static Set<WorldCoord> unsafe(World world, Set<WorldCoord> unkowns, WorldCoord startPoint){
		System.out.println("UNKOWNS: " + unkowns.size());
		Set<WorldCoord> safe = safe(world, unkowns, startPoint);
		System.out.println("SAFE: " + safe.size());
		Set<WorldCoord> unsafe = new HashSet<WorldCoord>();
		for(WorldCoord coord : unkowns){
			if(!safe.contains(coord))
				unsafe.add(coord);
		}
		System.out.println("UNSAFE: " + unsafe.size());
		return unsafe;
	}
	
	public static Set<WorldCoord> unregistered(World world, WorldCoord startPoint){
		Set<WorldCoord> unregistered = new HashSet<WorldCoord>(); //Identiferade unowned croots
		Queue<WorldCoord> openQueue = new ArrayDeque<WorldCoord>(); //Identiferade potentiella croots
		
		openQueue.add(startPoint);
		unregistered.add(startPoint);
		
		while(!openQueue.isEmpty()){
			WorldCoord current = openQueue.poll();
			Set<WorldCoord> edges = getEdges(world, current);
			for(WorldCoord edge : edges){
				if(!edge.isValid(world))
					continue;
				if(edge.hasCore(world))
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
