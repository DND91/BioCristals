package hok.chompzki.biocristals.tutorials.data;

public class DataVault {
	
	private static DataVault vault = null;
	public static DataVault getVault(){
		if(vault == null)
			vault = new DataVault();
		return vault;
	}
	
	
	
	
}
