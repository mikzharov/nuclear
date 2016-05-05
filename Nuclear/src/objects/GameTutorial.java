package objects;

import java.awt.Color;
import java.awt.event.MouseEvent;

import graphics.UIButton;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

//Oddly enough, it's a gameobject
public class GameTutorial extends GameObject{
	
	private static int tutorialStep = 0; //Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	private long tutorialTime = 0;//For measuring passed time in the tutorial
	private int tutX = 0;//To test if user moved
	private int tutY = 0;//To test if user moved
	private Color normal = new Color(174, 174, 207);
	
	private boolean done = false;
	
	UIText tutorial;

	
	public GameTutorial(){
		x = Integrator.x;
		y = Integrator.y;
		tutorial = new UIText(10, 10, Integrator.x-30, 100);
		tutorial.setTextDisplacement(25, 74);
		tutorial.setMovable(false);
		tutorial.setFontSize(40);
		tutorial.setText("Welcome to Nuclear Reactor simulator (click to continue)");
		
		ui.add(tutorial);
		
	}
	public void run(){
		tutorial.setVisible(true);
		switch(tutorialStep){
		case 0:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("In this game, you will learn how to operate a nuclear reactor (click)");
				Integrator.clicked = false;
			}
			break;
		case 1:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Nuclear reactors use nuclear fission to create energy (click)");
				Integrator.clicked = false;
			}
			break;
		case 2:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Nuclear fission is when a uranium atom is split in two by a neutron (click)");
				Integrator.clicked = false;
			}
			break;
		case 3:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Energy is released in this process, and it is used to boil water (click)");
				Integrator.clicked = false;
			}
			break;
		case 4:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The steam turns a turbine which creates energy! (click)");
				Integrator.clicked = false;
			}
			break;
		case 5:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Luckily for you, the fission process occurs on its own (click)");
				Integrator.clicked = false;
			}
			break;
		case 6:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Once you turn the turbines on, power will be generated (click)");
				Integrator.clicked = false;
			}
			break;
		case 7:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("As an operator, your job is to make sure nothing breaks (click)");
				Integrator.clicked = false;
			}
			break;
		case 8:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("This includes overheating, melting down, or catching on fire (click)");
				Integrator.clicked = false;
			}
			break;
		case 9:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The most important tools for this are the control rods (click)");
				Integrator.clicked = false;
			}
			break;
		case 10:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The control rods are made of dense, neutron absorbing material (click)");
				Integrator.clicked = false;
			}
			break;
		case 11:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("They slow the reaction and thus lower the temperature (click)");
				Integrator.clicked = false;
			}
			break;
		case 12:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("However, the change in pressure is proportional to the temperature (click)");
				Integrator.clicked = false;
			}
			break;
		case 13:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("As long as the temperature is above 100C the pressure will increase (click)");
				Integrator.clicked = false;
			}
			break;
		case 14:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("To prevent radtiation leaks the pressure must be below 6900 kPa (click)");
				Integrator.clicked = false;
			}
			break;
		case 15:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The pressure can be lowered by using the reactor's pumps (click)");
				Integrator.clicked = false;
			}
			break;
		case 16:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Increasing the pump level will reduce the reactor's pressure (click)");
				Integrator.clicked = false;
			}
			break;
		case 17:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Lower pressure produce less electricity, but are safer (click)");
				Integrator.clicked = false;
			}
			break;
		case 18:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Lastly, we have to watch out for the turbines overheating (click)");
				Integrator.clicked = false;
			}
			break;
		case 19:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("If the turbines spin too fast, they will catch on fire (not good) (click)");
				Integrator.clicked = false;
			}
			break;
		case 20:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("To prevent this, their max speed can be lowered (click)");
				Integrator.clicked = false;
			}
			break;
		case 21:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("So why not turn the turbines down, and pumps up? (click)");
				Integrator.clicked = false;
			}
			break;
		case 22:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("So why not turn the turbines down, and pumps up? (click)");
				Integrator.clicked = false;
			}
			break;
		case 23:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Because, we need to generate power! (click)");
				Integrator.clicked = false;
			}
			break;
		case 24:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("As an operator, you have to walk the line between safe generation (click)");
				Integrator.clicked = false;
			}
			break;
		case 25:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("and reckless behaviour. Or this will happen (click)");
				Integrator.clicked = false;
			}
			break;
		case 26:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Good Luck! (click to end tutorial)");
				Integrator.clicked = false;
			}
			break;
		case 27:
			if(Integrator.clicked){
				tutorialStep++;
				done=true;
				tutorial.setVisible(false);
				tutorial.setVisible(false);
				Integrator.clicked = false;
			}
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