package hok.chompzki.hivetera.containers;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.client.slots.SlotCrootBeetle;
import hok.chompzki.hivetera.client.slots.SlotEater;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotInsectHunt;
import hok.chompzki.hivetera.client.slots.SlotNestInsect;
import hok.chompzki.hivetera.client.slots.SlotResult;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import hok.chompzki.hivetera.data.BiomeKittehData;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.EnumUnlock;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.BiomeDictionary;

public class ContainerInsectHunt extends Container {
	
	public static final Random rand = new Random();
	
	private EntityPlayer player = null;
	public int selected = 1;
	private int lastSelected = 1;
	
	public InsectHunt insectHunt;
	
	public ItemStack clone = null;
	
	public ContainerInsectHunt(EntityPlayer player) {
		this.player = player;
		insectHunt = new InsectHunt(player, player.inventory.currentItem);
		
		this.addSlotToContainer(new SlotCrootBeetle(insectHunt, 0, 22, 8));
		
		for (int j = 0; j < 5; ++j)
        {
            for (int k = 0; k < 5; ++k)
            {
                this.addSlotToContainer(new SlotInsectHunt(this, insectHunt, 1 + k + j * 5, 44 + k * 18, 8 + j * 18));
            }
        }
		
		for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
            	
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18));
            }
        }
		
		for (int i = 0; i < 9; ++i)
        {
            if(i == player.inventory.currentItem){
            	this.addSlotToContainer(new SlotVisual(player.inventory, i, 8 + i * 18, 160));
        	}else
        		this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 160));
        }
	}
	
	public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.selected);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastSelected != this.selected)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.selected);
            }
        }

        this.lastSelected = this.selected;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_)
    {
    	if (p_75137_1_ == 0)
        {
        	this.selected = p_75137_2_;
        }
    }
    
    public boolean enchantItem(EntityPlayer player, int action){
    	super.enchantItem(player, action);
    	if(action == 0){
    		selected = 0;
    		clone = null;
    		return true;
    	} else if(action == 1){
    		selected = 1;
    		clone = null;
    		return true;
    	} else if(action == 2){
    		selected = 2;
    		clone = null;
    		return true;
    	} else if(action == 3){
    		selected = 3;
    		clone = null;
    		return true;
    	}
    	
    	return false;
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
        int numRows = 1 + 5 * 5;
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (p_82846_2_ < numRows)
            {
                if (!this.mergeItemStack(p_82846_1_, itemstack1, numRows, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(p_82846_1_, itemstack1, 0, numRows, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
	
	 protected boolean mergeItemStack(EntityPlayer player, ItemStack stack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
	    {
	        boolean flag1 = false;
	        int k = p_75135_2_;

	        if (p_75135_4_)
	        {
	            k = p_75135_3_ - 1;
	        }

	        Slot slot;
	        ItemStack itemstack1;

	        if (stack.isStackable())
	        {
	            while (stack.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_))
	            {
	                slot = (Slot)this.inventorySlots.get(k);
	                if(slot.isItemValid(stack)){
	                	
		                itemstack1 = slot.getStack();
	
		                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
		                {
		                    int l = itemstack1.stackSize + stack.stackSize;
	
		                    if (l <= stack.getMaxStackSize())
		                    {
		                        stack.stackSize = 0;
		                        itemstack1.stackSize = l;
		                        slot.onSlotChanged();
		                        flag1 = true;
		                        if(k == 0 && !player.worldObj.isRemote){
		                        	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
		                        }
		                    }
		                    else if (itemstack1.stackSize < stack.getMaxStackSize())
		                    {
		                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
		                        itemstack1.stackSize = stack.getMaxStackSize();
		                        slot.onSlotChanged();
		                        flag1 = true;
		                        if(k == 0 && !player.worldObj.isRemote){
		                        	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
		                        }
		                    }
		                }
	                }
	                if (p_75135_4_)
	                {
	                    --k;
	                }
	                else
	                {
	                    ++k;
	                }
	            }
	        }

	        if (stack.stackSize > 0)
	        {
	            if (p_75135_4_)
	            {
	                k = p_75135_3_ - 1;
	            }
	            else
	            {
	                k = p_75135_2_;
	            }

	            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
	            {
	                slot = (Slot)this.inventorySlots.get(k);
	                if(slot.isItemValid(stack)){
		                itemstack1 = slot.getStack();
		                
		                if (itemstack1 == null)
		                {
		                    slot.putStack(stack.copy());
		                    slot.onSlotChanged();
		                    stack.stackSize = 0;
		                    flag1 = true;
		                    if(k == 0 && !player.worldObj.isRemote){
		                    	ResearchUnlocks.unlock(player, EnumUnlock.PUT_NEST, stack);
	                        }
		                    break;
		                }
	                }
	                if (p_75135_4_)
	                {
	                    --k;
	                }
	                else
	                {
	                    ++k;
	                }
	            }
	        }

	        return flag1;
	    }
	
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        if(player.worldObj.isRemote)
        	return;
        
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for(int i = 1; i < this.insectHunt.getSizeInventory(); i++){
        	ItemStack stack = this.insectHunt.getStackInSlot(i);
        	if(stack != null && stack.getItem() != ItemRegistry.biomeSample){
        		stacks.add(stack);
        		this.insectHunt.setInventorySlotContents(i, null);
        	}
        }
        
        this.insectHunt.writeTo();
        
        if(0 < stacks.size()){
        	BioHelper.dropItems(player.worldObj, stacks, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
    }
    
    public ItemStack slotClick(int slot, int button, int c, EntityPlayer player)
    {
    	if(0 < slot  && slot < (1+5*5) && (button == 0 || button == 1)){
    		ItemStack held = player.inventory.getItemStack();
    		
    		if(this.selected == 0) {//Sacrifice
    			if(this.getSlot(slot).getHasStack() && (this.getSlot(slot).getStack().getItem() == ItemRegistry.biomeSample
    					|| this.getSlot(slot).getStack().getItem() instanceof IInsect)){
    				int stackSize = this.getSlot(slot).getStack().stackSize;
    				int pts = stackSize;
    				if(this.getSlot(slot).getStack().getItem() instanceof IInsect)
    					pts *= 2;
    				
    				this.insectHunt.points += pts;
    				
    				this.insectHunt.writeTo();
    				if(!player.worldObj.isRemote){
    					this.getSlot(slot).putStack(null);
    					this.detectAndSendChanges();
    				}
    				
    				return held;
    			}
    		}else if(this.selected == 1 && this.getSlot(slot).getHasStack() && this.getSlot(slot).getStack().getItem() == ItemRegistry.biomeSample) {//Open
    			if(this.getSlot(slot).getHasStack()){
    				ItemStack stack = this.getSlot(slot).getStack();
    				if(stack.getItem() != ItemRegistry.biomeSample)
    					return held;
    				
					int x = (slot-1)%5;
					int y = (slot-1)/5;
					int minX = getMinX(stack, x, y);
					int maxX = getMaxX(stack, x, y);
					int minY = getMinY(stack, x, y);
					int maxY = getMaxY(stack, x, y);
					
					if(canOpen(stack, x, y, minX, maxX, minY, maxY)){
						if(player.worldObj.isRemote)
							return held;
						
						this.getSlot(slot).putStack(null);
						present(stack.getItemDamage(), open(stack, x, y, minX, maxX, minY, maxY), x, y, minX, maxX, minY, maxY);
						this.detectAndSendChanges();
						//TODO: Show particles?
					}
					
    				return held;
    			}
    		}else if(this.selected == 2) {//Random sample
    			if(!this.getSlot(slot).getHasStack() && 2 <= this.insectHunt.points){
    				this.insectHunt.points -= 2;
    				this.insectHunt.writeTo();
    				if(!player.worldObj.isRemote){
    					BiomeDictionary.Type type = BiomeRegistry.idToBiome.get(rand.nextInt(BiomeRegistry.idToBiome.size()));
    					this.getSlot(slot).putStack(new ItemStack(ItemRegistry.biomeSample, 1, BiomeRegistry.biomeToId.get(type)));
    					this.detectAndSendChanges();
    				}
    				return held;
    			}
    		}else if(this.selected == 3) {//Copy sample
    			if(clone == null && this.getSlot(slot).getHasStack() && this.getSlot(slot).getStack().getItem() == ItemRegistry.biomeSample){
    				clone = this.getSlot(slot).getStack().copy();
    				this.detectAndSendChanges();
    				return held;
    			} else if(clone != null && !this.getSlot(slot).getHasStack() && 4 <= this.insectHunt.points){
    				this.insectHunt.points -= 4;
    				this.insectHunt.writeTo();
    				this.getSlot(slot).putStack(clone.copy());
    				this.detectAndSendChanges();
    				return held;
    			}
    		}
    		
    		if(this.getSlot(slot).getHasStack() && !player.worldObj.isRemote){
    			ResearchUnlocks.unlock(player, EnumUnlock.PICKUP, this.getSlot(slot).getStack());
    		}
    	}
    	
    	
    	
    	return super.slotClick(slot, button, c, player);
    }

	private int getMinX(ItemStack stack, int x, int y){
		int left = x;
    	for(; 0 <= left; left--){
			int slot = 1 + left + y * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					
				}else
					return left+1;
			}else
				return left+1;
		}
    	return MathHelper.clamp_int(left, 0, 5);
    }
    
    private int getMaxX(ItemStack stack, int x, int y){
    	int right = x;
    	for(; right < 5; right++){
			int slot = 1 + right + y * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					
				}else
					return right;
				
			}else
				return right;
		}
    	return MathHelper.clamp_int(right, 0, 5);
    }
    
    private int getMinY(ItemStack stack, int x, int y){
    	int down = y;
    	for(; 0 <= down; down--){
			int slot = 1 + x + down * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					
				}else
					return down+1;
				
			}else
				return down+1;
		}
    	return MathHelper.clamp_int(down, 0, 5);
    }
    
    private int getMaxY(ItemStack stack, int x, int y){
    	int up = y;
    	for(; up < 5; up++){
			int slot = 1 + x + up * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					
				}else
					return up;
				
			}else
				return up;
		}
    	return MathHelper.clamp_int(up, 0, 5);
    }

	private void present(int id, int pts, int cx, int cy, int minX, int maxX, int minY, int maxY) {
		pts = Math.max(3, pts);
		
		BiomeKittehData data = BiomeRegistry.kittehsBiomes.get(BiomeRegistry.idToBiome.get(id));
		
		for(int ps = 0; ps < pts; ps++){
			int mod = this.rand.nextInt(2);
			int x = mod == 0 ? this.rand.nextInt(maxX - minX) + minX : cx;
			int y = mod != 0 ? this.rand.nextInt(maxY - minY) + minY : cy;
			int slot = 1 + x + y * 5;
			if(!this.getSlot(slot).getHasStack()){
				ItemStack stack = data.next();
				if(stack != null){
					stack.getItem().onCreated(stack, this.player.worldObj, player);
					this.getSlot(slot).putStack(stack);
				}
			}else {
				ItemStack stack = this.getSlot(slot).getStack();
				stack.stackSize++;
				stack.stackSize = Math.min(stack.stackSize, stack.getMaxStackSize());
			}
		}
		
	}

	private int open(ItemStack stack, int cx, int cy, int minX, int maxX, int minY, int maxY) {
		int pts = -1;
		
		for(int y = minY; y < maxY; y++){
			int slot = 1 + cx + y * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					this.getSlot(slot).putStack(null);
					pts += 1;
				}else
					break;
				
			}
		}
		
		for(int x = minX; x < maxX; x++){
			int slot = 1 + x + cy * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					this.getSlot(slot).putStack(null);
					pts += 1;
				}else
					break;
				
			}
		}
		
		return pts;
	}

	private boolean canOpen(ItemStack stack, int cx, int cy, int minX, int maxX, int minY, int maxY) {
		int pts = -1;
		
		for(int y = minY; y < maxY; y++){
			int slot = 1 + cx + y * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					pts += 1;
				}else
					break;
				
			}
		}
		
		for(int x = minX; x < maxX; x++){
			int slot = 1 + x + cy * 5;
			if(this.getSlot(slot).getHasStack()){
				ItemStack s = this.getSlot(slot).getStack();
				if(s.getItem() == stack.getItem() && stack.getItemDamage() == s.getItemDamage()){
					pts += 1;
				}else
					break;
				
			}
		}
		
		return 3 < pts;
	}
}
