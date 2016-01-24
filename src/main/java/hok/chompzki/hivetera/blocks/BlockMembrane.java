package hok.chompzki.hivetera.blocks;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneMember;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMembrane extends BlockCroot {
	
	public static final String NAME = "blockMembrane";
	//public static final Material corePlasma = (new MaterialLiquid(MapColor.magentaColor));
    
    public BlockMembrane()
    {
    	super(Material.clay);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setLightOpacity(25);
		this.setResistance(2000.0f);
        this.setHardness(5.0F);
        this.setStepSound(soundTypeGravel);
        this.disableStats();
        this.setTickRandomly(true);
    }
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
    		if(world.isAirBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ)){
    			world.setBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, BlockRegistry.blood, 0, 3);
    			world.markBlockForUpdate(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
    		}
    	}
    }
    
    @Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(2);
	}
}






