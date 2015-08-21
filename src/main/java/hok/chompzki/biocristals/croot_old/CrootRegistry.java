package hok.chompzki.biocristals.croot_old;

import hok.chompzki.biocristals.blocks.BlockCrootSapling;
import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.croot.CrootTreeData;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.registrys.ConfigRegistry;

import java.util.HashMap;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class CrootRegistry {
	
	private static HashMap<String, CrootModule> modules = new HashMap<String, CrootModule>();
	
	public static HashMap<String, CrootTreeContainer> treeContainer = new HashMap<String, CrootTreeContainer>();
	
	public class CrootTreeContainer{
		
		public final String name;
		public final CrootModule sapling;
		public final CrootModule spurt;
		public final CrootModule tree;
		
		public CrootTreeContainer(String name, CrootModule sapling, CrootModule spurt, CrootModule tree){
			this.name = name;
			this.sapling = sapling;
			this.spurt = spurt;
			this.tree = tree;
		}
		
	}
	
	public static void register(String name, CrootModule mod){
		modules.put(name, mod);
	}
	
	public static CrootModule get(String name){
		return modules.get(name);
	}
	
	public void register(){
		
		for(CrootTreeData data : ConfigRegistry.treeData){
			System.out.println(" --- DATA INPUT --- ");
			data.print();
			
			CrootModule sapling = createCrootModule(data.sapling);
			CrootModule spurt = createCrootModule(data.spurt);
			CrootModule tree = createCrootModule(data.tree);
			
			treeContainer.put(data.name, new CrootTreeContainer(data.name, sapling, spurt, tree));
		}
		
		
		CrootModule module = new CrootModule();
		
		CrootHelper.addLine(module, 4, 1001, 0, 1, 0, BlockRegistry.crootTrunk, 0);
		
		CrootHelper.addLine(module, 5, 1001, 4, 0, 4, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, -4, 0, 4, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, 4, 0, -4, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, -4, 0, -4, BlockRegistry.crootTrunk, 0);
		
		CrootHelper.addLine(module, 5, 1001, 6, 0, 0, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, 0, 0, 6, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, -6, 0, 0, BlockRegistry.crootTrunk, 0);
		CrootHelper.addLine(module, 5, 1001, 0, 0, -6, BlockRegistry.crootTrunk, 0);
		
		CrootHelper.addCircle(module, 0, -1, 0, 6, BlockRegistry.crootRoots, 0, 1000, BlockRegistry.crootRoots, 0, 1000);
		CrootHelper.addCircle(module, 0, 5, 0, 6, BlockRegistry.crootTrunk, 0, 1001, BlockRegistry.crootTrunk, 0, 1001);
		
		CrootHelper.addBlock(module, 0, 0, 0, 0, BlockRegistry.crootCore, 0);
		register("core_1", module);
		
		module = new CrootModule();
		
		CrootHelper.addCylinder(module, 6, 0, 0, 0, 6, BlockRegistry.crootTrunk, 0, 1000, BlockRegistry.crootTrunk, 0, 1000);
		
		register("trunk_1", module);
		
		module = new CrootModule();
		
		CrootHelper.addCylinder(module, 5, 0, 0, 0, 6, BlockRegistry.crootLeaves, 0, 0, BlockRegistry.crootLeaves, 0, 0);
		CrootHelper.addSphere(module, 0, 6, 0, 6, BlockRegistry.crootLeaves, 0, 1000, BlockRegistry.crootLeaves, 0, 1000);
		
		register("crown_1", module);
	}

	private CrootModule createCrootModule(String[] datas) {
		CrootModule module = new CrootModule();
		int prio = 0;
		for(String data : datas){
			String[] action = data.split(" ");
			String name = action[0];
			
			if(name.equals("block")){
				addBlock(module, prio, action);
			} else if(name.equals("circle")){
				addCircle(module, prio, action);
			} else if(name.equals("full_circle")){
				addFullCircle(module, prio, action);
			} else if(name.equals("cylinder")){
				addCylinder(module, prio, action);
			} else if(name.equals("line")){
				addLine(module, prio, action);
			} else if(name.equals("remove")){
				removeBlock(module, action);
			}
			prio++;
		}
		
		
		return module;
	}

	private void addLine(CrootModule module, int prio, String[] action) {
		String[] blockName = action[5].split(":");
		Block block = GameRegistry.findBlock(blockName[0], blockName[1]);
		
		int x = Integer.parseInt(action[1]);
		int y = Integer.parseInt(action[2]);
		int z = Integer.parseInt(action[3]);
		int heigth = Integer.parseInt(action[4]);
		
		CrootHelper.addLine(module, heigth, prio, x, y, z, block, 0);
	}

	private void addBlock(CrootModule module, int prio, String[] action) {
		String[] blockName = action[4].split(":");
		Block block = GameRegistry.findBlock(blockName[0], blockName[1]);
		CrootHelper.addBlock(module, prio, Integer.parseInt(action[1]), Integer.parseInt(action[2]), Integer.parseInt(action[3]), block, 0);
	}
	
	//"cylinder 0 0 0 6 2 BioCristals:crootTrunk"
	private void addCylinder(CrootModule module, int prio, String[] action) {
		String[] blockName = action[6].split(":");
		Block outer = GameRegistry.findBlock(blockName[0], blockName[1]);
		
		int x = Integer.parseInt(action[1]);
		int y = Integer.parseInt(action[2]);
		int z = Integer.parseInt(action[3]);
		int radius = Integer.parseInt(action[4]);
		int heigth = Integer.parseInt(action[5]);
		
		CrootHelper.addCylinder(module, heigth, x, y, z, radius, outer, 0, prio, Blocks.air, 0, prio);
	}

	private void addCircle(CrootModule module, int prio, String[] action) {
		String[] blockName = action[5].split(":");
		Block outer = GameRegistry.findBlock(blockName[0], blockName[1]);
		Block inner = Blocks.air;
		if(action.length == 7){
			blockName = action[6].split(":");
			inner = GameRegistry.findBlock(blockName[0], blockName[1]);
		}
		
		int x = Integer.parseInt(action[1]);
		int y = Integer.parseInt(action[2]);
		int z = Integer.parseInt(action[3]);
		int radius = Integer.parseInt(action[4]);
		
		CrootHelper.addCircle(module, x, y, z, radius, outer, 0, prio, inner, 0, prio);
	}
	
	private void removeBlock(CrootModule module, String[] action) {
		int x = Integer.parseInt(action[1]);
		int y = Integer.parseInt(action[2]);
		int z = Integer.parseInt(action[3]);
		
		module.remove(x, y, z);
	}

	private void addFullCircle(CrootModule module, int prio, String[] action) {
		String[] blockName = action[5].split(":");
		Block outer = GameRegistry.findBlock(blockName[0], blockName[1]);
		Block inner = outer;
		if(action.length == 7){
			blockName = action[6].split(":");
			inner = GameRegistry.findBlock(blockName[0], blockName[1]);
		}
		
		int x = Integer.parseInt(action[1]);
		int y = Integer.parseInt(action[2]);
		int z = Integer.parseInt(action[3]);
		int radius = Integer.parseInt(action[4]);
		
		CrootHelper.addCircle(module, x, y, z, radius, outer, 0, prio, inner, 0, prio);
	}
}










