package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.blocks.BlockCrootSapling;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemCrootWithMetadata extends ItemBlockWithMetadata {

	public ItemCrootWithMetadata(Block block) {
		super(block, block);
		
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "." + BlockCrootSapling.subtypes[stack.getItemDamage()];
	}

}
