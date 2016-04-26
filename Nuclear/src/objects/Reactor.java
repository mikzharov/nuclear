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
	private boolean error = false;
	private String name;
	private double temperature = 25.0;
	private double coolingFactor=0;
	private double steamkPa = 101.3; //atmospheric pressure
	private Graphics2D g;
	public double controlRod = 1.0;
	UIText currentTemp = new UIText(10, Integrator.y-90, 300, 65);//renamed from warning to currentTemp, warning is for the warning messages
	UIText rodDepth = new UIText(currentTemp.getX() + currentTemp.getWidth() + 15, currentTemp.getY(), 400, 65);//We also want to use the relative coordinates
	UIText pressure = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 400, 65);
	UISlider rods = new UISlider(currentTemp.getX(), currentTemp.getY()-120, 420, UIComponent.defaultHeight);//Please use default height for sliders
	UIText warning = new UIText(rods.getX() + rods.getWidth() + 15, rods.getY()+45, 850, 65);
	
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
		currentTemp.setText(reactorTemp()); //Update the text somewhere else
		currentTemp.setFontSize(30);
		currentTemp.setTextDisplacement(10, 45);
		ui.add(currentTemp);
		
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod*100)+"%");
		rodDepth.setFontSize(30);
		rodDepth.setTextDisplacement(10, 45);
		ui.add(rodDepth);//UI is a class array for ui of things
		
		pressure.setText("Pressure (kPa): "+roundDouble(steamOutput()));
		pressure.setFontSize(30);
		pressure.setTextDisplacement(10, 45);
		ui.add(pressure);
		
		rods.setText("Control rods");
		ui.add(rods);
		
		warning.setText(reactorErrorMessage());
		warning.setFontSize(25);
		warning.setTextDisplacement(10, 45);
		ui.add(warning);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void updateControls(long deltaTime) {
		currentTemp.setText(reactorTemp());
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod*100)+"%");
		pressure.setText("Pressure (kPa): "+roundDouble(steamOutput()));
		
		warning.setText(reactorErrorMessage());
		//alternating flashing red and black text every second
		long currentTime = System.currentTimeMillis();
		//every even second go red
		if (currentTime/1000%2 == 0) {
			warning.setTextColor(Color.red);
		}
		//every odd second go black
		if (currentTime/1000%2 == 1) {
			warning.setTextColor(Color.black);
		}
			
		if (clicked == true) { //otherwise the error text box won't go away when desired
			warning.setVisible(error); //if an error message has been added
		}
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
		updateControls(deltaTime);
		
	}
	public double steamOutput() {
		//the pressure is proportional to the amount of water boiled which is proportional to the temperature, but it must first be boiled off, which takes time ie. 
		//if the temperature drops then the pressure should drop
		
		//water must be at 100C to evaporate
		if (temperature >= 100) {
			steamkPa+=temperature*0.0001; //temperature to water evaporated coefficient
		}
		//if it is less than 100C, water will condense and the pressure will go down
		else {
			//100-temp because the further the temperature form 100, the greater the decrease in pressure
			//ie. 25C is below 75C below 100C
			steamkPa-=(100-temperature)*0.001;
		}
		
		if (steamkPa <= 101.3) {
			steamkPa=101.3; //cannot be less than atmospheric pressure
		}
		
		return steamkPa;
	}
	
	public String reactorTemp() {
		double tempControl = controlRod;
		
		//the temperature is inversely proportional to the control rod depth, ie. if the control rods are 100% in then the temperature should drop
		tempControl=Math.abs(1-tempControl);
		
		//if the control rods are 75% in or more the temperature should drop
		tempControl-=0.25;
		
		temperature+=tempControl*0.45; //-coolingFactor;
		
		//the temperature should not be allowed to drop below 25C (room temperature of the reactors, ie. no reaction)
		if (temperature <= 25.0) {
			temperature=25.0;
		}
		return name+": "+roundDouble(temperature)+"°C";
	}
	
	public String reactorErrorMessage() {
		String errorMessage = "WARNING: ";
		//temperature melt down
		if (temperature > 450) {
			error=true;
			errorMessage+="OVERHEATING! ";
		}
		
		//pressure bursting
		if (steamkPa > 800) {
			error=true;
			errorMessage+="PRESSURE OVERLOAD! ";
		}
		
		//radiation leak
		if (steamkPa > 1000) {
			error=true;
			errorMessage+="RADIATION LEAK! ";
		}
		
		//melt down
		if (temperature > 700) {
			error=true;
			errorMessage+="REACTOR MELTDOWN! ";
		}
		
		if (temperature < 450 && steamkPa < 800) {
			error=false;
		}
		return errorMessage;
	}

}
