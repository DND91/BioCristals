package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.client.GuiInventoryOverlay;
import hok.chompzki.biocristals.client.renderer.RendererHolderPlant;
import hok.chompzki.biocristals.croot.cristal.RendererGhost;
import hok.chompzki.biocristals.croot.cristal.SpeciallRenenderGhost;
import hok.chompzki.biocristals.croot.cristal.TileGhost;
import hok.chompzki.biocristals.entity.EntityWSB;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy { //Client sided
	
	public static int idPlantHolder = 0;
	public static int idGhost = 1;
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	
    	MinecraftForge.EVENT_BUS.register(new GuiInventoryOverlay());
    	
    	idPlantHolder = RenderingRegistry.getNextAvailableRenderId();
    	idGhost = RenderingRegistry.getNextAvailableRenderId();
    	
    	RenderingRegistry.registerBlockHandler(idPlantHolder, new RendererHolderPlant());
    	//RenderingRegistry.registerBlockHandler(idGhost, new RendererGhost());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileGhost.class, new SpeciallRenenderGhost());
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityWSB.class, new RenderSnowball(ItemRegistry.wsb));
	}
	
	@Override
	public void initSaveHandling() {
		PlayerStorage.instance(false);
	}
}
