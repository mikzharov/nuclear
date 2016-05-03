package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import graphics.UIComponent;
import graphics.UISlider;
import logic.Integrator;

public class TurbineSystem extends GameObject{
	boolean clicked = false;
	UISlider limit = new UISlider(20, Integrator.y-20-UIComponent.defaultHeight, 420, UIComponent.defaultHeight);//Please use default height for sliders
	private Color turbineOutline = Color.cyan;
	//This will be a shell object to house all the pipes / pumps / turbines for a reactor
	private ArrayList<Turbine> turbines = new ArrayList<Turbine>();
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
	public TurbineSystem(int x, int y, int xSize, int ySize){
		super(x, y, xSize, ySize);
		
		limit.setFontSize(40);
		limit.setTextDisplacement(40, 75);
		limit.setText("Turbine Speed Limit");
		ui.add(limit);
		
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
	public void addTurbine(Turbine tur) {
		turbines.add(tur);
	}
	public void update(long deltaTime){
		int tSpeed = getSpeed();
		if(tSpeed > limit.getPercentage()*10){
			if(!setSpeed(tSpeed--)){
				if(!setSpeed(tSpeed-2)){
					for(Turbine p: turbines){
						p.setSpeed(tSpeed - 3);
					}
				}
			}
		}
	}
	public boolean setSpeed(int speed){
		if(speed < 0) speed = 0;
		if(speed / 2.0f > limit.getPercentage()*10f){
			return false;
		}
		for(Turbine p: turbines){
			p.setSpeed(speed);
		}
		powerPipe.setSpeed(speed);
		return true;
	}
	public int getSpeed(){
		return turbines.get(0).getSpeed();
	}
	public void setIncrement(int increment){
		for(Turbine p: turbines){
			p.setTime(increment);
		}
	}
	public void addObj(GameObject o){
		throw new UnsupportedOperationException("Use specific methods for this class");
	}
	public void mouseClicked(MouseEvent e) {
		//Do not do physics in mouseClicked, do it in update
		bounds.setLocation(x+Integrator.intLastXOffset, y + Integrator.intLastYOffset);
		AffineTransform g = new AffineTransform();//This code makes sure that the object was clicked
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains((e.getX()), (e.getY()))){
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
				}
			}
			if(!uiClicked){
				clicked=false;
				for(UIComponent comp: ui){
					comp.setVisible(false);
				}
			}
		}
	}
	
	public void hide() {
		limit.setVisible(false);
	}
}
