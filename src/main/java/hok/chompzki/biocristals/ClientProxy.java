package hok.chompzki.biocristals;

import hok.chompzki.biocristals.client.gui.GuiInventoryOverlay;
import hok.chompzki.biocristals.client.renderer.RendererGhost;
import hok.chompzki.biocristals.client.renderer.SpeciallRenenderGhost;
import hok.chompzki.biocristals.entity.EntityWSB;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.research.data.PlayerStorage;
import hok.chompzki.biocristals.tile_enteties.TileGhost;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public static int idGhost = 0;
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	
    	MinecraftForge.EVENT_BUS.register(new GuiInventoryOverlay());
    	
    	idGhost = RenderingRegistry.getNextAvailableRenderId();
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileGhost.class, new SpeciallRenenderGhost());
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityWSB.class, new RenderSnowball(ItemRegistry.wsb));
	}
	
	@Override
	public void initSaveHandling() {
		PlayerStorage.instance(false);
	}
}
