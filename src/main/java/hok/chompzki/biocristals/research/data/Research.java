package hok.chompzki.biocristals.research.data;

import hok.chompzki.biocristals.client.IArticle;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Research implements IArticle{

	public final int displayColumn;
	public final int displayRow;
	private boolean isSpecial;
	private final ItemStack iconStack;
	
	private final String code;
	private final String[] parents;
	private final ArticleContent content;
	
	public final Chapeter chapeter;
	public final Category category;
	
	public Research(String code, int column, int row, ItemStack iconStack, ArticleContent content, Chapeter chapeter, Category category, String... parents){
		this.code = code;
		this.parents = parents;
		this.displayColumn = column;
		this.displayRow = row;
		this.iconStack = iconStack;
		this.content = content;
		content.setCode(code);
		this.chapeter = chapeter;
		this.category = category;
	}
	
	public Research(String code, int column, int row, Item iconStack, ArticleContent content, Chapeter chapeter, Category category, String... parents) {
		this(code, column, row, new ItemStack(iconStack), content, chapeter, category, parents);
	}
	
	public Research(String code, int column, int row, Block iconStack, ArticleContent content, Chapeter chapeter, Category category, String... parents) {
		this(code, column, row, new ItemStack(iconStack), content, chapeter, category, parents);
	}
	
	public String getCode() {
		return code;
	}
	
	public String[] getParents() {
		return parents;
	}
	
	public String getTitle() {
		return StatCollector.translateToLocal("research."+code+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("research."+code+".text");
		return s;
	}
	
	public ItemStack getIconStack(){
		return iconStack;
	}

	public ArticleContent getContent() {
		return content;
	}
	
	public boolean getSpecial(){
		return isSpecial;
	}
	
	public Research setSpecial(){
		this.isSpecial = true;
		return this;
	}
	
	public Chapeter getChapeter() {
		return chapeter;
	}

	public Category getCategory() {
		return category;
	}
}
