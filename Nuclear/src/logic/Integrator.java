package logic;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import graphics.NCanvas;

public class Integrator {
	static boolean running = true;
	long last;
	public Canvas canvas;
	long start;
	BufferStrategy buffer;
	Graphics2D g;
	public void start(){
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		start = System.currentTimeMillis();
		g = (Graphics2D) buffer.getDrawGraphics();
		double frames = 0;
		while(running){
			last = System.currentTimeMillis();
			while(System.currentTimeMillis()-last < 17){
				//Do physics here
				
			}
			//Render here
			frames++;
			//System.out.println(frames / ((System.currentTimeMillis()-start)/1000.0));
			g.drawString((int)(frames / ((System.currentTimeMillis()-start)/1000.0))+"", 105, 105);
			Thread.yield();
			buffer.show();
		}
		g.dispose();
	}
	public Integrator(int x, int y){
		canvas = new Canvas();
	}
	public Component getPanel(){
		return canvas;
	}
}
