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
	protected Fire fire;
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected ArrayList<UIComponent> ui = new ArrayList<UIComponent>();
	protected boolean clicked = false;
	/**
	 * Returns the X coordinate
	 * @return The X coordinate
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns the Y coordinate
	 * @return The Y coordinate
	 */
	public int getY(){
		return y;
	}
	/**
	 * Methods meant to be overridden
	 * @return An error
	 */
	public int getImageX(){
		throw new UnsupportedOperationException();
	}
	/**
	 * Methods meant to be overridden
	 * @return An error
	 */
	public int getImageY(){
		throw new UnsupportedOperationException();
	}
	/**
	 * Sets the X coordinate of the Object
	 * @param x The X coordinate
	 */
	public void setX(int x){
		this.x = x;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the Y coordinate of the Object
	 * @param y The Y coordinate
	 */
	public void setY(int y){
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the X and Y coordinate at the same time
	 * @param x The new X coordinate
	 * @param y The new Y coordinate
	 */
	public void setXY(int x, int y){
		this.y = y;
		this.x = x;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the size and makes the component square
	 * @param size The width and height
	 */
	public void setSize(int size){
		xSize = ySize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the width
	 * @param size Width
	 */
	public void setXSize(int size){
		xSize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the height
	 * @param size Height
	 */
	public void setYSize(int size){
		ySize = size;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Sets the width and height
	 * @param y The width
	 * @param x The height
	 */
	public void setXYSize(int y, int x){
		xSize = x;
		ySize = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * The method called when a key is pressed on the canvas
	 * @param e The keyEvent made by the canvas
	 */
	public void keyPressed(KeyEvent e){
		
	}
	/**
	 * The method called when the mouse is clicked on the canvas
	 * @param e The mouseEvent made by the canvas
	 */
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
			if (Integrator.level == 1 && !UITutorial.powerTutorialOn) {
				Integrator.powerDisplay.setVisible(false);
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
					Integrator.reactor4.setVisible(true);
				}
				else if (UITutorial.turbineTutorialOn) { //for tutorial only!!
					Integrator.reactor4.setTurbineVisible(true);
				}
				else if (UITutorial.pumpTutorialOn) { //for tutorial only!!
					Integrator.reactor4.pump.pumps.get(4).showControls();
				}
				else if (UITutorial.powerTutorialOn) { //for tutorial only!!
					Integrator.powerDisplay.setVisible(true);
				} else {
					for(UIComponent comp: ui){
						comp.setVisible(false);
					}
				}
			}
		}
	}
	/**
	 * Returns the active status of the component
	 * @return
	 */
	public boolean getActive(){
		return clicked;
	}
	/**
	 * Draws the object
	 * @param g The graphics object to draw on
	 */
	public void drawObj(Graphics2D g){
		
	}
	/**
	 * Recursively returns all the objects and this
	 * @return An arraylist containing all the children gameobjects and this object too
	 */
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		for(GameObject temp: objects){
			all.addAll(temp.getObj());
		}
		
		return all;
	}
	/**
	 * Returns the UI array
	 * @return The UI component array list
	 */
	public ArrayList<UIComponent> getUi(){
		return ui;
	}
	/**
	 * Adds a gameobject as a child of this object
	 * @param a
	 */
	public void addObj(GameObject a){
		objects.add(a);
	}
	/**
	 * Add a lot of gameobjects as children of this object
	 * @param all The arraylist containing the gameobject you want to add
	 */
	public void addObj(ArrayList<GameObject> all){
		objects.addAll(all);
	}
	/**
	 * The method called to update the physics / time sensitive calculations
	 * @param deltaTime The time in milliseconds since this method was last called
	 */
	public void update(long deltaTime) {
		
	}
	/**
	 * The constructor that sets the X, Y coordinates, the width, height and updates the hitbox + initializes the fire
	 * @param x
	 * @param y
	 * @param xSize
	 * @param ySize
	 */
	public GameObject(int x, int y, int xSize, int ySize){
		this.x=x;
		this.y=y;
		this.xSize=xSize;
		this.ySize=ySize;
		bounds = new Rectangle(x, y, xSize, ySize);
		fire = new Fire(x, y, xSize, ySize, 2, 10000);
		fire.setActive(false);
		objects.add(fire);
		//fire.setActive(false);
	}
	/**
	 * Empty constructor
	 */
	public GameObject(){
		
	}
	/**
	 * Sets the object on fire, PLEASE DON'T SET THE FIRE ON FIRE!
	 * @param b Whether to set the object on fire or not
	 */
	public void setFire(boolean b){
		fire.setActive(b);
	}
}
