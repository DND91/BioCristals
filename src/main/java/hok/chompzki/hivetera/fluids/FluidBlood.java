package hok.chompzki.hivetera.fluids;

import hok.chompzki.hivetera.HiveteraMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlood extends BlockFluidClassic {
	
	public static final String NAME = "fluidBioBlood";
	
	@SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    
    public FluidBlood(Fluid fluid, Material material) {
            super(fluid, material);
            setBlockName(HiveteraMod.MODID + "_" + NAME);
    		setCreativeTab(HiveteraMod.creativeTab);
    		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
    		this.setLightOpacity(0);
    		this.setResistance(10000.0f);
            float f = 0.5F;
            //this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            this.setHardness(0.0F);
            this.setStepSound(soundTypeGrass);
            this.disableStats();
            this.setTickRandomly(true);
    }
    
    @Override
    public IIcon getIcon(int side, int meta) {
            return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
            stillIcon = register.registerIcon(this.getTextureName()+"Still");
            flowingIcon = register.registerIcon(this.getTextureName()+"Flowing");
    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
	
}
