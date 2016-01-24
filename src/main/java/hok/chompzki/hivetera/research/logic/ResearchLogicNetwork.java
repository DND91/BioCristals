package hok.chompzki.hivetera.research.logic;

import hok.chompzki.hivetera.research.data.Chapeter;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.Research;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncHandler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
		if(parents == null)
			return true;
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
	
	public void compelte(PlayerResearch player, String code){
		if(player == null || code == null)
			return;
		if(!dataNetwork.contains(code) || player.hasCompleted(code))
			return;
		if(!available(player, code) || code.equals("NONE"))
			return;
		player.addCompleted(code);
	}

	public List<Research> getOpenResearches(Chapeter selectedChapeter,
			PlayerResearch player) {
		List<Research> researches = getResearchables(selectedChapeter, player);
		researches.addAll(getCompletedResearches(selectedChapeter, player));
		return researches;
	}
	
	public List<Research> getResearchables(Chapeter selectedChapeter, PlayerResearch player){
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
			}else if(!banList.contains(code) && this.available(player, code) && dataNetwork.getResearch(code).getChapeter().getCode().equals(selectedChapeter.getCode())){
				returnList.add(dataNetwork.getResearch(code));
			}
			
			banList.add(code);
			
		}
		
		return returnList;
	}

	private Collection<? extends Research> getCompletedResearches(
			Chapeter selectedChapeter, PlayerResearch player) {
		List<Research> list = new ArrayList<Research>();
		
		for(String name : player.getCompleted()){
			Research res = this.dataNetwork.getResearch(name);
			if(res != null && res.getChapeter().getCode().equals(selectedChapeter.getCode()))
				list.add(res);
		}
		
		return list;
	}

	public boolean hasUnlocked(PlayerResearch player, Chapeter chap) {
		List<String> codes = dataNetwork.chapeters.get(chap.getCode());
		if(codes == null)
			return false;
		for(String code : codes){
			if(this.available(player, code))
				return true;
		}
		return false;
	}

	public boolean hasNew(PlayerResearch player, Chapeter chap) {
		List<String> codes = dataNetwork.chapeters.get(chap.getCode());
		if(codes == null)
			return false;
		for(String code : codes){
			if(PlayerStorageSyncHandler.totallyNew.contains(code))
				return true;
		}
		return false;
	}
	
}
