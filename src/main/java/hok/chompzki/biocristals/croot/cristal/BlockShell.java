package hok.chompzki.biocristals.croot.cristal;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.croot.BlockCroot;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneMember;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockShell extends BlockCroot {
	
	public static final String NAME = "blockShell";
	//public static final Material corePlasma = (new MaterialLiquid(MapColor.magentaColor));
    
    public BlockShell()
    {
    	super();
        setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setLightOpacity(25);
		this.setResistance(2000.0f);
        this.setHardness(5.0F);
        this.setStepSound(soundTypeGravel);
        this.disableStats();
        this.setTickRandomly(true);
    }
    
    @Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(1);
	}
}
