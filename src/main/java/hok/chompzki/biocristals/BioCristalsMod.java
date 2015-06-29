package hok.chompzki.biocristals;

import hok.chompzki.biocristals.registrys.CommonProxy;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = BioCristalsMod.MODID, modid = BioCristalsMod.MODID, version = BioCristalsMod.VERSION)
public class BioCristalsMod
{
	
    public static final String MODID = "BioCristals";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide = "hok.chompzki.biocristals.registrys.ClientProxy", serverSide = "hok.chompzki.biocristals.registrys.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
	}

    @EventHandler
	public void init(FMLInitializationEvent event) {
    	proxy.init(event);
	}

    @EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
	}
}
