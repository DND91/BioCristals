package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.StorageHandler;
import hok.chompzki.hivetera.api.IDataFile;

import java.io.Serializable;
import java.util.HashMap;

public class ArticleStorage implements IDataFile {
	
	public HashMap<String, Article> articles = new HashMap<String, Article>();
	
	private static ArticleStorage instance = null;
	public static ArticleStorage instance(){
		if(instance == null)
			instance = new ArticleStorage();
		return instance;
	}
	
	public boolean publishArticle(Article art){
		if(articles.containsKey(art.getCode())){
			return false;
		}
		
		articles.put(art.getCode(), art);
		return true;
	}
	
	public ArticleStorage(){
		StorageHandler.register(this);
	}
	
	@Override
	public String getFile() {
		return "artiacleVault.dat";
	}

	@Override
	public Serializable getObject() {
		return articles;
	}

	@Override
	public void setObject(Serializable obj) {
		articles = (HashMap<String, Article>) obj;
	}

	@Override
	public Serializable getEmtptyObject() {
		return new HashMap<String, Article>();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		articles.clear();
	}
}
