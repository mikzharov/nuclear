package objects;

import java.util.ArrayList;

public class PumpSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	ArrayList<Pump> pumps = new ArrayList<Pump>();
	/**
	 * Returns the pumps
	 */
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: pumps){
			all.addAll(temp.getObj());
		}
		return all;
	}
	/**
	 * Adds a pump
	 * @param pump The pump to add
	 */
	public void addPump(Pump pump) {
		pumps.add(pump);
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
	/**
	 * Returns all the cooling factors
	 * @return
	 */
	public float getCoolingFactor(){
		float result = 0;
		for(Pump temp: pumps){
			result += temp.getCoolingFactor();
		}
		return result;
	}
	/**
	 * Sets the disability of all the pump
	 * @param b Boolean to be set as
	 */
	public void setDisabled(boolean b) {
		for(Pump temp: pumps){
			temp.setDisabled(b);
		}
	}
	/**
	 * Sets the slider level of all the pumps
	 * @param percent The percentage (0-1 float)
	 */
	public void setPumpLevel(float percent) {
		for(Pump temp: pumps) {
			temp.pumpLevel.setPercentage(percent);
		}
	}
}
