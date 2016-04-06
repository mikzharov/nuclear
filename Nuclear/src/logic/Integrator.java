package logic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Integrator {
	static boolean running = true;
	long last;
	int x;
	int y;
	public Canvas canvas;
	long start;
	BufferStrategy buffer;
	Graphics2D g;
	public void start(){
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		start = System.currentTimeMillis();
		double frames = 0;
		g = (Graphics2D) buffer.getDrawGraphics();
		
		while(running){
			g.setColor(Color.white);
			g.fillRect(0,0,x,y);
			g = (Graphics2D) buffer.getDrawGraphics();
			g.setFont(new Font("TimesRoman", Font.PLAIN, 1500)); 
			last = System.currentTimeMillis();
			while(System.currentTimeMillis()-last < 17){
				//Do physics here
				
			}
			//Render here
			frames++;
			//System.out.println(frames / ((System.currentTimeMillis()-start)/1000.0));
			g.drawString((int)(frames / ((System.currentTimeMillis()-start)/1000.0))+"", 105, 1050);
			Thread.yield();
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
