package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.blocks.croot.TileCrootConsumer;
import hok.chompzki.biocristals.blocks.croot.TileCrootMember;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneConsumer;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneMember;
import hok.chompzki.biocristals.tile_enteties.TilePrimogenitus;
import hok.chompzki.biocristals.tile_enteties.TileReagentPurifier;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileReagentPurifier.class, "biocristals_tile_reagentpurifier");
		GameRegistry.registerTileEntity(TilePrimogenitus.class, "biocristals_tile_primogenitus");
		GameRegistry.registerTileEntity(TileCrootCore.class, "biocristals_tile_crootcore");
		
		GameRegistry.registerTileEntity(TileCrootOneMember.class, "biocristals_tile_croot_one_member");
		
		GameRegistry.registerTileEntity(TileCrootOneConsumer.class, "biocristals_tile_croot_one_consumer");
	}
	
}
