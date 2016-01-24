package hok.chompzki.hivetera.recipes;

import java.util.ArrayList;
import java.util.List;

public class RecipeData {
	public String code;
	public String output;
	public String[] input;
	
	public RecipeData(String code, String[] strings, String output){
		this.code = code;
		List<String> list = new ArrayList<String>();
		for(String row : strings){
			for(String column : row.split(" "))
				list.add(column);
		}
		
		this.input = list.toArray(new String[list.size()]);
		this.output = output;
	}
}
