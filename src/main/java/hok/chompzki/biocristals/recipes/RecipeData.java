package hok.chompzki.biocristals.recipes;

public class RecipeData {
	public String code;
	public String output;
	public int quantity;
	public String input;
	
	public RecipeData(String code, String input, String output, int quantity){
		this.code = code;
		this.input = input;
		this.output = output;
		this.quantity = quantity;
	}
}
