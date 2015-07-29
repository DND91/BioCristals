package hok.chompzki.biocristals.croot_old;

import hok.chompzki.biocristals.croot.CrootHelper;
import hok.chompzki.biocristals.registrys.BlockRegistry;

import java.util.HashMap;

public class CrootRegistry {
	
	private static HashMap<String, CrootModule> modules = new HashMap<String, CrootModule>();
	
	public static void register(String name, CrootModule mod){
		modules.put(name, mod);
	}
	
	public static CrootModule get(String name){
		return modules.get(name);
	}
	
	public void register(){
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
}
