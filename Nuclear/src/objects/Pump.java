package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import graphics.UIComponent;
import graphics.UISlider;
import logic.Integrator;

public class Pump extends GameObject{
//TODO
	public float coolingFactor = 0.0f;
	Color color = Color.gray;
	Color pumpOutline = Color.cyan;
	UISlider pumpLevel = new UISlider(20, Integrator.y-20-UIComponent.defaultHeight, 720, UIComponent.defaultHeight);//Please use default height for sliders
	public Pump(int x, int y, int size){
		super(x-size/2, y-size/2, size, size);
		pumpLevel.setText("Cooling Pumping Level");
		pumpLevel.setVisible(false);
		pumpLevel.setUpInterval(0.001f);
		pumpLevel.setDownInterval(0.01f);
		pumpLevel.setPercentage(0);
		ui.add(pumpLevel);
	}
	public void update(long deltaTime){
		coolingFactor = pumpLevel.getPercentage() * deltaTime / 100 / 4;
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
