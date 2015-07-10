package hok.chompzki.biocristals.tutorials.data.knowledges;

import java.util.ArrayList;

import hok.chompzki.biocristals.registrys.ItemRegistry;
import hok.chompzki.biocristals.tutorials.data.description.BabySteps;

public class Knowledges {
	
	public static ArrayList<Knowledge> knowledges = new ArrayList<Knowledge>();
	
	public static final Knowledge babySteps = new Knowledge("babySteps", ItemRegistry.bioBook, new BabySteps(), 0, 0).setSpecial();
	
	public void register(){	}
}
