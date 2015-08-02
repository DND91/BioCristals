package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.BioCristalsMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class Category {
	String code = null;
	ResourceLocation icon = null;
	ArticleContent content = null;
	
	public Category(String code, String icon, ArticleContent content){
		this.code = code;
		this.icon = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/icons/" + icon +".png");
		this.content = content;
	}
	
	public String getCode() {
		return code;
	}
	
	public ResourceLocation getIcon() {
		return icon;
	}

	public ArticleContent getContent() {
		return content;
	}
	
	public String getTitle() {
		return StatCollector.translateToLocal("research."+code+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("research."+code+".text");
		return s;
	}
}
