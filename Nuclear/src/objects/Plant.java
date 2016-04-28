package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Integrator;

public class Plant extends GameObject{
	private PipeSystem sys = null;
	BufferedImage floorPlans = null;
	int x = 0;
	int y = 0;
	public Plant(String path){
		try {
			floorPlans = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public BufferedImage getImage(){
		return floorPlans;
	}
	public int getImageX(){
		return floorPlans.getWidth() + this.x;
	}
	public int getImageY(){
		return floorPlans.getHeight() + this.y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void drawObj(Graphics2D g){
		g.drawImage(floorPlans, this.getX() + (Integrator.int_last_x_offset), this.getY() + Integrator.int_last_y_offset, this.getImageX() + Integrator.int_last_x_offset,  this.getImageY() + Integrator.int_last_y_offset, 0, 0, floorPlans.getWidth(), floorPlans.getHeight(), null);
	}
	public void setPipeSystem(PipeSystem s){
		objects.remove(sys);//Removes old one
		sys = s;
		objects.add(sys);
	}
}
