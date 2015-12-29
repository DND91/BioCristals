package hok.chompzki.biocristals.items.token;

import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;
import hok.chompzki.biocristals.research.data.DataHelper;
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
import net.minecraft.world.World;

public abstract class ItemToken extends Item implements IToken {
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	private String name;
	
	public ItemToken(String name){
		this.name = name;
		setUnlocalizedName(BioCristalsMod.MODID + "_" + name);
		setCreativeTab(BioCristalsMod.tokenTab);
		setTextureName(BioCristalsMod.MODID + ":" + name);
		setHasSubtypes(true);
		
		GameRegistry.registerItem(this, name, BioCristalsMod.MODID);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
		this.icons = new IIcon[2];
		this.itemIcon = iconReg.registerIcon(this.getIconString());
		this.icons[0] = iconReg.registerIcon(this.getIconString() + "_base");
		this.icons[1] = iconReg.registerIcon(this.getIconString() + "_pattern");
    }
	
	private int getColorFromDye(String name){
		int i = 0;
		for(String dye : ItemDye.field_150923_a){
			if(dye.equals(name))
				return ItemDye.field_150922_c[i];
			i++;
		}
		return 16777215;
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
       	if(pass == 0){
       		return super.getColorFromItemStack(stack, pass);
       	} else if(stack.getItemDamage() != 0 && pass == 1){
       		String channel = stack.stackTagCompound.getString("CHANNEL");
       		return getColorFromDye(channel);
       	}
       	return 16777215;
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(stack.getItemDamage() != 0 && renderPass == 1)
        	return this.icons[1];
		return this.icons[0];
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		if(stack.getItemDamage() != 0 && pass == 1)
        	return this.icons[1];
		return this.icons[0];
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		return this.icons[0];
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		ItemStack s = new ItemStack(item, 1, 0);
		this.onCreated(s, null, null);
		list.add(s);
		
		int i = 0;
		for(String dye : ItemDye.field_150923_a){
			ItemStack stack = new ItemStack(item, 1, i+1);
			this.onCreated(stack, null, null);
			stack.stackTagCompound.setString("CHANNEL", ItemDye.field_150923_a[i]);
			list.add(stack);
			i++;
		}
    }
	
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

	@Override
	public String getChannel(ItemStack stack) {
		return stack.stackTagCompound.getString("CHANNEL");
	}
	
	@Override
	public String getOwner(ItemStack stack) {
		return DataHelper.getOwner(stack);
	}
	
}
