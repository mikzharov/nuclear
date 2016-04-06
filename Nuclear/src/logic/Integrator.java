package logic;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.image.BufferStrategy;

import graphics.NCanvas;

public class Integrator {
	static boolean running = true;
	long last;
	public Canvas canvas;
	long start;
	BufferStrategy buffer;
	public void start(){
		
		start = System.currentTimeMillis();
		double frames = 0;
		while(running){
			last = System.currentTimeMillis();
			while(System.currentTimeMillis()-last < 17){
				//Do physics here
				
			}
			//Render here
			frames++;
			//System.out.println(frames / ((System.currentTimeMillis()-start)/1000.0));
			//(int)(frames / ((System.currentTimeMillis()-start)/1000.0))
		}
	}
	public Integrator(int x, int y){
		canvas = new Canvas();
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
	}
	public Component getPanel(){
		return canvas;
	}
}
