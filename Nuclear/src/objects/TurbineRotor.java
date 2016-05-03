package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import logic.Integrator;

public class TurbineRotor extends GameObject{
	private int speed=3;
	int increment_time = 30;
	int position = 0;

	private void count(){//This method updates the particle position on the pipe
		position+=speed;
		if(speed > 0){
			if(position > 20)position = 0;
		}
		if(speed < 0){
			if(position < -20)position = 0;
		}
	}
	int height = 5;//Height of the particle to be drawn
	long lastTime = 0;
	public TurbineRotor(int x1, int y1, int xSize1, int ySize1){
		super(x1, y1, xSize1, ySize1);
		lastTime = System.currentTimeMillis();
	}
	
	public void drawObj(Graphics2D g){
		Color oldColor = g.getColor();
		g.setColor(Color.black);
		g.fillRect(x + Integrator.intLastXOffset, y + Integrator.intLastYOffset, xSize, ySize);
		g.setColor(Color.gray);
		for(int i = y; i + position < y + ySize - height; i += height * 2){
			//if(i + position < y)continue;//prevents particles from being drawn outside the pipe
			//if(i + position > y + ySize)continue;
			g.fillRect(x + Integrator.intLastXOffset, i + position + Integrator.intLastYOffset, xSize, height);
		}
		
		if(lastTime + increment_time < System.currentTimeMillis()){
			lastTime = System.currentTimeMillis();
			count();
		}
		g.setColor(oldColor);
	}
	public void setTime(int time){//Sets the amount of time it takes particles to move
		increment_time = time;
	}
	public void setSpeed(int speed){//Sets how far each particle travels when it moves
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}
}
