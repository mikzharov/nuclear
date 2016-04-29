package objects;

import java.util.ArrayList;

public class TurbineSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	ArrayList<Turbine> turbines = new ArrayList<Turbine>();
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: turbines){
			all.addAll(temp.getObj());
		}
		return all;
	}
	public void addTurbine(Turbine tur) {
		turbines.add(tur);
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
}
