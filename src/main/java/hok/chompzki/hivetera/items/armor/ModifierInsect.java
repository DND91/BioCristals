package hok.chompzki.hivetera.items.armor;

import hok.chompzki.hivetera.api.IArmorInsect;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

public class ModifierInsect {
	
	public IArmorInsect insect;
	public IAttribute attribute;
	public AttributeModifier value;
	public boolean shouldAdd;
	
	public ModifierInsect(IArmorInsect insect, IAttribute attribute, AttributeModifier value, boolean add){
		this.insect = insect;
		this.attribute = attribute;
		this.value = value;
		this.shouldAdd = add;
	}
}
