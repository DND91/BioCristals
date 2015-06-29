package hok.chompzki.biocristals.items;

import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import scala.tools.nsc.interpreter.IMain.ReadEvalPrint.EvalException;
import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.ICristal;
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
	
	public ItemCollector(){
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 50;
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		if(player.worldObj.isRemote)
			return;
		
		if(count <= 10){
			player.stopUsingItem();
			player.clearItemInUse();
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
			collect(player, stack);
		}
    }
	
	private class Posistion{
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
		Minecraft mc = Minecraft.getMinecraft();
		int dx = mc.objectMouseOver.blockX;
		int dy = mc.objectMouseOver.blockY;
		int dz = mc.objectMouseOver.blockZ;
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		
		SortedSet<Posistion> closed = new TreeSet<Posistion>(new ComparablePosistion());
		Queue<Posistion> open = new ArrayDeque<Posistion>();
		open.add(new Posistion(dx, dy, dz));
		try{
			while(!open.isEmpty()){
				Posistion pos = open.poll();
				if(world.isAirBlock(pos.x, pos.y, pos.z)){
					closed.add(pos);
					continue;
				}
				Block block = world.getBlock(pos.x, pos.y, pos.z);
				if(!(block instanceof ICristal)){
					closed.add(pos);
					continue;
				}
				
				ICristal cristal = (ICristal)block;
				if(cristal.isMature(world, player, stack, pos.x, pos.y, pos.z))
					cristal.harvest(world, player, stack, pos.x, pos.y, pos.z, stacks);
				
				for(int x = pos.x - 1; x < pos.x + 2; x++)
				for(int y = pos.y - 1; y < pos.y + 2; y++)
				for(int z = pos.z - 1; z < pos.z + 2; z++){
					Posistion tmp = new Posistion(x, y, z);
					if(closed.contains(tmp) || tmp.equals(pos))
						continue;
					open.add(tmp);
				}
				closed.add(pos);
			}
		}catch (Exception ex){
			System.out.println("SOME KIND OF ERROR WHEN WORKING WITH THE COLLECTOR OF BIOCRISTALS!!!\n"  + ex.getLocalizedMessage());
		}
		BioHelper.dropItems(world, stacks, (int)player.posX, (int)player.posY + 1, (int)player.posZ);
	}
}

