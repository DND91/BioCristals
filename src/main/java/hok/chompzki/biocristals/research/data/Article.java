package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.research.data.method.ResearchMethod;

import java.io.Serializable;
import java.util.UUID;

public class Article implements Serializable {
	
	private UUID researcher = null;
	private final String code;
	
	public Article(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public ResearchMethod getMethod(){
		return null;
	}
}
