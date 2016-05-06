package objects;

import java.awt.event.MouseEvent;

import graphics.UIImage;
import graphics.UIText;
import logic.Integrator;

//Oddly enough, it's a gameobject
public class GameTutorial extends GameObject{
	
	private static int tutorialStep = 0; //Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	//private long tutorialTime = 0;//For measuring passed time in the tutorial
	//private int tutX = 0;//To test if user moved
	//private int tutY = 0;//To test if user moved
	//private Color normal = new Color(174, 174, 207);
	
	private boolean done = false;
	
	UIText tutorial;
	UIImage nuclearReactorIMG = new UIImage(Integrator.x/2-(1000/2), Integrator.y/2-(750/2), 1000, 750, "res/Nuclear_Power_Plant_Cattenom.jpg");
	UIImage fissionIMG = new UIImage(Integrator.x/2-(600/2), Integrator.y/2-(469/2), 600, 469, "res/u_fission.gif");
	UIImage boilingWaterIMG = new UIImage(Integrator.x/2-(600/2), Integrator.y/2-(469/2), 600, 469, "res/Magnox_reactor_schematic.svg.png");
	UIImage powerProcessIMG = new UIImage(Integrator.x/2-(580/2), Integrator.y/2-(450/2), 580, 450, "res/power_plant.gif");
	UIImage reactorfireIMG = new UIImage(Integrator.x/2-(594/2), Integrator.y/2-(378/2), 594, 378, "res/nuclear-reactor-fire.jpg");
	UIImage controlRodsIMG = new UIImage(Integrator.x/2-(350/2), Integrator.y/2-(350/2), 350, 350, "res/control-rod.jpg");
	UIImage controlRods2IMG = new UIImage(Integrator.x/2-(600/2), Integrator.y/2-(400/2), 600, 400, "res/control-rods-2.jpg");
	UIImage boilingWater2IMG = new UIImage(Integrator.x/2-(464/2), Integrator.y/2-(421/2), 464, 421, "res/pressurized-water-reactor-pwr.png");
	UIImage pressureGraphIMG = new UIImage(Integrator.x/2-(451/2), Integrator.y/2-(301/2), 451, 301, "res/boiling-graph.gif");
	UIImage pumpsIMG = new UIImage(Integrator.x/2-(1000/2), Integrator.y/2-(715/2), 1000, 715, "res/pumps.svg.png");
	UIImage turbineIMG = new UIImage(Integrator.x/2-(499/2), Integrator.y/2-(393/2), 499, 393, "res/turbine.jpg");
	UIImage turbineOnFireIMG = new UIImage(Integrator.x/2-(460/2), Integrator.y/2-(258/2), 460, 258, "res/turbine-fire.jpg");
	UIImage whyIMG = new UIImage(Integrator.x/2-(276/2), Integrator.y/2-(276/2), 276, 276, "res/Why-Pic.png");
	UIImage powerIMG = new UIImage(Integrator.x/2-(1000/2), Integrator.y/2-(750/2), 1000, 750, "res/Electric-Power-Grid.jpg");
	UIImage safeIMG = new UIImage(Integrator.x/2-(640/2), Integrator.y/2-(404/2), 640, 404, "res/safety.jpg");
	UIImage meltdownIMG = new UIImage(Integrator.x/2-(625/2), Integrator.y/2-(438/2), 625, 438, "res/nuclear-plant-fire.jpg");
	
