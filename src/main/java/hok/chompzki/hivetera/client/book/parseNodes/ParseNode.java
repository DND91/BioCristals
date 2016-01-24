package hok.chompzki.hivetera.client.book.parseNodes;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.minecraft.client.gui.Gui;
import hok.chompzki.hivetera.client.book.Article;
import hok.chompzki.hivetera.client.book.TokenCollection;
import hok.chompzki.hivetera.client.gui.ArticleFontRenderer;
import hok.chompzki.hivetera.client.gui.GuiArticle;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.research.data.GuiCoord;

public abstract class ParseNode {
	
	private static int tabs = 0;
	private static final int increament = 3;
	
	public static void addTab(){
		tabs++;
	}
	
	public static void subTab(){
		tabs--;
		if(tabs < 0)
			tabs = 0;
	}
	
	public static String getTabs(){
		String s = "";
		for(int i = 0; i < (tabs*increament); i++)
			s += " ";
		return s;
	}
	
	public static HashMap<String, Class> types = new HashMap<String, Class>();
	
	public static ParseNode get(String command){
		Class cls = types.get(command.toLowerCase());
		try {
			return (ParseNode) cls.getConstructor().newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	public final String command;
	
	protected ParseNode(String name, Class cls){
		this.command = name.toLowerCase();
		if(!types.containsKey(command))
			types.put(command, cls);
	}
	

	public void parse(Cursor cursor, Article arc, TokenCollection collection){
		
	}
	
	//Calculates numbers of pages and sets that up! Should only need to check height nothing on the width
	public void caulculate(Cursor cursor, Article arc, TokenCollection collection) {
		
	}
	
	public void print(){
		
	}
	
	public void draw(Cursor cursor, int currPage, int x, int y, Gui guiCraft,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale) {
		
	}
	
	public void drawOverlay(Cursor cursor, int currPage, int x, int y, Gui guiArticle,
			ArticleFontRenderer articleFontRenderer, boolean summary, double scale, int mosX, int mosY) {
		
	}
}
