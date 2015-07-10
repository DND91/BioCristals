package hok.chompzki.biocristals.tutorials.data.knowledges;

import hok.chompzki.biocristals.tutorials.data.description.IDescription;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Knowledge {
	public final int displayColumn;
	public final int displayRow;
	
	private final String knowledgeName;	
	
	private boolean isSpecial;
	private final ItemStack iconStack;
	
	private IDescription description;
	
	
	public Knowledge setSpecial()
    {
        this.isSpecial = true;
        return this;
    }
	
	public boolean getSpecial()
    {
        return this.isSpecial;
    }
	
	public Knowledge(String name, Item item, IDescription p, int colum, int row){
		this(name, new ItemStack(item), p, colum, row);
	}
	
	public Knowledge(String name, Block block, IDescription p, int colum, int row){
		this(name, new ItemStack(block), p, colum, row);
	}
	
	public Knowledge(String name, ItemStack itemstack, IDescription p, int colum, int row){
		iconStack = itemstack;
		knowledgeName = name;
		description = p;
		isSpecial = false;
		displayColumn = colum;
		displayRow = row;
		Knowledges.knowledges.add(this);
	}
	
	public String getUnlocalizedName() {
		return knowledgeName;
	}
	
	public String getTitle() {
		return StatCollector.translateToLocal("knowledge."+knowledgeName+".title");
	}
	
	public String getDesc(){
		String s = StatCollector.translateToLocal("knowledge."+knowledgeName+".text");
		return s;
	}
	
	public ItemStack getIconStack(){
		return iconStack;
	}
	
	public IDescription  getDescription(){
		return description;
	}
}
