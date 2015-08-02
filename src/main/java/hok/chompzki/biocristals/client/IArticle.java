package hok.chompzki.biocristals.client;

import hok.chompzki.biocristals.research.data.ArticleContent;

public interface IArticle {
	
	public ArticleContent getContent();
	
	public String getCode();
	
	public String getTitle();
	
	public String getDesc();
}
