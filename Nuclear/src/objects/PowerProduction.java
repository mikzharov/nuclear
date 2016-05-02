package objects;

import java.awt.Graphics2D;

import graphics.UIComponent;
import graphics.UIText;
/**
 * 
 * A GameObject that should really be a UIComponent
 *
 */
public class PowerProduction extends GameObject {
	UIText power1;
	UIText power2;
	UIText power3;
	UIText power4;
	UIText totalPower;
	long lastPower;
	public static int powerProduced = 0;
	
	public PowerProduction(int x, int y) {
		power1 = new UIText(x, y, 240, 100);//These first two values can't be negative! The top left corner is (0,0) so it will not render!
		power2 = new UIText(power1.getX()+power1.getWidth()+15, power1.getY(), 240, 100);
		power3 = new UIText(power2.getX()+power2.getWidth()+15, power1.getY(), 240, 100);
		power4 = new UIText(power3.getX()+power3.getWidth()+15, power1.getY(), 240, 100);
		totalPower = new UIText(power4.getX()+power4.getWidth()+15, power1.getY(), 240, 100);
		
		power1.setText("MW");
		power2.setText("MW");
		power3.setText("MW");
		power4.setText("MW");
		totalPower.setText("MW");
		
		power1.setFontSize(40);
		power2.setFontSize(40);
		power3.setFontSize(40);
		power4.setFontSize(40);
		totalPower.setFontSize(40);
		setActive(false);
		ui.add(power1);
		ui.add(power2);//Components only need to be put into the ui array once, not every time drawObj is called
		ui.add(power3);
		ui.add(power4);
		ui.add(totalPower);
		
		this.x = x;
		this.y = y;
		lastPower = System.currentTimeMillis();
		for(UIComponent comp: ui){
			comp.setVisible(true);
		}
		
	}
	public void drawObj(Graphics2D g) {
		//Do not update things in here! Only update stuff when it changes to prevent overhead
	}
	
	public void update(long deltaTime) {
		
	}
	
	public void updatePower(int reactor1, int reactor2, int reactor3, int reactor4) {
		//if power supply is over 1,000,000 kW, express in MW
		if (reactor4 > 1000000)
			power1.setText((reactor4/1000)+" MW/s");
		else
			power1.setText(reactor4+" kW/s");
		if (reactor3 > 1000000)
			power2.setText((reactor3/1000)+" MW/s");
		else
			power2.setText(reactor3+" kW/s");
		if (reactor2 > 1000000)
			power3.setText((reactor2/1000)+" MW/s");
		else
			power3.setText(reactor2+" kW/s");
		if (reactor1 > 1000000)
			power4.setText((reactor1/1000)+" MW/s");
		else
			power4.setText(reactor1+" kW/s");
		
		//every second the total power produced adds all the kiloWattseconds of the four reactors, giving the total power
		if (lastPower + 1000 < System.currentTimeMillis()) {
			powerProduced+=reactor4+reactor3+reactor2+reactor1;
			if (powerProduced > 1000000) { //so the text doesn't move outside of the box
				totalPower.setText((powerProduced/1000)+" MW");
			}
			else {
				totalPower.setText(powerProduced+" kW");
			}
			lastPower = System.currentTimeMillis();
		}
	}
	public void setActive(boolean bol){
		power1.setActive(bol);
		power2.setActive(bol);
		power3.setActive(bol);
		power4.setActive(bol);
	}
}
