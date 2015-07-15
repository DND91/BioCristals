package hok.chompzki.biocristals.research.data;

public abstract class ArticleContent {
	
	public enum Content{
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
	
	public String textOnPage(Content content, int p){
		return "";
	}
	
	public int numberOfPages(Content content){
		return 1;
	}
	
	public boolean hasPageSelection(int i){
		return false;
	}
	
	public boolean initSelection(){
		return false;
	}
	
	public void selected(boolean selection){
		
	}
}
