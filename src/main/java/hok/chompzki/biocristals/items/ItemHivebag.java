package hok.chompzki.biocristals.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.GuiHandler;
import hok.chompzki.biocristals.containers.Hivebag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHivebag extends Item {
	
	public static final String NAME = "itemHivebag";

	public ItemHivebag(){
		this.setMaxStackSize(1);
		this.setMaxDamage(500);
		this.setNoRepair();
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(par2World.isRemote || !player.isSneaking())
			return stack;
		if(stack.getTagCompound() == null)
			this.onCreated(stack, par2World, player);
		System.out.println("OPENS GUI!");
		player.openGui(BioCristalsMod.instance, GuiHandler.hivebagId, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.stackTagCompound = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		stack.stackTagCompound.setTag("INVENTORY", nbttaglist);
		stack.stackTagCompound.setBoolean("OPEN", false);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = Items.leather.getIconFromDamage(0);
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
        if(isOpen)
        	return Items.carrot.getIcon(stack, renderPass);
		return getIcon(stack, renderPass);
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
        if(isOpen)
        	return Items.carrot.getIcon(stack, pass);
        return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		if(stack.stackTagCompound == null)
			this.onCreated(stack, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer);
		boolean isOpen = stack.stackTagCompound.getBoolean("OPEN");
        if(isOpen)
        	return Items.carrot.getIcon(stack, 0);
        return this.getIconFromDamage(stack.getItemDamage());
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
	
	public double getDurabilityForDisplay(ItemStack stack)
    {
		int damage = 0;
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("DISTANCE"))
			damage = stack.getTagCompound().getInteger("DISTANCE");
        return 1.0d - ((double)damage / (double)stack.getMaxDamage());
    }
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean par5) {
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			float lastDistance = player.distanceWalkedOnStepModified;
			float currentDistance = player.distanceWalkedOnStepModified;
			if(player.getEntityData().hasKey("HIVECRAFT_PLAYER_LAST_DIST"))
				lastDistance = player.getEntityData().getFloat("HIVECRAFT_PLAYER_LAST_DIST");
			
			float sub_distance = 0.0f;
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("F_DISTANCE"))
				sub_distance = stack.getTagCompound().getFloat("F_DISTANCE");
			int distance = 0;
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("DISTANCE"))
				distance = stack.getTagCompound().getInteger("DISTANCE");
			
			sub_distance += (currentDistance-lastDistance);
			
			if(1.0f <= sub_distance){
				sub_distance--;
				distance++;
				if(!world.isRemote && stack.hasTagCompound()){
					Hivebag bag = new Hivebag(player, slot);
					bag.tickAll();
				}
			}
			if(sub_distance < 0.0f)
				sub_distance = 0.0f;
			
			if(stack.getMaxDamage() <= distance){
				distance = 0;
				if(!world.isRemote && stack.hasTagCompound()){
					player.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
				}
			}
			
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			player.getEntityData().setFloat("HIVECRAFT_PLAYER_LAST_DIST", currentDistance);
			stack.getTagCompound().setFloat("F_DISTANCE", sub_distance);
			stack.getTagCompound().setInteger("DISTANCE", distance);
		}
	}
	
	
}