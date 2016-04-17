package objects;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameObject {
	int x = 0;
	int y = 0;
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getImageX(){
		throw new UnsupportedOperationException();
	}
	public int getImageY(){
		throw new UnsupportedOperationException();
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setXY(int x, int y){
		this.y = y;
		this.x = x;
	}
	public void keyPressed(KeyEvent e){
		
	}
	public void mouseClicked(MouseEvent e){
		
	}
	public void drawObj(Graphics2D g){
		
	}
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: objects){
			all.addAll(temp.getObj());
		}
		
		return all;
	}
	public void addObj(GameObject a){
		objects.add(a);
	}
}
