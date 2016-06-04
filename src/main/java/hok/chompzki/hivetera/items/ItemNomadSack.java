package hok.chompzki.hivetera.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.oredict.OreDictionary;

public class ItemNomadSack extends Item {
	
	@SideOnly(Side.CLIENT)
    private IIcon[] iconArray;
	public static ArrayList<ItemStack> whitelist = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> blacklist = new ArrayList<ItemStack>();
	
	
	public final static String NAME = "itemNomandsSack";
	
	public ItemNomadSack(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		this.setMaxDamage(ConfigRegistry.nomadsSackSize);
		this.setNoRepair();
	}
	
	private boolean isWhitelisted(ItemStack block){
		for(ItemStack b : whitelist){
			if(OreDictionary.itemMatches(b, block, false))
				return true;
		}
		return false;
	}
	
	private boolean isBlacklisted(ItemStack item){
		for(ItemStack i : blacklist){
			if(OreDictionary.itemMatches(i, item, false))
				return true;
		}
		return false;
	}
	//IPlantable
	//Blocks banned, therefor whitelist for blocks; wool, pumpkin, melons, seeds, flowers
	//Items are okey, therefor blacklist for Items; coal, redstone, glowstone, stick, researchBook, crootStick, arrows, bed, compass, couldron, 
	//Wont accept any ItemTool or ItemFood
	public boolean canEat(ItemStack sack, ItemStack stack){
		if(stack.getItem() instanceof ItemBlock){
			if(!isWhitelisted(stack))
				return false;
		} else {
			if(stack.getItem().isItemTool(stack))
				return false;
			if(stack.getItem() instanceof ISpecialArmor || stack.getItem() instanceof ItemArmor)
				return false;
			if(stack.getItem() instanceof ItemFood)
				return false;
			if(isBlacklisted(stack))
				return false;
		}
		return true;
	}
	
	public void nbt(ItemStack stack){
		if(stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		else
			return;
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger("SIZE", 0);
		NBTTagList nbttaglist = new NBTTagList();
		stack.stackTagCompound.setTag("INVENTORY", nbttaglist);
	}
	
	public int getSize(ItemStack sack){
		nbt(sack);
		NBTTagCompound nbt = sack.getTagCompound();
		return nbt.getInteger("SIZE");
	}
	
	public void setSize(ItemStack sack, int size){
		nbt(sack);
		NBTTagCompound nbt = sack.getTagCompound();
		nbt.setInteger("SIZE", size);
	}
	
	public ArrayList<ItemStack> getInventory(ItemStack sack){
		nbt(sack);
		NBTTagCompound nbt = sack.getTagCompound();
		NBTTagList nbttaglist = nbt.getTagList("INVENTORY", NBT.TAG_COMPOUND);
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            list.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
        }
		return list;
	}
	
	public void setInventory(ItemStack sack, ArrayList<ItemStack> list){
		nbt(sack);
		NBTTagCompound nbt = sack.getTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		
		for(ItemStack stack : list){
			NBTTagCompound is = new NBTTagCompound();
			stack.writeToNBT(is);
			nbttaglist.appendTag(is);
		}
		
		nbt.setTag("INVENTORY", nbttaglist);
	}
	
	public void eat(ItemStack sack, InventoryPlayer inv, int slot){
		nbt(sack);
		int size = getSize(sack);
		ArrayList<ItemStack> list = getInventory(sack);
		ItemStack stack = inv.getStackInSlot(slot);
		
		if(size < ConfigRegistry.nomadsSackSize){
			if((size+stack.stackSize) <= ConfigRegistry.nomadsSackSize){
				ItemStack c = stack.copy();
				list.add(c);
				size += c.stackSize;
				stack.stackSize = 0;
				inv.setInventorySlotContents(slot, null);
			}else{
				int s = ConfigRegistry.nomadsSackSize - size;
				ItemStack c = stack.copy();
				c.stackSize = s;
				stack.stackSize -= s;
				list.add(c);
				size += s;
			}
		}
		
		setSize(sack, size);
		setInventory(sack, list);
	}
	
	public void eat(ItemStack sack, ItemStack stack){
		nbt(sack);
		int size = getSize(sack);
		ArrayList<ItemStack> list = getInventory(sack);
		
		if(size < ConfigRegistry.nomadsSackSize){
			if((size+stack.stackSize) <= ConfigRegistry.nomadsSackSize){
				ItemStack c = stack.copy();
				list.add(c);
				size += c.stackSize;
				stack.stackSize = 0;
			}else{
				int s = ConfigRegistry.nomadsSackSize - size;
				ItemStack c = stack.copy();
				c.stackSize = s;
				stack.stackSize -= s;
				list.add(c);
				size += s;
			}
		}
		
		setSize(sack, size);
		setInventory(sack, list);
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean currentItem) {
		if(entity instanceof EntityPlayer && currentItem){
			EntityPlayer player = (EntityPlayer)entity;
			InventoryPlayer inv = player.inventory;
			
			for(int i = 9; i < inv.getSizeInventory(); i++){
				if(i == slot || inv.getStackInSlot(i) == null)
					continue;
				if(canEat(stack, inv.getStackInSlot(i))){
					eat(stack, inv, i);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		nbt(stack);
		int size = getSize(stack);
		list.add("Content " + size + "/" + ConfigRegistry.nomadsSackSize);
		
	}
	
	public boolean onItemUse(ItemStack sack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		Block b = world.getBlock(x, y, z);
		TileEntity ent = world.getTileEntity(x, y, z);
		if(!world.isRemote && ent != null && ent instanceof IInventory){
			IInventory inv = (IInventory)ent;
			nbt(sack);
			int size = getSize(sack);
			ArrayList<ItemStack> list = getInventory(sack);
			
			for(ItemStack drop : list){
				BioHelper.addItemStackToInventory(drop, inv);
			}
			
			size = 0;
			Iterator<ItemStack> i = list.iterator();
			while (i.hasNext()) {
				ItemStack s = i.next();
				if(s.stackSize <= 0)
					i.remove();
				else
					size += s.stackSize;
			}
			
			setSize(sack, size);
			setInventory(sack, list);
			return true;
		}
        return false;
    }
	
	
	public double getDurabilityForDisplay(ItemStack sack)
    {
		nbt(sack);
		int size = getSize(sack);
        return 1.0d - ((double)size / (double)ConfigRegistry.nomadsSackSize);
    }
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack)
    {
        return true;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
}














