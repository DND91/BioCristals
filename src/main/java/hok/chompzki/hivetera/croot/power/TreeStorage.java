package hok.chompzki.hivetera.croot.power;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import hok.chompzki.hivetera.StorageHandler;
import hok.chompzki.hivetera.api.ICrootCore;
import hok.chompzki.hivetera.api.IDataFile;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.network.MessageHandlerInserCrafting;
import hok.chompzki.hivetera.research.data.network.MessageInsertCrafting;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenHandler;
import hok.chompzki.hivetera.research.data.network.PlayerStorageDelissenMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveHandler;
import hok.chompzki.hivetera.research.data.network.PlayerStorageFaveMessage;
import hok.chompzki.hivetera.research.data.network.PlayerStorageSyncMessage;

public class TreeStorage implements IDataFile {
	
	protected static TreeStorage storage = null;
	
	public static TreeStorage instance(){
		if(storage == null){
			storage = new TreeStorage();
		}
		return storage;
	}
	
	protected HashMap<WorldCoord, TreeForm> treeForms =  new HashMap<WorldCoord, TreeForm>();
	protected SimpleNetworkWrapper network;
	
	protected TreeStorage(){
		StorageHandler.register(this);
		
	}

	@Override
	public String getFile() {
		return "treeForms.dat";
	}

	@Override
	public Serializable getObject() {
		return treeForms;
	}

	@Override
	public void setObject(Serializable obj) {
		treeForms = (HashMap<WorldCoord, TreeForm>)obj;
	}

	@Override
	public Serializable getEmtptyObject() {
		return new HashMap<WorldCoord, TreeForm>();
	}

	@Override
	public void clear() {
		treeForms.clear();
	}
	
	public void setForm(ICrootCore core){
		setForm(new WorldCoord(core.getX(), core.getY(), core.getZ(), core.getDim()));
	}
	
	public void setForm(WorldCoord coords){
		if(coords == null)
			return;
		treeForms.put(coords, new TreeForm(coords));
	}
	
	public TreeForm getForm(ICrootCore core){
		return getForm(new WorldCoord(core.getX(), core.getY(), core.getZ(), core.getDim()));
	}
	
	public TreeForm getForm(WorldCoord coords){
		return treeForms.get(coords);
	}
	
	public void removeForm(ICrootCore core){
		removeForm(new WorldCoord(core.getX(), core.getY(), core.getZ(), core.getDim()));
	}
	
	public void removeForm(WorldCoord coords){
		treeForms.remove(coords);
	}
	
	public boolean containsForm(ICrootCore core){
		return containsForm(new WorldCoord(core.getX(), core.getY(), core.getZ(), core.getDim()));
	}
	
	public boolean containsForm(WorldCoord coords){
		return treeForms.containsKey(coords);
	}

}
