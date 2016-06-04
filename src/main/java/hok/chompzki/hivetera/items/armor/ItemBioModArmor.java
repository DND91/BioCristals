package hok.chompzki.hivetera.items.armor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.containers.BioArmor.BioArmorBase;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.registrys.ArmorPatternRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class ItemBioModArmor extends ItemArmor implements ISpecialArmor, IInsect {
	
	public static ArrayList<ModifierInsect> modifiers = new ArrayList<ModifierInsect>();
	
	@SideOnly(Side.CLIENT)
	private IIcon overlay;
	@SideOnly(Side.CLIENT)
	private static IIcon offensive;
	@SideOnly(Side.CLIENT)
	private static IIcon passive;
	@SideOnly(Side.CLIENT)
	private static IIcon defensive;
	@SideOnly(Side.CLIENT)
	private static IIcon eater;
	@SideOnly(Side.CLIENT)
	private static IIcon func;
	
	private String txt = "";
	
	
	public ItemBioModArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
	    super(material, 0, type);
	    this.txt = textureName;
	    this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(HiveteraMod.MODID + ":" + unlocalizedName);
	    this.setCreativeTab(HiveteraMod.creativeTab);
	    this.setHasSubtypes(true);
	    this.canRepair = true;
	    
	}
	
	public boolean isDamageable()
    {
        return true;
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return HiveteraMod.MODID + ":textures/armor/" + this.txt + "_" + (this.armorType == 2 ? "2" : "1") +"_"+ NBTHelper.get(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]) + ".png";
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		return super.onItemRightClick(stack, world, player);
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		int i = 0;
		for(String pattern : ArmorPatternRegistry.pattern_names){
			ItemStack s = new ItemStack(item, 1, 0);
			NBTHelper.init(s, "PATTERN", pattern);
			
			this.onCreated(s, null, null);
			list.add(s);
			i++;
		}
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		NBTHelper.init(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]);
		if(!stack.stackTagCompound.hasKey("INVENTORY")){
			NBTTagList nbttaglist = new NBTTagList();
			stack.stackTagCompound.setTag("INVENTORY", nbttaglist);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		list.add("Pattern: " + I18n.format("container."+NBTHelper.get(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]), new Object[0]));
		list.add("Protection: " + ((ItemBioModArmor)stack.getItem()).getDamageReduceAmount(stack));
		list.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage());
		
		BioArmor armor = new BioArmor(stack);
		if(armor.getSizeInventory() <= 0)
			return;
	
		if(!advancedTooltip && !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))){
			list.add("...");
			return;
		}
		
		for(BioArmorBase base : armor.inventory){
			if(base.slot == null)
				continue;
			list.add(" - " + base.slot.getDisplayName());
		}
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack p_82790_1_, int pass)
    {
		return this.getColor(p_82790_1_);
        /*if (pass == 0)
        {
            return 10511680;
        }
        else
        {
            int j = this.getColor(p_82790_1_);

            if (j < 0)
            {
                j = 10511680;
            }
            
            return j;
        }*/
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    public boolean hasColor(ItemStack p_82816_1_)
    {
        return true;
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    @Override
    public int getColor(ItemStack stack)
    {
    	String color = NBTHelper.get(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]);
    	
    	for(int i = 0; i < ArmorPatternRegistry.pattern_names.length; i++){
    		if(ArmorPatternRegistry.pattern_names[i].equals(color)){
    			return ArmorPatternRegistry.pattern_colors[i];
    		}
    	}
    	
    	return ArmorPatternRegistry.pattern_colors[0];
    }
    
    @Override
    public int getRenderPasses(int metadata){
    	return 2;
    }
    
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ == 1 ? this.overlay : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        
        this.overlay = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
        this.offensive = p_94581_1_.registerIcon(HiveteraMod.MODID + ":icons/offensive");
        this.defensive = p_94581_1_.registerIcon(HiveteraMod.MODID + ":icons/defensive");
        this.eater = p_94581_1_.registerIcon(HiveteraMod.MODID + ":icons/eater");
        this.func = p_94581_1_.registerIcon(HiveteraMod.MODID + ":icons/func");
        this.passive = p_94581_1_.registerIcon(HiveteraMod.MODID + ":icons/passive");
        
    }
    
	public static IIcon getBackgroundIcon(SocketType type) {
		switch(type){
		case OFFENSIVE:
			return offensive;
		case DEFENSIVE:
			return defensive;
		case EATER:
			return eater;
		case FUNC:
			return func;
		case PASSIVE:
			return passive;
		default:
			return null;
		}
	}
	
	//ARMOR AND INSERCTS WANTS ALL OF THESE STUFF
	
	public void setDamage(ItemStack stack, int damage)
    {
        super.setDamage(stack, Math.min(damage, this.getMaxDamage(stack)));
    }
	
	public static BioArmor[] getArmors(EntityPlayer player){
		BioArmor[] armors = new BioArmor[4];
		for(int i = 0; i < 4; i++){
			ItemStack stack = player.inventory.getStackInSlot(player.inventory.getSizeInventory() - 1 - i);
			if(stack == null || !(stack.getItem() instanceof ItemBioModArmor))
				armors[i] = null;
			else
				armors[i] = new BioArmor(stack);
		}
		return armors;
	}
	
	public static boolean contains(EntityPlayer player, IArmorInsect insect){
		BioArmor[] armors = getArmors(player);
		for(BioArmor armor : armors){
			if(armor == null || (armor.isBroken()))
				continue;
			for(int i = 0; i < armor.getSizeInventory(); i++){
				if(armor.getStackInSlot(i) != null && armor.getStackInSlot(i).getItem() == insect)
					return true;
			}
		}
		
		return false;
	}
	
	public static ItemStack getInsect(EntityPlayer player, IArmorInsect insect){
		BioArmor[] armors = getArmors(player);
		for(BioArmor armor : armors){
			if(armor == null || (armor.isBroken()))
				continue;
			for(int i = 0; i < armor.getSizeInventory(); i++){
				if(armor.getStackInSlot(i) != null && armor.getStackInSlot(i).getItem() == insect)
					return armor.getStackInSlot(i);
			}
		}
		
		return null;
	}
	
	public int getMaxDamage(ItemStack stack)
    {
		int dmg = this.getMaxDamage();
		BioArmor armor = new BioArmor(stack);
		
		for(int i = 0; i < armor.getSizeInventory(); i++){
			if(armor.getStackInSlot(i) != null && armor.getStackInSlot(i).getItem() instanceof IArmorInsect){
				IArmorInsect insect = (IArmorInsect)armor.getStackInSlot(i).getItem();
				dmg += insect.addMaxDamage(dmg, armor, i);
			}
		}
		
        return dmg;
    }
	
	public int getDamageReduction(ItemStack stack)
    {
		int dmg = this.damageReduceAmount;
		BioArmor armor = new BioArmor(stack);
		
		for(int i = 0; i < armor.getSizeInventory(); i++){
			if(armor.getStackInSlot(i) != null && armor.getStackInSlot(i).getItem() instanceof IArmorInsect){
				IArmorInsect insect = (IArmorInsect)armor.getStackInSlot(i).getItem();
				dmg += insect.getDamageReduction(dmg, armor, i);
			}
		}
		
        return dmg;
    }
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
		ItemBioModArmor a = (ItemBioModArmor)itemStack.getItem();
		
		BioArmor[] armors = getArmors(player);
		
		for(int i = 0; i < armors[a.armorType].getSizeInventory(); i++){
			if(armors[a.armorType].getStackInSlot(i) != null && armors[a.armorType].getStackInSlot(i).getItem() instanceof IArmorInsect){
				IArmorInsect insect = (IArmorInsect)armors[a.armorType].getStackInSlot(i).getItem();
				if(insect.shouldWork(world, player, armors, a.armorType, i))
					insect.onArmorTick(world, player, armors, a.armorType, i);
			}
		}
    }
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase entity,
			ItemStack armor, DamageSource source, double damage, int slot) {
		if(!(entity instanceof EntityPlayer))
			return null;
		
		EntityPlayer player = (EntityPlayer)entity;
		
		ItemBioModArmor a = (ItemBioModArmor)armor.getItem();
		ArmorProperties prop = new ArmorProperties(a.armorType, this.getDamageReduceAmount(armor) / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
		
		BioArmor[] armors = getArmors(player);
		
		for(int i = 0; i < armors[a.armorType].getSizeInventory(); i++){
			if(armors[a.armorType].getStackInSlot(i) != null && armors[a.armorType].getStackInSlot(i).getItem() instanceof IArmorInsect){
				IArmorInsect insect = (IArmorInsect)armors[a.armorType].getStackInSlot(i).getItem();
				if(insect.shouldWork(player.worldObj, player, armors, a.armorType, i))
					insect.addProperties(prop, armors, player, source, damage, a.armorType, i);
			}
		}
		return prop;
	}
	
	public int getDamageReduceAmount(ItemStack stack){
		return (int)Math.ceil((double)this.getDamageReduction(stack) * (((double)stack.getMaxDamage() - (double)stack.getItemDamage()) / (double)stack.getMaxDamage()));
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return getDamageReduceAmount(armor);
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer)entity;
		ItemBioModArmor a = (ItemBioModArmor)stack.getItem();
		
		BioArmor[] armors = getArmors(player);
		
		for(int i = 0; i < armors[a.armorType].getSizeInventory(); i++){
			if(armors[a.armorType].getStackInSlot(i) != null && armors[a.armorType].getStackInSlot(i).getItem() instanceof IArmorInsect){
				IArmorInsect insect = (IArmorInsect)armors[a.armorType].getStackInSlot(i).getItem();
				if(insect.shouldWork(player.worldObj, player, armors, a.armorType, i))
					insect.damageArmor(player.worldObj, player, armors, a.armorType, i, source, damage);
			}
		}
		
		stack.damageItem(damage, player);
	}

	@Override
	public double getCost(ItemStack stack) {
		return 10;
	}
	
	@Override
	public double getDrain(ItemStack stack) {
		return 10;
	}

	@Override
	public EnumResource getFoodType(ItemStack stack) {
		String pattern = NBTHelper.get(stack, "PATTERN", ArmorPatternRegistry.pattern_names[0]);
		for(int i = 0; i < ArmorPatternRegistry.pattern_names.length; i++){
    		if(ArmorPatternRegistry.pattern_names[i].equals(pattern)){
    			return ArmorPatternRegistry.pattern_food[i];
    		}
    	}
		return null;
	}

	@Override
	public double getFood(ItemStack stack) {
		return NBTHelper.get(stack, "FOOD", 0.0D);
	}

	@Override
	public void setFood(ItemStack stack, double food) {
		NBTHelper.init(stack, "FOOD", food);
		stack.getTagCompound().setDouble("FOOD", food);
	}
    
}
