package hok.chompzki.biocristals.items.insects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hok.chompzki.biocristals.NBTHelper;
import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.api.IToken;
import hok.chompzki.biocristals.containers.HoneyWidow;
import hok.chompzki.biocristals.hunger.drawbacks.Drawback;
import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.hunger.logic.EnumToken;
import hok.chompzki.biocristals.hunger.logic.ResourcePackage;
import hok.chompzki.biocristals.items.token.ItemToken;
import hok.chompzki.biocristals.registrys.DrawbackRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public abstract class ItemInsect extends Item implements IInsect {
	
	private final EnumResource foodType;
	private final double cost;
	private final double drain;
	
	public ItemInsect(EnumResource foodType, double cost, double drain){
		this.foodType = foodType;
		this.cost = cost;
		this.drain = Math.max(cost, drain);
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
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
		if(getCost(stack) <= 0.0D)
			return;
		double food = this.getFood(stack);
		
		if(getCost(stack) <= food && !forceFeed){
			food -= getCost(stack);
			this.setFood(stack, food);
			return;
		}
		
		List<ItemStack> banks = getTokens(player.inventory, EnumToken.BANK);
		List<ItemStack> eaters = getTokens(player.inventory, EnumToken.EATER);
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++){
			ItemStack s = player.inventory.getStackInSlot(i);
			if(s == null || s.getItem() != ItemRegistry.honeyWidow)
				continue;
			HoneyWidow widow = new HoneyWidow(player, i);
			
			List<ItemStack> bs = getTokens(widow, EnumToken.BANK);
			List<ItemStack> es = getTokens(widow, EnumToken.EATER);
			for(ItemStack s2 : bs)
				banks.add(s2);
			for(ItemStack s2 : es)
				eaters.add(s2);
		}
		
		if(0 < banks.size()){
			for(ItemStack bank : banks){
				IToken token = (IToken)bank.getItem();
				ResourcePackage pack = new ResourcePackage();
				token.drain(bank, pack, getDrain(stack));
				food += pack.get(getFoodType(stack));
				pack.put(getFoodType(stack), 0.0D);
				token.feed(bank, pack);
				
				if(getCost(stack) <= food){
					food -= getCost(stack);
					this.setFood(stack, food);
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
				token.drain(eater, pack, getDrain(stack));
				food += pack.get(getFoodType(stack));
				pack.put(getFoodType(stack), 0.0D);
				packs.add(pack);
				this.setFood(stack, food);
				if(getCost(stack) <= food){
					food -= getCost(stack);
					this.setFood(stack, food);
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
		double drainage = getDrain(stack);
		double food = this.getFood(stack);
		
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
			food -= getCost(stack);
			food = Math.max(0.0D, food);
			this.setFood(stack, food);
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
			food -= getCost(stack);
			food = Math.max(0.0D, food);
			this.setFood(stack, food);
			((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
			return;
		}
		
		float health = player.getHealth();
		float damage = (float) Math.min(health, drainage);
		player.attackEntityFrom(DamageSource.starve, damage);
		food += damage;
		food -= getCost(stack);
		food = Math.max(0.0D, food);
		this.setFood(stack, food);
		((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
	}
	
	public void drawbacks(EntityPlayer player, ItemStack stack, ResourcePackage pack){
		if(pack.total <= 0.0D)
			return;
		drawbacks(pack, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
	}
	
	public static void drawbacks(TileEntity tile, ItemStack eater, ResourcePackage pack){
		if(pack.total <= 0.0D)
			return;
		drawbacks(pack, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public static void drawbacks(ResourcePackage pack, World world, int x, int y, int z){
		if(pack.total <= 0.0D)
			return;
		for(Drawback drawback : DrawbackRegistry.list)
			if(drawback.canAct(pack, world, x, y, z))
				drawback.act(pack, world, x, y, z);
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
		NBTHelper.init(stack, "FOOD", 0.0D);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltip) {
		if(20.0D <= getCost(stack) || 20.0D <= getDrain(stack)){
			list.add(((char)167) + "4DO NOT USE WITHOUT NETWORK");
		}
		list.add("Food: " + I18n.format("container."+getFoodType(stack), new Object[0]));
		list.add("Use Cost: " + getCost(stack));
		list.add("Drainage: " + getDrain(stack));
		double food = this.getFood(stack);
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
		this.onCreated(s, null, null);
		list.add(s);
    }
	
	@Override
	public double getCost(ItemStack stack) {
		return cost * stack.stackSize;
	}

	@Override
	public double getDrain(ItemStack stack) {
		return drain * stack.stackSize;
	}

	@Override
	public EnumResource getFoodType(ItemStack stack) {
		return foodType;
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
