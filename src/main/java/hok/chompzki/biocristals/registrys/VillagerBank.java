package hok.chompzki.biocristals.registrys;

import net.minecraft.util.ResourceLocation;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.villagers.BasicResercher;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

public class VillagerBank {
	
	public static final int basicResercherId = 2015;
	public static final ResourceLocation basicResercherSkin = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/mobs/villagers/basicResercher.png");
	
	
	public void init(FMLInitializationEvent event) {
    	//VillagerRegistry.instance().
		VillagerRegistry.instance().registerVillagerId(basicResercherId);
		VillagerRegistry.instance().registerVillageTradeHandler(basicResercherId, new BasicResercher());
		
		if(event.getSide() == Side.CLIENT){
			VillagerRegistry.instance().registerVillagerSkin(basicResercherId, basicResercherSkin);
		}
	}
}
