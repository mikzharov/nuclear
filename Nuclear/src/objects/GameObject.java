package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import graphics.UIComponent;

public class GameObject {
	int x = 0;
	int y = 0;
	int xSize = 0, ySize = 0;
	Rectangle bounds = new Rectangle();
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	ArrayList<UIComponent> ui = new ArrayList<UIComponent>();
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
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setY(int y){
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setXY(int x, int y){
		this.y = y;
		this.x = x;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setSize(int size){
		xSize = ySize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setXSize(int size){
		xSize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setYSize(int size){
		ySize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public void setXYSize(int y, int x){
		xSize = x;
		ySize = y;
		bounds = new Rectangle(x, y, xSize, ySize);
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
	public ArrayList<UIComponent> getUi(){
		ArrayList<UIComponent> all = new ArrayList<UIComponent>();
		all.addAll(ui);
		return all;
	}
	public void addObj(GameObject a){
		objects.add(a);
	}
	public void addObjs(ArrayList<GameObject> all){
		objects.addAll(all);
	}
}
