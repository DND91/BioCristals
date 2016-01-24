package hok.chompzki.hivetera.hunger.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ResourcePackage {
	
	private HashMap<EnumResource, Double> resources = new HashMap<EnumResource, Double>();
	
	private HashMap<EnumResource, Double> backlog = new HashMap<EnumResource, Double>();
	
	public double total = 0.0D;
	public double diffrance = 0.0D;
	
	public double get(EnumResource res){
		if(resources.containsKey(res))
			return resources.get(res);
		else
			return 0.0D;
	}
	
	public ResourcePackage put(EnumResource res, Double d){
		if(resources.containsKey(res)){
			this.total -= this.resources.get(res);
			this.diffrance -= this.resources.get(res);
		}
		resources.put(res, d);
		this.total += d;
		this.diffrance += d;
		return this;
	}
	
	public ResourcePackage add(EnumResource res, Double d){
		if(d < 0.0D)
			return this;
		if(!resources.containsKey(res))
			put(res, d);
		else{
			put(res, d + get(res));
		}
		return this;
	}
	
	public ResourcePackage sub(EnumResource res, Double d){
		if(d <= 0.0D || !resources.containsKey(res))
			return this;
		
		double v = get(res);
		v = Math.max(0.0D, v - d);
		
		this.diffrance = Math.max(0.0D, diffrance - d);
		
		this.resources.put(res, v);
		
		return this;
	}
	
	
	
	public List<ResourcePackage> split(int i){
		List<ResourcePackage> list = new ArrayList<ResourcePackage>();
		
		for(int j = 0; j < i; j++){
			ResourcePackage pack = new ResourcePackage();
			for(EnumResource res : EnumResource.values()){
				pack.put(res, this.get(res) / ((double)i));
				if(this.hasBacklog(res))
					pack.putBacklog(res, this.getBacklog(res) / ((double)i));
			}
			pack.total = this.total / ((double)i);
			pack.diffrance = this.diffrance / ((double)i);
			list.add(pack);
		}
		
		return list;
	}
	
	public void combine(List<ResourcePackage> list){
		double tot = this.total;
		double diff = this.diffrance;
		for(ResourcePackage p : list){
			for(EnumResource res : EnumResource.values()){
				this.add(res, p.get(res));
				if(p.hasBacklog(res))
					this.addBacklog(res, p.getBacklog(res));
			}
			tot += p.total;
			diff += p.diffrance;
		}
		this.total = tot;
		this.diffrance = diff;
	}

	public void clear() {
		this.resources.clear();
		total = 0.0D;
		diffrance = 0.0D;
		this.backlog.clear();
	}

	public void print() {
		System.out.println("RESOURCE PACKAGE!");
		for(EnumResource res : EnumResource.values()){
			System.out.println(res.name() + ": " + this.get(res));	
		}
		System.out.println("TOTAL: " + total);
		System.out.println("DIFFRANCE: " + diffrance);
		System.out.println("--- BACKLOG ---");
		for(EnumResource res : EnumResource.values()){
			System.out.println(res.name() + ": " + this.getBacklog(res));	
		}
	}
	
	public ResourcePackage copy(){
		ResourcePackage cpy = new ResourcePackage();
		
		for(EnumResource res : EnumResource.values()){
			cpy.put(res, this.get(res));
		}
		
		for(EnumResource res : EnumResource.values()){
			cpy.putBacklog(res, this.getBacklog(res));
		}
		
		cpy.total = this.total;
		cpy.diffrance = this.diffrance;
		
		return cpy;
	}
	
	public void backlog(EnumResource res){
		double value = this.get(res);
		this.put(res, 0.0D);
		this.resources.remove(res);
		
		if(this.hasBacklog(res)){
			value += getBacklog(res);
		}
		
		putBacklog(res, value);
	}
	
	public double getBacklog(EnumResource res){
		if(this.backlog.containsKey(res)){
			return this.backlog.get(res);
		} else {
			return 0.0D;
		}
	}
	
	public void putBacklog(EnumResource res, double v){
		this.backlog.put(res, v);
	}
	
	public void addBacklog(EnumResource res, Double d){
		if(d < 0.0D)
			return;
		if(!backlog.containsKey(res))
			putBacklog(res, d);
		else{
			putBacklog(res, d + getBacklog(res));
		}
	}
	
	public boolean hasBacklog(){
		return !this.backlog.isEmpty();
	}
	
	public boolean hasBacklog(EnumResource res){
		return this.backlog.containsKey(res);
	}
	
	public void normalize(){
		for(Entry<EnumResource, Double> entry : this.backlog.entrySet()){
			this.add(entry.getKey(), entry.getValue());
		}
		this.backlog.clear();
	}
	
	public void readBacklogClear(ResourcePackage pack){
		for(EnumResource res : EnumResource.values()){
			if(pack.hasBacklog(res))
				this.add(res, pack.getBacklog(res));	
		}
	}
	
	public ResourcePackage getBacklogClear(){
		ResourcePackage pack = new ResourcePackage();
		for(EnumResource res : EnumResource.values()){
			pack.add(res, this.getBacklog(res));	
		}
		this.backlog.clear();
		return pack;
	}

	public void read(ResourcePackage drain) {
		this.clear();
		for(EnumResource res : EnumResource.values()){
			this.put(res, drain.get(res));
		}
		
		for(EnumResource res : EnumResource.values()){
			this.putBacklog(res, drain.getBacklog(res));
		}
		
		this.total = drain.total;
		this.diffrance = drain.diffrance;
	}
}
