package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ArticleContent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class Category {
	String code = null;
	ResourceLocation icon = null;
	
	public Category(String code, String icon){
		this.code = code;
		this.icon = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/icons/" + icon +".png");
	}
	
	public String getCode() {
		return code;
	}
	
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public String getTitle() {
		return StatCollector.translateToLocal("research."+code+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("research."+code+".text");
		return s;
	}
}
