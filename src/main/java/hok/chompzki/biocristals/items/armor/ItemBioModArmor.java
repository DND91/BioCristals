package hok.chompzki.biocristals.items.armor;

import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.NBTHelper;
import hok.chompzki.biocristals.registrys.ArmorPatternRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBioModArmor extends ItemArmor {
	private String txt = "";
	public ItemBioModArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
	    super(material, 0, type);
	    this.txt = textureName;
	    this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(BioCristalsMod.MODID + ":" + unlocalizedName);
	    this.setCreativeTab(BioCristalsMod.creativeTab);
	    this.setHasSubtypes(true);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return BioCristalsMod.MODID + ":textures/armor/" + this.txt + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		return super.onItemRightClick(stack, world, player);
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		for(String pattern : ArmorPatternRegistry.pattern_names){
			ItemStack s = new ItemStack(item, 1, 0);
			NBTHelper.init(s, "PATTERN", pattern);
			this.onCreated(s, null, null);
			list.add(s);
		}
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		list.add("Pattern: " + I18n.format("container."+NBTHelper.get(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]), new Object[0]));
	}
}
