package hok.chompzki.hivetera.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.tile_enteties.TileCrootOneMember;

public class BlockCrootStreamStem extends BlockCroot {
	
	public static final String[] subtypes = new String[] {"normal"};
    private static final IIcon[] icons = new IIcon[subtypes.length];
    public static final String NAME = "crootStreamStem";
    
    public BlockCrootStreamStem()
    {
    	super();
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
        this.setCreativeTab(HiveteraMod.creativeTab);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	meta &= 7;
        return icons[MathHelper.clamp_int(meta, 0, subtypes.length)];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < subtypes.length; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    public int damageDropped(int meta)
    {
        return MathHelper.clamp_int(meta & 7, 0, 5);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        for (int i = 0; i < icons.length; ++i)
        {
        	icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_" + subtypes[i]);
        }
    }
    
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Items.stick;
    }
    
    public int quantityDropped(Random rand)
    {
        return rand.nextInt(3) + 1;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileCrootOneMember(0);
	}

}
