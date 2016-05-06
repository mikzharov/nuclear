package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import logic.Integrator;

public class Fire extends GameObject{
	int particles = 500;
	long lastTime = 0;
	Color red = Color.red;
	Color yellow = Color.yellow;
	Random random = new Random();
	ArrayList<Integer> posX = new ArrayList<Integer>();
	ArrayList<Integer> posY = new ArrayList<Integer>();
	private boolean active = true;
	int size = 2;
	public Fire(int x, int y, int xSize, int ySize, int size, int particles){
		//super(x, y, xSize, ySize); <---- DO NOT UNCOMMENT, CREATES INFINITE FIRE!
		this.x=x;
		this.y=y;
		this.xSize=xSize;
		this.ySize=ySize;
		bounds = new Rectangle(x, y, xSize, ySize);
		lastTime = System.currentTimeMillis();
		this.size = size;
		this.particles = particles;
	}
	public void update(long deltaTime){
		if(xSize <= 0 || ySize <= 0)return;
		if(lastTime + 250 < System.currentTimeMillis() && active){
			posX.clear();
			posY.clear();
			for(int i = 0; i < particles; i++){
				posX.add(random.nextInt(Math.abs(xSize))+x);
				posY.add(random.nextInt(Math.abs(ySize))+y);
			}
			lastTime = System.currentTimeMillis();
		}
	}
	public void drawObj(Graphics2D g){
		if(xSize <= 0 || ySize <= 0)return;
		if(active && particles <= posX.size()){
			for(int i = 0; i < particles; i ++){
				if(random.nextInt(2) == 0){
					g.setColor(red);
				}else{
					g.setColor(yellow);
				}
				g.fillRect(posX.get(i)+Integrator.intLastXOffset, posY.get(i)+Integrator.intLastYOffset, size, size);
			}
		}
	}
	public boolean getActive(){
		return active;
	}
	public void setActive(boolean b){
		active = b;
	}
	public void fight(){
		if(particles > 0 && active){
			particles --;
		}else{
			this.active = false;
		}
	}
}
