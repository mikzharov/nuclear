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
import java.util.ArrayList;

import graphics.*;
import main.Main;
import objects.PipeSystem;
import objects.ControlRodBundle;
import objects.GameObject;
import objects.Pipe;
import objects.Pipe.Orientation;
import objects.Plant;
import objects.PowerProduction;
import objects.Reactor;
import objects.Turbine;
import objects.TurbineSystem;

public class Integrator {
	//Tutorial things
	private static boolean clicked = false;//Not strictly for the tutorial, but heavily used to check if anything at all was clicked (to proceed)
	private static int tutorialStep = 0;//Controls what the tutorial is teaching, should increase in chronological order, with previous tasks being completed successfully
	private long tutorialTime = 0;//For measuring passed time in the tutorial
	private int tutX = 0;//To test if user moved
	private int tutY = 0;//To test if user moved
	//Tutorial things
	
	private static int level = 1;//Sets the default, it is always overriden though
	public static boolean running = true;//If false, the game quits
	public static boolean paused = false;
	private long last;//The long which stores the time in milliseconds at the last update
	public static int x;//The size of the screen
	public static int y;//The size of the screen
	private int xOffset = 0;//The horizontal offset of the gameworld (not UI though)
	private int yOffset = -300;//The horizontal offset of the gameworld (not UI though), -300 to start with the image in an ideal location
	public static float scale = 0.6f; // The scale of the game (Hopefully not UI though)
	public static Canvas canvas;//Canvas component
	private BufferStrategy buffer;//Buffer for drawing and creating graphics
	private Graphics2D g;//This object will be used to draw
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();//This is where all the game objects will be stored
	private static ArrayList<UIComponent> ui = new ArrayList<UIComponent>();//This is where all the game objects will be stored
	private boolean active(){
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
	public void start(){
		running = true;
		//Making level below
		Plant plant = new Plant("res/chernobyl.jpg");//Creates a plant
		Reactor reactor4 = new Reactor(705, 240, 150, 320, "4");
		Reactor reactor3 = new Reactor(1345, 240, 150, 320, "3");
		Reactor reactor2 = new Reactor(2850, 195, 170, 350, "2");
		Reactor reactor1 = new Reactor(3945, 195, 170, 350, "1");
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
		Pipe pipe = new Pipe(840, 560, Orientation.VERTICAL, 300, 10);
		Pipe pipe1 = new Pipe(715, 560, Orientation.VERTICAL, -300, 10);
		pipe1.setColor(Color.blue);
		pipe.setColor(Color.blue);
		Pipe pipe2 = new Pipe(777, 937, Orientation.VERTICAL, 172, 10);
		Pipe pipe3 = new Pipe(200, 937, Orientation.VERTICAL, 172, 10);
		Pipe pipe4 = new Pipe(200, 937, Orientation.VERTICAL, 172, 10);
		Pipe pipe_1 = new Pipe(100, 650, Orientation.VERTICAL, -205, 10);//_1 for second pipe section
		Pipe pipe_2 = new Pipe(100, 650, Orientation.HORIZONTAL, 620, 10);
		Pipe pipe_3 = new Pipe(300, 750, Orientation.VERTICAL, 105, 10);
		Pipe pipe_4 = new Pipe(300, 750, Orientation.HORIZONTAL, -545, 10);
		Pipe mainElectric = new Pipe(200, 1100, Orientation.HORIZONTAL, 4700, 15);
		pipe_1.setColor(Color.blue);
		pipe_2.setColor(Color.blue);
		pipe_3.setColor(Color.blue);
		pipe_4.setColor(Color.blue);
		sys4.addPipe(pipe);
		sys4.addPipe(pipe1);
		sys4.addPipe(pipe2);
		sys4.addPipe(pipe3);
		sys4.addPipe(pipe4);
		sys4.addPipe(pipe_1);
		sys4.addPipe(pipe_2);
		sys4.addPipe(pipe_3);
		sys4.addPipe(pipe_4);
		sysM.addPipe(mainElectric);
		
		reactor4.setPipeSystem(sys4);
		plant.setPipeSystem(sysM);
		
		PipeSystem sys3 = new PipeSystem(); //for reactor 3
		Pipe pipe5 = new Pipe(1475, 560, Orientation.VERTICAL, 300, 10);
		Pipe pipe6 = new Pipe(1350, 560, Orientation.VERTICAL, -300, 10);
		Pipe pipe6_1 = new Pipe(1900, 788, Orientation.VERTICAL, 70, 10);
		Pipe pipe6_2 = new Pipe(1475, 788, Orientation.HORIZONTAL, 425, 10);
		Pipe pipe6_3 = new Pipe(2060, 650, Orientation.VERTICAL, -206, 10);
		Pipe pipe6_4 = new Pipe(1350, 650, Orientation.HORIZONTAL, -710, 10);
		pipe6.setColor(Color.blue);
		pipe6_1.setColor(Color.blue);
		pipe6_2.setColor(Color.blue);
		pipe6_3.setColor(Color.blue);
		pipe6_4.setColor(Color.blue);
		pipe5.setColor(Color.blue);
		//steam pipes
		Pipe pipe7 = new Pipe(1455, 937, Orientation.VERTICAL, 172, 10);
		Pipe pipe8 = new Pipe(1975, 937, Orientation.VERTICAL, 172, 10);
		
		sys3.addPipe(pipe5);
		sys3.addPipe(pipe6);
		sys3.addPipe(pipe7);
		sys3.addPipe(pipe8);
		sys3.addPipe(pipe6_1);
		sys3.addPipe(pipe6_2);
		sys3.addPipe(pipe6_3);
		sys3.addPipe(pipe6_4);
		reactor3.setPipeSystem(sys3);
		
		PipeSystem sys2 = new PipeSystem(); //for reactor 2
		//water pipes
		Pipe pipe9 = new Pipe(2980, 545, Orientation.VERTICAL, 160, 10);
		Pipe pipe10 = new Pipe(2980, 705, Orientation.HORIZONTAL, 233, 10);
		Pipe pipe11 = new Pipe(3213, 705, Orientation.VERTICAL, 145, 10);
		pipe9.setColor(Color.blue);
		pipe10.setColor(Color.blue);
		pipe11.setColor(Color.blue);
		
		Pipe pipe12 = new Pipe(2880, 545, Orientation.VERTICAL, -200, 10);
		Pipe pipe13 = new Pipe(2880, 745, Orientation.HORIZONTAL, -236, 10);
		Pipe pipe14 = new Pipe(3112, 745, Orientation.VERTICAL, -105, 10);
		pipe12.setColor(Color.blue);
		pipe13.setColor(Color.blue);
		pipe14.setColor(Color.blue);
		
		//steam pipes
		Pipe pipe15 = new Pipe(2573, 930, Orientation.VERTICAL, 172, 10);
		Pipe pipe16 = new Pipe(3163, 930, Orientation.VERTICAL, 172, 10);
		
		sys2.addPipe(pipe9);
		sys2.addPipe(pipe10);
		sys2.addPipe(pipe11);
		sys2.addPipe(pipe12);
		sys2.addPipe(pipe13);
		sys2.addPipe(pipe14);
		sys2.addPipe(pipe15);
		sys2.addPipe(pipe16);
		reactor2.setPipeSystem(sys2);
		
		PipeSystem sys1 = new PipeSystem(); //for reactor 1
		//water pipes
		Pipe pipe17 = new Pipe(4075, 545, Orientation.VERTICAL, 200, 10);
		Pipe pipe18 = new Pipe(3877, 745, Orientation.HORIZONTAL, -208, 10);
		Pipe pipe19 = new Pipe(3877, 745, Orientation.VERTICAL, 105, 10);
		pipe17.setColor(Color.blue);
		pipe18.setColor(Color.blue);
		pipe19.setColor(Color.blue);
		
		Pipe pipe20 = new Pipe(3975, 545, Orientation.VERTICAL, -160, 10);
		Pipe pipe21 = new Pipe(3774, 705, Orientation.HORIZONTAL, 211, 10);
		Pipe pipe22 = new Pipe(3774, 705, Orientation.VERTICAL, -145, 10);
		pipe20.setColor(Color.blue);
		pipe21.setColor(Color.blue);
		pipe22.setColor(Color.blue);
		
		//steam pipes
		Pipe pipe23 = new Pipe(3830, 930, Orientation.VERTICAL, 172, 10);
		Pipe pipe24 = new Pipe(4415, 930, Orientation.VERTICAL, 172, 10);
		
		sys2.addPipe(pipe17);
		sys2.addPipe(pipe18);
		sys2.addPipe(pipe19);
		sys2.addPipe(pipe20);
		sys2.addPipe(pipe21);
		sys2.addPipe(pipe22);
		sys2.addPipe(pipe23);
		sys2.addPipe(pipe24);
		reactor1.setPipeSystem(sys1);
		
		//PIPES ABOVE
		//Turbines below
		TurbineSystem tSys4 = new TurbineSystem();
		tSys4.addTurbine(new Turbine(74, 864, 39, 57));
		tSys4.addTurbine(new Turbine(122, 864, 48, 57));
		tSys4.addTurbine(new Turbine(178, 869, 35, 48));
		tSys4.addTurbine(new Turbine(219, 864, 48, 57));
		tSys4.addTurbine(new Turbine(275, 864, 45, 57));
		reactor4.setTurbineSystem(tSys4);
		//Turbines above
		if(level != 1){
			add(plant);//Adds the plant to the world array so it can be rendered
		}
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
		UIText tutorial = new UIText(10, 10, x-30, 100);
		tutorial.setTextDisplacement(25, 74);
		tutorial.setMovable(false);
		tutorial.setFontSize(50);
		tutorial.setText("Welcome to Nuclear Reactor simulator (click to continue)");
		
		UIButton tutButton = new UIButton(x/2-150, y/2, 300, 100);
		tutButton.setText("Button");
		tutButton.setVisible(false);

		UISlider tutSlider = new UISlider(x/2-150, y/2, 300, 100);
		tutSlider.setText("Slider");
		tutSlider.setFontSize(55);
		tutSlider.setTextDisplacement(25, 75);
		tutSlider.setVisible(false);
		if(level == 1){
			add(tutButton);
			add(tutSlider);
			add(tutorial);
		}
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
			if(paused){
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
			buffer.show();//Shows the picture
			if(quitButton.clicked)System.exit(0);
			if(mainButton.clicked){
				clear();
				Main.resume();
			}
			if(level == 1){
				switch(tutorialStep){
				case 0:
					if(clicked){
						tutorialStep++;
						tutorial.setText("Here you will learn the basics of the game (click)");
						clicked = false;
					}
					break;
				case 1:
					if(clicked){
						tutorialStep++;
						clicked = false;
					}
					break;
				case 2:
					tutButton.setVisible(true);
					tutorial.setText("This is a button (click)");
					if(clicked){
						tutorialStep++;
						clicked = false;
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
						clicked = false;//This prevents the current click from setting off the next one
					}
					tutButton.clicked=false;
					break;
				case 4:
					tutorial.setText("Good. This is a slider (click)");
					tutButton.setVisible(false);
					tutSlider.setVisible(true);
					if(clicked){
						tutorialStep++;
						clicked = false;
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
					clicked = false;
					break;
				case 8:
					if(clicked){
						add(plant);
						tutSlider.setVisible(false);
						tutorial.setText("This is the reactor (click)");
						tutorialStep++;
						clicked = false;
					}
					break;
				case 9:
					if(clicked){
						if(x < 1290)tutorial.setFontSize(40);
						tutorial.setText("It is big, so you need to move (WASD | arrows) (try moving)");
						tutorialStep++;
						clicked = false;
						tutX = xOffset;
						tutY = yOffset;
					}
					break;
				case 10:
					if(Math.abs(xOffset - tutX) > 100 || Math.abs(yOffset - tutY) > 100){
						tutorial.setText("Also try zooming (mouse wheel)");
						tutorialStep++;
						scale = 0.6f;
					}
					break;
				case 11:
					if(Math.abs(scale-0.6f) > 0.2f){
						tutorialStep++;
						tutorial.setText("Now click on the blue outline of a reactor to bring it into focus");
					}
					break;
				case 12:
					if(reactor4.getActive()||reactor3.getActive()||reactor2.getActive()||reactor1.getActive()){
						tutorialStep++;
						tutorial.setText("Great, notice that you have a control rod slider. (click)");
					}
					clicked = false;
					break;
				case 13:
					if(clicked){
						tutorial.setText("The control rods will absorb neutrons from uranium atoms (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 14:
					if(clicked){
						tutorial.setText("Uranium atoms release neutrons when other neutrons hit them (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 15:
					if(clicked){
						tutorial.setText("This process is called a chain reaction (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 16:
					if(clicked){
						tutorial.setText("When you start it, it is hard to stop (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 17:
					if(clicked){
						tutorial.setText("To stop it, prevent neutrons from hitting the uranium (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 18:
					if(clicked){
						tutorial.setText("You can do this with the control rods, they go into the reactor to absorb neutrons (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				case 19:
					if(clicked){
						tutorial.setText("Which prevents them from hitting the uranium (click)");
						tutorialStep++;
						clicked = false;//This prevents the current click from setting off the next one
					}
					break;
				}
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
		scale = 0.6f;
		objects.clear();
		ui.clear();
	}
	public static void setLevel(int lvl) {
		//level = lvl;
		level = 2; //for testing
		//TODO change value from testing
	}
}
