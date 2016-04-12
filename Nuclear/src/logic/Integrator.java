package logic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.GameObject;
import objects.Plant;

public class Integrator {
	static boolean running = true;
	long last;
	int x;
	int y;
	int x_offset = 0;
	public Canvas canvas;
	long start;
	BufferStrategy buffer;
	Graphics2D g;
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public void offset(int i){
		x_offset+=i;
	}
	int scroll = 10;
	public synchronized void start(){

		Plant plant = new Plant("res/chernobyl.jpg");
		plant.setY((int) (y/20.0));
		objects.add(plant);
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		start = System.currentTimeMillis();
		double frames = 0;
		g = (Graphics2D) buffer.getDrawGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.requestFocusInWindow();
	    canvas.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0){
					offset(scroll);
				}else if(e.getWheelRotation()<0){
					offset(-scroll);
				}
			}
	    });
	    canvas.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					offset(-scroll);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					offset(scroll);
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
	    });
	    int last_x_offset = x_offset;
	    last = System.currentTimeMillis();
	    long now = System.currentTimeMillis();
	    long deltaTime = System.currentTimeMillis();
	    long dt = 100;
		while(running){
			
			g.setColor(Color.white);
			g.fillRect(0,0,x,y);
			g = (Graphics2D) buffer.getDrawGraphics();
			g.setRenderingHints(rh);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			now = System.currentTimeMillis();
			deltaTime += now - last;
			last = System.currentTimeMillis();
			if(deltaTime>25)deltaTime=25;
			while((deltaTime) >= dt){
				//Do physics here
				deltaTime-=dt;
			}
			float c = deltaTime/(float)dt;
			int int_last_x_offset = (int) (x_offset * c + (1-c) * last_x_offset);
			last_x_offset = int_last_x_offset;
			for(GameObject temp : objects){
				BufferedImage a = temp.getImage();
				g.drawImage(a, temp.getX() + (int_last_x_offset), temp.getY(), temp.getImageX() + int_last_x_offset,  temp.getImageY(), 0, 0, a.getWidth(), a.getHeight(), null);
			}
			//Render here
			frames++;
			g.drawString((int)(((frames / ((System.currentTimeMillis()-start))))*1000)+"", 50, 50);
			buffer.show();
		}
		g.dispose();
	}
	public Integrator(int x, int y){
		canvas = new Canvas();
		this.x=x;
		this.y=y;
	}
	public Component getPanel(){
		return canvas;
	}
}
