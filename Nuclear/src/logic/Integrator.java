package logic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

import graphics.*;
import main.HighScores;
import main.Main;
import objects.PipeSystem;
import objects.ControlRodBundle;
import objects.GameObject;
import objects.GameTutorial;
import objects.Pipe;
import objects.Pipe.Orientation;
import objects.Plant;
import objects.PowerProduction;
import objects.Pump;
import objects.PumpSystem;
import objects.Reactor;
import objects.TurbineRotor;
import objects.Turbine;
import objects.UITutorial;

public class Integrator {
	public static boolean clicked = false;
	public static boolean looped = false;
	private static int level = 1;//Sets the default, it is always overriden though
	public static boolean running = true;//If false, the game quits
	public static boolean paused = false;
	private long last;//The long which stores the time in milliseconds at the last update
	public static int x;//The size of the screen
	public static int y;//The size of the screen
	private int xOffset = 0;//The horizontal offset of the gameworld (not UI though)
	private int yOffset = -250;//The horizontal offset of the gameworld (not UI though), -300 to start with the image in an ideal location
	public static float scale = 0.6f; // The scale of the game (Hopefully not UI though)
	public static Canvas canvas;//Canvas component
	private BufferStrategy buffer;//Buffer for drawing and creating graphics
	private Graphics2D g;//This object will be used to draw
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();//This is where all the game objects will be stored
	private static ArrayList<UIComponent> ui = new ArrayList<UIComponent>();//This is where all the game objects will be stored
	public static boolean justDied = false; //check if a reactor just died, to check how many are left without checking every time one dies
	public static boolean gameover = false; //Why is this true?
	public static boolean saveTheScoreOnce = true;
	
	public static boolean active(){
		for(UIComponent temp:ui){
			if(temp.getActive())
				return true;
		}
		return false;
	}
	public static void add(UIComponent ui1){
		canvas.addKeyListener(ui1.key);
		canvas.addMouseListener(ui1.mouse);
		canvas.addMouseMotionListener(ui1.mouse);
		ui.add(ui1);
	}
	public static void add(GameObject ob){
		ArrayList<UIComponent> all = ob.getUi();
		for(UIComponent temp:all){
			add(temp);
		}
		objects.add(ob);
		
		ArrayList<GameObject> allObj = ob.getObj();
		allObj.remove(0);
		if(allObj.size() != 0){
			for(GameObject temp:allObj){
				add(temp);
			}
		}
	}
	public void offset_x(int i){
		if(!active())
		xOffset+=i;
	}
	public void offset_y(int i){
		if(!active())
		yOffset+=i;
	}
	public void zoom(float i){
		if(scale - i < 0){
			scale = 0;
			return;
		}
		scale -= i;
	}
	int scroll = 40;//This controls the scroll speed
	float zoom_factor = 0.01f;//Zoom speed
	public static int intLastXOffset, intLastYOffset;
	
	public static Plant plant;
	//declaring the reactors
	public static Reactor reactor4;
	public static Reactor reactor3;
	public static Reactor reactor2;
	public static Reactor reactor1;
	
