package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	
	private String name;
	private double temperature = 300.0;
	private Graphics2D g;
	public double controlRod = 1.0;
	
	public Reactor(int xPos, int yPos, int xSize, int ySize, String name) {
		this.x = xPos;
		this.y = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.name = name;
	}
	
	public void drawObj(Graphics2D g) {
		g.setColor(Color.cyan);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.int_last_x_offset, y+Integrator.int_last_y_offset, xSize, ySize);
		g.setStroke(oldStroke);
	}
	
	public void drawControls(Graphics2D g, double controlRod) {
		UIText warning = new UIText(150, 700, 300, 200);
		warning.setText(reactorError(controlRod));
		warning.drawObj(g);
		
		UIText rodDepth = new UIText(500, 700, 300, 200);
		rodDepth.setText(String.valueOf(controlRod));
		rodDepth.drawObj(g);
		
		UIText pressure = new UIText(150, 900, 300, 200);
		pressure.setText(String.valueOf(steamOutput(controlRod)));
		pressure.drawObj(g);
	}
	
	public void mouseClicked(MouseEvent e) {
		drawControls(g, controlRod);
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
		return "Reactor "+name+" is overheating!";
	}

}
