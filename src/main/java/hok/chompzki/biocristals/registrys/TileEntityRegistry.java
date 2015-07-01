package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.tile_enteties.TileReagentPurifier;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileReagentPurifier.class, "biocristals_tile_reagentpurifier");
	}
	
}
