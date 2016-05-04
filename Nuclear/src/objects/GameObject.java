package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import graphics.UIComponent;
import logic.Integrator;

public class GameObject {
	int x = 0, y = 0, xSize = 0, ySize = 0;
	Rectangle bounds = new Rectangle();
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected ArrayList<UIComponent> ui = new ArrayList<UIComponent>();
	protected boolean clicked = false;
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
	public void mouseClicked(MouseEvent e) {
		if(Integrator.active() && !clicked){
			return;
		}
		//Do not do physics in mouseClicked, do it in update
		bounds.setLocation(x+Integrator.intLastXOffset, y + Integrator.intLastYOffset);
		AffineTransform g = new AffineTransform();//This code makes sure that the object was clicked
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains(e.getPoint())){
			//Hit
			clicked=true;
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
		} else {
			boolean uiClicked=false;
			for(UIComponent utemp: ui){
				if(utemp.getBounds().contains(e.getPoint())){
					uiClicked = true;
					clicked = true;
					break;
				}
			}
			if(!uiClicked){
				
				clicked=false;
				if (UITutorial.reactorTutorialOn) { //for tutorial only!!
					Integrator.reactor4.showControls();
				}
				else if (UITutorial.turbineTutorialOn) { //for tutorial only!!
					Integrator.reactor4.setTurbineVisible(true);
				}
				else {
					for(UIComponent comp: ui){
						comp.setVisible(false);
					}
				}
			}
		}
	}
	public boolean getActive(){
		return clicked;
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
		return ui;
	}
	public void addObj(GameObject a){
		objects.add(a);
	}
	public void addObj(ArrayList<GameObject> all){
		objects.addAll(all);
	}
	public void update(long deltaTime) {
		
	}
	public GameObject(int x, int y, int xSize, int ySize){
		this.x=x;
		this.y=y;
		this.xSize=xSize;
		this.ySize=ySize;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public GameObject(){
		
	}
}
