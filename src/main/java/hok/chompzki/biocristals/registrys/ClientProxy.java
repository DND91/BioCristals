package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy { //Client sided
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	
    	MinecraftForge.EVENT_BUS.register(new GuiInventoryOverlay());
    	
	}
	
	@Override
	public void initSaveHandling() {
		PlayerStorage.instance(false);
	}
}
