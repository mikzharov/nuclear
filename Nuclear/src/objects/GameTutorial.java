package objects;

import graphics.UIButton;
import graphics.UISlider;
import graphics.UIText;
import logic.Integrator;

//Oddly enough, it's a gameobject
public class GameTutorial extends GameObject{
	
	private static int tutorialStep = 0;//Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	//private long tutorialTime = 0;//For measuring passed time in the tutorial
	UIText tutorial = new UIText(10, 10, x-30, 100);
	
	
	UIButton tutButton = new UIButton(x/2-150, y/2, 300, 100);
	

	UISlider tutSlider = new UISlider(x/2-150, y/2, 300, 100);

	
	public GameTutorial(){
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
		ui.add(tutButton);
		ui.add(tutSlider);
		ui.add(tutorial);
	}
	public void run(){
		tutorial.setVisible(true);
		switch(tutorialStep){
		case 1:
			if(Integrator.reactor4.getActive()||Integrator.reactor3.getActive()||Integrator.reactor2.getActive()||Integrator.reactor1.getActive()){
				tutorialStep++;
				Integrator.clicked = false;
				tutorial.setText("Great, notice that reactors have a control rod slider. (click)");
			}
			break;
		case 2:
			if(Integrator.clicked){
				tutorial.setText("The control rods will absorb neutrons from uranium atoms (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 3:
			if(Integrator.clicked){
				tutorial.setText("Uranium atoms release neutrons when other neutrons hit them (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 4:
			if(Integrator.clicked){
				tutorial.setText("This process is called a chain reaction (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 5:
			if(Integrator.clicked){
				tutorial.setText("When you start it, it is hard to stop (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 6:
			if(Integrator.clicked){
				tutorial.setText("To stop it, prevent neutrons from hitting the uranium (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 7:
			if(Integrator.clicked){
				tutorial.setText("You can do this with the control rods, they go into the reactor to absorb neutrons (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		case 8:
			if(Integrator.clicked){
				tutorial.setText("Which prevents them from hitting the uranium (click)");
				tutorialStep++;
				Integrator.clicked = false;//This prevents the current click from setting off the next one
			}
			break;
		}
	}
}
