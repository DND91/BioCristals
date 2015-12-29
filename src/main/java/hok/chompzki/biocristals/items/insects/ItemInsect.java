package hok.chompzki.biocristals.items.insects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;
import hok.chompzki.biocristals.items.token.ItemToken;
import hok.chompzki.biocristals.research.data.DataHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public abstract class ItemInsect extends Item implements IInsect {
	
	protected final EnumResource foodType;
	protected final double cost;
	protected final double drain;
	
	public ItemInsect(EnumResource foodType, double cost, double drain){
		this.foodType = foodType;
		this.cost = cost;
		this.drain = drain;
		this.setMaxStackSize(1);
	}
	
	public static List<ItemStack> getTokens(IInventory inv, EnumToken token){
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack stack = inv.getStackInSlot(i);
			if(stack == null)
				continue;
			if((stack.getItem() instanceof ItemToken) && ((IToken)stack.getItem()).getType(stack) == token){
				list.add(stack);
			}
		}
		return list;
	}
	
	public void feed(EntityPlayer player, ItemStack stack, boolean forceFeed){
		if(player.capabilities.isCreativeMode)
			return;
		if(cost == 0.0D || drain == 0.0D)
			return;
		double food = stack.stackTagCompound.getDouble("FOOD");
		
		if(cost <= food && !forceFeed){
			food -= cost;
			stack.stackTagCompound.setDouble("FOOD", food);
			return;
		}
		
		List<ItemStack> banks = getTokens(player.inventory, EnumToken.BANK);
		List<ItemStack> eaters = getTokens(player.inventory, EnumToken.EATER);
		
		
		if(0 < banks.size()){
			for(ItemStack bank : banks){
				IToken token = (IToken)bank.getItem();
				ResourcePackage pack = new ResourcePackage();
				token.drain(bank, pack, drain);
				food += pack.get(foodType);
				pack.put(foodType, 0.0D);
				token.feed(bank, pack);
				
				if(cost <= food){
					food -= cost;
					stack.stackTagCompound.setDouble("FOOD", food);
					return;
				}
			}
		}
		
		if(0 < eaters.size()){
			ResourcePackage mainPack = new ResourcePackage();
			List<ResourcePackage> packs = new ArrayList<ResourcePackage>();
			
			for(ItemStack eater : eaters){
				IToken token = (IToken)eater.getItem();
				ResourcePackage pack = new ResourcePackage();
				token.drain(eater, pack, drain);
				food += pack.get(foodType);
				pack.put(foodType, 0.0D);
				packs.add(pack);
				stack.stackTagCompound.setDouble("FOOD", food);
				if(cost <= food){
					food -= cost;
					stack.stackTagCompound.setDouble("FOOD", food);
					mainPack.combine(packs);
					drawbacks(player, stack, mainPack);
					return;
				}
			}
			
			mainPack.combine(packs);
			drawbacks(player, stack, mainPack);
		}
		
		feedFromPlayer(player, stack);
	}
	
	public void feedFromPlayer(EntityPlayer player, ItemStack stack){
		FoodStats foodStats = player.getFoodStats();
		double drainage = drain;
		double food = stack.stackTagCompound.getDouble("FOOD");
		
		double saturation = foodStats.getSaturationLevel();
		
		if(saturation < drainage){
			food += saturation;
			drainage -= saturation;
			saturation = 0.0D;
			foodStats.setFoodSaturationLevel((float) saturation);
		} else if (drainage <= saturation) {
			food += drainage;
			saturation -= drainage;
			drainage = 0.0D;
			foodStats.setFoodSaturationLevel((float) saturation);
		}
		if(drainage == 0.0D){
			food -= cost;
			food = Math.max(0.0D, food);
			stack.stackTagCompound.setDouble("FOOD", food); //S06PacketUpdateHealth
			((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
			return;
		}
		
		int foodLevel = foodStats.getFoodLevel();
		
		if(foodLevel < drainage){
			food += foodLevel;
			drainage -= foodLevel;
			foodLevel = 0;
			foodStats.setFoodLevel(foodLevel);
		} else if (drainage <= foodLevel) {
			food += drainage;
			foodLevel -= drainage;
			drainage = 0.0D;
			foodStats.setFoodLevel(foodLevel);
		}
		
		if(drainage == 0.0D){
			food -= cost;
			food = Math.max(0.0D, food);
			stack.stackTagCompound.setDouble("FOOD", food);
			((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
			return;
		}
		
		float health = player.getHealth();
		float damage = (float) Math.min(health, drainage);
		player.attackEntityFrom(DamageSource.starve, damage);
		food += damage;
		food -= cost;
		food = Math.max(0.0D, food);
		stack.stackTagCompound.setDouble("FOOD", food);
		((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
	}
	
	public void drawbacks(EntityPlayer player, ItemStack stack, ResourcePackage pack){
		if(pack.total <= 0.0D)
			return;
		
		
		
	}
	
	public static void drawbacks(TileEntity tile, ItemStack eater, ResourcePackage pack){
		if(pack.total <= 0.0D)
			return;
		
		
		
	}
	
	public static double[] drain(TileEntity tile, boolean pit, ItemStack input, double drain, EnumResource... res){
		double[] v = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
		if(input.getItem() instanceof ItemFood){
			ItemFood food = (ItemFood)input.getItem();
			if(!pit)
				v[EnumResource.RAW_FOOD.toInt()] = food.func_150905_g(input) * food.func_150906_h(input) * 2.0D * 0.125D;
			else
				v[EnumResource.RAW_FOOD.toInt()] = food.func_150905_g(input) * food.func_150906_h(input) * 2.0D;
			input.stackSize--;
		}else if((input.getItem() instanceof ItemToken) && ((IToken)input.getItem()).getType(input) == EnumToken.EATER){
			IToken eater = (IToken)input.getItem();
			ResourcePackage pack = new ResourcePackage();
			eater.drain(input, pack, drain);
			for(EnumResource r : res){
				v[r.toInt()] = pack.get(r);
				pack.put(r, 0.0D);
			}
			ItemInsect.drawbacks(tile, input, pack);
		}else if((input.getItem() instanceof ItemToken) && ((IToken)input.getItem()).getType(input) == EnumToken.BANK){
			IToken bank = (IToken)input.getItem();
			ResourcePackage pack = new ResourcePackage();
			bank.drain(input, pack, drain);
			for(EnumResource r : res){
				v[r.toInt()] = pack.get(r);
				pack.put(r, 0.0D);
			}
			bank.feed(input, pack);
		}
		return v;
	}
	
	
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(stack.hasTagCompound())
			return;
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setDouble("FOOD", 0.0D);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		if(20.0D <= cost || 20.0D <= drain){
			list.add("§4DO NOT USE WITHOUT NETWORK");
		}
		list.add("Food: " + I18n.format("container."+foodType, new Object[0]));
		list.add("Use Cost: " + cost);
		list.add("Drainage: " + drain);
		double food = stack.stackTagCompound == null ? 0.0D : stack.stackTagCompound.getDouble("FOOD");
		DecimalFormat df = new DecimalFormat("0.00"); 
		list.add("Stored: " + df.format(food));
	}
	
	public boolean getShareTag()
    {
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		ItemStack s = new ItemStack(item, 1, 0);
		s.stackTagCompound = new NBTTagCompound();
		this.onCreated(s, null, null);
		list.add(s);
    }
	
	@Override
	public double getCost(ItemStack stack) {
		return cost;
	}

	@Override
	public double getDrain(ItemStack stack) {
		return drain;
	}

	@Override
	public EnumResource getFoodType(ItemStack stack) {
		return foodType;
	}
	
	public double getFood(ItemStack stack){
		return stack.stackTagCompound.getDouble("FOOD");
	}
	
	public void setFood(ItemStack stack, double food){
		stack.stackTagCompound.setDouble("FOOD", food);
	}
}
