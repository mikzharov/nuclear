package logic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.*;

import objects.GameObject;
import objects.Plant;

public class Integrator {
	public static boolean running = true;//If false, the game quits
	public static boolean paused = false;
	long last;//The long which stores the time in milliseconds at the last update
	int x;//The size of the screen
	int y;//The size of the screen
	int x_offset = 0;//The horizontal offset of the gameworld (not UI though)
	int y_offset = 0;//The horizontal offset of the gameworld (not UI though)
	float scale = 1f; // The scale of the game (Hopefully not UI though)
	public Canvas canvas;//Canvas component
	long start;
	BufferStrategy buffer;//Buffer for drawing and creating graphics
	Graphics2D g;//This object will be used to draw
	ArrayList<GameObject> objects = new ArrayList<GameObject>();//This is where all the game objects will be stored
	ArrayList<UIComponent> UIComponents = new ArrayList<UIComponent>();//This is where all the game objects will be stored
	public boolean active(){
		for(UIComponent temp:UIComponents){
			if(temp.active)
				return true;
		}
		return false;
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
		scale += i;
	}
	int scroll = 40;//This controls the scroll speed
	float zoom_factor = 0.01f;//Zoom speed
	public void start(){
		
		Plant plant = new Plant("res/chernobyl.jpg");//Creates a plant
		plant.setY((int) (y/20.0));//Sets the plant location
		objects.add(plant);//Adds the plant to the world array so it can be rendered
		
		UISlider test = new UISlider(10, 10, 400, 110);
		test.setText("Test");
		canvas.addMouseListener(test.mouse);
		canvas.addMouseMotionListener(test.mouse);
		canvas.addKeyListener(test.key);
		UIComponents.add(test);
		
		UIButton test1 = new UIButton(10, 300, 400, 110);
		test1.setText("Test");
		canvas.addMouseListener(test1.mouse);
		canvas.addMouseMotionListener(test1.mouse);
		UIComponents.add(test1);
		
		
		canvas.createBufferStrategy(2);//Enables double buffering
		buffer = canvas.getBufferStrategy();//Initializes the buffer
		start = System.currentTimeMillis();//Initializes the time the program started (to display FPS)
		double frames = 0;
		g = (Graphics2D) buffer.getDrawGraphics();//Gets the graphics object
		
		RenderingHints rh = new RenderingHints(//Turns ANTIALIASING on
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.requestFocusInWindow();//Makes sure that the canvas will receive clicks
	    canvas.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {//Adds the scrolling action listener
				if(e.getWheelRotation()>0){
					zoom(zoom_factor);
				}else if(e.getWheelRotation()<0){
					zoom(-zoom_factor);
				}
			}
	    });
	    canvas.addKeyListener(new KeyListener(){//Adds the arrow action listener
			@Override
			public void keyPressed(KeyEvent e) {
				if(!paused && e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(x_offset > -3500){
						offset_x(-scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_LEFT){
					if(x_offset < 100){
						offset_x(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_UP){
					if(y_offset < 300){
						offset_y(scroll);
					}
				}
				if(!paused && e.getKeyCode() == KeyEvent.VK_DOWN){
					if(y_offset > -400){
						offset_y(-scroll);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					paused = !paused;
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
			int int_last_x_offset = (int) (x_offset * c + (1-c) * last_x_offset);//Does the linear interpolation
			int int_last_y_offset = (int) (y_offset * c + (1-c) * last_y_offset);//Does the linear interpolation
			last_x_offset = int_last_x_offset;//Updates last time for interpolation
			last_y_offset = int_last_y_offset;//Updates last time for interpolation
			old = g.getTransform();
			g.translate(x/2.0, y/2.0);
			g.scale(scale, scale);
			g.translate(-x/2.0, -y/2.0);
			for(GameObject temp : objects){
				BufferedImage a = temp.getImage();//Gets the image of the thing
				g.drawImage(a, temp.getX() + (int_last_x_offset), temp.getY() + int_last_y_offset, temp.getImageX() + int_last_x_offset,  temp.getImageY() + int_last_y_offset, 0, 0, a.getWidth(), a.getHeight(), null);//Draws the thing
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
	public Integrator(int x, int y){//A constructor which takes the screen size
		canvas = new Canvas();//Initializes the canvas
		this.x=x;
		this.y=y;
	}
}
