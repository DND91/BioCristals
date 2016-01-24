package hok.chompzki.hivetera.data;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BiomeKittehData {
	
	private final NavigableMap<Double, ItemStack> map = new TreeMap<Double, ItemStack>();
    private final Random random;
    private double total = 0;
    
    public BiomeKittehData() {
        this(new Random());
    }

    public BiomeKittehData(Random random) {
        this.random = random;
    }
    
    public BiomeKittehData add(double weight, Item result) {
    	return this.add(weight, new ItemStack(result));
    }
    
    public BiomeKittehData add(double weight, Block result) {
    	return this.add(weight, new ItemStack(result));
    }

    public BiomeKittehData add(double weight, ItemStack result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public ItemStack next() {
        double value = random.nextDouble() * total;
        ItemStack stack = map.ceilingEntry(value).getValue();
        return stack == null ? null : stack.copy();
    }
    
    public Set<Entry<Double, ItemStack>> entrySet(){
    	return map.entrySet();
    }
    
    public Double getTotal(){
    	return total;
    }
    
	
}
