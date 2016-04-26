package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import graphics.UIComponent;
import graphics.UIText;
import logic.Integrator;

public class PowerProduction extends GameObject {
	
	UIText power1 = new UIText(-240, -275, 325, 100);
	UIText power2 = new UIText(power1.getX()+power1.getWidth()+15, power1.getY(), 325, 100);
	UIText power3 = new UIText(power2.getX()+power2.getWidth()+15, power1.getY(), 325, 100);
	UIText power4 = new UIText(power3.getX()+power3.getWidth()+15, power1.getY(), 325, 100);
	UIText totalPower = new UIText(power4.getX()+power4.getWidth()+15, power1.getY(), 325, 100);
	
	public PowerProduction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void drawObj(Graphics2D g) {
		
		power1.setText("MW");
		power2.setText("MW");
		power3.setText("MW");
		power4.setText("MW");
		totalPower.setText("MW");
		
		power1.setFontSize(50);
		power2.setFontSize(50);
		power3.setFontSize(50);
		power4.setFontSize(50);
		totalPower.setFontSize(50);
		
		ui.add(power1);
		ui.add(power2);
		ui.add(power3);
		ui.add(power4);
		ui.add(totalPower);
		
		for(UIComponent comp: ui){
			comp.drawObj(g);
			comp.setVisible(true);
		}
	}
	
	public void update(long deltaTime) {
	}
	
	public void updatePower(int reactor1, int reactor2, int reactor3, int reactor4) {
		power1.setText(reactor4+" MW");
		power2.setText(reactor3+" MW");
		power3.setText(reactor2+" MW");
		power4.setText(reactor1+" MW");
		totalPower.setText((reactor4+reactor3+reactor2+reactor1)+" MW");
	}
}
