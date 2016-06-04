package hok.chompzki.hivetera.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.potion.Potion;

public abstract class PotionBase extends Potion {
	
	static void setFinalStatic(Field field, Object newValue) throws Exception {
	  field.setAccessible(true);
	
	  Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(null, newValue);
	}
	
	static Field getPotionArrayField(){
		Field[] declaredFields = String.class.getDeclaredFields();
		for (Field field : declaredFields) {
		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		        try {
					Object obj = field.get(null);
					if(obj != null && obj instanceof Potion[])
						return field;
				} catch (Exception e) {
					e.printStackTrace();
				}
		        
		    }
		}
		
		return null;
	}
	
	static void increasePotionArray() throws Exception{
		Field field = getPotionArrayField();
		if(field == null){
			throw new IllegalArgumentException("ERROR MISSING POTION TYPES FIELD! :(");
		}
		
		String[] newArray = new String[Potion.potionTypes.length + 1];
	    System.arraycopy(Potion.potionTypes, 0, newArray, 0, Potion.potionTypes.length);
	    setFinalStatic(field, newArray);
	}
	
	public static int addPotionID(){
		for(int i = 0; i < Potion.potionTypes.length; i++){
			if(Potion.potionTypes[i] == null)
				return i;
		}
		
		try {
			increasePotionArray();
			return addPotionID();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	protected PotionBase(boolean p_i1573_2_, int p_i1573_3_) {
		super(addPotionID(), p_i1573_2_, p_i1573_3_);
		
	}

}
