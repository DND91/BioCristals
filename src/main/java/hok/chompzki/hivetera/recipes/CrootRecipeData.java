package hok.chompzki.hivetera.recipes;

import java.util.ArrayList;
import java.util.List;

public class CrootRecipeData {
	public String code;
	public String output;
	public String[] input;
	
	public CrootRecipeData(String code, String[] input, String output){
		this.code = code;
		List<String> list = new ArrayList<String>();
		for(String row : input){
			for(String column : row.split(" "))
				list.add(column);
		}
		
		this.input = list.toArray(new String[list.size()]);
		this.output = output;
	}
}
