package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

import graphics.UIButton;
import graphics.UIComponent;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

public class Turbine extends GameObject{
	UISlider limit = new UISlider(20, Integrator.y-40-UIComponent.defaultHeight*2, 420, UIComponent.defaultHeight);//Please use default height for sliders
	UIButton spinDown = new UIButton(limit.getX() + limit.getWidth() + 20, limit.getY(), 200, UIComponent.defaultHeight);
	UISlider coolant = new UISlider(spinDown.getX() + spinDown.getWidth() + 20, limit.getY(), 400, UIComponent.defaultHeight);
	UIText temper = new UIText(20, Integrator.y-20-UIComponent.defaultHeight, 250, UIComponent.defaultHeight);
	UIText stat = new UIText(temper.getX() + temper.getWidth() + 20, Integrator.y-20-UIComponent.defaultHeight, 500, UIComponent.defaultHeight);
	boolean spinTurbineDown = false;
	public Color turbineOutline = Color.cyan;
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	private ArrayList<TurbineRotor> turbines = new ArrayList<TurbineRotor>();
	private Pipe powerPipe;
	private double temperature = 25.0;
	private boolean burnt = false;
	/**
	 * Returns the turbine rotors
	 */
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		all.addAll(objects);
		if(powerPipe!=null){
			all.add(powerPipe);
		}
		for(GameObject temp: turbines){
			all.addAll(temp.getObj());
		}
		return all;
	}
	/**
	 * Constructor
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @param xSize The width
	 * @param ySize The height
	 */
	public Turbine(int x, int y, int xSize, int ySize){
		super(x, y, xSize, ySize);
		fire.particles = 10000;
		fire.size = 1;
		limit.setDownInterval(0.001f);
		limit.setFontSize(40);
		limit.setTextDisplacement(40, 75);
		limit.setText("Turbine Speed Limit");
		limit.setPercentage(0);//Turbines are not spun up to begin
		
		spinDown.setText("Spin Down");
		spinDown.setFontSize(40);
		spinDown.setTextDisplacement(20, 75);
		
		coolant.setText("H2 coolant level");
		coolant.setFontSize(40);
		coolant.setTextDisplacement(30, 75);
		coolant.setPercentage(0);
		
		temper.setText("25 C");
		temper.setFontSize(40);
		temper.setTextDisplacement(30, 75);
		
		stat.setText("Status: Need H2");
		stat.setFontSize(40);
		stat.setTextDisplacement(30, 75);
		
		ui.add(coolant);
		ui.add(limit);
		ui.add(spinDown);
		ui.add(temper);
		ui.add(stat);
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	/**
	 * Sets the power pipe
	 * @param s The pipe connecting to the main power output 
	 */
	public void setPipe(Pipe s){
		powerPipe = s;
	}
	/**
	 * Draws it
	 */
	public void drawObj(Graphics2D g){
		g.setColor(turbineOutline);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.intLastXOffset, y+Integrator.intLastYOffset, xSize, ySize);
		g.setStroke(oldStroke);
	}
	/**
	 * Adds a turbine rotor
	 * @param tur The turbine rotor
	 */
	public void addTurbine(TurbineRotor tur) {
		turbines.add(tur);
	}
	/**
	 * Updates physics, temp, graphics etc.
	 */
	public void update(long deltaTime){
		int tSpeed = getSpeed();
		if(coolant.getPercentage() == 0){
			limit.disabled = true;
			stat.setText("Status: Need H2");
			limit.down(deltaTime);
		}else{
			stat.setText("Status: Nominal");
			limit.disabled = false;
		}
		if(burnt){
			limit.disabled = true;
			coolant.disabled = true;
			limit.down(deltaTime);
			stat.setText("Status: Burnt out");
		}
		if(tSpeed > limit.getPercentage()*10){
			if(!setSpeed(tSpeed--)){
				if(!setSpeed(tSpeed-2)){
					for(TurbineRotor p: turbines){
						p.setSpeed(tSpeed - 3);
					}
				}
			}
		}
		if(spinDown.clicked){
			spinTurbineDown = true;
			spinDown.clicked = false;
			limit.disabled = true;
		}
		if(spinTurbineDown){
			limit.down(deltaTime);
			if(limit.getPercentage() == 0){
				limit.disabled = false;
				spinTurbineDown = false;
			}
		}
		temperature += 0.001 * getSpeed() * limit.getPercentage() * deltaTime - 0.001 * deltaTime * coolant.getPercentage();
		if(temperature < 25){
			temperature = 25;
		}
		temper.setText(Reactor.roundDouble(temperature) + " C");
		if(temperature > 571 && coolant.getPercentage() > 0){//Autoignition point for H2
			this.setFire(true);
			stat.setText("Status: ON FIRE!");
			burnt = true;
			coolant.down(deltaTime);
		}else{
			this.fire.fight();
		}
	}
	/**
	 * Sets the speed of the turbine
	 * @param speed A 0 - 5 integer (for best effects)
	 * @return Whether the speed was set successfully
	 */
	public boolean setSpeed(int speed){
		if(speed < 0) speed = 0;
		if(speed / 2.0f > limit.getPercentage()*10f){
			return false;
		}
		for(TurbineRotor p: turbines){
			p.setSpeed(speed);
		}
		powerPipe.setSpeed(speed);
		return true;
	}
	/**
	 * Returns the speed of the turbines
	 * @return The speed
	 */
	public int getSpeed(){
		return turbines.get(0).getSpeed();
	}
	/**
	 * Sets the increment that the rotors move
	 * @param increment When the rotors move
	 */
	public void setIncrement(int increment){
		for(TurbineRotor p: turbines){
			p.setTime(increment);
		}
	}
	/**
	 * Method telling you how to use the class correctly
	 */
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
	/**
	 * Hides the turbine controls
	 */
	public void hide() {//Please change this to setVisible(boolean a)
		limit.setVisible(false);
		spinDown.setVisible(false);
	}
	/**
	 * Shows the turbine controls
	 */
	public void setVisible(boolean b) {
		for(UIComponent comp: ui){
			comp.setVisible(b);
		}
	}
}
