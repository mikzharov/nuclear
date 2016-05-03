package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import graphics.UIButton;
import graphics.UIComponent;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	private PipeSystem pSys = null;//This is for the components that need to be controlled by the reactor
	private Turbine t = null;//This is for the components that need to be controlled by the reactor
	private Turbine t2 = null;//2 turbines per reactor
	private PumpSystem pump = null;
	private boolean error = false;
	private String name;
	private double temperature = 25.0;
	private double coolingFactor=0;
	private double steamkPa = 101.3; //atmospheric pressure
	private Graphics2D g;
	public double controlRod = 1.0;
	private Color reactorOutline = Color.cyan;
	private Color lifeColor = Color.green;
	private Font reactorFont = new Font("Impact", Font.PLAIN, 80);
	private Stroke lifeStroke = new BasicStroke(5);
	private double reactorLife = 100.0;
	private boolean dead = false;
	UIText currentTemp = new UIText(10, Integrator.y-90, 300, 65);//renamed from warning to currentTemp, warning is for the warning messages
	UIText rodDepth = new UIText(currentTemp.getX() + currentTemp.getWidth() + 15, currentTemp.getY(), 400, 65);//We also want to use the relative coordinates
	UIText pressureText = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 400, 65);
	UISlider rods = new UISlider(currentTemp.getX(), currentTemp.getY()-120, 420, UIComponent.defaultHeight);//Please use default height for sliders
	UIText warning = new UIText(rods.getX() + rods.getWidth() + 15, rods.getY()+45, 695, 65);
	UIButton emergencyCooling = new UIButton(currentTemp.getX()+rods.getWidth()+15, rods.getY(), 170, 35);
	UIButton neutronPoison = new UIButton(emergencyCooling.getX()+emergencyCooling.getWidth()+15, rods.getY(), 170, 35);
		
	public double roundDouble(double a){
		return Math.round(a*100.0)/100.0;
	}
	
	public Reactor(int xPos, int yPos, int xSize, int ySize, String name) {
		super(xPos, yPos, xSize, ySize);
		this.name = name;
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
		drawControls(g, controlRod);
	}
	
	public void drawObj(Graphics2D g) {
		g.setColor(reactorOutline);
		float thickness = 5.0f;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x+Integrator.intLastXOffset, y+Integrator.intLastYOffset, xSize, ySize);		
		g.setStroke(oldStroke);
		
		g.setColor(Color.white);
		g.fillRect(x+Integrator.intLastXOffset-xSize-80, y+ySize/2+Integrator.intLastYOffset-100, 170+50, 150);
		
		g.setColor(Color.gray);
		g.setStroke(lifeStroke);
		g.drawRect(x+Integrator.intLastXOffset-xSize-80, y+ySize/2+Integrator.intLastYOffset-100, 170+50, 150);
		
		g.setColor(lifeColor);
		g.setFont(reactorFont);
		g.drawString(String.valueOf(roundDouble(reactorLife)), x+Integrator.intLastXOffset-xSize-55, y+ySize/2+Integrator.intLastYOffset+10);
	}
	
	public void drawControls(Graphics2D g, double controlRod) {//Call this once to add them to the rendering list
		currentTemp.setText(""); //Update the text somewhere else
		currentTemp.setFontSize(30);
		currentTemp.setTextDisplacement(10, 45);
		ui.add(currentTemp);
		
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod*100)+"%");
		rodDepth.setFontSize(30);
		rodDepth.setTextDisplacement(10, 45);
		ui.add(rodDepth);//UI is a class array for ui of things
		
		pressureText.setText("Pressure (kPa): ");
		pressureText.setFontSize(30);
		pressureText.setTextDisplacement(10, 45);
		ui.add(pressureText);
		
		rods.setText("Control rods");
		ui.add(rods);
		
		warning.setText(reactorErrorMessage());
		warning.setFontSize(30);
		warning.setTextDisplacement(10, 45);
		ui.add(warning);
		
		emergencyCooling.setText("Emr. Cooling");
		emergencyCooling.setTextDisplacement(10, 25);
		emergencyCooling.setFontSize(20);
		ui.add(emergencyCooling);
		
		neutronPoison.setText("Neutron Poison");
		neutronPoison.setTextDisplacement(10, 25);
		neutronPoison.setFontSize(20);
		ui.add(neutronPoison);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void updateControls(long deltaTime) {
		currentTemp.setText(reactorTemp(deltaTime));
		rodDepth.setText("Control Rod Depth: "+roundDouble(controlRod*100)+"%");
		pressureText.setText("Pressure (kPa): "+roundDouble(steamOutput(deltaTime)));
		
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
		
		if (emergencyCooling.clicked) {
			emergencyCooling.clicked=false;
			if (coolingFactor == 0) {
				emergencyCooling.setTextColor(Color.blue);
				coolingFactor = 10;
			}
			else if (coolingFactor == 10) {
				emergencyCooling.setTextColor(Color.black);
				coolingFactor = 0;
			}
		}
		
		if (Integrator.gameover) {
			for(UIComponent comp: ui){
				comp.setVisible(false);
			}
		}
	}
	
	public void update(long deltaTime) {
		if(!rods.disabled){
			controlRod=rods.getPercentage();
		}else{
			controlRod = 1;//temp goes down if the reactor is "dead"
		}
		
		updateControls(deltaTime);
		pSys.setSpeed(0);
		t.setSpeed(0);
		t2.setSpeed(0);
		if(steamOutput(deltaTime) > 115){
			pSys.setSpeed(1);
			t.setSpeed(1);
			t2.setSpeed(1);
		}
		if(steamOutput(deltaTime) > 200){
			pSys.setSpeed(2);
			t.setSpeed(2);
			t2.setSpeed(2);
		}
		if(steamOutput(deltaTime) > 225){
			pSys.setSpeed(3);
			t.setSpeed(3);
			t2.setSpeed(3);
		}
		if(steamOutput(deltaTime) > 300){
			pSys.setSpeed(4);
			t.setSpeed(4);
			t2.setSpeed(4);
		}
		if(steamOutput(deltaTime) > 350){
			pSys.setSpeed(5);
			t.setSpeed(5);
			t2.setSpeed(5);
		}
		
		if (error) {
			if (reactorLife > 0)
				reactorLife-=0.01;
			if(temperature>600){//The maximum temp of a fuel rod in an RBMK reactor 600 C, so going beyond that will incur penalties
				reactorLife-=0.1;
			}
			if(steamkPa > 6900){//The drum seperators maximum pressure is something like 69 bar, 6900 in kpa
				reactorLife-=0.1;
			}
		}
		
		if(reactorLife < 75 && reactorLife >= 50) {
			lifeColor = Color.yellow;
		}
		if(reactorLife < 50 && reactorLife >= 25) {
			lifeColor = Color.orange;
		}
		if(reactorLife < 25 && reactorLife > 0.1) {
			lifeColor = Color.red;
		}
		if(reactorLife < 0.1) {
			reactorLife=0.0;
			lifeColor = Color.black;
			reactorOutline = Color.black;
			dead=true;
			Integrator.justDied=true;
			rods.disabled = true;
		}
	}
	public double steamOutput(long deltaTime) {
		//the pressure is proportional to the amount of water boiled which is proportional to the temperature, but it must first be boiled off, which takes time ie. 
		//if the temperature drops then the pressure should drop
		
		//water must be at 100C to evaporate
		if (temperature >= 100) {
			steamkPa+=temperature*0.000005*deltaTime; //temperature to water evaporated coefficient
		}
		//if it is less than 100C, water will condense and the pressure will go down
		else {
			//100-temp because the further the temperature form 100, the greater the decrease in pressure
			//ie. 25C is below 75C below 100C
			steamkPa-=(100-temperature)*0.00001*deltaTime;
		}
		
		if (steamkPa <= 101.3) {
			steamkPa=101.3; //cannot be less than atmospheric pressure
		}
		
		if(dead) {
			steamkPa = 101.3;
		}
		
		return steamkPa;
	}
	
	public String reactorTemp(long deltaTime) {
		double tempControl = controlRod;
		
		//the temperature is inversely proportional to the control rod depth, ie. if the control rods are 100% in then the temperature should drop
		tempControl=Math.abs(1-tempControl);
		
		//if the control rods are 75% in or more the temperature should drop
		tempControl-=0.017 * deltaTime;
		float coolant = 0.0f;
		if(pump!=null)coolant = pump.getCoolingFactor()*deltaTime;
		temperature+=tempControl*0.063*deltaTime-coolant; //set at 0.95 for testing, final should be 0.05 //-coolingFactor;
		
		//the temperature should not be allowed to drop below 25C (room temperature of the reactors, ie. no reaction)
		if (temperature <= 25.0) {
			temperature=25.0;
		}
		
		return name+": "+roundDouble(temperature)+" C";
	}
	
	public String reactorErrorMessage() {
		String errorMessage = "WARNING: ";
		
		//temperature melt down
		if (temperature > 450) {
			error=true;
			reactorOutline=Color.red;
			//melt down
			if (temperature > 700) {
				errorMessage+="REACTOR MELTDOWN! ";
			}
			else {
				errorMessage+="OVERHEATING! ";
			}
		}
		
		//pressure bursting
		if (steamkPa > 6900) {
			error=true;
			reactorOutline=Color.red;
			//radiation leak
			if (steamkPa > 7300) {
				errorMessage+="RADIATION LEAK! ";
			}
			else {
				errorMessage+="CRITICAL PRESSURE! ";
			}
		}
		
		if (temperature < 450 && steamkPa < 800) {
			reactorOutline=Color.cyan;
			error=false;
		}
		return errorMessage;
	}
	
	public int powerGeneration() {
		double megaWatts = ((t.getSpeed()+t2.getSpeed())*0.0004*(steamkPa*steamkPa));
		return (int)megaWatts;
	}
	
	public void setPipeSystem(PipeSystem s){
		objects.remove(pSys);//Removes old one
		pSys = s;
		objects.add(pSys);
	}
	
	public void setTurbine(Turbine s){
		objects.remove(t);//Removes old one
		t = s;
		objects.add(t);
	}
	
	public void setTurbine2(Turbine s){
		objects.remove(t2);//Removes old one
		t2 = s;
		objects.add(t2);
	}
	
	public void setPumpSystem(PumpSystem p){
		objects.remove(p);//Removes old one
		pump = p;
		objects.add(p);
	}
	
	public boolean getActive(){
		return clicked;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void hideControls() {
		currentTemp.setVisible(false);
		rodDepth.setVisible(false);
		pressureText.setVisible(false);
		rods.setVisible(false);
		warning.setVisible(false);
		emergencyCooling.setVisible(false);
		neutronPoison.setVisible(false);
	}
}
