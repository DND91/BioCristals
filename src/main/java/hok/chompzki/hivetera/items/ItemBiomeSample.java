package hok.chompzki.hivetera.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.data.BiomeKittehData;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public class ItemBiomeSample extends Item{
	
	public static final String NAME = "itemBiomeSample";
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemBiomeSample(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.sampleTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		setHasSubtypes(true);
		setMaxStackSize(1);
		
		GameRegistry.registerItem(this, NAME, HiveteraMod.MODID);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
		icons = new IIcon[BiomeRegistry.idToBiome.size()];
        
        for(int i = 0; i < icons.length; i++){
        	icons[i] = iconReg.registerIcon(HiveteraMod.MODID + ":" + NAME + "_" + BiomeRegistry.idToBiome.get(i));
        }
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		return this.icons[stack.getItemDamage()];
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		return this.icons[stack.getItemDamage()];
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		return this.icons[stack.getItemDamage()];
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {	
		int i = 0;
		for(IIcon ice : this.icons){
			ItemStack stack = new ItemStack(item, 1, i);
			this.onCreated(stack, null, null);
			list.add(stack);
			i++;
		}
    }
	
	/*
	public String getUnlocalizedName(ItemStack stack)
    {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, icons.length);
        return super.getUnlocalizedName() + "." + BiomeRegistry.idToBiome.get(stack.getItemDamage());
    }
	*/
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		BiomeKittehData data = BiomeRegistry.kittehsBiomes.get(BiomeRegistry.idToBiome.get(stack.getItemDamage()));
		
		list.add( "Biome: " + BiomeRegistry.idToBiome.get(stack.getItemDamage()).name());
		if(!advancedTooltip && !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))){
			list.add("...");
			return;
		}
		
		list.add("Chance% Item");
		Double oldV = 0.0D;
		for(Entry<Double, ItemStack> entry2 : data.entrySet()){
			Double v = entry2.getKey();
			ItemStack s = entry2.getValue();
			
			String row = Math.round(((v-oldV) / data.getTotal()) * 100.D) + "% " + (s == null ? "NOTHING" : "" + s.getDisplayName() + "");
			
			oldV = v;
			list.add(row);
		}
		
		
	}
}
