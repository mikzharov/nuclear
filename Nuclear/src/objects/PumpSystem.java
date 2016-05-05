package objects;

import java.util.ArrayList;

public class PumpSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	ArrayList<Pump> pumps = new ArrayList<Pump>();
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: pumps){
			all.addAll(temp.getObj());
		}
		return all;
	}
	public void addPump(Pump pump) {
		pumps.add(pump);
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
	public float getCoolingFactor(){
		float result = 0;
		for(Pump temp: pumps){
			result += temp.getCoolingFactor();
		}
		return result;
	}
	public void setPumpLevel(float percent) {
		for(Pump temp: pumps) {
			temp.pumpLevel.setPercentage(percent);
		}
	}
}
