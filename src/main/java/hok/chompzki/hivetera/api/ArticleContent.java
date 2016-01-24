package hok.chompzki.hivetera.api;

import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;

public abstract class ArticleContent {
	
	public enum EnumContent{
		INTRO,
		THEORY,
		METHOD,
		RESULT
	}
	
	protected String code = null;
	
	public ArticleContent(){
		
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String textOnPage(EnumContent content, int p){
		return "";
	}
	
	public int numberOfPages(EnumContent content){
		return 1;
	}

	public String getCode() {
		return code;
	}
	
}
