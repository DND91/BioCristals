package hok.chompzki.hivetera.research.data;

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
}
