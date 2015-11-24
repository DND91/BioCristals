package hok.chompzki.biocristals.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.registrys.ConfigRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemNomadSack extends Item {
	
	@SideOnly(Side.CLIENT)
    private IIcon[] iconArray;
	
	
	public final static String NAME = "itemNomandsSack";
	public final static int maxSize = 2500;
	
	public ItemNomadSack(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
		this.setMaxDamage(maxSize);
		this.setNoRepair();
	}
	//IPlantable
	public boolean canEat(ItemStack sack, ItemStack stack){
		if(stack.getItem() instanceof ItemBlock){
			Block b = Block.getBlockFromItem(stack.getItem());
			if(!(b instanceof IPlantable) && !(b == Blocks.wool))
				return false;
		}
		if(stack.getItem().isItemTool(stack))
			return false;
		if(stack.getItem() instanceof ItemFood)
			return false;
		if(stack.getItem() == Items.stick || stack.getItem() == Items.coal
				|| stack.getItem() == ItemRegistry.researchBook)
			return false;
		
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
		
		if(size < this.maxSize){
			if((size+stack.stackSize) <= this.maxSize){
				list.add(stack);
				size += stack.stackSize;
				inv.setInventorySlotContents(slot, null);
			}else{
				int s = maxSize - size;
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
		if(!world.isRemote && entity instanceof EntityPlayer && currentItem){
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
		list.add("Content " + size + "/" + this.maxSize);
		
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
        return 1.0d - ((double)size / (double)maxSize);
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













