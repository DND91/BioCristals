package hok.chompzki.biocristals;

import hok.chompzki.biocristals.registrys.CommonProxy;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.biocristals.research.data.network.PlayerStorageSyncHandler;
import hok.chompzki.biocristals.research.data.network.PlayerStorageSyncMessage;
import hok.chompzki.biocristals.research.events.MessageHandlerInserCrafting;
import hok.chompzki.biocristals.research.events.MessageInsertCrafting;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(name = BioCristalsMod.MODID, modid = BioCristalsMod.MODID, version = BioCristalsMod.VERSION)
public class BioCristalsMod
{
	
	public static CreativeTabs creativeTab = new BioCristalTab();
	
    public static final String MODID = "BioCristals";
    public static final String VERSION = "1.2";
    
    public static SimpleNetworkWrapper network;
    
    @SidedProxy(clientSide = "hok.chompzki.biocristals.registrys.ClientProxy", serverSide = "hok.chompzki.biocristals.registrys.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(MODID)
    public static BioCristalsMod instance = new BioCristalsMod();
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    	PlayerStorage.instance();
	}

    @EventHandler
	public void init(FMLInitializationEvent event) {
    	proxy.init(event);
    	network = NetworkRegistry.INSTANCE.newSimpleChannel("BioC_PS");
	    network.registerMessage(PlayerStorageSyncHandler.class, PlayerStorageSyncMessage.class, 0, Side.CLIENT);
	    network.registerMessage(PlayerStorageDelissenHandler.class, PlayerStorageDelissenMessage.class, 1, Side.SERVER);
	    network.registerMessage(MessageHandlerInserCrafting.class, MessageInsertCrafting.class, 2, Side.SERVER);
	    network.registerMessage(PlayerStorageFaveHandler.class, PlayerStorageFaveMessage.class, 3, Side.SERVER);
	}

    @EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
	}
}
