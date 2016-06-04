package hok.chompzki.hivetera.items.insects;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemUnlockBug extends ItemFood{
	
	public static final String NAME = "itemUnlockBug";
	
	public ItemUnlockBug() {
		super(1, 1, false);
        setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.insectTab);
		setTextureName(HiveteraMod.MODID + ":" + NAME);
		setAlwaysEdible();
	}
	
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
            
        return p_77659_1_;
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		--stack.stackSize;
		if(world.isRemote)
			return stack;
        
        UUID id = player.getGameProfile().getId();
        PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
        if(research == null){
			System.out.println("UNKOWN PLAYER TRIED UNLOCK BUG WITHOUT REGISTATION: " + player.getDisplayName());
			return stack;
		}
        for(Research master : ReserchDataNetwork.instance().getMasters()){
        	unlockAll(research, master.getCode());
        }
        
        return stack;
    }
	
	public void unlockAll(PlayerResearch research, String code){
		if(research.hasCompleted(code))
			return;
		
		ResearchLogicNetwork.instance().compelte(research, code);
		
		for(String child : ReserchDataNetwork.instance().getChildren(code)){
			unlockAll(research, child);
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
    	list.add("Unlocks all researchs...");
    	
    }
    

}
