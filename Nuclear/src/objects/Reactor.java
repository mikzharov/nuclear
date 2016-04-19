package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import graphics.UIComponent;
import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	
	private String name;
	private double temperature = 300.0;
	private Graphics2D g;
	public double controlRod = 1.0;
	UIText warning = new UIText(10, Integrator.y-150, 650, 105);//We want to reuse these
	UIText rodDepth = new UIText(warning.getX() + warning.getWidth() + 15, warning.getY(), 200, 105);//We also want to use the relative coordinates
	UIText pressure = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 200, 105);
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
		warning.setText(reactorError(controlRod));//Update the text somewhere else
		ui.add(warning);
		
		rodDepth.setText(String.valueOf(controlRod));
		ui.add(rodDepth);//UI is a class array for Ui of things
		
		pressure.setText(String.valueOf(steamOutput(controlRod)));
		ui.add(pressure);
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		bounds.setLocation(x+Integrator.int_last_x_offset, y + Integrator.int_last_y_offset);
		AffineTransform g = new AffineTransform();//This code makes sure that the object was clicked
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains((e.getX()), (e.getY()))){
			//Hit
			System.out.println("Clicked Reactor");
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
		}else{
			for(UIComponent comp: ui){
				comp.setVisible(false);
			}
		}
	}
	
	public void update(long deltaTime) {
		
	}
	
	public long steamOutput(double controlRod) {
		long steamKPa = 0;
		
		if(controlRod == 0) {
			controlRod = 0.001;
		}
		else {
			steamKPa = (long)(1/controlRod);
		}
		
		if(steamKPa == 1) {
			steamKPa = 0;
		}
		
		return steamKPa;
	}
	
	public String reactorError(double controlRod) {
		if(controlRod == 0) {
			controlRod = 0.001;
		}
		
		controlRod-=0.5;
		
		temperature+=controlRod*10;
		return name+": overheating!";
	}

}
