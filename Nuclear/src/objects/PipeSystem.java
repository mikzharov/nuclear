package objects;

import java.awt.Color;
import java.util.ArrayList;

public class PipeSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: pipes){
			all.addAll(temp.getObj());
		}
		return all;
	}
	public void addPipe(Pipe pipe) {
		pipes.add(pipe);
	}
	public void setSpeed(int speed){
		for(Pipe p: pipes){
			p.setSpeed(speed);
		}
	}
	public void setIncrement(int increment){
		for(Pipe p: pipes){
			p.setTime(increment);
		}
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
}
