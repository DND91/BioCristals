package hok.chompzki.hivetera.items.insects;

import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.INestInsect;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemKraKenBug extends ItemFood implements INestInsect{
	
	public static final String NAME = "itemKraKenBug";
	
	public ItemKraKenBug() {
		super(4, 2.0F, false);
        setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        player.getFoodStats().func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int func_150905_g(ItemStack stack)
    {
        return NBTHelper.get(stack, "FOOD_LEVEL", 4);
    }
	
	@Override
    public float func_150906_h(ItemStack stack)
    {
        return NBTHelper.get(stack, "SATURATION", 2.0F);
    }
    
    public void fix(ItemStack stack){
    	NBTHelper.init(stack, "FOOD_LEVEL", 4);
    	NBTHelper.init(stack, "SATURATION", 2.0F);
    	NBTHelper.init(stack, "FOOD", 0.0D);
    }
    
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
    	fix(stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
    	if(20.0D <= this.getDrain(p_77624_1_) || 20.0D <= this.getCost(p_77624_1_)){
			list.add(((char)167) + "4DO NOT USE WITHOUT NETWORK");
		}
		list.add("Food: " + I18n.format("container."+EnumResource.WASTE, new Object[0]));
		list.add("Use Cost: " + this.getCost(p_77624_1_));
		list.add("Drainage: " + this.getDrain(p_77624_1_));
		double food = NBTHelper.get(p_77624_1_, "FOOD", 0.0D);
		DecimalFormat df = new DecimalFormat("0.00"); 
		list.add("Stored: " + df.format(food));
    	list.add("We will never behave!");
    }

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		return "I will help animals grow!";
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityAgeable> list = world.getEntitiesWithinAABB(EntityAgeable.class, bb.expand(3, 3, 3));
		for(EntityAgeable ent : list){
			if(ent.isChild())
				return true;
		}
		
		return false;
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		if(world.isRemote)
			return;
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.xCoord, entity.yCoord, entity.zCoord, entity.xCoord + 1, entity.yCoord + 1, entity.zCoord + 1);
		List<EntityAgeable> list = world.getEntitiesWithinAABB(EntityAgeable.class, bb.expand(3, 3, 3));
		
		for(EntityAgeable ent : list){
			if(ent.isChild()){
				ent.addGrowth(5);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		ItemStack s = new ItemStack(item, 1, 0);
		this.onCreated(s, null, null);
		list.add(s);
    }
	
	public boolean getShareTag()
    {
        return true;
    }

	@Override
	public int workSpan(ItemStack stack) {
		return 64 / stack.stackSize;
	}

	@Override
	public double getCost(ItemStack stack) {
		return 1.0D * stack.stackSize;
	}

	@Override
	public double getDrain(ItemStack stack) {
		return 10.0D * stack.stackSize;
	}

	@Override
	public EnumResource getFoodType(ItemStack stack) {
		return EnumResource.WASTE;
	}

	public double getFood(ItemStack stack){
		NBTHelper.init(stack, "FOOD", 0.0D);
		double food = stack.stackTagCompound.getDouble("FOOD");
		food = Math.max(0.0D, food);
		food = Math.min(food, this.getDrain(stack));
		return food;
	}
	
	public void setFood(ItemStack stack, double food){
		NBTHelper.init(stack, "FOOD", 0.0D);
		food = Math.max(0.0D, food);
		food = Math.min(food, this.getDrain(stack));
		stack.stackTagCompound.setDouble("FOOD", food);
	}

}
