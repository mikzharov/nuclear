package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

import graphics.UIButton;
import graphics.UIComponent;
import graphics.UISlider;
import logic.Integrator;

public class Turbine extends GameObject{
	UISlider limit = new UISlider(20, Integrator.y-20-UIComponent.defaultHeight, 420, UIComponent.defaultHeight);//Please use default height for sliders
	UIButton spinDown = new UIButton(limit.getX() + limit.getWidth() + 20, Integrator.y-20-UIComponent.defaultHeight, 200, UIComponent.defaultHeight);
	UISlider coolant = new UISlider(spinDown.getX() + spinDown.getWidth() + 20, Integrator.y-20-UIComponent.defaultHeight, 400, UIComponent.defaultHeight);
	boolean spinTurbineDown = false;
	public Color turbineOutline = Color.cyan;
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	private ArrayList<TurbineRotor> turbines = new ArrayList<TurbineRotor>();
	private Pipe powerPipe;
	public ArrayList<GameObject> getObj(){
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		all.add(this);
		if(powerPipe!=null){
			all.add(powerPipe);
		}
		for(GameObject temp: turbines){
			all.addAll(temp.getObj());
		}
		return all;
	}
	public Turbine(int x, int y, int xSize, int ySize){
		super(x, y, xSize, ySize);
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
		ui.add(coolant);
		ui.add(limit);
		ui.add(spinDown);
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	public void setPipe(Pipe s){
		powerPipe = s;
	}
	public void drawObj(Graphics2D g){
		g.setColor(turbineOutline);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.intLastXOffset, y+Integrator.intLastYOffset, xSize, ySize);
		g.setStroke(oldStroke);
	}
	public void addTurbine(TurbineRotor tur) {
		turbines.add(tur);
	}
	public void update(long deltaTime){
		int tSpeed = getSpeed();
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
		if(limit.getPercentage()==0){
			coolant.disabled = false;
		}else{
			coolant.disabled = true;
		}
	}
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
	public int getSpeed(){
		return turbines.get(0).getSpeed();
	}
	public void setIncrement(int increment){
		for(TurbineRotor p: turbines){
			p.setTime(increment);
		}
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
	
	public void hide() {//Please change this to setVisible(boolean a)
		limit.setVisible(false);
		spinDown.setVisible(false);
	}
	
	public void setVisible(boolean b) {
		for(UIComponent comp: ui){
			comp.setVisible(b);
		}
	}
}
