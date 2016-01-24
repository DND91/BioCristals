package hok.chompzki.hivetera.recipes;

public class PurifierData {
	
	public String name;
	public String filter;
	public String code;
	public String output;
	public String input;
	public Integer time;
	
	public PurifierData(String name, String filter, String code, String input, String output, Integer time){
		this.name = name;
		this.filter = filter;
		this.code = code;
		this.input = input;
		this.output = output;
		this.time = time;
	}
	
}
