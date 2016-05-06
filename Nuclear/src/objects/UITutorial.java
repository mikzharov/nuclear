package objects;

import java.awt.Color;
import java.awt.event.MouseEvent;

import graphics.UIButton;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

//Oddly enough, it's a gameobject
public class UITutorial extends GameObject{
	
	private static int tutorialStep = -1; //Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	private long tutorialTime = 0;//For measuring passed time in the tutorial
	private int tutX = 0;//To test if user moved
	private int tutY = 0;//To test if user moved
	public static boolean reactorTutorialOn = false;
	public static boolean turbineTutorialOn = false;
	public static boolean turnReactorYellow = false;
	public static boolean pumpTutorialOn = false;
	public static boolean powerTutorialOn = false;
	private Color normal = new Color(174, 174, 207);
	
	private boolean done = false;
	
	UIButton tutButton;
	UIText tutorial;
	UISlider tutSlider;
	UIButton tutOver;

	/**
	 * Constructor
	 */
	public UITutorial(){
		x = Integrator.x;
		y = Integrator.y;
		tutButton  = new UIButton(x/2-150, y/2, 300, 100);
		tutorial = new UIText(10, 10, Integrator.x-30, 100);
		tutSlider = new UISlider(x/2-150, y/2, 300, 100);
		tutOver = new UIButton(x/2-150, y/2, 300, 100);
		tutSlider.setText("Slider");
		tutSlider.setFontSize(55);
		tutSlider.setTextDisplacement(25, 75);
		tutSlider.setVisible(false);
		tutorial.setTextDisplacement(25, 74);
		tutorial.setMovable(false);
		tutorial.setFontSize(40);
		tutorial.setText("Time to learn how to use the UI! (click to continue)");
		tutButton.setText("Button");
		tutButton.setVisible(false);
		tutorial.setVisible(false);
		tutOver.setVisible(false);
		tutOver.setText("Start");
		
		ui.add(tutButton);
		ui.add(tutSlider);
		ui.add(tutorial);
		ui.add(tutOver);
		
	}
	/**
	 * Runs the tutorial, checks if the conditions are are met, etc.
	 */
	public void run(){
		switch(tutorialStep){
		case -1:
			tutorial.setVisible(true);
			tutorialStep++;
			Integrator.clicked=false;
			break;
		case 0:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Here you will learn the basics of the game controls (click)");
				Integrator.clicked = false;
			}
			break;
		case 1:
			if(Integrator.clicked){
				tutorialStep++;
				Integrator.clicked = false;
			}
			break;
		case 2:
			tutButton.setVisible(true);
			tutorial.setText("This is a button (click)");
			if(Integrator.clicked){
				tutorialStep++;
				Integrator.clicked = false;
				tutorial.setText("It can be dragged around like most components (wait)");
				if(tutorialTime == 0){
					tutorialTime = System.currentTimeMillis();
				}
				tutButton.clicked = false;
			}
			break;
		case 3:
			if(tutorialTime + 10000 < System.currentTimeMillis()){
				tutorial.setText("Try clicking it (click it)");
				tutorialTime=0;
				if(tutButton.clicked)tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			tutButton.clicked=false;
			break;
		case 4:
			tutorial.setText("Good. This is a slider (click)");
			tutButton.setVisible(false);
			tutSlider.setVisible(true);
			if(Integrator.clicked){
				tutorialStep++;
				Integrator.clicked = false;
			}
			tutSlider.active = false;
			break;
		case 5:
			if(tutSlider.active){
				tutorial.setText("Now press 'down arrow' to bring down the slider level to 0");
				tutorialStep++;
			}else{
				tutorial.setText("Click it to bring it into focus");
			}
			break;
		case 6:
			if(tutSlider.getPercentage() == 0){
				tutorial.setText("Now press 'up arrow' to bring the slider up to max");
				tutorialStep++;
			}else{
				tutorial.setText("Now press 'down arrow' to bring down the slider level to 0");
			}
			if(!tutSlider.active){
				tutorial.setText("Click it to bring it into focus");
			}
			break;
		case 7:
			if(tutSlider.getPercentage() == 1){
				tutorial.setText("Good, lets move onto reactor basics (click)");
				tutorialStep++;
			}else{
				tutorial.setText("Now press 'up arrow' to bring the slider up to max");
			}
			if(!tutSlider.active){
				tutorial.setText("Click it to bring it into focus");
			}
			Integrator.clicked = false;
			break;
		case 8:
			if(Integrator.clicked){
				Integrator.add(Integrator.plant);
				tutSlider.setVisible(false);
				tutorial.setText("This is the reactor (click)");
				tutorialStep++;
				Integrator.clicked = false;
			}
			break;
		case 9:
			if(Integrator.clicked){
				if(x < 1290)tutorial.setFontSize(40);
				tutorial.setText("It is big, so you need to move (WASD | arrows) (try moving)");
				tutorialStep++;
				Integrator.clicked = false;
				tutX = Integrator.intLastXOffset;
				tutY = Integrator.intLastYOffset;
			}
			break;
		case 10:
			if(Math.abs(Integrator.intLastXOffset - tutX) > 100 || Math.abs(Integrator.intLastYOffset - tutY) > 100){
				tutorial.setText("Also try zooming (mouse wheel)");
				tutorialStep++;
				Integrator.scale = 0.6f;
			}
			break;
		case 11:
			if(Math.abs(Integrator.scale-0.6f) > 0.2f){
				turnReactorYellow=true;
				tutorialStep++;
				tutorial.setText("Now click on the green circle of the leftmost reactor");
				Integrator.reactor4.reactorOutline = Color.yellow;
			}
			break;
		case 12:
			if (Integrator.reactor4.clicked) {
				reactorTutorialOn=true;
				turnReactorYellow=false;
				tutorialStep++;
				tutorial.setText("This is the control panel for a reactor (click)");
				Integrator.reactor4.reactorOutline = Color.cyan;
				Integrator.clicked=false;
				Integrator.reactor4.clicked=false;
			}
			break;
		case 13:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("It shows the reactor's temperature and pressure (click)");
				Integrator.reactor4.currentTemp.setColor(Color.yellow);
				Integrator.reactor4.pressureText.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 14:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("It also shows the depth of the control rods (click)");
				Integrator.reactor4.currentTemp.setColor(normal);
				Integrator.reactor4.pressureText.setColor(normal);
				Integrator.reactor4.rodDepth.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 15:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("The control rods are controlled by a slider (click)");
				Integrator.reactor4.rodDepth.setColor(normal);
				Integrator.reactor4.rods.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 16:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("They can be adjusted with the (up|down) arrows (click)");
				Integrator.clicked=false;
			}
		case 17:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Th SCRAM button automatically lowers the control rods (click)");
				Integrator.reactor4.rods.setColor(normal);
				Integrator.reactor4.scram.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 18:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("All pumps can controlled by the master pump (click)");
				Integrator.reactor4.scram.setColor(normal);
				Integrator.reactor4.masterPump.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 19:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Pumps must be on before removing the control rods (click)");
				Integrator.clicked=false;
			}
			break;
		case 20:
			if (Integrator.clicked) {
				reactorTutorialOn=false;
				tutorialStep++;
				tutorial.setText("Click on empty screen space to leave the control panel (click)");
				Integrator.reactor4.masterPump.setColor(normal);
				Integrator.clicked=false;
			}
			break;
		case 21:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on the leftmost grey turbine to bring it into focus (click)");
				Integrator.reactor4.t.turbineOutline = Color.yellow;
				Integrator.clicked=false;
			}
			break;
		case 22:
			if (Integrator.reactor4.isTurbineActive()) {
				turbineTutorialOn=true;
				tutorialStep++;
				tutorial.setText("This is the control panel for a turbine (click)");
				Integrator.reactor4.t.turbineOutline = Color.cyan;
				Integrator.clicked=false;
			}
			break;
		case 23:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Each reactor has two turbines to generate power (click)");
				Integrator.clicked=false;
			}
			break;
		case 24:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on the slider to adjust the turbine's max speed (click)");
				Integrator.reactor4.t.limit.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 25:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("The default speed is zero (click)");
				Integrator.clicked=false;
			}
			break;
		case 26:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("To stop the turbines click on the \"Spin Down\" button (click)");
				Integrator.reactor4.t.limit.setColor(normal);
				Integrator.reactor4.t.spinDown.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			
			break;
		case 27:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("H2 coolant prevents the turbine from overheating (click)");
				Integrator.reactor4.t.spinDown.setColor(normal);
				Integrator.reactor4.t.coolant.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 28:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("It is also required to start the turbine (click)");
				Integrator.clicked=false;
			}
			break;
		case 29:
			if (Integrator.clicked) {
				turbineTutorialOn=false;
				tutorialStep++;
				tutorial.setText("Click on empty screen space to leave the control panel (click)");
				Integrator.reactor4.t.coolant.setColor(normal);
				Integrator.clicked=false;
			}
			break;
		case 30:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on the bottom left circle next to the reactor (click)");
				Integrator.reactor4.pump.pumps.get(4).pumpOutline = Color.yellow;
				Integrator.clicked=false;
			}
			break;
		case 31:
			if (Integrator.reactor4.pump.pumps.get(4).getActive()) {
				pumpTutorialOn=true;
				tutorialStep++;
				tutorial.setText("Each pump can be controlled individually with a slider (click)");
				Integrator.reactor4.pump.pumps.get(4).pumpOutline = Color.cyan;
				Integrator.reactor4.pump.pumps.get(4).pumpLevel.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 32:
			if (Integrator.clicked) {
				pumpTutorialOn=false;
				tutorialStep++;
				tutorial.setText("Click on empty screen space to leave the control panel (click)");
				Integrator.reactor4.pump.pumps.get(4).pumpLevel.setColor(normal);
				Integrator.clicked=false;
			}
			break;
		case 33:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setLocation(10, Integrator.y-tutorial.getHeight()-15);
				tutorial.setText("These five panels are the plant's power output (click)");
				Integrator.powerDisplay.setVisible(true);
				Integrator.clicked=false;
			}
			break;
		case 34:
			if (Integrator.clicked) {
				Integrator.powerDisplay.setVisible(true);
				tutorialStep++;
				tutorial.setText("These four show each reactor's power output per second(click)");
				Integrator.powerDisplay.power1.setColor(Color.yellow);
				Integrator.powerDisplay.power2.setColor(Color.yellow);
				Integrator.powerDisplay.power3.setColor(Color.yellow);
				Integrator.powerDisplay.power4.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 35:
			if (Integrator.clicked) {
				tutorialStep++;
				Integrator.powerDisplay.setVisible(true);
				tutorial.setText("Reactor 4 (click)");
				Integrator.powerDisplay.power2.setColor(normal);
				Integrator.powerDisplay.power3.setColor(normal);
				Integrator.powerDisplay.power4.setColor(normal);
				Integrator.clicked=false;
			}
			break;
		case 36:
			if (Integrator.clicked) {
				tutorialStep++;
				Integrator.powerDisplay.setVisible(true);
				tutorial.setText("Reactor 3 (click)");
				Integrator.powerDisplay.power1.setColor(normal);
				Integrator.powerDisplay.power2.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 37:
			if (Integrator.clicked) {
				tutorialStep++;
				Integrator.powerDisplay.setVisible(true);
				tutorial.setText("Reactor 2 (click)");
				Integrator.powerDisplay.power2.setColor(normal);
				Integrator.powerDisplay.power3.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 38:
			if (Integrator.clicked) {
				tutorialStep++;
				Integrator.powerDisplay.setVisible(true);
				tutorial.setText("Reactor 1 (click)");
				Integrator.powerDisplay.power3.setColor(normal);
				Integrator.powerDisplay.power4.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 39:
			if (Integrator.clicked) {
				tutorialStep++;
				Integrator.powerDisplay.setVisible(true);
				tutorial.setText("This is the total power your plant has produced (click)");
				Integrator.powerDisplay.power4.setColor(normal);
				Integrator.powerDisplay.totalPower.setColor(Color.yellow);
				Integrator.clicked=false;
			}
			break;
		case 40:
			if (Integrator.clicked) {
				Integrator.powerDisplay.setVisible(true);
				powerTutorialOn=false;
				tutorialStep++;
				tutorial.setText("It will be the high score at the end of the game (click)");
				Integrator.clicked=false;
			}
			break;
		case 41:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setLocation(10, 10);
				tutorial.setText("These numbers represent the damage done to a reactor (click)");
				Integrator.powerDisplay.totalPower.setColor(normal);
				Integrator.powerDisplay.setVisible(false);
				Integrator.reactor4.lifeColor = Color.yellow;
				Integrator.reactor3.lifeColor = Color.yellow;
				Integrator.reactor2.lifeColor = Color.yellow;
				Integrator.reactor1.lifeColor = Color.yellow;
				Integrator.clicked=false;
			}
			break;
		case 42:
			if (Integrator.clicked) {
				turnReactorYellow=true;
				tutorialStep++;
				tutorial.setLocation(10, 10);
				tutorial.setText("The reactors will turn red when they are being damaged (click)");
				Integrator.reactor4.lifeColor = Color.green;
				Integrator.reactor3.lifeColor = Color.green;
				Integrator.reactor2.lifeColor = Color.green;
				Integrator.reactor1.lifeColor = Color.green;
				Integrator.reactor4.reactorOutline = Color.red;
				Integrator.reactor3.reactorOutline = Color.red;
				Integrator.reactor2.reactorOutline = Color.red;
				Integrator.reactor1.reactorOutline = Color.red;
				Integrator.clicked=false;
			}
			break;
		case 43:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setLocation(10, 10);
				tutorial.setText("When they are destroyed (value of 0.0) they will be black (click)");
				Integrator.reactor4.reactorOutline = Color.black;
				Integrator.reactor3.reactorOutline = Color.black;
				Integrator.reactor2.reactorOutline = Color.black;
				Integrator.reactor1.reactorOutline = Color.black;
				Integrator.clicked=false;
			}
			break;
		case 44:
			if (Integrator.clicked) {
				turnReactorYellow=false;
				tutorialStep++;
				tutorial.setLocation(10, 10);
				tutorial.setText("Remember, to start producing power, you must: (click)");
				Integrator.reactor4.reactorOutline = Color.cyan;
				Integrator.reactor3.reactorOutline = Color.cyan;
				Integrator.reactor2.reactorOutline = Color.cyan;
				Integrator.reactor1.reactorOutline = Color.cyan;
				Integrator.clicked=false;
			}
			break;
		case 45:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("1) Turn on the H2 coolant 2) Speed up the turbines (click)");
				Integrator.clicked=false;
			}
			break;
		case 46:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("3) Activate the pumps 4) Take out the control rods (click)");
				Integrator.clicked=false;
			}
			break;
		case 47:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Best of luck! (click)");
				tutOver.setVisible(true);
				Integrator.clicked=false;
			}
			break;
		case 48:
			if (tutOver.clicked) {
				tutorialStep++;
				tutorial.setVisible(false);
				tutOver.setVisible(false);
				Integrator.clicked=false;
				tutOver.clicked=false;
				Integrator.powerDisplay.setVisible(true);
				PowerProduction.powerProduced=0;
				Integrator.level = 2;
				done=true;
			}
			break;
		}
	}
	/**
	 * Shell method to prevent default mouseClicked activity
	 */
	public void mouseClicked(MouseEvent e) {
		//Do nothing
	}
	/**
	 * Whether the tutorial is done
	 * @return Boolean done
	 */
	public boolean done(){
		return done;
	}
}
