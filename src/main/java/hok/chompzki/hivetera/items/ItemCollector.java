package hok.chompzki.hivetera.items;

import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import scala.tools.nsc.interpreter.IMain.ReadEvalPrint.EvalException;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IGrowthCristal;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCollector extends Item {
	
	public final static String NAME = "itemCollector";
    private IIcon[] icons;
    private final static String[] iconNames={"Open","Close"};
    public short iconState;


	public ItemCollector(){
		setUnlocalizedName(HiveteraMod.MODID + "_" + NAME);
		setCreativeTab(HiveteraMod.creativeTab);
        iconState=0;
        setTextureName(HiveteraMod.MODID + ":" + NAME+iconNames[1]);
        this.setMaxStackSize(1);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		if(player.worldObj.isRemote)
			return;
		
		if(count%40==0) {
            this.iconState = 1;
            player.stopUsingItem();
            player.clearItemInUse();
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            collect(player, stack);
        }else if(count%40==5 && this.iconState==1){
            this.iconState=0;
		}
    }
	
	public class Posistion implements Comparable{
		public int x;
		public int y;
		public int z;
		
		public Posistion(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public boolean equals(Object obj){
		    if (obj == null) return false;
		    if (obj == this) return true;
		    if (!(obj instanceof Posistion))return false;
		    Posistion other = (Posistion)obj;
		    return other.x == x && other.y == y && other.z == z;
		}
		
		@Override
		public int compareTo(Object obj) {
			if (obj == null) return -1;
		    if (obj == this) return 0;
		    if (!(obj instanceof Posistion))return 1;
		    Posistion b = (Posistion)obj;
		    if (x < b.x) return -1;
	        if (x > b.x) return +1;
			if (y < b.y) return -1;
	        if (y > b.y) return +1;
	        if (z < b.z) return -1;
	        if (z > b.z) return +1;
	        
			return 0;
		}
	}
	
	public class ComparablePosistion implements Comparator<Posistion> {
		@Override
		public int compare(Posistion a, Posistion b) {
			if (a.x < b.x) return -1;
	        if (a.x > b.x) return +1;
			if (a.y < b.y) return -1;
	        if (a.y > b.y) return +1;
	        if (a.z < b.z) return -1;
	        if (a.z > b.z) return +1;
	        
			return 0;
		}
	}
	
	public void collect(EntityPlayer player, ItemStack stack){
		World world = player.worldObj;
		
		MovingObjectPosition mop = DataHelper.rayTrace(player, 6.0f, 1.0f);
		
		int dx = mop.blockX;
		int dy = mop.blockY;
		int dz = mop.blockZ;
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		
		SortedSet<Posistion> closed = new TreeSet<Posistion>(new ComparablePosistion());
		Queue<Posistion> open = new ArrayDeque<Posistion>();
		open.add(new Posistion(dx, dy, dz));
		int blocks = 0;
		try{
			while(!open.isEmpty()){
				Posistion pos = open.poll();
				
				Block block = world.getBlock(pos.x, pos.y, pos.z);
				if(block == null || !(block instanceof IGrowthCristal)){
					closed.add(pos);
					continue;
				}
				
				IGrowthCristal cristal = (IGrowthCristal)block;
				if(cristal.isMature(world, player, stack, pos.x, pos.y, pos.z))
					cristal.harvest(world, player, stack, pos.x, pos.y, pos.z, stacks);
				blocks++;
				
				closed.add(pos);
				
				if(ConfigRegistry.maxBlocksCollector < blocks)
					break;
				
				for(int x = pos.x - 1; x < pos.x + 2; x++)
				for(int y = pos.y - 1; y < pos.y + 2; y++)
				for(int z = pos.z - 1; z < pos.z + 2; z++){
					Posistion tmp = new Posistion(x, y, z);
					if(closed.contains(tmp) || tmp.equals(pos) || open.contains(tmp))
						continue;
					
					open.add(tmp);
				}
				
			}
		}catch (Exception ex){
			System.out.println("SOME KIND OF ERROR WHEN WORKING WITH THE COLLECTOR OF BIOCRISTALS!!!\n"  + ex.getLocalizedMessage());
		}
		
		BioHelper.dropItems(world, stacks, (int)player.posX, (int)player.posY + 1, (int)player.posZ);
	}


    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
       icons = new IIcon[iconNames.length];
        for (int it=0;it<iconNames.length;++it){
            icons[it]=iconRegister.registerIcon(HiveteraMod.MODID + ":" + NAME+iconNames[it]);
        }
        itemIcon=icons[this.iconState];
        }
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass){
        if(renderPass==0) {
            if (this.iconState == 0) return icons[0];
            if (this.iconState == 1) return icons[1];
        }
         return icons[0];

    }

}

