package logic;

import java.awt.Component;

import graphics.Canvas;

public class Integrator {
	static boolean running = true;
	long last;
	public Canvas canvas;
	long start;
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
			System.out.println("0");
			canvas.render((int)(frames / ((System.currentTimeMillis()-start)/1000.0)));
		}
	}
	public Integrator(int x, int y){
		canvas = new Canvas(x, y);
		canvas.g.drawString("awdawdawdawdawdwad", 5, 5);
	}
	public Component getPanel(){
		return canvas;
	}
}
