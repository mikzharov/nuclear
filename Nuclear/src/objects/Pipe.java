package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import logic.Integrator;

public class Pipe extends GameObject{
	private int speed=5;
	int increment_time = 25;
	int position = 0;
	boolean positive = true;
	public static enum Orientation{
		VERTICAL, HORIZONTAL //This will dictate whether to draw the pipe horizontally or vertically (instead of entering points)
	}
	Color color = null;
	Orientation or;
	/**
	 * This method updates the particle position on the pipe
	 */
	private void count(){//This method updates the particle position on the pipe
		position+=speed;
		if(speed > 0){
			if(position > 20)position = 0;
		}
		if(speed < 0){
			if(position < -20)position = 0;
		}
	}
	int width;
	long lastTime = 0;
	/**
	 * The constructor for the pipe
	 * @param x1 The X position
	 * @param y1 The Y position
	 * @param or The orientation
	 * @param length The length of the pipe (make it negative to make the particles flow backwards)
	 * @param width The width of the pipe
	 * @param c The color
	 */
	public Pipe(int x1, int y1, Orientation or, int length, int width, Color c){
		super(x1, y1, Math.abs(width), Math.abs(length));//This line is useless since all the variables are overwritten, however it removes errors
		color = c;
		if(length < 0){
			length *= -1;
			speed *= -1;
			positive = false;
		}
		x = x1;
		y = y1;
		if(or == Orientation.VERTICAL){//Generates x and y depending on orientation
			xSize = width;
			ySize = length;
		}else if(or == Orientation.HORIZONTAL){
			xSize = length;
			ySize = width;
		}
		this.or = or;
		this.width = width;
		bounds = new Rectangle(x, y, xSize, ySize);//Creates rectangle
		lastTime = System.currentTimeMillis();
	}
	/**
	 * Draws the pipe
	 */
	public void drawObj(Graphics2D g){
		Color oldColor = g.getColor();
		g.setColor(Color.black);
		g.fillRect(x + Integrator.intLastXOffset, y + Integrator.intLastYOffset, xSize, ySize);
		g.setColor(color);
		if(or == Orientation.HORIZONTAL){
			for(int i = x; i + position < x + xSize; i += width * 2){
				if(i + position < x)continue;//prevents particles from being drawn outside the pipe
				if(i + position > x + xSize)continue;
				g.fillRect(i + position + Integrator.intLastXOffset, y + Integrator.intLastYOffset, width, width);
			}
		}
		if(or == Orientation.VERTICAL){
			for(int i = y; i + position < y + ySize; i += width * 2){
				if(i + position < y)continue;//prevents particles from being drawn outside the pipe
				if(i + position > y + ySize)continue;
				g.fillRect(x + Integrator.intLastXOffset, i + position + Integrator.intLastYOffset, width, width);
			}
		}
		
		if(lastTime + increment_time < System.currentTimeMillis()){
			lastTime = System.currentTimeMillis();
			count();
		}
		g.setColor(oldColor);
	}
	/**
	 * Sets the color of the pipe
	 * @param color The new Color
	 */
	public void setColor(Color color){
		this.color = color;
	}
	/**
	 * Prevents pipes from being hidden by default mouseClicked code
	 */
	public void mouseClicked(MouseEvent e) {
		
	}
	/**
	 * Sets the amount of time it takes the paricles to move a bit on the pipe
	 * @param time The integer time, make it small
	 */
	public void setTime(int time){//Sets the amount of time it takes particles to move
		increment_time = Math.abs(time);//No negative time
	}
	/**
	 * Sets how far each particle travels when it moves
	 * @param speed The integer time, make it small
	 */
	public void setSpeed(int speed){//Sets how far each particle travels when it moves
		if(positive){
			this.speed = Math.abs(speed);
		}else{
			this.speed = -Math.abs(speed);
		}
	}
}
