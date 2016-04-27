package objects;

import java.util.ArrayList;

public class CompleteSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	public ArrayList<GameObject> getObj(){
		
		//TODO implement custom arrays and method to return the arraylist
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: objects){
			all.addAll(temp.getObj());
		}
		return all;
	}
}
