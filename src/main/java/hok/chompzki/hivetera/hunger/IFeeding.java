package hok.chompzki.hivetera.hunger;

import hok.chompzki.hivetera.hunger.logic.Feeder;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;

import java.util.List;

public interface IFeeding {

	public List<Feeder> order(List<Feeder> feeds);

	public void feed(List<Feeder> feeds, ResourcePackage cpy);

}
