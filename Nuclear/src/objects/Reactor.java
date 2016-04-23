package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import graphics.UIComponent;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	
	private boolean clicked = false;
	private String name;
	private double temperature = 300.0;
	private double coolingFactor=0;
	private Graphics2D g;
	public double controlRod = 1.0;
	UIText warning = new UIText(10, Integrator.y-150, 300, 105);//We want to reuse these
	UIText rodDepth = new UIText(warning.getX() + warning.getWidth() + 15, warning.getY(), 400, 105);//We also want to use the relative coordinates
	UIText pressure = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 400, 105);
	UISlider rods = new UISlider(warning.getX(), warning.getY()-120, 420, UIComponent.defaultHeight);//Please use default height for sliders
	public double roundDouble(double a){
		return Math.round(a*100.0)/100.0;
	}
	public Reactor(int xPos, int yPos, int xSize, int ySize, String name) {
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
		this.x = xPos;
		this.y = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.name = name;
		bounds = new Rectangle(x, y, xSize, ySize);
		drawControls(g, controlRod);
	}
	
	public void drawObj(Graphics2D g) {
		g.setColor(Color.cyan);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.int_last_x_offset, y+Integrator.int_last_y_offset, xSize, ySize);
		g.setStroke(oldStroke);
	}
	
	public void drawControls(Graphics2D g, double controlRod) {//Call this once to add them to the rendering list
		warning.setText(reactorError()); //Update the text somewhere else
		warning.setFontSize(30);
		ui.add(warning);
		
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod)+"%");
		rodDepth.setFontSize(30);
		ui.add(rodDepth);//UI is a class array for ui of things
		
		pressure.setText("Pressure: "+roundDouble(controlRod)+" kPa");
		pressure.setFontSize(30);
		ui.add(pressure);
		rods.setText("Control rods");
		ui.add(rods);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void updateControls() {
		warning.setText(reactorError());
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod)+"%");
		pressure.setText("Pressure: "+roundDouble(controlRod)+" kPa");
	}
	
	public void mouseClicked(MouseEvent e) {
		//Do not do physics in mouseClicked, do it in update
		bounds.setLocation(x+Integrator.int_last_x_offset, y + Integrator.int_last_y_offset);
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
	public void update(long deltaTime) {
		controlRod=rods.getPercentage();
		if(clicked == true) {
			updateControls();
		}
	}
	public long steamOutput() {
		double tempControl = controlRod;//Do not write to the controlRod variable
		long steamKPa = 0;
		
		if(tempControl == 0) {
			tempControl = 0.001;
		}
		else {
			steamKPa = (long)(1/tempControl);
		}
		
		if(steamKPa == 1) {
			steamKPa = 0;
		}
		
		return steamKPa;
	}
	
	public String reactorError() {
		double tempControl = controlRod;
		temperature+=tempControl*10-coolingFactor;
		return name+": "+roundDouble(temperature)+"°C";
	}
	
	public void keyPressed(KeyEvent e) {
			//Do not do physics in keyPressed, do it in update
	}

}
