package hok.chompzki.hivetera.registrys;

import net.minecraft.util.ResourceLocation;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.villagers.BasicResercher;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VillagerBank {
	
	public static final int basicResercherId = 2015;
	
	@SideOnly(Side.CLIENT)
	public static ResourceLocation basicResercherSkin;
	
	
	public void init(FMLInitializationEvent event) {
    	//VillagerRegistry.instance().
		VillagerRegistry.instance().registerVillagerId(basicResercherId);
		VillagerRegistry.instance().registerVillageTradeHandler(basicResercherId, new BasicResercher());
		
		if(event.getSide() == Side.CLIENT){
			basicResercherSkin = new ResourceLocation(HiveteraMod.MODID + ":textures/client/mobs/villagers/basicResercher.png");
			VillagerRegistry.instance().registerVillagerSkin(basicResercherId, basicResercherSkin);
		}
	}
}
