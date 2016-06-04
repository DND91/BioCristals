package hok.chompzki.hivetera.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.NBTHelper;
import hok.chompzki.hivetera.containers.InsectHunt;
import hok.chompzki.hivetera.registrys.BiomeRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.GuiHandler;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.tile_enteties.TileNest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ItemCrootStick extends Item {
	
	/**
	 * NEW INSECT HUNT SYSTEM
	 * Player brakes blocks that gives it a chance to gain a random token inside of the new hunt insect gui grid, random position
	 * Inside this gui players can either unlock a token and gain a random insect OR
	 * destroy a token gaining a point.
	 * 2 points can be spent on gaining a new random token  OR
	 * 4 points can be spent on gaining a specific token
	 * 
	 * Does token open inside of gui or in player hand?
	 * 		
	 * What are the rules for opening a token? 
	 * 		Open Hor or Ver like Candy Crush, the more you gain the better reward.
	 *  	Click the token inside the gui with a speciall selection button and gain a reward?
	 * 
	 * Tokens are biome bound and have a chance to give a insect found in that biome
	 */
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public final static String NAME = "itemCrootStick";
	private Random random = new Random();
	
	public ItemCrootStick(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
		this.icons = new IIcon[2];
		this.itemIcon = iconReg.registerIcon(this.getIconString());
		this.icons[0] = iconReg.registerIcon(this.getIconString() + "_full");
		this.icons[1] = iconReg.registerIcon(this.getIconString() + "_empty");
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		boolean has = NBTHelper.get(stack, "BEETLE", false);
		if(has)
			return this.icons[0];
		return this.icons[1];
    }
	
	public IIcon getIcon(ItemStack stack, int pass)
    {
		boolean has = NBTHelper.get(stack, "BEETLE", false);
		if(has)
			return this.icons[0];
		return this.icons[1];
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		boolean has = NBTHelper.get(stack, "BEETLE", false);
		if(has)
			return this.icons[0];
		return this.icons[1];
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
    {
		if(par2World.isRemote)
			return stack;
		this.onCreated(stack, par2World, player);
		
		player.openGui(HiveteraMod.instance, 111, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
	
	private boolean hasInInventory(Item item, EntityPlayer player){
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack == null)
				continue;
			if(stack.getItem() == item){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean canHarvest(Block block){
		Material material = block.getMaterial();
		
		if(Blocks.dirt == block || Blocks.grass == block)
			return false;
		
		if(Material.leaves != material && Material.grass != material
				&& Material.plants != material
				&& Material.vine != material
				&& Material.snow != material
				&& Material.ice != material)
			return false;
		return true;
	}
	
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
		
		if(!(living instanceof EntityPlayer) || world.isRemote)
			return false;
		
		Material material = block.getMaterial();
		
		if(!canHarvest(block))
			return false;
		
		EntityPlayer player = (EntityPlayer)living;
		InventoryPlayer inv = player.inventory;
		
		if(!world.isRemote && block.getMaterial() == Material.leaves && random.nextInt(ConfigRegistry.krakenChance) % ConfigRegistry.krakenChance == 0){ //Add chance kraken bug
			float f = this.random.nextFloat() * 0.8F + 0.1F;
            float f1 = this.random.nextFloat() * 0.8F + 0.1F;
            float f2 = this.random.nextFloat() * 0.8F + 0.1F;
            
			EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(ItemRegistry.kraKenBug, 1));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)this.random .nextGaussian() * f3);
            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
		}
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		
		Type[] list = BiomeDictionary.getTypesForBiome(biome);
		for(Type type : list)
		if(BiomeRegistry.biomeToId.containsKey(type)){
			//TODO: ADD SOME KIND OF CHANCE AND BLOCK CONTROLL!
			
			InsectHunt hunt = new InsectHunt(player, player.inventory.currentItem);
			
			if(!player.capabilities.isCreativeMode && (hunt.getStackInSlot(0) == null || 
					hunt.getStackInSlot(0).getItem() != ItemRegistry.crootBeetle || 
					hunt.getStackInSlot(0).stackSize <= 0))
					break;
			
			int slot = 1 + random.nextInt(5) + random.nextInt(5) * 5;
			
			if(hunt.getStackInSlot(slot) == null){
				int id = BiomeRegistry.biomeToId.get(type);
				hunt.setInventorySlotContents(slot, new ItemStack(ItemRegistry.biomeSample, 1, id));
				
				if(!player.capabilities.isCreativeMode && random.nextInt(4) == 0)
					hunt.decrStackSize(0, 1);
			}
		}
		
		return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		list.add("Right click to open gui");
		list.add("Remember to always keep it filled with Croot Beetles!");
		
	}
}
