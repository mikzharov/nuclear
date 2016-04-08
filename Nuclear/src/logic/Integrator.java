package logic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Main;
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
	public void start(){
		Plant plant = new Plant("res/chernobyl.jpg");
		plant.setY(100);
		objects.add(plant);
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		start = System.currentTimeMillis();
		double frames = 0;
		g = (Graphics2D) buffer.getDrawGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    Main.frame.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				System.out.println("awdawda");
				if(e.getWheelRotation()>0){
					x_offset++;
				}else{
					x_offset--;
				}
			}
	    });
		while(running){
			g.setColor(Color.white);
			g.fillRect(0,0,x,y);
			g = (Graphics2D) buffer.getDrawGraphics();
			g.setRenderingHints(rh);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			last = System.currentTimeMillis();
			while((System.currentTimeMillis()-last)*10 < 166){
				//Do physics here
				
			}
			for(GameObject temp : objects){
				BufferedImage a = temp.getImage();
				g.drawImage(a, temp.getX() + x_offset, temp.getY(), temp.getImageX() + x_offset,  temp.getImageY(), 0, 0, a.getWidth(), a.getHeight(), null);
			}
			System.out.println();
			//Render here
			frames++;
			//System.out.println(frames / ((System.currentTimeMillis()-start)/1000.0));
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
