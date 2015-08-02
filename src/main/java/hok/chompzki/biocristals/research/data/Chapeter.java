package hok.chompzki.biocristals.research.data;

import java.util.ArrayList;

import hok.chompzki.biocristals.BioCristalsMod;
import hok.chompzki.biocristals.client.IArticle;
import hok.chompzki.biocristals.research.logic.content.ServerContent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class Chapeter implements IArticle{
	
	public static ArrayList<Chapeter> chapeters = new ArrayList<Chapeter>();
	
	String code = null;
	ResourceLocation icon = null;
	ArticleContent content = null;
	
	public Chapeter(String code, String icon, ArticleContent content){
		this.code = code;
		this.icon = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/icons/" + icon + ".png");
		this.content = content;
		content.setCode(code);
		this.chapeters.add(this);
	}
	
	public Chapeter(String code, String icon, ServerContent content,
			boolean b) {
		this.code = code;
		this.icon = new ResourceLocation(BioCristalsMod.MODID + ":textures/client/gui/icons/" + icon + ".png");
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
		return StatCollector.translateToLocal("chapeter."+code+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("chapeter."+code+".text");
		return s;
	}
	
}