	UITutorial uiTut;
	public static GameTutorial gameTut;
	public void start(){
		uiTut = new UITutorial();
		gameTut = new GameTutorial();
		if(level == 1){
			add(uiTut);
		}
		running = true;
		//Making level below
		reactor4 = new Reactor(705, 240, 150, 320, "4");
		reactor3 = new Reactor(1345, 240, 150, 320, "3");
		reactor2 = new Reactor(2850, 195, 170, 350, "2");
		reactor1 = new Reactor(3945, 195, 170, 350, "1");
		plant = new Plant("res/chernobyl.jpg");//Creates a plant
		
		//add control rods to the reactors
		reactor4.addObj(new ControlRodBundle(736, 357, 90));
		reactor3.addObj(new ControlRodBundle(1378, 357, 90));
		reactor2.addObj(new ControlRodBundle(2890, 418, 90));
		reactor1.addObj(new ControlRodBundle(3985, 418, 90));
		plant.addObj(reactor1);
		plant.addObj(reactor2);
		plant.addObj(reactor3);
		plant.addObj(reactor4);
		

		//Making the level above
		
		//PIPES BELOW
		//Pipes are just for aesthetics, do not include them in physics
		PipeSystem sys4 = new PipeSystem();//For reactor 4
		PipeSystem sysM = new PipeSystem();//For the whole plant
		Pipe pipe = new Pipe(840, 560, Orientation.VERTICAL, 300, 10, Color.blue);
		Pipe pipe1 = new Pipe(715, 560, Orientation.VERTICAL, -300, 10, Color.blue);


		Pipe pipe_1 = new Pipe(100, 650, Orientation.VERTICAL, -205, 10, Color.blue);//_1 for second pipe section
		Pipe pipe_2 = new Pipe(100, 650, Orientation.HORIZONTAL, 620, 10, Color.blue);
		Pipe pipe_3 = new Pipe(300, 750, Orientation.VERTICAL, 105, 10, Color.blue);
		Pipe pipe_4 = new Pipe(300, 750, Orientation.HORIZONTAL, -545, 10, Color.blue);
		Pipe mainElectric = new Pipe(200, 1100, Orientation.HORIZONTAL, 4700, 15, Color.yellow);

		sys4.addPipe(pipe);
		sys4.addPipe(pipe1);
		sys4.addPipe(pipe_1);
		sys4.addPipe(pipe_2);
		sys4.addPipe(pipe_3);
		sys4.addPipe(pipe_4);
		sysM.addPipe(mainElectric);
		
		reactor4.setPipeSystem(sys4);
		plant.setPipeSystem(sysM);
		
		PipeSystem sys3 = new PipeSystem(); //for reactor 3
		Pipe pipe5 = new Pipe(1475, 560, Orientation.VERTICAL, 300, 10, Color.blue);
		Pipe pipe6 = new Pipe(1350, 560, Orientation.VERTICAL, -300, 10, Color.blue);
		Pipe pipe6_1 = new Pipe(1900, 788, Orientation.VERTICAL, 70, 10, Color.blue);
		Pipe pipe6_2 = new Pipe(1475, 788, Orientation.HORIZONTAL, 425, 10, Color.blue);
		Pipe pipe6_3 = new Pipe(2060, 650, Orientation.VERTICAL, -206, 10, Color.blue);
		Pipe pipe6_4 = new Pipe(1350, 650, Orientation.HORIZONTAL, -710, 10, Color.blue);

		//steam pipes

		sys3.addPipe(pipe5);
		sys3.addPipe(pipe6);


		sys3.addPipe(pipe6_1);
		sys3.addPipe(pipe6_2);
		sys3.addPipe(pipe6_3);
		sys3.addPipe(pipe6_4);
		reactor3.setPipeSystem(sys3);
		
		PipeSystem sys2 = new PipeSystem(); //for reactor 2
		//water pipes
		Pipe pipe9 = new Pipe(2980, 545, Orientation.VERTICAL, 160, 10, Color.blue);
		Pipe pipe10 = new Pipe(2980, 705, Orientation.HORIZONTAL, 233, 10, Color.blue);
		Pipe pipe11 = new Pipe(3213, 705, Orientation.VERTICAL, 145, 10, Color.blue);

		Pipe pipe12 = new Pipe(2880, 545, Orientation.VERTICAL, -200, 10, Color.blue);
		Pipe pipe13 = new Pipe(2880, 745, Orientation.HORIZONTAL, -236, 10, Color.blue);
		Pipe pipe14 = new Pipe(3112, 745, Orientation.VERTICAL, -105, 10, Color.blue);

		
		//steam pipes


		Pipe pipe24_1 = new Pipe(2500, 705, Orientation.VERTICAL, 147, 10, Color.blue);
		Pipe pipe24_2 = new Pipe(2500, 705, Orientation.HORIZONTAL, -700, 10, Color.blue);
		Pipe pipe24_3 = new Pipe(2650, 745, Orientation.VERTICAL, -105, 10, Color.blue);
		Pipe pipe24_4 = new Pipe(2650, 745, Orientation.HORIZONTAL, 230, 10, Color.blue);
		
		
		sys2.addPipe(pipe24_1);
		sys2.addPipe(pipe24_2);
		sys2.addPipe(pipe24_3);
		sys2.addPipe(pipe24_4);
		sys2.addPipe(pipe9);
		sys2.addPipe(pipe10);
		sys2.addPipe(pipe11);
		sys2.addPipe(pipe12);
		sys2.addPipe(pipe13);
		sys2.addPipe(pipe14);

		reactor2.setPipeSystem(sys2);
		
		PipeSystem sys1 = new PipeSystem(); //for reactor 1
		//water pipes
		Pipe pipe17 = new Pipe(4075, 545, Orientation.VERTICAL, 200, 10, Color.blue);
		Pipe pipe18 = new Pipe(3877, 745, Orientation.HORIZONTAL, -208, 10, Color.blue);
		Pipe pipe19 = new Pipe(3877, 745, Orientation.VERTICAL, 105, 10, Color.blue);

		
		Pipe pipe20 = new Pipe(3985, 545, Orientation.VERTICAL, -160, 10, Color.blue);
		Pipe pipe21 = new Pipe(3774, 705, Orientation.HORIZONTAL, 211, 10, Color.blue);
		Pipe pipe22 = new Pipe(3774, 705, Orientation.VERTICAL, -145, 10, Color.blue);

		
		//steam pipes
		Pipe pipe25_1 = new Pipe(4350, 745, Orientation.VERTICAL, 105, 10, Color.blue);
		Pipe pipe25_2 = new Pipe(3975, 745, Orientation.HORIZONTAL, 380, 10, Color.blue);
		Pipe pipe25_3 = new Pipe(4500, 705, Orientation.VERTICAL, -145, 10, Color.blue);
		Pipe pipe25_4 = new Pipe(3774, 705, Orientation.HORIZONTAL, -730, 10, Color.blue);
		
		pipe25_1.setColor(Color.blue);
		pipe25_2.setColor(Color.blue);
		pipe25_3.setColor(Color.blue);
		pipe25_4.setColor(Color.blue);
		
		sys1.addPipe(pipe25_1);
		sys1.addPipe(pipe25_2);
		sys1.addPipe(pipe25_3);
		sys1.addPipe(pipe25_4);
		sys1.addPipe(pipe17);
		sys1.addPipe(pipe17);
		sys1.addPipe(pipe18);
		sys1.addPipe(pipe19);
		sys1.addPipe(pipe20);
		sys1.addPipe(pipe21);
		sys1.addPipe(pipe22);
		reactor1.setPipeSystem(sys1);
		
		//PIPES ABOVE
		//Turbines below
		//reactor 4 turbines
		Turbine tSys4 = new Turbine(65, 855, 258, 73);
		tSys4.addTurbine(new TurbineRotor(74, 864, 39, 57));
		tSys4.addTurbine(new TurbineRotor(122, 864, 48, 57));
		tSys4.addTurbine(new TurbineRotor(178, 869, 35, 48));
		tSys4.addTurbine(new TurbineRotor(219, 864, 48, 57));
		tSys4.addTurbine(new TurbineRotor(275, 864, 45, 57));
		tSys4.setPipe(new Pipe(200, 937, Orientation.VERTICAL, 172, 10, Color.yellow));
		
		Turbine tSys4_1 = new Turbine(653, 861, 260, 71);
		tSys4_1.addTurbine(new TurbineRotor(660, 869, 39, 57));
		tSys4_1.addTurbine(new TurbineRotor(708, 869, 48, 57));
		tSys4_1.addTurbine(new TurbineRotor(764, 874, 35, 48));
		tSys4_1.addTurbine(new TurbineRotor(807, 869, 48, 57));
		tSys4_1.addTurbine(new TurbineRotor(861, 869, 45, 57));
		tSys4_1.setPipe(new Pipe(777, 937, Orientation.VERTICAL, 172, 10, Color.yellow));
		reactor4.setTurbine2(tSys4_1);
		reactor4.setTurbine(tSys4);
		
		//reactor 3 turbines
		Turbine tSys3 = new Turbine(1323, 860, 371, 72);
		tSys3.addTurbine(new TurbineRotor(1345, 865, 38, 59));
		tSys3.addTurbine(new TurbineRotor(1391, 865, 38, 59));
		tSys3.addTurbine(new TurbineRotor(1445, 870, 31, 48));
		tSys3.addTurbine(new TurbineRotor(1486, 865, 38, 59));
		tSys3.addTurbine(new TurbineRotor(1535, 865, 38, 59));
		tSys3.setPipe(new Pipe(1455, 937, Orientation.VERTICAL, 172, 10, Color.yellow));
		
		Turbine tSys3_1 = new Turbine(1845, 860, 371, 68);
		tSys3_1.addTurbine(new TurbineRotor(1867, 865, 38, 59));
		tSys3_1.addTurbine(new TurbineRotor(1913, 865, 38, 59));
		tSys3_1.addTurbine(new TurbineRotor(1964, 870, 31, 48));
		tSys3_1.addTurbine(new TurbineRotor(2007, 865, 38, 59));
		tSys3_1.addTurbine(new TurbineRotor(2056, 865, 38, 59));
		tSys3_1.setPipe(new Pipe(1975, 937, Orientation.VERTICAL, 172, 10, Color.yellow));
		reactor3.setTurbine(tSys3_1);
		reactor3.setTurbine2(tSys3);
		
		//reactor 2 turbines
		Turbine tSys2 = new Turbine(2451, 852, 397, 75);
		tSys2.addTurbine(new TurbineRotor(2459, 860, 39, 57));
		tSys2.addTurbine(new TurbineRotor(2459+48, 860, 48, 57));
		tSys2.addTurbine(new TurbineRotor(2459+104, 865, 35, 48));
		tSys2.addTurbine(new TurbineRotor(2459+145, 860, 48, 57));
		tSys2.addTurbine(new TurbineRotor(2459+201, 860, 45, 57));
		tSys2.setPipe(new Pipe(2573, 930, Orientation.VERTICAL, 172, 10, Color.yellow));
		
		Turbine tSys2_1 = new Turbine(3040, 851, 447, 75);
		tSys2_1.addTurbine(new TurbineRotor(3049, 860, 39, 57));
		tSys2_1.addTurbine(new TurbineRotor(3049+48, 860, 48, 57));
		tSys2_1.addTurbine(new TurbineRotor(3049+104, 865, 35, 48));
		tSys2_1.addTurbine(new TurbineRotor(3049+145, 860, 48, 57));
		tSys2_1.addTurbine(new TurbineRotor(3049+201, 860, 45, 57));
		tSys2_1.setPipe(new Pipe(3163, 930, Orientation.VERTICAL, 172, 10, Color.yellow));
		reactor2.setTurbine2(tSys2_1);
		reactor2.setTurbine(tSys2);
		
		//reactor 1 turbines
		Turbine tSys1 = new Turbine(3706, 851, 393, 75);
		tSys1.addTurbine(new TurbineRotor(3714, 860, 39, 57));
		tSys1.addTurbine(new TurbineRotor(3714+48, 860, 48, 57));
		tSys1.addTurbine(new TurbineRotor(3714+104, 865, 35, 48));
		tSys1.addTurbine(new TurbineRotor(3714+145, 860, 48, 57));
		tSys1.addTurbine(new TurbineRotor(3714+201, 860, 45, 57));
		tSys1.setPipe(new Pipe(3830, 930, Orientation.VERTICAL, 172, 10, Color.yellow));
		Turbine tSys1_1 = new Turbine(4291, 851, 393, 75);
		tSys1_1.addTurbine(new TurbineRotor(4298, 860, 39, 57));
		tSys1_1.addTurbine(new TurbineRotor(4298+48, 860, 48, 57));
		tSys1_1.addTurbine(new TurbineRotor(4298+104, 865, 35, 48));
		tSys1_1.addTurbine(new TurbineRotor(4298+145, 860, 48, 57));
		tSys1_1.addTurbine(new TurbineRotor(4298+201, 860, 45, 57));
		tSys1_1.setPipe(new Pipe(4415, 930, Orientation.VERTICAL, 172, 10, Color.yellow));
		reactor1.setTurbine2(tSys1_1);
		reactor1.setTurbine(tSys1);
		//Turbines above
		//Pumps below
		PumpSystem r3 = new PumpSystem();
		r3.addPump(new Pump(1368, 207, 40));
		reactor3.setPumpSystem(r3);
		//Pumps above
		if(level != 1){
			add(plant);//Adds the plant to the world array so it can be rendered
		}
		//Making end game GUI
		//game over page
		UIText endTitle = new UIText(x/4, y/5, x/2, UIComponent.defaultHeight);
		UIText yourScore = new UIText(endTitle.getX(), y/5+UIComponent.defaultHeight+10, x/2, UIComponent.defaultHeight);
		UIButton saveScore = new UIButton(x/4, y/5+UIComponent.defaultHeight*2+20, x/2, UIComponent.defaultHeight);
		
		yourScore.setVisible(false);
		saveScore.setVisible(false);
		endTitle.setVisible(false);
		
		endTitle.setText("GAME OVER");
		endTitle.setTextDisplacement(endTitle.getX()/2-40, 95);
		yourScore.setText("Your score: "+PowerProduction.powerProduced+" kW");
		yourScore.setFontSize(50);
		yourScore.setTextDisplacement(45, 90);
		saveScore.setText("Save score and continue...");
		saveScore.setFontSize(50);
		saveScore.setTextDisplacement(45, 90);
		saveScore.setUsableDuringPaused(true);
		
		endTitle.setVisible(false);
		yourScore.setVisible(false);
		saveScore.setVisible(false);
		
		add(endTitle);
		add(yourScore);
		add(saveScore);
		
		//high score page
		UIScoreSheet topFiveScores = new UIScoreSheet(endTitle.getX(), endTitle.getY()+UIComponent.defaultHeight+10, x/2, 400);
		UIButton backToMain = new UIButton(endTitle.getX(), topFiveScores.getY()+topFiveScores.getHeight()+10, x/4-15, 100);
		UIButton quitGame = new UIButton(endTitle.getX()+backToMain.getX()+15, topFiveScores.getY()+topFiveScores.getHeight()+10, x/4-15, 100);

		topFiveScores.setVisible(false);
		backToMain.setVisible(false);
		quitGame.setVisible(false);
		
		topFiveScores.setFontSize(40);
		backToMain.setText("Main Menu");
		backToMain.setFontSize(40);
		backToMain.setTextDisplacement(80, 90);
		backToMain.setUsableDuringPaused(true);
		quitGame.setText("Quit Game");
		quitGame.setFontSize(40);
		quitGame.setTextDisplacement(80, 90);
		quitGame.setUsableDuringPaused(true);
		
		add(topFiveScores);
		add(backToMain);
		add(quitGame);
		
		//end game file i/o
		HighScores highscore = new HighScores();
		
		//Making paused GUI below
		UIText pauseText = new UIText(x/2-x/8, y/5, x/4, UIComponent.defaultHeight);
		pauseText.setText("Paused");
		pauseText.setVisible(false);
		
		
		UIButton quitButton = new UIButton(x/2-x/8, y/5+UIComponent.defaultHeight+10, x/4, UIComponent.defaultHeight);
		quitButton.setText("Quit");
		quitButton.setVisible(false);
		quitButton.setUsableDuringPaused(true);
		
		
		UIButton mainButton = new UIButton(x/2-x/6-25, y/5+UIComponent.defaultHeight*2+20, x/3+40, UIComponent.defaultHeight);
		mainButton.setText("Main Menu");
		mainButton.setVisible(false);
		mainButton.setUsableDuringPaused(true);
		
		//Making paused GUI above
		
		//UI below
		PowerProduction powerDisplay = new PowerProduction(10, 10);
		
		if(level != 1){
			add(powerDisplay);
		}
	
		//Tutorial UI below
		
		//Tutorial UI above
		//UI above
		
		quitButton.setMovable(false);
		mainButton.setMovable(false);
		add(pauseText);
		add(quitButton);
		add(mainButton);
		canvas.createBufferStrategy(2);//Enables double buffering
		buffer = canvas.getBufferStrategy();//Initializes the buffer
		g = (Graphics2D) buffer.getDrawGraphics();//Gets the graphics object
		
		RenderingHints rh = new RenderingHints(//Turns ANTIALIASING on
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.requestFocusInWindow();//Makes sure that the canvas will receive clicks
		MouseAdapter mouse = new MouseAdapter(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {//Adds the scrolling action listener
				if(e.getWheelRotation()>0){
					zoom(zoom_factor);
				}else if(e.getWheelRotation()<0){
					zoom(-zoom_factor);
				}
			}
			public void mouseClicked(MouseEvent e){
				clicked = true;
				if(!paused){
					for(GameObject temp: objects){
						temp.mouseClicked(e);
					}
				}
			}
	    };
		canvas.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
	    canvas.addKeyListener(new KeyListener(){//Adds the arrow action listener
			@Override
			public void keyPressed(KeyEvent e) {
				if(!paused && e.getKeyCode() == KeyEvent.VK_RIGHT || !paused && e.getKeyCode() == KeyEvent.VK_D){
					if(xOffset > -3500){
						offset_x(-scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_LEFT || !paused && e.getKeyCode() == KeyEvent.VK_A){
					if(xOffset < 100){
						offset_x(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_UP || !paused && e.getKeyCode() == KeyEvent.VK_W){
					if(yOffset < 300){
						offset_y(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_DOWN || !paused && e.getKeyCode() == KeyEvent.VK_S){
					if(yOffset > -400){
						offset_y(-scroll);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					paused = !paused;
				}
				if(!paused){
					for(GameObject temp: objects){
						temp.keyPressed(e);
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}//These are required, but not used
			@Override
			public void keyTyped(KeyEvent arg0) {}//These are required, but not used
	    });
	    int lastXOffset = xOffset;//This is for interpolation purposes, initially sets the values as the same
	    int lastYOffset = xOffset;//This is for interpolation purposes, initially sets the values as the same
	    last = System.currentTimeMillis();//Sets the last time to the start time
	    long now = System.currentTimeMillis();//Sets the now time so it is not null or 0
	    long deltaTime = System.currentTimeMillis();//Sets the delta time so it is not null or 0
	    long dt = 10;//This is the time in milliseconds at which the game will process physics, 100 milliseconds at a time
		Font defaultFont = new Font("TimesRoman", Font.PLAIN, 15);
		AffineTransform old;
		float c = 0;
		
	    while(running){
	    	looped = true;
			g.setColor(Color.white);//Clears the screen
			g.fillRect(0,0,x,y);
			g = (Graphics2D) buffer.getDrawGraphics();
			g.setRenderingHints(rh);
			g.setFont(defaultFont);//Sets font
			now = System.currentTimeMillis();//Sets now
			deltaTime += now - last;//Calculates time since last update
			last = System.currentTimeMillis();//Updates the last time
			if(deltaTime>25)deltaTime=25;//Makes sure that the physics does not try to do too much
			while(!paused && (deltaTime) >= dt){//As long as we still need to do physics updates, do physics updates
				//Do physics here
				for(GameObject temp: objects){
					temp.update(deltaTime);
				}
				//DO NOT RENDER ANYWHERE IN THIS WHILE LOOP
				
				deltaTime-=dt;//Counts down the time that needs to be processed
				looped = false;
			}
			//ONLY RENDER PAST THIS POINT
			old = g.getTransform();
			
			if(!paused){
				c = deltaTime/(float)dt;//Calculates a time which will be used for linear interpolation
			}
			intLastXOffset = (int) (xOffset * c + (1-c) * lastXOffset);//Does the linear interpolation
			intLastYOffset = (int) (yOffset * c + (1-c) * lastYOffset);//Does the linear interpolation
			lastXOffset = intLastXOffset;//Updates last time for interpolation
			lastYOffset = intLastYOffset;//Updates last time for interpolation
			g.translate(x/2.0, y/2.0);
			g.scale(scale, scale);
			g.translate(-x/2.0, -y/2.0);
			
			for(GameObject temp : objects){
				temp.drawObj(g);
				g.setColor(Color.black); //individual objects that set the color will permanently change it, so we have to reset it to black
			}
			g.setTransform(old);
			if(!paused)
				powerDisplay.updatePower(reactor1.powerGeneration(), reactor2.powerGeneration(), reactor3.powerGeneration(), reactor4.powerGeneration());
			
			if (justDied) { //only check after one dies, reduces processing
				if (reactor1.isDead() && reactor2.isDead() && reactor3.isDead() && reactor4.isDead()) { //check if all reactors are dead
					paused=true;
					gameover=true;
				}
				else {
					justDied=false;
				}
			}
			if (gameover) {
				if (saveTheScoreOnce) { //only do this once
					endTitle.setVisible(true);
					yourScore.setText("Your score: "+PowerProduction.powerProduced+" kW");
					yourScore.setVisible(true);
					saveScore.setVisible(true);
					powerDisplay.hide();
					reactor1.hideControls();
					reactor2.hideControls();
					reactor3.hideControls();
					reactor4.hideControls();
					tSys4.hide();
					tSys4_1.hide();
					tSys3.hide();
					tSys3_1.hide();
					tSys2.hide();
					tSys2_1.hide();
					tSys1.hide();
					tSys1_1.hide();
				}
			}
			if (saveScore.clicked) {
				saveScore.clicked = false;
				if (saveTheScoreOnce) { //only do this once
					try {
						highscore.read();
						highscore.write(Main.playerName, String.valueOf(PowerProduction.powerProduced));
					} catch (IOException e1) {
						e1.printStackTrace(); //for testing only
					}
					saveTheScoreOnce=false;
				}
				endTitle.setText("High Scores");
				endTitle.setTextDisplacement(endTitle.getX()/2-60, 95);
				topFiveScores.setVisible(true);
				backToMain.setVisible(true);
				quitGame.setVisible(true);
				yourScore.setVisible(false);
				saveScore.setVisible(false);
			}
			if (backToMain.clicked) {
				backToMain.clicked=false;
				clear();
				Main.resume();
			}
			if (quitGame.clicked)
				System.exit(0);
			
 			if(paused && !gameover){
				pauseText.setVisible(true);
				quitButton.setVisible(true);
				mainButton.setVisible(true);
			}else{
				quitButton.setVisible(false);
				pauseText.setVisible(false);
				mainButton.setVisible(false);
			}
			for(UIComponent temp : ui){
				if(temp.getVisible())
				temp.drawObj(g);//Draws the thing
			}
			if(level == 1){
				if(!uiTut.done()){
					uiTut.run();
				}else{
					gameTut.run();
				}
			}
			buffer.show();//Shows the picture
			if(quitButton.clicked)System.exit(0);
			if(mainButton.clicked){
				clear();
				Main.resume();
			}
		}
		g.dispose();//Cleans the graphics (although this is not required)
	}
	public Integrator(int x1, int y1){//A constructor which takes the screen size
		canvas = new Canvas();//Initializes the canvas
		x=x1;
		y=y1;
	}
	public static void clear(){
		running = false;
		paused = false;
		gameover = false;
		saveTheScoreOnce = true;
		HighScores.namesAndScores.clear();
		HighScores.namesAndScoresSorted.clear();
		PowerProduction.powerProduced = 0;
		scale = 0.6f;
		objects.clear();
		ui.clear();
	}
	public static void setLevel(int lvl) {
		//level = lvl;
		level = 2; //for testing
	}
}
