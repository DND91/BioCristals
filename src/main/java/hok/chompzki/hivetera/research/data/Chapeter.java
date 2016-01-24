package hok.chompzki.hivetera.research.data;

import java.util.ArrayList;

import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.IArticle;
import hok.chompzki.hivetera.client.book.BookTokenizer;
import hok.chompzki.hivetera.research.logic.content.ServerContent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class Chapeter implements IArticle{
	
	public static ArrayList<Chapeter> chapeters = new ArrayList<Chapeter>();
	
	String code = null;
	ResourceLocation icon = null;
	
	public Chapeter(String code, String icon){
		this.code = code;
		this.icon = new ResourceLocation(HiveteraMod.MODID + ":textures/client/gui/icons/" + icon + ".png");
		this.chapeters.add(this);
		BookTokenizer.register(code);
	}

	public String getCode() {
		return code;
	}
	
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public String getTitle() {
		return StatCollector.translateToLocal("chapeter."+code+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("chapeter."+code+".text");
		return s;
	}
	
}
