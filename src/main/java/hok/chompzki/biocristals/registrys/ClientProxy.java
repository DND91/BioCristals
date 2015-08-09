package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.client.renderer.RendererHolderPlant;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy { //Client sided
	
	public static int idPlantHolder = 0;
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	
    	MinecraftForge.EVENT_BUS.register(new GuiInventoryOverlay());
    	
    	idPlantHolder = RenderingRegistry.getNextAvailableRenderId();
    	
    	RenderingRegistry.registerBlockHandler(idPlantHolder, new RendererHolderPlant());
	}
	
	@Override
	public void initSaveHandling() {
		PlayerStorage.instance(false);
	}
}
