package hok.chompzki.hivetera;

import hok.chompzki.hivetera.client.gui.GuiInventoryOverlay;
import hok.chompzki.hivetera.client.renderer.RendererGhost;
import hok.chompzki.hivetera.client.renderer.SpeciallRenenderGhost;
import hok.chompzki.hivetera.entity.EntityFruitSpider;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.tile_enteties.TileGhost;
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
    	RenderingRegistry.registerEntityRenderingHandler(EntityFruitSpider.class, new RenderSnowball(ItemRegistry.fruitSpider));
	}
	
	@Override
	public void initSaveHandling() {
		PlayerResearchStorage.instance(true);
		PlayerHungerStorage.instance(true);
	}
}
