package hok.chompzki.hivetera.api;

import java.io.Serializable;

public interface IDataFile {
	
	public String getFile();
	
	public Serializable getObject();
	
	public void setObject(Serializable obj);
	
	public Serializable getEmtptyObject();
	
	public void clear();
}
