package hok.chompzki.biocristals.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.ICristal;
import hok.chompzki.biocristals.api.IStructure;
import hok.chompzki.biocristals.blocks.BlockBiomass;
import hok.chompzki.biocristals.registrys.CristalRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemAttuner extends Item {
	public static final String[] bowPullIconNameArray = new String[] {"stage_0", "stage_1", "stage_2"};
	@SideOnly(Side.CLIENT)
    private IIcon[] iconArray;
	
	
	public final static String NAME = "itemAttuner";
	
	public ItemAttuner(){
		setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(CreativeTabs.tabMisc);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useTicks)
    {
		if(world.isRemote)
			return;
		
		if((this.getMaxItemUseDuration(stack) - 100) <= useTicks){
			int x = Minecraft.getMinecraft().objectMouseOver.blockX;
			int y = Minecraft.getMinecraft().objectMouseOver.blockY;
			int z = Minecraft.getMinecraft().objectMouseOver.blockZ;
			if(world.isAirBlock(x, y, z))
				return;
			if(world.getBlock(x, y, z) instanceof BlockBiomass){
				IStructure struct = CristalRegistry.get(stack, player, world, x, y, z);
				if(struct == null){
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Envoirment not adopted for any crystaline transformation..."));
					return;
				}
				
				EntityItem item = BioHelper.getFirstEntityItemWithinAABB(world, player, ItemRegistry.bioReagent, 10, 10, 10);
				if(item == null){
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Crystaline transformation is missing a reagent..."));
					return;
				}
				
				Block block = world.getBlock(x, y, z);
				struct.pay(stack, player);
				struct.construct(stack, player, world, x, y, z);
				
				item.getEntityItem().stackSize--;
				if(item.getEntityItem().stackSize <= 0)
					item.setDead();
				
			}else if(world.getBlock(x, y, z) instanceof ICristal){
				ICristal cristal = (ICristal) world.getBlock(x, y, z);
				if(cristal.isMature(world, player, stack, x, y, z)){
					cristal.harvest(world, player, stack, x, y, z);
				}
			}
		}
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString());
        this.iconArray = new IIcon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = p_94581_1_.registerIcon(this.getIconString() + "_" + bowPullIconNameArray[i]);
        }
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int p_94599_1_)
    {
        return this.iconArray[p_94599_1_];
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
	        if (usingItem == null) { return itemIcon; }
	        int ticksInUse = stack.getMaxItemUseDuration() - useRemaining;
	        if (ticksInUse > 17) {
	            return iconArray[2];
	        } else if (ticksInUse > 13) {
	            return iconArray[1];
	        } else if (ticksInUse > 0) {
	            return iconArray[0];
	        } else {
	            return itemIcon;
	        }
    }
}
