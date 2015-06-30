package hok.chompzki.biocristals.transformation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import hok.chompzki.biocristals.api.BioHelper;
import hok.chompzki.biocristals.api.IEntityTransformation;

public class WeakFleshTransformation implements IEntityTransformation {
	
	private Class<? extends EntityLiving> input;
	private ItemStack[] output;
	
	public WeakFleshTransformation(Class<? extends EntityLiving> input, ItemStack... output){
		this.input = input;
		this.output = output;
	}

	
	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player,
			Entity taget) {
		return true;
	}

	@Override
	public boolean canTransform(ItemStack stack, EntityPlayer player,
			World world, Entity taget) {
		return taget.getClass().isAssignableFrom(input);
	}
	
	@Override
	public void pay(ItemStack stack, EntityPlayer player, Entity taget) {
		taget.attackEntityFrom(DamageSource.generic, 1.0f);
		taget.hitByEntity(player);
		
	}

	@Override
	public void transform(ItemStack stack, EntityPlayer player, World world,
			Entity taget) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack s : this.output)
			list.add(s.copy());
		BioHelper.dropItems(world, list, (int)player.posX, (int)player.posY, (int)player.posZ);
	}

}
