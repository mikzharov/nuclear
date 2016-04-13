package objects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Reactor extends GameObject {
	
	private int xPos, yPos, xSize, ySize;
	private String name;
	private double temperature = 300.0;
	
	public Reactor(int xPos, int yPos, int xSize, int ySize, String name) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.name = name;
	}
	
	public void drawOutline(Graphics2D g) {
		g.setColor(Color.cyan);
		g.drawRect(xPos, yPos, xSize, ySize);
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
