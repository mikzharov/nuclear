package objects;

import java.awt.event.MouseEvent;

import graphics.UIButton;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

//Oddly enough, it's a gameobject
public class UITutorial extends GameObject{
	
	private static int tutorialStep = 0; //10 for testing //Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	private long tutorialTime = 0;//For measuring passed time in the tutorial
	private int tutX = 0;//To test if user moved
	private int tutY = 0;//To test if user moved
	public static boolean reactorTutorialOn = false;
	public static boolean turbineTutorialOn = false;
	
	private boolean done = false;
	
	UIButton tutButton;
	UIText tutorial;
	UISlider tutSlider;

	
	public UITutorial(){
		x = Integrator.x;
		y = Integrator.y;
		tutButton  = new UIButton(x/2-150, y/2, 300, 100);
		tutorial = new UIText(10, 10, Integrator.x-30, 100);
		tutSlider = new UISlider(x/2-150, y/2, 300, 100);
		tutSlider.setText("Slider");
		tutSlider.setFontSize(55);
		tutSlider.setTextDisplacement(25, 75);
		tutSlider.setVisible(false);
		tutorial.setTextDisplacement(25, 74);
		tutorial.setMovable(false);
		tutorial.setFontSize(50);
		tutorial.setText("Welcome to Nuclear Reactor simulator (click to continue)");
		tutButton.setText("Button");
		tutButton.setVisible(false);
		tutorial.setVisible(true);
		ui.add(tutButton);
		ui.add(tutSlider);
		ui.add(tutorial);
		
	}
	public void run(){
		switch(tutorialStep){
		case 0:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Here you will learn the basics of the game (click)");
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
				tutorialStep++;
				tutorial.setText("Now click on the green circle of the leftmost reactor");
			}
			break;
		case 12:
			if (Integrator.clicked) {
				reactorTutorialOn=true;
				tutorialStep++;
				tutorial.setText("This is the control panel for a reactor (click)");
			}
			Integrator.clicked=false;
			break;
		case 13:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("It shows the reactor's temperature and pressure (click)");
			}
			Integrator.clicked=false;
			break;
		case 14:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("It also shows the depth of the control rods (click)");
			}
			Integrator.clicked=false;
			break;
		case 15:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("The control rods are controlled by a slider (click)");
			}
			Integrator.clicked=false;
			break;
		case 16:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("They can be adjusted with the (up|down) arrows (click)");
			}
			Integrator.clicked=false;
			break;
		case 17:
			if (Integrator.clicked) {
				reactorTutorialOn=false;
				tutorialStep++;
				tutorial.setText("Click on empty screen space to leave the control panel (click)");
			}
			Integrator.clicked=false;
			break;
		case 18:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on a grey turbine to bring it into focus (click)");
			}
			Integrator.clicked=false;
			break;
		case 19:
			if (Integrator.clicked) {
				turbineTutorialOn=true;
				tutorialStep++;
				tutorial.setText("This is the control panel for a turbine (click)");
			}
			Integrator.clicked=false;
			break;
		case 20:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Each reactor has two turbines to generate power (click)");
			}
			Integrator.clicked=false;
			break;
		case 21:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on the slider to adjust the turbine max speed (click)");
			}
			Integrator.clicked=false;
			break;
		case 22:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("The default speed is zero (click)");
			}
			Integrator.clicked=false;
			break;
		case 23:
			if (Integrator.clicked) {
				tutorialStep++;
				tutorial.setText("Click on the \"Spin Down\" button to stop the turbines (click)");
			}
			Integrator.clicked=false;
			break;
		}
	}
	public void mouseClicked(MouseEvent e) {
		//Do nothing
	}
	public boolean done(){
		return done;
	}
}
