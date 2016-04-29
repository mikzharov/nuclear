package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import logic.Integrator;

public class Pipe extends GameObject{
	int speed=5;
	int increment_time = 25;
	int position = 0;
	public static enum Orientation{
		VERTICAL, HORIZONTAL //This will dicate whether to draw the pipe horizontally or vertically (instead of entering points)
	}
	Color color = new Color(255, 255, 0);
	Orientation or;
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
	public Pipe(int x1, int y1, Orientation or, int length, int width){
		if(length < 0){
			length *= -1;
			speed *= -1;
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
	public void setColor(Color color){
		this.color = color;
	}
	public void mouseClicked(MouseEvent e) {
		//Do not do physics in mouseClicked, do it in update
		bounds.setLocation(x+Integrator.intLastXOffset, y + Integrator.intLastYOffset);
		AffineTransform g = new AffineTransform();//This code makes sure that the object was clicked
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains((e.getX()), (e.getY()))){
			//Clicked
		}
	}
	public void setTime(int time){//Sets the amount of time it takes particles to move
		increment_time = time;
	}
	public void setSpeed(int speed){//Sets how far each particle travels when it moves
		this.speed = speed;
	}
}
