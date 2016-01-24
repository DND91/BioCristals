package hok.chompzki.hivetera.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IEntityTransformation;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;

public class WeakFleshTransformation implements IEntityTransformation {
	
	private Class<? extends EntityLiving> input;
	private ItemStack[] output;
	private String code = null;
	
	public WeakFleshTransformation(Class<? extends EntityLiving> input, String code, ItemStack... output){
		this.input = input;
		this.output = output;
		this.code = code;
	}

	
	@Override
	public boolean hasResources(ItemStack stack, EntityPlayer player,
			Entity taget) {
		UUID id = player.getGameProfile().getId();
		PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
		if(research == null){
			System.out.println("UNKOWN PLAYER TRIED TRANSFORMATION WITHOUT REGISTATION: " + player.getDisplayName());
			return false;
		}
		return code.equals("NONE") || ResearchLogicNetwork.instance().available(research, code);
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
		
		if(code != null){
			UUID id = player.getGameProfile().getId();
			PlayerResearch research = PlayerResearchStorage.instance(false).get(id);
			if(research == null){
				System.out.println("UNKOWN PLAYER TRIED WEAK FLESH WITHOUT REGISTATION: " + player.getDisplayName());
				return;
			}
			ResearchLogicNetwork.instance().compelte(research, code);
		}
	}

}