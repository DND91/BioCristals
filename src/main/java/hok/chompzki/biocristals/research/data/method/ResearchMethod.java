package hok.chompzki.biocristals.research.data.method;

import java.io.Serializable;

public abstract class ResearchMethod {
	
	public final String name;
	
	public ResearchMethod(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
