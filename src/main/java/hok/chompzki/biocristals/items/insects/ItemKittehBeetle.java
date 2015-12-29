package hok.chompzki.biocristals.items.insects;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.BioHelper;
import hok.chompzki.biocristals.api.IInsect;
import hok.chompzki.biocristals.hunger.logic.EnumResource;
import hok.chompzki.biocristals.registrys.BiomeRegistry;
import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.tile_enteties.TileNest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemKittehBeetle extends ItemInsect{
	
	public static final String NAME = "itemKittehBeetle";
	
	public ItemKittehBeetle() {
		super(EnumResource.NURITMENT, 10.0D, 20.0D);
        setUnlocalizedName(BioCristalsMod.MODID + "_" + NAME);
		setCreativeTab(BioCristalsMod.creativeTab);
		setTextureName(BioCristalsMod.MODID + ":" + NAME);
		
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		ForgeDirection d = ForgeDirection.getOrientation(side);
		if(world.isRemote || !world.isAirBlock(x+d.offsetX, y+d.offsetY, z+d.offsetZ))
			return false;
		
		if(!Blocks.cake.canBlockStay(world, x+d.offsetX, y+d.offsetY, z+d.offsetZ))
			return false;
		
		feed(player, stack, false);
		
		world.setBlock(x+d.offsetX, y+d.offsetY, z+d.offsetZ, Blocks.cake);
		
		return true;
    }

	@Override
	public String getActionText(TileEntity entity, ItemStack stack) {
		if(entity == null)
			return "I will attract insects. ";
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		Type[] list = BiomeDictionary.getTypesForBiome(biome);
		String s = "";
		for(Type type : list)
		if(BiomeRegistry.kittehsBiomes.containsKey(type)){
			s += type.name() + ", ";
		}
		s = 0 < s.length() ? "Biomes: " + s.substring(0, s.length()-2) : "Unkown biomes.";
		return "I will attract insects. " + s;
	}

	@Override
	public ItemStack[] getResult(ItemStack stack) {
		return new ItemStack[] {new ItemStack(Items.melon)};
	}

	@Override
	public boolean canUpdate(TileEntity entity, ItemStack stack) {
		return true;
	}

	@Override
	public void tileUpdate(TileEntity entity, ItemStack stack) {
		World world = entity.getWorldObj();
		int x = entity.xCoord;
		int y = entity.yCoord;
		int z = entity.zCoord;
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		TileNest nest = (TileNest)entity;
		
		Type[] list = BiomeDictionary.getTypesForBiome(biome);
		boolean has = false;
		for(Type type : list)
		if(BiomeRegistry.kittehsBiomes.containsKey(type)){
			has = true;
			ItemStack result = BiomeRegistry.kittehsBiomes.get(type).next();
			if(result == null)
				continue;
			result.getItem().onCreated(result, world, null);
			BioHelper.addItemStackToInventory(result, nest, 2, 4);
		}
	}

	@Override
	public int lifeSpan(ItemStack stack) {
		return 1000;
	}

	@Override
	public int workSpan(ItemStack stack) {
		return 100;
	}
}
