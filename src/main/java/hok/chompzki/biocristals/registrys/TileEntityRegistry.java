package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.croot.TileCroot;
import hok.chompzki.biocristals.croot.cristal.TileGhost;
import hok.chompzki.biocristals.croot.cristal.TilePlatformer;
import hok.chompzki.biocristals.croot.cristal.TileStructer;
import hok.chompzki.biocristals.tile_enteties.TileCrootCore;
import hok.chompzki.biocristals.tile_enteties.TileCrootHollow;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneConsumer;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneMember;
import hok.chompzki.biocristals.tile_enteties.TileExperiment;
import hok.chompzki.biocristals.tile_enteties.TileExtractor;
import hok.chompzki.biocristals.tile_enteties.TileHolderPlant;
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
		
		GameRegistry.registerTileEntity(TileCrootHollow.class, "biocristals_tile_croot_hollow");
		GameRegistry.registerTileEntity(TileExtractor.class, "biocristals_tile_exctractor");
		GameRegistry.registerTileEntity(TileHolderPlant.class, "biocristals_tile_place_holder");
		
		GameRegistry.registerTileEntity(TileExperiment.class, "biocristals_tile_experiment");
		GameRegistry.registerTileEntity(TileGhost.class, "biocristals_tile_ghost");
		GameRegistry.registerTileEntity(TileStructer.class, "biocristals_tile_structer");
		GameRegistry.registerTileEntity(TilePlatformer.class, "biocristals_tile_platformer");
	}
	
}
