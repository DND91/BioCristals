package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.items.armor.SocketType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public interface IArmorInsect extends IInsect{

	int addMaxDamage(int currentDmg, BioArmor armor, int slot);

	void onArmorTick(World world, EntityPlayer player, BioArmor[] armors,
			int type, int slot);

	void addProperties(ArmorProperties prop, BioArmor[] armors, EntityPlayer player, DamageSource source,
			double damage, int type, int slot);

	boolean shouldWork(World world, EntityPlayer player, BioArmor[] armors,
			int armorType, int i);

	void damageArmor(World worldObj, EntityPlayer player, BioArmor[] armors,
			int armorType, int i, DamageSource source, int damage);
	
	SocketType getType();

	boolean shouldMod(World worldObj, EntityPlayer player);

	void applyModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player);

	void removeModifier(IAttributeInstance attribute, AttributeModifier value, EntityPlayer player);

	double getBaseModValue();

	int getDamageReduction(int dmg, BioArmor armor, int i);
}
