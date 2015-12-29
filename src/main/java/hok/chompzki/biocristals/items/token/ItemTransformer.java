package hok.chompzki.biocristals.items.token;

import java.util.List;
import java.util.UUID;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;
import hok.chompzki.biocristals.research.data.DataHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemTransformer extends ItemToken implements IToken {
	
	public final static String NAME = "itemTransformer";
	
	public ItemTransformer(){
		super(NAME);
	}
	
	public EnumResource getRaw(EnumResource res){
		if(res == EnumResource.BIOMASS)
			return EnumResource.RAW_FOOD;
		if(res == EnumResource.RAW_FOOD)
			return EnumResource.WASTE;
		
		return EnumResource.BIOMASS;
	}
	
	private String getByproduct(EnumResource res) {
		if(res == EnumResource.WASTE || res == EnumResource.RAW_FOOD)
			return "None";
		
		return I18n.format("container."+EnumResource.WASTE, new Object[0]);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		String channel = itemstack.hasTagCompound() ? this.getChannel(itemstack) : "NONE";
		list.add("Channel: " + I18n.format("container."+channel, new Object[0]));
		
		if(!itemstack.hasTagCompound())
			return;
		
		int product = itemstack.stackTagCompound.getInteger("PRODUCT");
		double effectivness = itemstack.stackTagCompound.getDouble("EFFECTIVNESS");
		double balance = itemstack.stackTagCompound.getDouble("BALANCE");
		
		list.add("Raw: " + I18n.format("container."+getRaw(EnumResource.values()[product]), new Object[0]));
		list.add("Effectivness: " + (effectivness * 100) + "%");
		list.add("Product: " + I18n.format("container."+EnumResource.values()[product], new Object[0]));
		list.add("Byproduct: " + getByproduct(EnumResource.values()[product]));
		list.add("Product vs Byproduct");
		list.add("Balance: " + (balance*100) + " / " + (100-balance*100));
		
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(stack.hasTagCompound())
			return;
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("CHANNEL", "NONE");
		stack.stackTagCompound.setInteger("PRODUCT", 0);
		stack.stackTagCompound.setDouble("EFFECTIVNESS", 0.05D);
		stack.stackTagCompound.setDouble("BALANCE", 0.05D);
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(!DataHelper.hasOwner(itemstack)){
			DataHelper.belongsTo(player, itemstack);
			return itemstack;
		}
		if(world.isRemote)
			return itemstack;
		
		return itemstack;
	}

	@Override
	public boolean stop(ItemStack stack) {
		return false;
	}
	
	@Override
	public EnumToken getType(ItemStack stack) {
		return EnumToken.TRANSFORMER;
	}
	
	@Override
	public void feed(ItemStack stack, ResourcePackage amount) {
		int p = stack.stackTagCompound.getInteger("PRODUCT");
		double effectivness = stack.stackTagCompound.getDouble("EFFECTIVNESS");
		double balance = stack.stackTagCompound.getDouble("BALANCE");
		
		EnumResource product = EnumResource.values()[p];
		
		if(product == EnumResource.BIOMASS){
			double v = amount.get(EnumResource.RAW_FOOD) * effectivness;
			amount.add(product, v * balance);
			amount.add(EnumResource.WASTE, v * (1.0D - balance));
			amount.put(EnumResource.RAW_FOOD, 0.0D);
		} else if(product == EnumResource.PSY_ENG){
			double v = amount.get(EnumResource.BIOMASS) * effectivness;
			amount.add(product, v * balance);
			amount.add(EnumResource.WASTE, v * (1.0D - balance));
			amount.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.NURITMENT){
			double v = amount.get(EnumResource.BIOMASS) * effectivness;
			amount.add(product, v * balance);
			amount.add(EnumResource.WASTE, v * (1.0D - balance));
			amount.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.LIFE_FLUIDS){
			double v = amount.get(EnumResource.BIOMASS) * effectivness;
			amount.add(product, v * balance);
			amount.add(EnumResource.WASTE, v * (1.0D - balance));
			amount.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.WASTE){
			double v = amount.get(EnumResource.BIOMASS) * effectivness;
			amount.add(product, 2.0D * v * balance);
			amount.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.RAW_FOOD){
			double v = amount.get(EnumResource.WASTE) * effectivness;
			amount.add(product, v * balance);
			amount.put(EnumResource.WASTE, 0.0D);
		}
		
	}
	
	@Override
	public void drain(ItemStack stack, ResourcePackage pack, double amount) {
		int p = stack.stackTagCompound.getInteger("PRODUCT");
		double effectivness = stack.stackTagCompound.getDouble("EFFECTIVNESS");
		double balance = stack.stackTagCompound.getDouble("BALANCE");
		
		EnumResource product = EnumResource.values()[p];
		
		if(product == EnumResource.BIOMASS){
			double v = pack.get(EnumResource.RAW_FOOD) * effectivness;
			pack.add(product, v * balance);
			pack.add(EnumResource.WASTE, v * (1.0D - balance));
			pack.put(EnumResource.RAW_FOOD, 0.0D);
		} else if(product == EnumResource.PSY_ENG){
			double v = pack.get(EnumResource.BIOMASS) * effectivness;
			pack.add(product, v * balance);
			pack.add(EnumResource.WASTE, v * (1.0D - balance));
			pack.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.NURITMENT){
			double v = pack.get(EnumResource.BIOMASS) * effectivness;
			pack.add(product, v * balance);
			pack.add(EnumResource.WASTE, v * (1.0D - balance));
			pack.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.LIFE_FLUIDS){
			double v = pack.get(EnumResource.BIOMASS) * effectivness;
			pack.add(product, v * balance);
			pack.add(EnumResource.WASTE, v * (1.0D - balance));
			pack.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.WASTE){
			double v = pack.get(EnumResource.BIOMASS) * effectivness;
			pack.add(product, 2.0D * v * balance);
			pack.put(EnumResource.BIOMASS, 0.0D);
		} else if(product == EnumResource.RAW_FOOD){
			double v = pack.get(EnumResource.WASTE) * effectivness;
			pack.add(product, v * balance);
			pack.put(EnumResource.WASTE, 0.0D);
		}
	}

}
