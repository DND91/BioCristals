package hok.chompzki.hivetera.containers;

import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.registrys.ConfigRegistry;

import java.awt.Color;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class Hivebag implements IInventory {
	
	public final int maxCookTime = 50;
	
	public class HivebagBase{
		public ItemStack slot = null;
		public int startSize = 0;
		public int startCookTime = 0;
		public int cookTime = 0;
		public int colorValue = 0;
	}
	
	ItemStack stack = null;
	NBTTagCompound nbt = null;
	public final int size = 9;
	public HivebagBase[] inventory = new HivebagBase[size];
	EntityPlayer player = null;
	int slot = -1;
	public final float strength = 0.0f;
	
	float sub_distance = 0.0f;
	int distance = 0;
	boolean isOpen = false;
	private Random rand = new Random();
	
	public Hivebag(EntityPlayer player2, int slot) {
		this.player = player2;
		this.stack = player2.inventory.getStackInSlot(slot);
		nbt = stack.getTagCompound();
		this.slot = slot;
		this.readFrom();
	}

	public void readFrom(){
		if(slot == -1)
			nbt = player.inventory.getCurrentItem().getTagCompound();
		else
			nbt = player.inventory.getStackInSlot(slot).getTagCompound();
		
		if(nbt.hasKey("F_DISTANCE"))
			sub_distance = nbt.getFloat("F_DISTANCE");
		if(nbt.hasKey("DISTANCE"))
			distance = nbt.getInteger("DISTANCE");
		
		if(nbt.hasKey("OPEN"))
			isOpen = nbt.getBoolean("OPEN");
		
		for(int i = 0; i < size; i++)
			this.inventory[i] = new HivebagBase();
		
		NBTTagList list = nbt.getTagList("INVENTORY", NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = list.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            
            if (j >= 0 && j < this.inventory.length)
            {
            	this.inventory[j] = new HivebagBase();
            	this.inventory[j].colorValue = nbttagcompound1.getInteger("COLOR_VALUE");
            	this.inventory[j].startCookTime = nbttagcompound1.getInteger("START_COOK_TIME");
            	this.inventory[j].cookTime = nbttagcompound1.getInteger("COOK_TIME");
            	this.inventory[j].startSize = nbttagcompound1.getInteger("START_SIZE");
            	this.inventory[j].slot = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}
	
	public void writeTo(){
		nbt = new NBTTagCompound();
		
		nbt.setFloat("F_DISTANCE", sub_distance);
		nbt.setInteger("DISTANCE", distance);
		
		nbt.setBoolean("OPEN", isOpen);
		
		NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i].slot != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                nbttagcompound1.setInteger("COLOR_VALUE", this.inventory[i].colorValue);
                nbttagcompound1.setInteger("START_COOK_TIME", this.inventory[i].startCookTime);
                nbttagcompound1.setInteger("COOK_TIME", this.inventory[i].cookTime);
                nbttagcompound1.setInteger("START_SIZE", this.inventory[i].startSize);
                this.inventory[i].slot.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        nbt.setTag("INVENTORY", nbttaglist);
        if(slot == -1)
        	player.inventory.getCurrentItem().setTagCompound(nbt);
        else
        	player.inventory.getStackInSlot(slot).setTagCompound(nbt);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.inventory[var1].slot;
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.inventory[par1] != null)
        {
            ItemStack itemstack;
            if (this.inventory[par1].slot.stackSize <= par2)
            {
                itemstack = this.inventory[par1].slot;
                this.inventory[par1].slot = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[par1].slot.splitStack(par2);

                if (this.inventory[par1].slot.stackSize == 0)
                {
                    this.inventory[par1].slot = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.inventory[par1] != null)
        {
            ItemStack itemstack = this.inventory[par1].slot;
            this.inventory[par1].slot = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.inventory[par1].slot = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "container.hivebag";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
				inventory[i].slot = null;
		}
		writeTo();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {	}
	
	@Override
	public void closeInventory() {	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return !(var2.getItem() instanceof ItemHivebag);
	}
	
	public void tickAll() {
		for(int i = 0; i < this.getSizeInventory(); i++){
			if(this.canSmelt(i) && this.inventory[i].cookTime == 0){
				this.inventory[i].startSize = this.inventory[i].slot.stackSize;
				this.inventory[i].cookTime = ConfigRegistry.hivebagCookTime; //this.inventory[i].slot.stackSize * AcidRecipes.smelting().getCookTime(this.inventory[i].slot);
				this.inventory[i].startCookTime = this.inventory[i].cookTime;
			}else if(0 < this.inventory[i].cookTime){
				this.inventory[i].cookTime--;
				double tmp = 1.0 - ((double)this.inventory[i].cookTime / (double)this.inventory[i].startCookTime);
        		this.inventory[i].colorValue = (int)(tmp * 10000.0);
				if(this.inventory[i].cookTime == 0){
					ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[i].slot).copy(); //AcidRecipes.smelting().getSmeltingResult(this.inventory[i].slot, strength);
					stack.stackSize = this.inventory[i].startSize;
					this.setInventorySlotContents(i, stack);
			        //HiveCraft.instance.packetPipeline.sendToAllAround(new PacketParticle(player, 14, "heart"), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64.0f));
				}
			}
		}
		this.markDirty();
	}

	private boolean canSmelt(int slot) {
		if (this.inventory[slot].slot == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack =  FurnaceRecipes.smelting().getSmeltingResult(this.inventory[slot].slot); // AcidRecipes.smelting().getSmeltingResult(this.inventory[slot].slot, strength);
        	
            if (itemstack == null) return false;
            return true;
        }
	}
	
	public int canInsertStack(ItemStack stack){
		if(stack == null)
			return -1;
		for(int i = 0; i < this.getSizeInventory(); i++){
			ItemStack temp = this.getStackInSlot(i);
			if(temp == null)
				return i;
			if(temp.isItemEqual(stack) && (temp.stackSize + stack.stackSize) <= this.getInventoryStackLimit())
				return i;
		}
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
    public Color getCookProgressColorScale(int slot, Color start, Color end)
    {
		if(!this.canSmelt(slot))
			return Color.BLACK;
		
		double scale = (double)this.inventory[slot].colorValue / 10000.0;
		int r = end.getRed() - start.getRed();
		int g = end.getGreen() - start.getGreen();
		int b = end.getBlue() - start.getBlue();
        r *= scale;
        g *= scale;
        b *= scale;
        r += start.getRed();
        g += start.getGreen();
        b += start.getBlue();
        r = Math.abs(r);
        g = Math.abs(g);
        b = Math.abs(b);
        r = Math.min(r,  255);
        g = Math.min(g, 255);
        b = Math.min(b,  255);
		return new Color(r,g,b);
    }

}

