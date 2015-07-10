package hok.chompzki.biocristals.registrys;

import hok.chompzki.biocristals.tutorials.data.knowledges.Knowledges;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class TutorialRegistry {
	
	public void preInit(FMLPreInitializationEvent event) {
		Knowledges knws = new Knowledges();
		knws.register();
		
		//Load Descriptions
		//Load Relations
	}
	
}
