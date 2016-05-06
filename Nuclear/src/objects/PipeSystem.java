package objects;

import java.util.ArrayList;

public class PipeSystem extends GameObject{
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	/**
	 * Returns all the pipes in the object
	 */
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: pipes){
			all.addAll(temp.getObj());
		}
		return all;
	}
	/**
	 * Adds a pipe to the system
	 * @param pipe The pipe to add
	 */
	public void addPipe(Pipe pipe) {
		pipes.add(pipe);
	}
	/**
	 * Sets the speed of all the pipes in the system
	 * @param speed The integer speed
	 */
	public void setSpeed(int speed){
		for(Pipe p: pipes){
			p.setSpeed(speed);
		}
	}
	/**
	 * Sets the time it takes the particles to move along a bit of the distance on the pipe
	 * @param increment The integer time it takes
	 */
	public void setIncrement(int increment){
		for(Pipe p: pipes){
			p.setTime(increment);
		}
	}
	/**
	 * Makes sure no one is misusing the pipe
	 */
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
}
