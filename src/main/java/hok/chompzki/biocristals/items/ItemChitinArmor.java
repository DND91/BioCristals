package hok.chompzki.biocristals.items;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemChitinArmor extends ItemArmor {
	private String txt = "";
	public ItemChitinArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
	    super(material, 0, type);
	    this.txt = textureName;
	    this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(BioCristalsMod.MODID + ":" + unlocalizedName);
	    this.setCreativeTab(BioCristalsMod.creativeTab);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return BioCristalsMod.MODID + ":textures/armor/" + this.txt + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
}
