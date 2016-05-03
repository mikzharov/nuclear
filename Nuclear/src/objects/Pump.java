package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import logic.Integrator;

public class Pump extends GameObject{
//TODO
	public float coolingFactor = 0.0f;
	Color color = Color.gray;
	Color pumpOutline = Color.cyan;
	public Pump(int x, int y, int size){
		super(x-size/2, y-size/2, size, size);
	}
	public void drawObj(Graphics2D g){
		g.setColor(pumpOutline);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.intLastXOffset, y+Integrator.intLastYOffset, xSize, ySize);		
		g.setStroke(oldStroke);
		
		g.setColor(color);
		g.fillOval(x+5+Integrator.intLastXOffset, y+5+Integrator.intLastYOffset, xSize-10, ySize-10);
	}
	public float getCoolingFactor(){
		return coolingFactor;
	}
}
