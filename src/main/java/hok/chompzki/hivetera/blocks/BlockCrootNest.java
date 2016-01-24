package hok.chompzki.hivetera.blocks;

import java.util.Random;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockCrootNest extends Block {
	
	public static final String NAME = "blockCrootNest";
	//public static final Material corePlasma = (new MaterialLiquid(MapColor.magentaColor));
    
    public BlockCrootNest()
    {
    	super(Material.rock);
        setBlockName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setBlockTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setLightOpacity(25);
		this.setResistance(2000.0f);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeGravel);
        this.disableStats();
        this.setTickRandomly(true);
    }
	
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(BlockRegistry.crootCore);
    }
}
