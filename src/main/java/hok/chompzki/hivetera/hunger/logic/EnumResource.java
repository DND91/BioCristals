package hok.chompzki.hivetera.hunger.logic;

public enum EnumResource {
	PSY_ENG(0),
	NURITMENT(1),
	LIFE_FLUIDS(2),
	WASTE(3),
	BIOMASS(4),
	RAW_FOOD(5);
	
	int i;
	
	EnumResource(int i){
		this.i = i;
	}
	
	public int toInt(){
		return i;
	}
}
