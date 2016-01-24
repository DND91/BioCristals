package hok.chompzki.hivetera.research.data;

import hok.chompzki.hivetera.croot.power.WorldCoord;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import joptsimple.internal.Strings;

public class ReserchDataNetwork {
	
	public HashMap<String, Research> reserches = new HashMap<String, Research>();
	
	private static ReserchDataNetwork instance = null;
	public static ReserchDataNetwork instance(){
		if(instance == null){
			instance = new ReserchDataNetwork();
		}
		return instance;
	}
	
	public static void register(Research reserch){
		ReserchDataNetwork instance = ReserchDataNetwork.instance();
		instance.registerResearch(reserch);
	}
	
	public static void build(){
		ReserchDataNetwork instance = ReserchDataNetwork.instance();
		instance.buildNetwork();
	}
	
	/** START OF CLASS **/
	//Researches without parents
	public ArrayList<Research> masters = new ArrayList<Research>();
	//A research parents
	public HashMap<String, ArrayList<String>> parents = new HashMap<String, ArrayList<String>>();
	//A research children
	public HashMap<String, ArrayList<String>> children = new HashMap<String, ArrayList<String>>();
	//Chapeter holder
	public HashMap<String, ArrayList<String>> chapeters = new HashMap<String, ArrayList<String>>();
	
	public ReserchDataNetwork(){
		
	}
	
	public void registerResearch(Research reserch){
		String code = reserch.getCode();
		reserches.put(code, reserch);
	}
	
	public void buildNetwork(){
		Collection<Research> list = reserches.values();
		for(Research res : list){ //TODO: What if research dosn't exist?
			String code = res.getCode();
			String[] p = res.getParents();
			if(!children.containsKey(code))
				children.put(code, new ArrayList<String>());
			
			ArrayList<String> parents = new ArrayList<String>();
			for(String name : p){
				parents.add(name);
				if(!children.containsKey(name))
					children.put(name, new ArrayList<String>());
				ArrayList<String> children = this.children.get(name);
				children.add(code);
			}
			this.parents.put(code, parents);
		}
		
		for(Research res : list){ //Find master reserches
			String code = res.getCode();
			List<String> parents = this.parents.get(code);
			if(parents.size() <= 0)
				masters.add(res);
			
			if(!chapeters.containsKey(res.getChapeter().getCode())){
				chapeters.put(res.getChapeter().getCode(), new ArrayList<String>());
			}
			
			ArrayList<String> chapeter = chapeters.get(res.getChapeter().getCode());
			
			if(!chapeter.contains(code)){
				chapeter.add(code);
			}
		}
		
		ArrayList<String> banList = new ArrayList<String>();
		ArrayDeque<String> workQue = new ArrayDeque<String>();
		HashSet<GuiCoord> guiCoords = new HashSet<GuiCoord>();
		
		for(Research res : masters){
			workQue.add(res.getCode());
		}
		
		while(!workQue.isEmpty()){
			String code = workQue.pop();
			if(banList.contains(code))
				continue;
			banList.add(code);
			Research res = this.getResearch(code);
			List<String> p = this.parents.get(code);
			List<String> c = this.children.get(code);
			
			res.updateCoords(p, c, guiCoords);
			
			for(String chi : c){
				if(banList.contains(chi))
					continue;
				workQue.add(chi);
			}
		}
		
	}
	
	public void printNetworkInfo(){
		
		System.out.println(" === ALL === ");
		for(Research res : reserches.values()){
			System.out.print(res.getCode() + " ");
		}
		System.out.println();
		System.out.println(" =============== ");
		
		System.out.println(" === MASTERS === ");
		for(Research res : masters){
			System.out.print(res.getCode() + " ");
		}
		System.out.println();
		System.out.println(" =============== ");
		
		for(Research res : reserches.values()){
			String code = res.getCode();
			System.out.println(" === " + code + " === ");
			ArrayList<String> list = parents.get(code);
			System.out.print("Parents: ");
			for(String name : list){
				System.out.print(name+ " ");
			}
			System.out.println();
			list = children.get(code);
			System.out.print("Children: ");
			for(String name : list){
				System.out.print(name+ " ");
			}
			System.out.println();
			System.out.println(" =============== ");
		}
		
		System.out.println(" === MASTERS STRUCTURE === ");
		for(Research res : masters){
			System.out.println(" === MASTERS " + res.getCode() + " === ");
			int depth = calculateDepth(res.getCode(), new ArrayList<String>());
			int breadth = calculateBreadth(res.getCode());
			System.out.println("Depth: " + depth);
			System.out.println("Breadth: " + breadth);
		}
	}
	
	private int calculateDepth(String code, List<String> banList){
		int i = 0;
		banList.add(code);
		
		for(String child : children.get(code)){
			if(banList.contains(child))
				continue;
			
			i = Math.max(i, calculateDepth(child, banList));
		}
		
		return i + 1;
	}
	
	private int calculateBreadth(String code){
		List<String> banList = new ArrayList<String>();
		ArrayDeque<String> parents = new ArrayDeque<String>();
		ArrayDeque<String> children = new ArrayDeque<String>();
		int breadth = 1;
		parents.add(code);
		
		do{
			while(!parents.isEmpty()){
				String parent = parents.pop();
				List<String> list = this.children.get(parent);
				for(String c : list)
					children.add(c);
				banList.add(parent);
			}
			
			breadth = Math.max(breadth, children.size());
			
			while(!children.isEmpty()){
				String child = children.pop();
				if(!banList.contains(child))
					parents.push(child);
			}
			
		}while(!parents.isEmpty());
		
		return breadth;
	}
	
	public List<String> getChildren(String code){
		return children.get(code);
	}
		
	public List<String> getParents(String code){
		return parents.get(code);
	}	
	
	public List<Research> getMasters(){
		return masters;
	}	
	
	public Research getResearch(String code){
		return reserches.get(code);
	}
	
	public boolean contains(String code) {
		return reserches.containsKey(code);
	}
	
	public final ArrayList<String> getChapeter(String code){
		return chapeters.get(code);
	}
}
