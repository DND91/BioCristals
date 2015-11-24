package hok.chompzki.biocristals.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemKraKenBug extends ItemFood {
	
	public static final String NAME = "itemKraKenBug";
	
	public ItemKraKenBug() {
		super(1, 1, false);
        setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        player.getFoodStats().func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);
        return stack;
    }
	
	public int func_150905_g(ItemStack stack)
    {
		fix(stack);
		NBTTagCompound nbt = stack.getTagCompound();
        return nbt.getInteger("FOOD");
    }

    public float func_150906_h(ItemStack stack)
    {
    	fix(stack);
    	NBTTagCompound nbt = stack.getTagCompound();
        return nbt.getFloat("SATURATION");
    }
    
    public void fix(ItemStack stack){
    	if(stack.getTagCompound() == null){
    		stack.setTagCompound(new NBTTagCompound());
        	NBTTagCompound nbt = stack.getTagCompound();
        	nbt.setInteger("FOOD", 4);
        	nbt.setFloat("SATURATON", 0.8F);
    	}
    }
    
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
    	fix(stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
    	list.add("We will never behave!");
    }

}
