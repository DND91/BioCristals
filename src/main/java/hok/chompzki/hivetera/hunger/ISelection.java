package hok.chompzki.hivetera.hunger;

import hok.chompzki.hivetera.hunger.logic.Feeder;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;

import java.util.ArrayList;
import java.util.List;

public interface ISelection {

	public List<Feeder> select(String channel, ResourcePackage amount,
			ArrayList<Feeder> feeders);

}
