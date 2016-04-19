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
import objects.ControlRodBundle;
import objects.GameObject;
import objects.Plant;
import objects.Reactor;

public class Integrator {
	public static boolean running = true;//If false, the game quits
	public static boolean paused = false;
	long last;//The long which stores the time in milliseconds at the last update
	public static int x;//The size of the screen
	public static int y;//The size of the screen
	int x_offset = 0;//The horizontal offset of the gameworld (not UI though)
	int y_offset = 0;//The horizontal offset of the gameworld (not UI though)
	public static float scale = 0.6f; // The scale of the game (Hopefully not UI though)
	public static Canvas canvas;//Canvas component
	long start;
	BufferStrategy buffer;//Buffer for drawing and creating graphics
	Graphics2D g;//This object will be used to draw
	static ArrayList<GameObject> objects = new ArrayList<GameObject>();//This is where all the game objects will be stored
	public static ArrayList<UIComponent> UIComponents = new ArrayList<UIComponent>();//This is where all the game objects will be stored
	public boolean active(){
		for(UIComponent temp:UIComponents){
			if(temp.active)
				return true;
		}
		return false;
	}
	public static void add(UIComponent ui){
		canvas.addKeyListener(ui.key);
		canvas.addMouseListener(ui.mouse);
		canvas.addMouseMotionListener(ui.mouse);
		UIComponents.add(ui);
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
		x_offset+=i;
	}
	public void offset_y(int i){
		if(!active())
		y_offset+=i;
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
	public static int int_last_x_offset, int_last_y_offset;
	public void start(){
		
		Plant plant = new Plant("res/chernobyl.jpg");//Creates a plant
		Reactor reactor4 = new Reactor(705, 240, 150, 320, "4");
		Reactor reactor3 = new Reactor(1345, 240, 150, 320, "3");
		Reactor reactor2 = new Reactor(2330, 195, 170, 350, "2");
		Reactor reactor1 = new Reactor(3425, 195, 170, 350, "1");
		reactor4.addObjs(ControlRodBundle.makeColumn(4, 4, 744, 369));
		/*
		ControlRodBundle rod1R4 = new ControlRodBundle(), rod2R4 = new ControlRodBundle(), rod3R4 = new ControlRodBundle(), rod4R4 = new ControlRodBundle(), rod5R4 = new ControlRodBundle(), rod6R4 = new ControlRodBundle(),
				rod7R4 = new ControlRodBundle(), rod8R4 = new ControlRodBundle(), rod9R4 = new ControlRodBundle(), rod10R4 = new ControlRodBundle(), rod11R4 = new ControlRodBundle(), rod12R4 = new ControlRodBundle(),
				rod13R4 = new ControlRodBundle(), rod14R4 = new ControlRodBundle();
		
		int row1 = 745;
		int col1 = 378;
		int row2 = 725;
		int col2 = 395;
		int row3 = 725;
		int col3 = 412;
		rod1R4.setXY(row1, col1);
		rod2R4.setXY(row1+20, col1);
		rod3R4.setXY(row1+40, col1);
		rod4R4.setXY(row1+60, col1);
		rod5R4.setXY(row2, col2);
		rod6R4.setXY(row2+20, col2);
		rod7R4.setXY(row2+40, col2);
		rod8R4.setXY(row2+60, col2);
		rod9R4.setXY(row2+80, col2);
		rod10R4.setXY(row2+100, col2);
		rod11R4.setXY(row3+20, col3);
		rod12R4.setXY(row3+40, col3);
		rod13R4.setXY(row3+60, col3);
		rod14R4.setXY(row3+80, col3);
		
		reactor4.addObj(rod1R4);reactor4.addObj(rod8R4);
		reactor4.addObj(rod2R4);reactor4.addObj(rod9R4);
		reactor4.addObj(rod3R4);reactor4.addObj(rod10R4);
		reactor4.addObj(rod4R4);reactor4.addObj(rod11R4);
		reactor4.addObj(rod5R4);reactor4.addObj(rod12R4);
		reactor4.addObj(rod6R4);reactor4.addObj(rod13R4);
		reactor4.addObj(rod7R4);reactor4.addObj(rod14R4);*/
		
		
		plant.addObj(reactor1);
		plant.addObj(reactor2);
		plant.addObj(reactor3);
		plant.addObj(reactor4);
		add(plant);//Adds the plant to the world array so it can be rendered
		
		UISlider test = new UISlider(10, 10, 400, 110);
		test.setText("Test");
		canvas.addMouseListener(test.mouse);
		canvas.addMouseMotionListener(test.mouse);
		canvas.addKeyListener(test.key);
		UIComponents.add(test);
		
		
		canvas.createBufferStrategy(2);//Enables double buffering
		buffer = canvas.getBufferStrategy();//Initializes the buffer
		start = System.currentTimeMillis();//Initializes the time the program started (to display FPS)
		double frames = 0;
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
				for(GameObject temp: objects){
					temp.mouseClicked(e);
				}
			}
	    };
		canvas.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
	    canvas.addKeyListener(new KeyListener(){//Adds the arrow action listener
			@Override
			public void keyPressed(KeyEvent e) {
				if(!paused && e.getKeyCode() == KeyEvent.VK_RIGHT || !paused && e.getKeyCode() == KeyEvent.VK_D){
					if(x_offset > -3500){
						offset_x(-scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_LEFT || !paused && e.getKeyCode() == KeyEvent.VK_A){
					if(x_offset < 100){
						offset_x(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_UP || !paused && e.getKeyCode() == KeyEvent.VK_W){
					if(y_offset < 300){
						offset_y(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_DOWN || !paused && e.getKeyCode() == KeyEvent.VK_S){
					if(y_offset > -400){
						offset_y(-scroll);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					paused = !paused;
				}
				for(GameObject temp: objects){
					temp.keyPressed(e);
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}//These are required, but not used
			@Override
			public void keyTyped(KeyEvent arg0) {}//These are required, but not used
	    });
	    int last_x_offset = x_offset;//This is for interpolation purposes, initially sets the values as the same
	    int last_y_offset = x_offset;//This is for interpolation purposes, initially sets the values as the same
	    last = System.currentTimeMillis();//Sets the last time to the start time
	    long now = System.currentTimeMillis();//Sets the now time so it is not null or 0
	    long deltaTime = System.currentTimeMillis();//Sets the delta time so it is not null or 0
	    long dt = 100;//This is the time in milliseconds at which the game will process physics, 100 milliseconds at a time
		Font defaultFont = new Font("TimesRoman", Font.PLAIN, 15);
		AffineTransform old;
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
				deltaTime-=dt;//Counts down the time that needs to be processed
			}
			float c = deltaTime/(float)dt;//Calculates a time which will be used for linear interpolation
			int_last_x_offset = (int) (x_offset * c + (1-c) * last_x_offset);//Does the linear interpolation
			int_last_y_offset = (int) (y_offset * c + (1-c) * last_y_offset);//Does the linear interpolation
			last_x_offset = int_last_x_offset;//Updates last time for interpolation
			last_y_offset = int_last_y_offset;//Updates last time for interpolation
			old = g.getTransform();
			g.translate(x/2.0, y/2.0);
			g.scale(scale, scale);
			g.translate(-x/2.0, -y/2.0);
			for(GameObject temp : objects){
				temp.drawObj(g);
				g.setColor(Color.black); //individual objects that set the color will permanently change it, so we have to reset it to black
			}
			g.setTransform(old);
			for(UIComponent temp : UIComponents){
				temp.drawObj(g);//Draws the thing
			}
			//Render here
			frames++;//Increases frame count to display FPS
			test.setText("FPS: "+(int)(((frames / ((System.currentTimeMillis()-start))))*1000)+"");
			buffer.show();//Shows the picture
		}
		g.dispose();//Cleans the graphics (although this is not required)
	}
	public Integrator(int x1, int y1){//A constructor which takes the screen size
		canvas = new Canvas();//Initializes the canvas
		x=x1;
		y=y1;
	}
}