	/**
	 * The constructor
	 */
	public GameTutorial(){
		x = Integrator.x;
		y = Integrator.y;
		tutorial = new UIText(10, 10, Integrator.x-30, 100);
		tutorial.setTextDisplacement(25, 74);
		tutorial.setMovable(false);
		tutorial.setFontSize(40);
		tutorial.setText("Welcome to Nuclear Reactor simulator (click to continue)");
		nuclearReactorIMG.setVisible(true);
		fissionIMG.setVisible(false);
		boilingWaterIMG.setVisible(false);
		powerProcessIMG.setVisible(false);
		reactorfireIMG.setVisible(false);
		controlRodsIMG.setVisible(false);
		controlRods2IMG.setVisible(false);
		boilingWater2IMG.setVisible(false);
		pressureGraphIMG.setVisible(false);
		pumpsIMG.setVisible(false);
		turbineIMG.setVisible(false);
		turbineOnFireIMG.setVisible(false);
		whyIMG.setVisible(false);
		powerIMG.setVisible(false);
		safeIMG.setVisible(false);
		meltdownIMG.setVisible(false);
		
		
		ui.add(nuclearReactorIMG);
		ui.add(fissionIMG);
		ui.add(boilingWaterIMG);
		ui.add(powerProcessIMG);
		ui.add(reactorfireIMG);
		ui.add(controlRodsIMG);
		ui.add(controlRods2IMG);
		ui.add(boilingWater2IMG);
		ui.add(pressureGraphIMG);
		ui.add(pumpsIMG);
		ui.add(turbineIMG);
		ui.add(turbineOnFireIMG);
		ui.add(whyIMG);
		ui.add(powerIMG);
		ui.add(safeIMG);
		ui.add(meltdownIMG);
		ui.add(tutorial);
	}
	/**
	 * Updates the tutorial, checks if the conditions have been met to move on to the next step, moves on to the next step
	 */
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
				nuclearReactorIMG.setVisible(false);
				fissionIMG.setVisible(true);
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
				fissionIMG.setVisible(false);
				boilingWaterIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 4:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The steam turns a turbine which creates energy! (click)");
				boilingWaterIMG.setVisible(false);
				powerProcessIMG.setVisible(true);
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
				powerProcessIMG.setVisible(false);
				reactorfireIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 9:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The most important tools for this are the control rods (click)");
				reactorfireIMG.setVisible(false);
				controlRodsIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 10:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The control rods are made of dense, neutron absorbing material (click)");
				controlRodsIMG.setVisible(false);
				controlRods2IMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 11:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("They lower the temp. If the temp. goes above 700C it will MELTDOWN (click)");
				controlRods2IMG.setVisible(false);
				boilingWater2IMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 12:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("However, the change in pressure is exponential to the temperature (click)");
				boilingWater2IMG.setVisible(false);
				pressureGraphIMG.setVisible(true);
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
				tutorial.setText("To prevent radtiation leaks the pressure MUST BE BELOW 6900 kPa (click)");
				Integrator.clicked = false;
			}
			break;
		case 15:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("The pressure can be lowered by using the reactor's pumps (click)");
				pressureGraphIMG.setVisible(false);
				pumpsIMG.setVisible(true);
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
				tutorial.setText("Lower pressures produce less electricity, but are safer (click)");
				Integrator.clicked = false;
			}
			break;
		case 18:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Lastly, we have to watch out for the turbines overheating (click)");
				pumpsIMG.setVisible(false);
				turbineIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 19:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("If the turbines spin too fast, they will catch on fire (not good) (click)");
				turbineIMG.setVisible(false);
				turbineOnFireIMG.setVisible(true);
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
				turbineOnFireIMG.setVisible(false);
				whyIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 22:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Because, we need to generate power! (click)");
				whyIMG.setVisible(false);
				powerIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 23:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("As an operator, you have to walk the line between safe generation (click)");
				powerIMG.setVisible(false);
				safeIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 24:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("and reckless behaviour. Or this will happen (click)");
				safeIMG.setVisible(false);
				meltdownIMG.setVisible(true);
				Integrator.clicked = false;
			}
			break;
		case 25:
			if(Integrator.clicked){
				tutorialStep++;
				tutorial.setText("Good Luck! (click to end tutorial)");
				meltdownIMG.setVisible(false);
				Integrator.clicked = false;
			}
			break;
		case 26:
			if(Integrator.clicked){
				tutorialStep++;
				done=true;
				tutorial.setVisible(false);
				Integrator.clicked = false;
			}
			break;
		}
	}
	/**
	 * Overrides the method so the default actions are not performed
	 */
	public void mouseClicked(MouseEvent e) {
		//Do nothing
	}
	/**
	 * Returns the status of the tutorial
	 * @return The boolean signifying whether the tutorial is done
	 */
	public boolean done(){
		return done;
	}
}