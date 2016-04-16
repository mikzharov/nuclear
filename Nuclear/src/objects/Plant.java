package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Plant extends GameObject{
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
}
