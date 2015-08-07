package hok.chompzki.biocristals.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.croot.BlockCroot;
import hok.chompzki.biocristals.registrys.BlockRegistry;
import hok.chompzki.biocristals.tile_enteties.TileCrootOneMember;
import hok.chompzki.biocristals.tile_enteties.TileHolderPlant;

public class BlockAttunedEarth extends BlockCroot {

	public static final String NAME = "blockAttunedEarth";
	
	public BlockAttunedEarth()
    {
    	super();
        setBlockName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setBlockTextureName(BioCristalsMod.MODID + ":" + NAME);
        this.setCreativeTab(BioCristalsMod.creativeTab);
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(0);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		
		ItemStack stack = player.getCurrentEquippedItem();
		if(stack == null || !world.isAirBlock(x, y+1, z))
			return false;
		if(!(stack.getItem() instanceof IPlantable))
			return false;
		System.out.println(player.getCurrentEquippedItem());
		IPlantable plantable = (IPlantable)stack.getItem();
		Block plant = plantable.getPlant(world, x, y+1, z);
		int meta = plantable.getPlantMetadata(world, x, y+1, z);
		if(plant != null && TileHolderPlant.canGorw(plantable)){
			world.setBlock(x, y+1, z, BlockRegistry.holderPlant, meta, 3);
			TileHolderPlant holder = (TileHolderPlant) world.getTileEntity(x, y+1, z);
			holder.setPlant(plantable, plant);
			stack.stackSize--;
			if(stack.stackSize <= 0)
				player.setCurrentItemOrArmor(0, null);
			world.markBlockForUpdate(x, y+1, z);
			holder.markDirty();
		}
		
        return true;
    }


}
