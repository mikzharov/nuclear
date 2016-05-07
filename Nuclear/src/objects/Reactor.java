package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import graphics.UIButton;
import graphics.UIComponent;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

public class Reactor extends GameObject {
	private PipeSystem pSys = null;//This is for the components that need to be controlled by the reactor
	public Turbine t = null; //public for tutorial //This is for the components that need to be controlled by the reactor
	private Turbine t2 = null;//2 turbines per reactor
	public PumpSystem pump = null;
	private boolean error = false;
	private String name;
	private double temperature = 25.0;
	private double steamkPa = 101.3; //atmospheric pressure
	public double controlRod = 1.0;
	public Color reactorOutline = Color.cyan;
	public Color lifeColor = Color.green;
	private Font reactorFont = new Font("Impact", Font.PLAIN, 80);
	private Stroke lifeStroke = new BasicStroke(5);
	private double reactorLife = 100.0;
	private boolean dead = false;
	UIText currentTemp = new UIText(10, Integrator.y-90, 300, 65);//renamed from warning to currentTemp, warning is for the warning messages
	UIText rodDepth = new UIText(currentTemp.getX() + currentTemp.getWidth() + 15, currentTemp.getY(), 400, 65);//We also want to use the relative coordinates
	UIText pressureText = new UIText(rodDepth.getX() + rodDepth.getWidth() + 15, rodDepth.getY(), 400, 65);
	UISlider rods = new UISlider(currentTemp.getX(), currentTemp.getY()-120, 420, UIComponent.defaultHeight);//Please use default height for sliders
	private UIText warning = new UIText(rods.getX() + rods.getWidth() + 15, rods.getY()+45, 695, 65);
	UIButton scram = new UIButton(currentTemp.getX()+rods.getWidth()+15, rods.getY(), 170, 35);
	UISlider masterPump = new UISlider(currentTemp.getX(), rods.getY()-rods.getHeight()-15, 420, UIComponent.defaultHeight);
	/**
	 * Rounds a double to two decimal places
	 * @param a The double to round
	 * @return The rounded double
	 */
	public static double roundDouble(double a){
		return Math.round(a*100.0)/100.0;
	}
	/**
	 * This is the constructor
	 * @param xPos The X position
	 * @param yPos The Y position
	 * @param xSize The width
	 * @param ySize The height
	 * @param name The name of the reactor
	 */
	public Reactor(int xPos, int yPos, int xSize, int ySize, String name) {
		super(xPos, yPos, xSize, ySize);
		this.name = name;
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
		drawControls(controlRod);
		warning.setVisible(false);
	}
	/**
	 * Draws it
	 */
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
	/**
	 * Adds all the controls to the Ui array and configures them
	 * @param controlRod
	 */
	public void drawControls(double controlRod) {//Call this once to add them to the rendering list
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
		rods.setUpInterval(0.001f);
		ui.add(rods);
		
		warning.setText(reactorErrorMessage());
		warning.setFontSize(30);
		warning.setTextDisplacement(10, 45);
		ui.add(warning);
		
		scram.setText("SCRAM");
		scram.setTextDisplacement(10, 25);
		scram.setFontSize(20);
		ui.add(scram);
		
		
		masterPump.setText("Master Pump");
		masterPump.setPercentage(0);
		masterPump.setUpInterval(0.01f);
		masterPump.setDownInterval(0.01f);
		ui.add(masterPump);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	/**
	 * Updates the controls and other things
	 * @param deltaTime The time since the last time this was called
	 */
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

		warning.setVisible(error && clicked); //if an error message has been added
		
		if (Integrator.gameover) {
			for(UIComponent comp: ui){
				comp.setVisible(false);
			}
		}
		if (UITutorial.reactorTutorialOn) {
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
			warning.setVisible(false);
		}
		
	}
	/**
	 * The same override
	 */
	public void mouseClicked(MouseEvent e) {
		if(Integrator.active() && !clicked){
			return;
		}
		//Do not do physics in mouseClicked, do it in update
		bounds.setLocation(x+Integrator.intLastXOffset, y + Integrator.intLastYOffset);
		AffineTransform g = new AffineTransform();//This code makes sure that the object was clicked
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains(e.getPoint())){
			//Hit
			clicked=true;
			
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
			warning.setVisible(false);
		} else if(clicked) {
			boolean uiClicked=false;
			for(UIComponent utemp: ui){
				if(utemp.getBounds().contains(e.getPoint())){
					uiClicked = true;
					clicked = true;
					break;
				}
			}
			if(!uiClicked){
				
				clicked=false;
				if (UITutorial.reactorTutorialOn) { //for tutorial only!!
					Integrator.reactor4.setVisible(true);
				}
				else if (UITutorial.turbineTutorialOn) { //for tutorial only!!
					Integrator.reactor4.setTurbineVisible(true);
				}
				else {
					for(UIComponent comp: ui){
						comp.setVisible(false);
					}
					warning.setVisible(false);//<solves the warning bug flashing
				}
			}
		}
	}
	/**
	 * The update function
	 */
	public void update(long deltaTime) {
		if(dead){
			fire.setActive(true);
			fire.fight();
		}
		warning.setVisible(false);
		if (scram.clicked) {
 			if(rods.getPercentage() == 1){
 				scram.setTextColor(Color.black);
 				rods.disabled = false;
 				scram.clicked=false;
 			}else{
 				scram.setTextColor(Color.red);
 				rods.up(deltaTime);
 				rods.disabled = true;
 			}
 		}
		if(!dead){
			controlRod=rods.getPercentage();
		}else{
			controlRod = 1;//temp goes down if the reactor is "dead"
		}
		if (masterPump.getActive()) {
			masterPump.disabled = false;
			if(pump!=null)
				pump.setPumpLevel(masterPump.getPercentage());
		}
		if(masterPump.getPercentage() == 0 && Integrator.level != 3){//The pumps must always be somewhat on for reactor operations
			rods.disabled = true;
			rods.up(deltaTime);
		}else{
			rods.disabled = false;
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
			if(temperature>600){//The maximum temp of a fuel rod in an RBMK reactor 600 C, so going beyond that will incur penalties
				reactorLife-=0.2;
			}
			if(steamkPa > 6900){//The drum separators maximum pressure is something like 69 bar, 6900 in kpa
				reactorLife-=0.2;
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
		if(!error)warning.setVisible(false);
		
	}
	/**
	 * The amount of steam being generated
	 * @param deltaTime Time since last time this was called in milliseconds
	 * @return Returns the double steam amount
	 */
	public double steamOutput(long deltaTime) {
		//the pressure is proportional to the amount of water boiled which is proportional to the temperature, but it must first be boiled off, which takes time ie. 
		//if the temperature drops then the pressure should drop
		float coolant = 0;
		if(pump!=null)coolant = pump.getCoolingFactor();
		//water must be at 100C to evaporate
		if (temperature >= 100) {
			steamkPa+=(temperature*0.000005*deltaTime-(coolant*0.25)); //temperature to water evaporated coefficient
		}
		//if it is less than 100C, water will condense and the pressure will go down
		else {
			//100-temp because the further the temperature form 100, the greater the decrease in pressure
			//ie. 25C is below 75C below 100C
			steamkPa-=((100-temperature)*0.00001*deltaTime+coolant);
		}
		
		if (steamkPa <= 101.3) {
			steamkPa=101.3; //cannot be less than atmospheric pressure
		}
		
		if(dead) {
			steamkPa = 101.3;
		}
		
		return steamkPa;
	}
	/**
	 * The temperature of the reactor
	 * @param deltaTime The time in milliseconds to calculate for
	 * @return Returns the string value of the temperature
	 */
	public String reactorTemp(long deltaTime) {
		double tempControl = controlRod;
		
		//the temperature is inversely proportional to the control rod depth, ie. if the control rods are 100% in then the temperature should drop
		tempControl=Math.abs(1-tempControl);
		
		//if the control rods are 75% in or more the temperature should drop
		tempControl-=0.017 * deltaTime;
		float coolant = 0.0f;
		if(pump!=null)coolant = pump.getCoolingFactor();
		temperature+=tempControl*0.063*deltaTime-coolant; //set at 0.95 for testing, final should be 0.05 //-coolingFactor;
		
		//the temperature should not be allowed to drop below 25C (room temperature of the reactors, ie. no reaction)
		if (temperature <= 25.0) {
			temperature=25.0;
		}
		
		return name+": "+roundDouble(temperature)+" C";
	}
	/**
	 * Returns an error message for the reactor
	 * @return The string error message
	 */
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
		if(masterPump.getPercentage() == 0 && Integrator.level != 3){
			error=true;
			errorMessage+="NEED PUMPS! ";
		}
		if(fire.getActive()){
			error=true;
			errorMessage+="ON FIRE! ";
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
		
		if (!fire.getActive() && temperature < 450 && steamkPa < 6900 && (masterPump.getPercentage() != 0 || Integrator.level == 3)) {
			if (!UITutorial.turnReactorYellow) //this was messing with the reactor tutorial
				reactorOutline=Color.cyan;
			error=false;
		}
		return errorMessage;
	}
	/**
	 * The amount of power generated (in some unit)
	 * @return The power generated
	 */
	public int powerGeneration() {
		double megaWatts = ((t.getSpeed()+t2.getSpeed())*0.005*(steamkPa*steamkPa));
		if(megaWatts > 500000)megaWatts = 500000;
		return (int)megaWatts;
	}
	/**
	 * Sets the pipe system between the reactor and the turbines
	 * @param s
	 */
	public void setPipeSystem(PipeSystem s){
		objects.remove(pSys);//Removes old one
		pSys = s;
		objects.add(pSys);
	}
	/**
	 * Sets the turbine of the reactor
	 * @param s The turbine
	 */
	public void setTurbine(Turbine s){
		for(GameObject tmp: s.getObj()){
			objects.remove(tmp);
		}
		t = s;
		objects.addAll(t.getObj());
	}
	/**
	 * Sets the second turbine of the reactor
	 * @param s The second turbine
	 */
	public void setTurbine2(Turbine s){
		objects.remove(t2);//Removes old one
		t2 = s;
		objects.add(t2);
	}
	/**
	 * Sets the pump system to cool the reactor
	 * @param p The pump to add
	 */
	public void setPumpSystem(PumpSystem p){
		objects.remove(p);//Removes old one
		pump = p;
		objects.add(p);
	}
	/**
	 * Gets the active state of the reactor
	 */
	public boolean getActive(){
		return clicked;
	}
	/**
	 * Whether the reactor is dead or not
	 * @return The boolean of whether the reactor is non operational
	 */
	public boolean isDead() {
		return dead;
	}
	/**
	 * Sets the component visibility
	 * @param b The boolean value of whether the controls are visible
	 */
	public void setVisible(boolean b) {
		currentTemp.setVisible(b);
		rodDepth.setVisible(b);
		pressureText.setVisible(b);
		rods.setVisible(b);
		warning.setVisible(b);
		scram.setVisible(b);
	}
	/**
	 * Sets the turbine to visible
	 * @param b Boolean visible
	 */
	public void setTurbineVisible(boolean b){
		t.setVisible(b);
	}
	/**
	 * Sets the second turbine to visible
	 * @param b Boolean visible
	 */
	public void setTurbine2Visible(boolean b){
		t2.setVisible(b);
	}
	/**
	 * For the tutorial, whether the first turbine is clicked
	 * @return Boolean clicked
	 */
	public boolean isTurbineActive() {
		return t.clicked;
	}
	/**
	 * Sets all the pumps visibility
	 * @param b Boolean visible
	 */
	public void setPumpVisible(boolean b) {
		pump.pumps.get(4).pumpLevel.setVisible(b); //set bottom left pump visible
	}												//Why??
}
