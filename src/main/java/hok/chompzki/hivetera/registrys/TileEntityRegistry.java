package hok.chompzki.hivetera.registrys;

import hok.chompzki.hivetera.tile_enteties.TileCroot;
import hok.chompzki.hivetera.tile_enteties.TileCrootBreeder;
import hok.chompzki.hivetera.tile_enteties.TileCrootCore;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneConsumer;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneMember;
import hok.chompzki.hivetera.tile_enteties.TileExtractor;
import hok.chompzki.hivetera.tile_enteties.TileGhost;
import hok.chompzki.hivetera.tile_enteties.TileHolderPlant;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import hok.chompzki.hivetera.tile_enteties.TilePlatformer;
import hok.chompzki.hivetera.tile_enteties.TilePrimogenitus;
import hok.chompzki.hivetera.tile_enteties.TileReagentPurifier;
import hok.chompzki.hivetera.tile_enteties.TileReplacer;
import hok.chompzki.hivetera.tile_enteties.TileSacrificePit;
import hok.chompzki.hivetera.tile_enteties.TileStructer;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
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
		
		GameRegistry.registerTileEntity(TileGhost.class, "biocristals_tile_ghost");
		GameRegistry.registerTileEntity(TileStructer.class, "biocristals_tile_structer");
		GameRegistry.registerTileEntity(TilePlatformer.class, "biocristals_tile_platformer");
		GameRegistry.registerTileEntity(TileNest.class, "biocristals_tile_nest");
		GameRegistry.registerTileEntity(TileCrootBreeder.class, "biocristals_tile_croot_breeder");
		GameRegistry.registerTileEntity(TileReplacer.class, "biocristals_tile_replacer");
		GameRegistry.registerTileEntity(TileTokenAssembler.class, "biocristals_tile_token_assembler");
		GameRegistry.registerTileEntity(TileSacrificePit.class, "biocristals_tile_sacrifice_pit");
	}
	
}
