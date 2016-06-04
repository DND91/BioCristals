package hok.chompzki.hivetera.client.slots;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.FMLCommonHandler;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.SocketType;
import hok.chompzki.hivetera.items.insects.ItemHivebag;
import hok.chompzki.hivetera.items.token.ItemToken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SlotBioArmor extends Slot {
	
	private SocketType type;
	
	public SlotBioArmor(SocketType type, IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		this.type = type;
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if(side == Side.CLIENT)
			this.setBackgroundIcon(ItemBioModArmor.getBackgroundIcon(type));
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		if(type == SocketType.EATER){
			return (stack.getItem() instanceof ItemFood) || (stack.getItem() instanceof ItemToken) && (
					((IToken)stack.getItem()).getType(stack) == EnumToken.EATER
					|| ((IToken)stack.getItem()).getType(stack) == EnumToken.BANK);
		}else
			if(stack.getItem() instanceof IArmorInsect){
				return type == ((IArmorInsect)stack.getItem()).getType();
			} else
				return false;
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
		return true;
    }
	
	@Override
	public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_)
    {
		super.onSlotChange(p_75220_1_, p_75220_2_);
		this.inventory.markDirty();
    }
}

