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
import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	
	private boolean clicked = false;
	private String name;
	private double temperature = 300.0;
	private Graphics2D g;
	public double controlRod = 1.0;
	UIText warning = new UIText(10, Integrator.y-150, 300, 105);//We want to reuse these
	UIText rodDepth = new UIText(warning.getX() + warning.getWidth() + 15, warning.getY(), 400, 105);//We also want to use the relative coordinates
	UIText pressure = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 400, 105);
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
		
		rodDepth.setText("Control Rod Depth: "+String.valueOf(this.controlRod)+"%");
		rodDepth.setFontSize(30);
		ui.add(rodDepth);//UI is a class array for ui of things
		
		pressure.setText("Pressure: "+String.valueOf(steamOutput())+" kPa");
		pressure.setFontSize(30);
		ui.add(pressure);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void updateControls() {
		warning.setText(reactorError());
		rodDepth.setText("Control Rod Depth: "+String.valueOf(this.controlRod)+"%");
		pressure.setText("Pressure: "+String.valueOf(steamOutput())+" kPa");
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
			this.clicked=true;
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
		}
		else {
			if (e.getY() < 500) { //otherwise if you click on the controls they disappear
				this.clicked=false;
				for(UIComponent comp: ui){
					comp.setVisible(false);
				}
			}
		}
	}
	
	public void update(long deltaTime) {
		
	}
	
	public long steamOutput() {
		long steamKPa = 0;
		
		if(this.controlRod == 0) {
			this.controlRod = 0.001;
		}
		else {
			steamKPa = (long)(1/this.controlRod);
		}
		
		if(steamKPa == 1) {
			steamKPa = 0;
		}
		
		return steamKPa;
	}
	
	public String reactorError() {
		if(this.controlRod == 0) {
			this.controlRod = 0.001;
		}
		
		this.controlRod-=0.5;
		
		temperature+=this.controlRod*10;
		return name+": "+temperature+"°C";
	}
	
	public void keyPressed(KeyEvent e) {
		if(this.clicked == true) {
			if(!Integrator.paused && e.getKeyCode() == KeyEvent.VK_W){
				if (this.controlRod < 100.0) {
					this.controlRod+=0.5;
					System.out.println(this.controlRod);
				}
			}
			if(!Integrator.paused && e.getKeyCode() == KeyEvent.VK_S){
				if (this.controlRod > 0.0) {
					this.controlRod-=0.5;
					System.out.println(this.controlRod);
				}
			}
			updateControls();
		}	
	}

}
