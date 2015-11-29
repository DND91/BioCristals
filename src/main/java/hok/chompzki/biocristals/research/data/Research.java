package hok.chompzki.biocristals.research.data;

import java.util.HashSet;
import java.util.List;

import hok.chompzki.biocristals.api.ArticleContent;
import hok.chompzki.biocristals.api.IArticle;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Research implements IArticle{

	public int displayColumn;
	public int displayRow;
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

	public void updateCoords(List<String> parents, List<String> children, HashSet<GuiCoord> coords) {
		if(parents.size() <= 0){
			int curCol = displayColumn;
			int curRow = displayRow;
			
			GuiCoord coord = new GuiCoord(curCol, curRow, chapeter.getCode());
			
			if(coords.contains(coord)){
				int radius = 1;
				int dx = curCol < 0 ? -1 : 1;
				int dy = curRow < 0 ? 1 : -1;
				do{
					for(int x = curCol - dx * radius; x != curCol + dx * (radius+1); x += dx){
						for(int y = curRow - dy * radius; y != curRow + dy * (radius+1); y += dy){
							GuiCoord tc = new GuiCoord(x, y, chapeter.getCode());
							if(!coords.contains(tc)){
								coord = tc;
								break;
							}
						}
						if(!coords.contains(coord)){
							break;
						}
					}
					radius++;
				}while(coords.contains(coord));
			}
			
			
			coords.add(coord);
			
			displayColumn = coord.x;
			displayRow = coord.y;
		}else{
			String masterParent = parents.get(0);
			Research master = ReserchDataNetwork.instance().getResearch(masterParent);
			int mcol = master.displayColumn;
			int mrow = master.displayRow;
			
			int curCol = mcol + displayColumn;
			int curRow = mrow + displayRow;
			if(!master.getChapeter().equals(chapeter)){
				curCol = displayColumn;
				curRow = displayRow;
			}
			
			GuiCoord coord = new GuiCoord(curCol, curRow, chapeter.getCode());
			
			if(coords.contains(coord)){
				int radius = 1;
				int dx = curCol < 0 ? -1 : 1;
				int dy = curRow < 0 ? 1 : -1;
				do{
					for(int x = curCol - dx * radius; x != curCol + dx * (radius+1); x += dx){
						for(int y = curRow - dy * radius; y != curRow + dy * (radius+1); y += dy){
							GuiCoord tc = new GuiCoord(x, y, chapeter.getCode());
							if(!coords.contains(tc)){
								coord = tc;
								break;
							}
						}
						if(!coords.contains(coord)){
							break;
						}
					}
					radius++;
				}while(coords.contains(coord));
			}
			
			
			coords.add(coord);
			
			displayColumn = coord.x;
			displayRow = coord.y;
		}
	}
}








