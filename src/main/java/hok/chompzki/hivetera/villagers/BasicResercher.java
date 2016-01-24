package hok.chompzki.hivetera.villagers;

import hok.chompzki.hivetera.hunger.PlayerHungerNetwork;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ConfigRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.research.data.DataHelper;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class BasicResercher implements IVillageTradeHandler {
	
	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager,
			MerchantRecipeList recipeList, Random random) {
		
		
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(ItemRegistry.researchBook)));
		
		if(random.nextInt(5) == 0){
			String network = ConfigRegistry.networkNames[random.nextInt(ConfigRegistry.networkNames.length)];
			villager.setCustomNameTag(network);
			villager.func_110163_bv();
			ItemStack portal = new ItemStack(ItemRegistry.hiveBrain);
			portal.getItem().onCreated(portal, null, null);
			portal.getTagCompound().setString("NETWORK", network);
			recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(ItemRegistry.hiveBrain), new ItemStack(Items.wheat, 8 + random.nextInt(64-8)), portal));
			
			if(!PlayerHungerStorage.instance(villager.worldObj.isRemote).containsKey(network)){
				PlayerHungerStorage.instance(villager.worldObj.isRemote).put(network, new PlayerHungerNetwork(network));
				PlayerHungerStorage.instance(villager.worldObj.isRemote).get(network).setName(network);
			}
		}
		
		if(random.nextInt(10) == 0){
			String network = ConfigRegistry.networkNames[random.nextInt(ConfigRegistry.networkNames.length)];
			ItemStack portal = new ItemStack(ItemRegistry.hiveBrain);
			portal.getItem().onCreated(portal, null, null);
			portal.getTagCompound().setString("NETWORK", network);
			recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.diamond), portal));
			
			if(!PlayerHungerStorage.instance(villager.worldObj.isRemote).containsKey(network)){
				PlayerHungerStorage.instance(villager.worldObj.isRemote).put(network, new PlayerHungerNetwork(network));
				PlayerHungerStorage.instance(villager.worldObj.isRemote).get(network).setName(network);
			}
		}
		
		if(random.nextInt(10) == 0){
			String network = ConfigRegistry.networkNames[random.nextInt(ConfigRegistry.networkNames.length)];
			ItemStack portal = new ItemStack(ItemRegistry.hiveBrain);
			portal.getItem().onCreated(portal, null, null);
			portal.getTagCompound().setString("NETWORK", network);
			recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(ItemRegistry.voidCrawler), portal));
			
			if(!PlayerHungerStorage.instance(villager.worldObj.isRemote).containsKey(network)){
				PlayerHungerStorage.instance(villager.worldObj.isRemote).put(network, new PlayerHungerNetwork(network));
				PlayerHungerStorage.instance(villager.worldObj.isRemote).get(network).setName(network);
			}
		}
	}

}
