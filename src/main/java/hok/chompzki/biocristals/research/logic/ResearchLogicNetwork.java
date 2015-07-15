package hok.chompzki.biocristals.research.logic;

import hok.chompzki.biocristals.research.data.PlayerResearch;
import hok.chompzki.biocristals.research.data.Research;
import hok.chompzki.biocristals.research.data.ReserchDataNetwork;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ResearchLogicNetwork {
	
	private static ResearchLogicNetwork instance = null;
	public static ResearchLogicNetwork instance(){
		if(instance == null){
			instance = new ResearchLogicNetwork();
		}
		return instance;
	}
	
	/** CLASS BENINING **/
	
	private final ReserchDataNetwork dataNetwork;
	
	public ResearchLogicNetwork(){
		dataNetwork = ReserchDataNetwork.instance();
	}
	
	public List<Research> getResearchables(PlayerResearch player){
		List<String> banList = new ArrayList<String>();
		List<Research> returnList = new ArrayList<Research>();
		ArrayDeque<String> parents = new ArrayDeque<String>();
		
		for(Research master : dataNetwork.getMasters()){
			parents.add(master.getCode()); 
		}
		
		while(!parents.isEmpty()){
			String code = parents.pop();
			
			if(banList.contains(code))
				continue;
			
			if(player.hasCompleted(code)){
				List<String> list = dataNetwork.getChildren(code);
				for(String c : list)
						parents.add(c);
			}else if(!banList.contains(code) && this.available(player, code)){
				returnList.add(dataNetwork.getResearch(code));
			}
			
			banList.add(code);
			
		}
		
		return returnList;
	}
	
	public boolean available(PlayerResearch player, String code){
		List<String> parents = dataNetwork.getParents(code);
		
		for(String name : parents){
			if(!player.hasCompleted(name))
				return false;
		}
		
		return true;
	}
	
	public List<Research> getCompletedResearches(PlayerResearch player){
		List<Research> list = new ArrayList<Research>();
		
		for(String name : player.getCompleted()){
			list.add(this.dataNetwork.getResearch(name));
		}
		
		return list;
	}
	
	public List<Research> getOpenResearches(PlayerResearch player){
		List<Research> researches = getResearchables(player);
		researches.addAll(getCompletedResearches(player));
		return researches;
	}
}
