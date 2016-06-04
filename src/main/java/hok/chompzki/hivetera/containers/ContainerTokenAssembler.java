package hok.chompzki.hivetera.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.client.slots.CostInventoryTokenAssembler;
import hok.chompzki.hivetera.client.slots.InventoryTokenAssembler;
import hok.chompzki.hivetera.client.slots.SlotInsect;
import hok.chompzki.hivetera.client.slots.SlotResult;
import hok.chompzki.hivetera.client.slots.SlotTokenAssembler;
import hok.chompzki.hivetera.client.slots.SlotTokenAssemblerResult;
import hok.chompzki.hivetera.client.slots.SlotVisual;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.EnumUnlock;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.tile_enteties.TileCrootHollow;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import hok.chompzki.hivetera.tile_enteties.TileTokenAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerTokenAssembler extends Container {
	
	public IInventory craftVisual = new InventoryTokenAssembler(this);
	public IInventory craftResult = new InventoryTokenAssembler(this);
	public IInventory craftCost = new CostInventoryTokenAssembler(this);
	private EntityPlayer player = null;
	private TileTokenAssembler assembler = null;
	private int selected = 0;
	private int color = 0;
	private Integer[] values = new Integer[20];
	
	private ForgeDirection[] inputSides = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	

	public ContainerTokenAssembler(EntityPlayer player,
			TileTokenAssembler tileEntity) {
		super();
		this.player = player;
		assembler = tileEntity;
		
		this.addSlotToContainer(new SlotTokenAssembler(player, craftVisual, craftCost, assembler, 0, 174, 8));
		this.addSlotToContainer(new SlotTokenAssemblerResult(player, craftResult, 0, 232, 8));
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (i = 0; i < 3; ++i){
            for (j = 0; j < 11; ++j){
            	this.addSlotToContainer(new SlotVisual (craftCost, i + j * 3, 176 + i * 26, 50 + j * 18));
            }
        }
		
		i = 0;
		j = 0;
		k = 0;
		
		for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
            	
        		this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 174 + j * 18 + i));
        		
            }
        }
		
        for (j = 0; j < 9; ++j)
        {
    		this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 232 + i));
        }
        
        onCraftMatrixChanged(assembler);
	}
	
	public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.assembler.selected);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.assembler.color);
        for(int i = 0; i < this.assembler.values.length; i++){
        	p_75132_1_.sendProgressBarUpdate(this, 2+i, this.assembler.values[i]);
        }
        
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

            if (this.selected != this.assembler.selected)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.assembler.selected);
            }
            
            if (this.color != this.assembler.color)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.assembler.color);
            }
            
            for(int j = 0; j < this.assembler.values.length; j++){
            	if(this.values[j] != this.assembler.values[j]){
            		icrafting.sendProgressBarUpdate(this, 2+j, this.assembler.values[j]);
            	}
            }
            
        }
        
        this.selected = this.assembler.selected;
        this.color = this.assembler.color;
        for(int j = 0; j < this.assembler.values.length; j++){
        	this.values[j] = this.assembler.values[j];
        }
    }
    
    public boolean enchantItem(EntityPlayer player, int action){
    	super.enchantItem(player, action);
    	if(action == 100){
    		this.craft();
    		return true;
    	}
    	
    	if(action == 0){
			assembler.selected--;
			if(assembler.selected < 0)
				assembler.selected = EnumToken.values().length-1;
			for(int i = 0; i < this.assembler.values.length;i++){
				this.assembler.values[i] = 0;
			}
			
			if(EnumToken.values()[assembler.selected] == EnumToken.TRANSFORMER){
    			this.assembler.values[0] = 0;
    			this.assembler.values[1] = 25;
    			this.assembler.values[2] = 50;
    		}
			
			if(EnumToken.values()[assembler.selected] == EnumToken.BANK){
    			this.assembler.values[6] = 1;
    		}
			
			this.update();
			return true;
		} else if(action == 1){
			assembler.selected++;
			if(EnumToken.values().length <= assembler.selected)
				assembler.selected = 0;
			for(int i = 0; i < this.assembler.values.length;i++){
				this.assembler.values[i] = 0;
			}
			
			if(EnumToken.values()[assembler.selected] == EnumToken.TRANSFORMER){
    			this.assembler.values[0] = 0;
    			this.assembler.values[1] = 25;
    			this.assembler.values[2] = 50;
    		}
			
			if(EnumToken.values()[assembler.selected] == EnumToken.BANK){
    			this.assembler.values[6] = 1;
    		}
			
			this.update();
			return true;
			
		} else if(action == 2){
			assembler.color--;
			if(assembler.color < 0)
				assembler.color = ItemDye.field_150923_a.length;
			this.update();
			return true;
		} else if(action == 3){
			assembler.color++;
			if(ItemDye.field_150923_a.length < assembler.color)
				assembler.color = 0;
			this.update();
			return true;
		}
    	
    	if(EnumToken.values()[selected] == EnumToken.TRANSFORMER && (action-4) < assembler.values.length){
    		if(action == 4){ // PRODUCT
    			assembler.values[0]--;
    			if(assembler.values[0] < 0)
    				assembler.values[0] = EnumResource.values().length-1;
    			this.update();
    			return true;
    		} else if(action == 5){
    			assembler.values[0]++;
    			if(EnumResource.values().length <= assembler.values[0])
    				assembler.values[0] = 0;
    			this.update();
    			return true;
    		}if(action == 6){ // EFFIECENCY
    			assembler.values[1]--;
    			if(assembler.values[1] < 1)
    				assembler.values[1] = 1;
    			this.update();
    			return true;
    		} else if(action == 7){
    			assembler.values[1]++;
    			if(100 < assembler.values[1])
    				assembler.values[1] = 100;
    			this.update();
    			return true;
    		} if(action == 8){ // BALANCE
    			assembler.values[2]--;
    			if(assembler.values[2] < 25)
    				assembler.values[2] = 25;
    			this.update();
    			return true;
    		} else if(action == 9){
    			assembler.values[2]++;
    			if(100 < assembler.values[2])
    				assembler.values[2] = 100;
    			this.update();
    			return true;
    		} 
    	}
    	
    	if(EnumToken.values()[selected] == EnumToken.FILTER && (action-4) < assembler.values.length){
    		int v = assembler.values[action-4];
    		assembler.values[action-4] = v == 0 ? 1 : 0;
    		this.update();
    		return true;
    	}
    	
    	if(EnumToken.values()[selected] == EnumToken.BANK && (action-4) < assembler.values.length){
    		if(3 < action && action < 10){
	    		int v = assembler.values[action-4];
	    		assembler.values[action-4] = v == 0 ? 1 : 0;
	    		this.update();
    		}else if(action == 10){ // MAX STORAGE
    			assembler.values[6]--;
    			if(assembler.values[6] <= 0)
    				assembler.values[6] = 1;
    			this.update();
    			return true;
    		} else if(action == 11){
    			assembler.values[6]++;
    			if(10000 < assembler.values[6])
    				assembler.values[6] = 10000;
    			this.update();
    			return true;
    		}
    		return true;
    	}
    	
    	return false;
    }
    
    private void craft() {
    	if(this.assembler.getWorldObj().isRemote)
    		return;
    	
    	if(!canFunction() && !player.capabilities.isCreativeMode)
			return;
    	
		List<ItemStack> cost = new ArrayList<ItemStack>();
		intoList(craftCost, cost);
		
		List<ItemStack> payment = new ArrayList<ItemStack>();
		IInventory[] inputs = BioHelper.getInventories(assembler, inputSides);
		for(IInventory inv : inputs){
			intoList(inv, payment);
		}
		
		if(payment.size() < cost.size() && !player.capabilities.isCreativeMode)
			return;
        if(!affords(cost, payment) && !player.capabilities.isCreativeMode)
        	return;
        if(this.craftResult.getStackInSlot(0) == null){
        	this.craftResult.setInventorySlotContents(0, craftVisual.getStackInSlot(0).copy());
        }else{
        	ItemStack slot = this.craftResult.getStackInSlot(0);
        	ItemStack visual = this.craftVisual.getStackInSlot(0).copy();
        	
        	if(slot.stackSize == slot.getMaxStackSize())
        		return;
        	
        	if(slot.getItem() == visual.getItem() && slot.getItemDamage() == visual.getItemDamage()
					&& ((!slot.hasTagCompound() && !visual.hasTagCompound()) || 
						((slot.hasTagCompound() && visual.hasTagCompound()) && slot.stackTagCompound.equals(visual.stackTagCompound)))){
				if(slot.getMaxStackSize() < slot.stackSize + visual.stackSize)
					slot.stackSize = slot.getMaxStackSize();
				else
					slot.stackSize += visual.stackSize;
			}
        }
    	
        if(!player.capabilities.isCreativeMode)
	        for(IInventory inv : inputs){
				pay(inv, cost);
			}
	}
    
    private void pay(IInventory inv, List<ItemStack> costs) {
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null)
				continue;
			
			Iterator<ItemStack> ite = costs.iterator();
			while(ite.hasNext()){
				ItemStack cost = ite.next();
				
				if(slot.getItem() == cost.getItem() && slot.getItemDamage() == cost.getItemDamage())
						/*&& ((!slot.hasTagCompound() && !cost.hasTagCompound()) || 
							((slot.hasTagCompound() && cost.hasTagCompound()) && slot.stackTagCompound.equals(cost.stackTagCompound))))*/{
					if(cost.stackSize <= slot.stackSize){
						inv.decrStackSize(i, cost.stackSize);
						cost.stackSize = 0;
					} else {
						cost.stackSize -= slot.stackSize;
						inv.decrStackSize(i, slot.stackSize);
					}
				}
			
				if(cost.stackSize <= 0)
					ite.remove();
			}
			
		}
	}

	private boolean affords(List<ItemStack> cost, List<ItemStack> payment) {
		for(ItemStack c : cost){
			if(!affords(c, payment))
				return false;
		}
		return true;
	}
    
    private boolean affords(ItemStack cost, List<ItemStack> payment) {
    	
		for(ItemStack p : payment){
			if(cost.getItem() == p.getItem() && cost.getItemDamage() == p.getItemDamage())
					/*&& ((!cost.hasTagCompound() && !p.hasTagCompound()) || 
						((cost.hasTagCompound() && p.hasTagCompound()) && cost.stackTagCompound.equals(p.stackTagCompound))))*/{
				if(cost.stackSize <= p.stackSize)
					return true;
			}
		}
		return false;
	}
    
    public boolean canFunction(){
		for(ForgeDirection side : inputSides){
			TileEntity ent = BioHelper.getTileEntityOnSide(assembler, side);
			if(ent != null && ent instanceof IInventory)
				return true;
		}
		return false;
	}
    
    public void intoList(ItemStack stack, List<ItemStack> list){
		for(int i = 0; i < list.size(); i++){
			ItemStack slot = list.get(i);
			if(stack.getItem() == slot.getItem() && stack.getItemDamage() == slot.getItemDamage()
					&& ((!stack.hasTagCompound() && !slot.hasTagCompound()) || 
						((stack.hasTagCompound() && slot.hasTagCompound()) && stack.stackTagCompound.equals(slot.stackTagCompound)))){
				slot.stackSize += stack.stackSize;
				return;
			}
		}
		list.add(stack.copy());
	}
    
    public void intoList(IInventory inv, List<ItemStack> list){
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack stack = inv.getStackInSlot(i);
			if(stack == null)
				continue;
			intoList(stack, list);
		}
	}
    
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_)
    {
    	super.updateProgressBar(p_75137_1_, p_75137_2_);
        if (p_75137_1_ == 0)
        {
        	this.assembler.selected = p_75137_2_;
        	this.update();
        }
        
        if (p_75137_1_ == 1)
        {
        	this.assembler.color = p_75137_2_;
        	this.update();
        }
        
        if(1 < p_75137_1_ && p_75137_1_ < 22){
        	this.assembler.values[p_75137_1_-2] = p_75137_2_;
        	this.update();
        }
    }
    
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
    	super.onCraftMatrixChanged(par1IInventory);
    	this.update();
    }


	
	private void update() {
		if(assembler.getWorldObj().isRemote)
			return;
		
		ItemStack result = null;
		
		switch(EnumToken.values()[this.assembler.selected]){
		case FEEDER:
			result = new ItemStack(ItemRegistry.tokenFeeder);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			break;
		case EATER:
			result = new ItemStack(ItemRegistry.tokenEater);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			break;
		case BANK:
			result = new ItemStack(ItemRegistry.tokenBank);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			for(int i = 0; i < EnumResource.values().length;i++){
				int j = assembler.values[i];
				if(j == 1){
					result.stackTagCompound.setDouble(EnumResource.values()[i].name(), 0.0D);
				}
			}
			result.stackTagCompound.setDouble("SIZE", assembler.values[6]*100);
			break;
		case BRIDGE:
			result = new ItemStack(ItemRegistry.tokenBridge);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			break;
		case FILTER:
			result = new ItemStack(ItemRegistry.tokenFilter);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			int[] a = new int[EnumResource.values().length];
			for(int i = 0; i < EnumResource.values().length;i++){
				a[i] = 0;
			}
			for(int i = 0; i < EnumResource.values().length;i++){
				int j = assembler.values[i];
				a[i] = j;
			}
			result.stackTagCompound.setIntArray("FILTER", a);
			break;
		case TRANSFORMER:
			result = new ItemStack(ItemRegistry.tokenTransformer);
			result.getItem().onCreated(result, assembler.getWorldObj(), player);
			result.stackTagCompound.setInteger("PRODUCT", this.assembler.values[0]);
			result.stackTagCompound.setDouble("EFFECTIVNESS", ((double)this.assembler.values[1]) / 100.0D);
			result.stackTagCompound.setDouble("BALANCE", ((double)this.assembler.values[2]) / 100.0D);
			break;
		}
		
		if(this.assembler.color == 0){
			result.setItemDamage(0);
			result.stackTagCompound.setString("CHANNEL", "NONE");
		} else {
			result.setItemDamage(this.assembler.color);
			int trueColor = this.assembler.color-1;
			result.stackTagCompound.setString("CHANNEL", ItemDye.field_150923_a[trueColor]);
		}
		
		
		this.updateCost();
		
		this.craftVisual.setInventorySlotContents(0, result);
		this.detectAndSendChanges();
	}
	
	private void insertCost(ItemStack stack, IInventory inv){
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null){
				inv.setInventorySlotContents(i, stack);
				return;
			}else if(stack.getItem() == slot.getItem() && stack.getItemDamage() == slot.getItemDamage()
					&& ((!stack.hasTagCompound() && !slot.hasTagCompound()) || 
						((stack.hasTagCompound() && slot.hasTagCompound()) && stack.stackTagCompound.equals(slot.stackTagCompound)))){
				slot.stackSize += stack.stackSize;
				return;
			}
		}
	}
	
	private void updateCost() {
		for(int i = 0; i < craftCost.getSizeInventory(); i++){
			if(craftCost.getStackInSlot(i) != null){
				craftCost.decrStackSize(i, craftCost.getStackInSlot(i).stackSize);
				craftCost.setInventorySlotContents(i, null);
			}
		}
		
		boolean psy = false;
		boolean nur = false;
		boolean life = false;
		boolean was = false;
		boolean bio = false;
		boolean raw = false;
		
		this.detectAndSendChanges();
		
		switch(EnumToken.values()[this.assembler.selected]){
		case FEEDER:
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 4), craftCost);
			insertCost(new ItemStack(ItemRegistry.crootBeetle, 4), craftCost);
			insertCost(new ItemStack(Items.wheat, 4), craftCost);
			
			break;
		case EATER:
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 4), craftCost);
			insertCost(new ItemStack(ItemRegistry.kraKenBug, 4), craftCost);
			insertCost(new ItemStack(Items.wheat, 4), craftCost);
			
			break;
		case BANK:
			psy = assembler.values[0] == 1;
			nur = assembler.values[1] == 1;
			life = assembler.values[2] == 1;
			was = assembler.values[3] == 1;
			bio = assembler.values[4] == 1;
			raw = assembler.values[5] == 1;
			int size = assembler.values[6];
			
			if(psy)
				insertCost(new ItemStack(Items.diamond, Math.max(1, 64 * size / 50)), craftCost);
			if(nur)
				insertCost(new ItemStack(Items.coal, Math.max(1, 64 * size / 20)), craftCost);
			if(life)
				insertCost(new ItemStack(Items.apple, Math.max(1, 64 * size / 5)), craftCost);
			if(was)
				insertCost(new ItemStack(Items.bowl, Math.max(1, 64 * size / 5)), craftCost);
			if(bio)
				insertCost(new ItemStack(ItemRegistry.crootBeetle, Math.max(1, 64 * size / 10)), craftCost);
			if(raw)
				insertCost(new ItemStack(Items.wheat, Math.max(1, 64 * size / 5)), craftCost);
			
			insertCost(new ItemStack(Blocks.chest, size), craftCost);
			insertCost(new ItemStack(Blocks.cobblestone, 64 * size / 10), craftCost);
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 64 * size / 20), craftCost);
			insertCost(new ItemStack(Items.wheat, 64 * size / 5), craftCost);
			
			break;
		case BRIDGE:
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 1), craftCost);
			insertCost(new ItemStack(Items.flint, 1), craftCost);
			
			break;
		case FILTER:
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 1), craftCost);
			insertCost(new ItemStack(Items.flint, 1), craftCost);
			insertCost(new ItemStack(Items.stick, 4), craftCost);
			
			psy = assembler.values[0] == 1;
			nur = assembler.values[1] == 1;
			life = assembler.values[2] == 1;
			was = assembler.values[3] == 1;
			bio = assembler.values[4] == 1;
			raw = assembler.values[5] == 1;
			
			if(psy)
				insertCost(new ItemStack(Items.diamond, 2), craftCost);
			if(nur)
				insertCost(new ItemStack(Items.coal, 16), craftCost);
			if(life)
				insertCost(new ItemStack(Items.apple, 32), craftCost);
			if(was)
				insertCost(new ItemStack(Items.bowl, 8), craftCost);
			if(bio)
				insertCost(new ItemStack(ItemRegistry.crootBeetle, 4), craftCost);
			if(raw)
				insertCost(new ItemStack(Items.wheat, 64), craftCost);
			
			break;
		case TRANSFORMER:
			insertCost(new ItemStack(ItemRegistry.chitinPlate, 8), craftCost);
			
			int product = assembler.values[0];
			int effect = Math.max(1, assembler.values[1]);
			int balance = assembler.values[2];
			int byproduct = 100-balance;
			
			insertCost(new ItemStack(Items.wheat, 50 <= balance ? 2 * (balance-50) : 2), craftCost);
			insertCost(new ItemStack(Items.coal, 50 < byproduct ? 4 * (byproduct-50) : 4), craftCost);
			
			psy = product == 0;
			nur = product == 1;
			life = product == 2;
			was = product == 3;
			bio = product == 4;
			raw = product == 5;
			
			//balance = Math.max((50 < byproduct ? byproduct-50 : 1), (50 <= balance ? balance-50 : 1));
			
			insertCost(new ItemStack(Blocks.furnace, Math.max(1, effect / 2)), craftCost);
			
			if(psy){
				insertCost(new ItemStack(Items.diamond, effect <= 50 ? Math.max(1, 1 * effect / 5) : 10 + 2 * (effect-50)), craftCost);
			}
			if(nur){
				insertCost(new ItemStack(Items.coal, Math.max(1, 2 * effect)), craftCost);
			}
			if(life){
				insertCost(new ItemStack(Items.apple, Math.max(1, 2 * effect)), craftCost);
			}
			if(was){
				insertCost(new ItemStack(Items.bowl, Math.max(1, 2 * effect)), craftCost);
			}
			if(bio){
				insertCost(new ItemStack(ItemRegistry.crootBeetle, Math.max(1, 2 * effect)), craftCost);
			}
			if(raw){
				insertCost(new ItemStack(Items.wheat, Math.max(1, 2 * effect)), craftCost);
			}
			break;
		}
		
		if(this.assembler.color != 0){
			insertCost(new ItemStack(Items.dye, 1, this.assembler.color-1), craftCost);
		}
		
		this.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.assembler.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
        int numRows = 34;
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < numRows)
            {
                if (!this.mergeItemStack(itemstack1, numRows, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, numRows, false))
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
            slot.onPickupFromSlot(p_82846_1_, itemstack);
        }
        
        return itemstack;
    }
	
	public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        if(!assembler.getWorldObj().isRemote && this.craftResult.getStackInSlot(0) != null){
	        List<ItemStack> list = new ArrayList<ItemStack>();
	        list.add(this.craftResult.getStackInSlot(0));
	        BioHelper.dropItems(assembler.getWorldObj(), list, assembler.xCoord, assembler.yCoord, assembler.zCoord);
        }
    }
}
